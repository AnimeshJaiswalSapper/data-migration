package ai.sapper.migration.DataMigration.Repository;


import ai.sapper.migration.DataMigration.model.Case;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;

@Repository
public interface CaseRepository extends MongoRepository<Case, String> {

    @Override
    <S extends Case> S insert(S entity);

    List<Case> findAllByCreatedDateBefore(ZonedDateTime cutoffDate);
    List<Case> findAllByCreatedDateBetween(ZonedDateTime oldCutOffDate, ZonedDateTime cutoffDate);

    List<Case> findAllByCoaId(String coaId);
}
