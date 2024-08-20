package ai.sapper.migration.DataMigration.Repository.mongo;

import ai.sapper.migration.DataMigration.model.mongo.SapperRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface SapperRuleRepository extends MongoRepository<SapperRule,String> {

}
