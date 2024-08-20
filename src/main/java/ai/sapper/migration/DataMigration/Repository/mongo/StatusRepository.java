package ai.sapper.migration.DataMigration.Repository.mongo;

import ai.sapper.migration.DataMigration.model.mongo.Status;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StatusRepository extends MongoRepository<Status,String> {

}
