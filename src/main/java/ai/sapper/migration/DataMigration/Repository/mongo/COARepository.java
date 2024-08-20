package ai.sapper.migration.DataMigration.Repository.mongo;

import ai.sapper.migration.DataMigration.constants.Status;
import ai.sapper.migration.DataMigration.model.mongo.COA;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface COARepository extends MongoRepository<COA, String> {

    public Page<COA> findByStatus(Status status, Pageable pageable);

    public COA findByName(String coaName);

}
