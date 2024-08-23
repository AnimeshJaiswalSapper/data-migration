package ai.sapper.migration.DataMigration.service;

import ai.sapper.migration.DataMigration.Repository.mongo.DataMigrationRepository;
import ai.sapper.migration.DataMigration.Repository.postgres.PostgresRepository;
import ai.sapper.migration.DataMigration.model.mongo.DataMigration;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
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

    private static final List<String> MODELS = List.of("COA");

    @Value("${mongo.class.path}")
    private String mongoModelClassPath;

    @Value("${postgres.class.path}")
    private String postgresModelClassPath;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private DataMigrationRepository dataMigrationRepository;

    @Autowired
    private PostgresRepository postgresRepository;

    @PostConstruct
    public void start() {
        log.info("---------STARTED DATA MIGRATION------");
        MODELS.forEach(this::processModel);
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

            saveDocuments(postgresModelObj, fetchedDocuments);

            updateOrSaveDataMigration(collection, fetchedDocuments, dataMigration);
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

    @Transactional
    private void saveDocuments(Object postgresModelObj, List<Object> fetchedDocuments) throws Exception {
        Method convertMethod = postgresModelObj.getClass().getMethod("convert", Object.class);

        for (Object document : fetchedDocuments) {
            Object postgresEntity = convertMethod.invoke(postgresModelObj, document);
            if (postgresEntity != null) {
                postgresRepository.save(postgresEntity);
            }
        }
    }

    private void updateOrSaveDataMigration(String collection, List<Object> fetchedDocuments, DataMigration dataMigration) {
        Object lastDocument = fetchedDocuments.get(fetchedDocuments.size() - 1);
        try {
            String processedId = extractField(lastDocument, "id");
            Date processedDate = extractDateField(lastDocument);

            if (dataMigration != null) {
                updateDataMigration(processedId, processedDate, dataMigration);
            } else {
                saveDataMigration(collection, processedId, processedDate);
            }
        } catch (Exception e) {
            log.error("Error updating/saving DataMigration for collection {}: {}", collection, e.getMessage(), e);
        }
    }

    private void updateDataMigration(String processedId, Date processedDate, DataMigration existingDataMigration) {
        existingDataMigration.setLastProcessedId(processedId);
        existingDataMigration.setLastProcessedDate(processedDate);
        dataMigrationRepository.save(existingDataMigration);
    }

    private void saveDataMigration(String collection, String processedId, Date processedDate) {
        DataMigration dataMigration = new DataMigration();
        dataMigration.setCollectionName(collection);
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
