package ai.sapper.migration.DataMigration.Repository.mongo;

import ai.sapper.migration.DataMigration.model.mongo.Config;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ConfigRepository extends MongoRepository<Config, Long> {

}
