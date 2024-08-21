package ai.sapper.migration.DataMigration.Repository.mongo;

import ai.sapper.migration.DataMigration.constants.CaseType;
import ai.sapper.migration.DataMigration.model.mongo.CaseDocumentDO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface CaseDocumentRepository extends MongoRepository<CaseDocumentDO, String> {

    public Optional<CaseDocumentDO> findByCaseId(String caseId);

    @Query("{'caseId': ?0, 'type': ?1}")
    Optional<CaseDocumentDO> findByCaseIdAndType(String id, CaseType type);

    public List<CaseDocumentDO> findByCaseIdInAndType(List<String> caseIds , CaseType type);

    List<CaseDocumentDO> findByIdAfterOrderByCreatedDateAsc(String id);

    List<CaseDocumentDO> findAllByOrderByCreatedDateAsc();
}
