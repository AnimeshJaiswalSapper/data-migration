package ai.sapper.migration.DataMigration.Repository.mongo;

import ai.sapper.migration.DataMigration.model.mongo.AuditSnapshot;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AuditSnapshotRepository extends MongoRepository<AuditSnapshot,Long> {

}
