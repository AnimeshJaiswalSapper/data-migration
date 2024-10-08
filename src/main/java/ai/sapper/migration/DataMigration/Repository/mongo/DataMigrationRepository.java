package ai.sapper.migration.DataMigration.Repository.mongo;

import ai.sapper.migration.DataMigration.model.mongo.DataMigration;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DataMigrationRepository extends MongoRepository<DataMigration, String> {
    public DataMigration findByCollectionName(String collectionName);
}