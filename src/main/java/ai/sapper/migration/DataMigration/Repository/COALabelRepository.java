package ai.sapper.migration.DataMigration.Repository;

import ai.sapper.migration.DataMigration.constants.Status;
import ai.sapper.migration.DataMigration.model.mongo.COA;
import ai.sapper.migration.DataMigration.model.mongo.COALabel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface COALabelRepository extends MongoRepository<COALabel, String> {

    public List<COALabel> findByCoaAndStatus(COA coa, Status status);

    public List<COALabel> findByCoaAndStatusOrderByCreatedDateAsc(COA coa, Status status);

    public List<COALabel> findByName(String name);

    public Page<COALabel> findByCoaAndStatus(COA coa, Status status, Pageable pageable);

    public Page<COALabel> findByCoa(COA coa, Pageable pageable);

    @Query("{ 'coa': ?0, 'expression': ?1,'conditionExp':?2}")
    public Boolean exists(String coaId, String expression, String condition);

}
