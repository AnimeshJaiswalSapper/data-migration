package ai.sapper.migration.DataMigration.service;

import ai.sapper.migration.DataMigration.Repository.mongo.DataMigrationRepository;
import ai.sapper.migration.DataMigration.model.mongo.DataMigration;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class DataMigrationService {

    private static final List<String> models = List.of(
            "Case", "CaseDocumentDO", "COALabel", "COA", "CaseMerge",
            "SapperRule", "Status", "AuditEntity", "AuditSnapshot",
            "AuditSnapshotOriginal", "Config"
    );

    @Value("${class.path}")
    private String modelClassPaths;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private DataMigrationRepository dataMigrationRepository;

    @PostConstruct
    public void start() {
        log.info("---------STARTED DATA MIGRATION------");
        models.forEach(this::processModel);
    }

    private void processModel(String collection) {
        String classPath = modelClassPaths + "." + collection;
        try {
            Object serviceObj = applicationContext.getBean(Class.forName(classPath));

            DataMigration dataMigration = dataMigrationRepository.findByCollectionName(collection);

            Date lastProcessedDate = dataMigration != null ? dataMigration.getLastProcessedDate() : null;
            String lastProcessedId = dataMigration != null ? dataMigration.getLastProcessedId() : null;

            List<Object> documents = fetchDocuments(serviceObj, lastProcessedDate, lastProcessedId);

            if (documents.isEmpty()) {
                log.info("No Data found in Collection : {}", collection);
                return;
            }

            Object lastDocument = documents.get(documents.size() - 1);
            String processedId = extractField(lastDocument, "id");
            Date processedDate = extractDateField(lastDocument);

            if(dataMigration!=null)
                updateDataMigration(processedId,processedDate,dataMigration);
            else
                saveDataMigration(collection,processedId,processedDate);

        } catch (Exception e) {
            log.error("Error processing model {}: {}", collection, e.getMessage(), e);
        }
    }

    private List<Object> fetchDocuments(Object serviceObj, Date lastProcessedDate, String lastProcessedId) throws Exception {
        Method readMethod = serviceObj.getClass().getMethod("read", Date.class, String.class);
        return (List<Object>) readMethod.invoke(serviceObj, lastProcessedDate, lastProcessedId);
    }

    private void updateDataMigration(String processedId, Date processedDate, DataMigration existingDataMigration) throws Exception {
        existingDataMigration.setLastProcessedId(processedId);
        existingDataMigration.setLastProcessedDate(processedDate);
//        implemented after write
//        existingDataMigration.setFailedIds(new ArrayList<>());
        dataMigrationRepository.save(existingDataMigration);
    }

    private void saveDataMigration(String collecion, String processedId,Date processedDate) throws Exception {
        DataMigration dataMigration = new DataMigration();
        dataMigration.setCollectionName(collecion);
        dataMigration.setLastProcessedId(processedId);
        dataMigration.setFailedIds(new ArrayList<>());
        dataMigration.setLastProcessedDate(processedDate);
        dataMigrationRepository.save(dataMigration);
    }

    private String extractField(Object obj, String fieldName) throws Exception {
        Field field = obj.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return (String) field.get(obj);
    }

    private Date extractDateField(Object obj) throws Exception {
        Optional<Field> dateField = findDateField(obj);
        if (dateField.isPresent()) {
            Field field = dateField.get();
            field.setAccessible(true);
            return (Date) field.get(obj);
        }
        return null;
    }

    private Optional<Field> findDateField(Object obj) {
        Class<?> clazz = obj.getClass();

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
