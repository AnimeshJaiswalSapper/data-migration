package ai.sapper.migration.DataMigration.service;

import ai.sapper.migration.DataMigration.Repository.mongo.DataMigrationRepository;
import ai.sapper.migration.DataMigration.Repository.postgres.PostgresRepository;
import ai.sapper.migration.DataMigration.model.mongo.DataMigration;
import ai.sapper.migration.DataMigration.model.postgres.CaseDocumentDO;
import ai.sapper.migration.DataMigration.s3migration.service.S3migrationService;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

@Component
@Slf4j
public class DataMigrationService {

    private static final List<String> MODELS = List.of("AuditEntity", "AuditSnapshot", "AuditSnapshotOriginal", "Case", "CaseMerge", "CaseDocumentDO", "COA", "COALabel", "Config", "Entity", "SapperRule", "Status", "RuleRuntimeData");

    @Value("${mongo.class.path}")
    private String mongoModelClassPath;

    @Value("${postgres.class.path}")
    private String postgresModelClassPath;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private DataMigrationRepository dataMigrationRepository;

    @Autowired
    private DocumentService documentService;

    @Autowired
    private S3migrationService s3migrationService;

    @Value("${db.migration:false}")
    private Boolean dbMigration;

    @Value("${s3.migration:true}")
    private Boolean s3migration;

    @PostConstruct
    public void start() {
        log.info("---------STARTED DATA MIGRATION------");
        if (dbMigration)
            MODELS.forEach(this::processModel);
        if (s3migration)
            s3migrationService.s3migration();
    }

    private void processModel(String collection) {
        try {
            Class<?> mongoModelClass = loadClass(mongoModelClassPath, collection);
            Class<?> postgresModelClass = loadClass(postgresModelClassPath, collection);

            Object mongoModelObj = applicationContext.getBean(mongoModelClass);
            Object postgresModelObj = applicationContext.getBean(postgresModelClass);

            DataMigration dataMigration = dataMigrationRepository.findByCollectionName(collection);
            Date lastProcessedDate = dataMigration != null ? dataMigration.getLastProcessedDate() : null;
            String lastProcessedId = dataMigration != null ? dataMigration.getLastProcessedId() : null;

            List<Object> fetchedDocuments = fetchDocuments(mongoModelObj, lastProcessedDate, lastProcessedId);

            if (fetchedDocuments.isEmpty()) {
                log.info("No Data found for Collection: {}", collection);
                return;
            }

            List<Object> failedDocs = new ArrayList<>();
            documentService.saveDocuments(postgresModelObj, fetchedDocuments,failedDocs,collection);

            updateOrSaveDataMigration(collection, fetchedDocuments, dataMigration,failedDocs);
        } catch (Exception e) {
            log.error("Error processing model {}: {}", collection, e.getMessage(), e);
        }
    }

    private Class<?> loadClass(String classPath, String collection) throws ClassNotFoundException {
        return Class.forName(classPath + "." + collection);
    }

    private List<Object> fetchDocuments(Object mongoModelObj, Date lastProcessedDate, String lastProcessedId) throws Exception {
        Method readMethod = mongoModelObj.getClass().getMethod("read", Date.class, String.class);
        return (List<Object>) readMethod.invoke(mongoModelObj, lastProcessedDate, lastProcessedId);
    }

    private void updateOrSaveDataMigration(String collection, List<Object> fetchedDocuments, DataMigration dataMigration, List<Object> failedDocs) {
        Object lastDocument = fetchedDocuments.get(fetchedDocuments.size() - 1);
        try {
            String processedId = extractField(lastDocument, "id");
            Date processedDate = extractDateField(lastDocument, collection);

            if (dataMigration != null) {
                updateDataMigration(processedId, processedDate, failedDocs, dataMigration);
            } else {
                saveDataMigration(collection, processedId, failedDocs, processedDate);
            }
        } catch (Exception e) {
            log.error("Error updating/saving DataMigration for collection {}: {}", collection, e.getMessage(), e);
        }
    }

    private void updateDataMigration(String processedId, Date processedDate, List<Object> failedDocs, DataMigration existingDataMigration) {
        existingDataMigration.setLastProcessedId(processedId);
        existingDataMigration.setLastProcessedDate(processedDate);
        existingDataMigration.getFailedDocs().addAll(failedDocs);
        dataMigrationRepository.save(existingDataMigration);
    }

    private void saveDataMigration(String collection, String processedId, List<Object> failedDocs, Date processedDate) {
        DataMigration dataMigration = new DataMigration();
        dataMigration.setCollectionName(collection);
        dataMigration.setLastProcessedId(processedId);
        dataMigration.setLastProcessedDate(processedDate);
        dataMigration.setFailedDocs(failedDocs);
        dataMigrationRepository.save(dataMigration);
    }

    private String extractField(Object obj, String fieldName) throws Exception {
        Field field = obj.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return (String) field.get(obj);
    }

    private Date extractDateField(Object obj, String collection) throws Exception {
        Optional<Field> dateField = findDateField(obj, collection);
        if (dateField.isPresent()) {
            Field field = dateField.get();
            field.setAccessible(true);
            return (Date) field.get(obj);
        }
        return null;
    }

    private Optional<Field> findDateField(Object obj, String collection) {
        Class<?> clazz = obj.getClass();

        if (collection.equals("Config") || collection.equals("Entity")) {
            Field field = findFieldInHierarchy(clazz, "lastModifiedDate");
            if (field != null) {
                field.setAccessible(true);
                return Optional.of(field);
            } else {
                log.warn("lastModified date field not found in class hierarchy: {}", clazz.getName());
                return Optional.empty();
            }
        }

        Field field = findFieldInHierarchy(clazz, "createdDate");
        if (field == null) {
            field = findFieldInHierarchy(clazz, "createdAt");
        }

        if (field != null) {
            field.setAccessible(true);
            return Optional.of(field);
        } else {
            log.warn("Neither 'createdDate' nor 'createdAt' fields found in class hierarchy: {}", clazz.getName());
            return Optional.empty();
        }
    }

    private Field findFieldInHierarchy(Class<?> clazz, String fieldName) {
        while (clazz != null) {
            try {
                return clazz.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                clazz = clazz.getSuperclass();
            }
        }
        return null;
    }
}
