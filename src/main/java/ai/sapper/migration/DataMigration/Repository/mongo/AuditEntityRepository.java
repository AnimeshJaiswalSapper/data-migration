package ai.sapper.migration.DataMigration.Repository.mongo;

import ai.sapper.migration.DataMigration.model.mongo.AuditEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AuditEntityRepository extends MongoRepository<AuditEntity,Long> {
}
