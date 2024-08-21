package ai.sapper.migration.DataMigration.Repository.mongo;

import ai.sapper.migration.DataMigration.common.BaseEntity;
import ai.sapper.migration.DataMigration.model.mongo.CaseMerge;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CaseMergeRepository extends MongoRepository<CaseMerge, String> {

    List<CaseMerge> findByMergeCaseId(String id);

}
