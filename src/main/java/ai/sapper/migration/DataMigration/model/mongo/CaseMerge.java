package ai.sapper.migration.DataMigration.model.mongo;

import ai.sapper.migration.DataMigration.service.mongo.ReadService;
import ai.sapper.migration.DataMigration.common.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@ToString(callSuper = true)
@Component
public class CaseMerge extends BaseEntity {
    @Id
    @Indexed(unique = true)
    protected String id;

    private String oldCaseId;
    private String mergeCaseId;

    @Autowired
    ReadService readService;

    public List<CaseMerge> read(String lastProcessedId) {
        return  readService.findDocumentsSorted(CaseMerge.class,
                "caseMerge",
                "createdDate",
                true,
                lastProcessedId
        );
    }

}
