package ai.sapper.migration.DataMigration.model.postgres;

import ai.sapper.migration.DataMigration.common.mongo.BaseEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.stereotype.Component;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Slf4j
@Builder
@Component("case_merge")
public class CaseMerge extends BaseEntity {
    @Id
    @Indexed(unique = true)
    protected String id;

    private String oldCaseId;
    private String mergeCaseId;

    public CaseMerge convert(Object mongoDocument) throws JsonProcessingException {
        try {
            if (mongoDocument instanceof ai.sapper.migration.DataMigration.model.mongo.CaseMerge mongoCaseMerge) {
                return CaseMerge.builder()
                        .id(mongoCaseMerge.getId())
                        .oldCaseId(mongoCaseMerge.getOldCaseId())
                        .mergeCaseId(mongoCaseMerge.getMergeCaseId())
                        .build();
            }
        } catch (Exception e) {
            log.error("Error converting MongoDB document: [CaseMerge] , exception : {}", e.getMessage(), e);
            throw new RuntimeException("Conversion failed", e);
        }
        return null;
    }



}
