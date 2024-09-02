package ai.sapper.migration.DataMigration.Repository.mongo;

import ai.sapper.migration.DataMigration.model.mongo.DataMigration;
import ai.sapper.migration.DataMigration.model.mongo.S3Migration;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface S3MigrationRepository extends MongoRepository<S3Migration, String> {
    public S3Migration findByCollectionName(String collectionName);
}
