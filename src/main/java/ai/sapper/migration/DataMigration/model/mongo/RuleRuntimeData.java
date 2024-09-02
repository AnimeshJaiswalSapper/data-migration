package ai.sapper.migration.DataMigration.model.mongo;

import ai.sapper.migration.DataMigration.common.mongo.BaseEntity;
import ai.sapper.migration.DataMigration.service.mongo.ReadService;
import com.unifiedframework.model.block.CaseDocument;
import lombok.Data;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;

import static ai.sapper.migration.DataMigration.constants.Collections.CREATED_DATE;


@Document(collection = "rules.output.runtime")
@ToString(callSuper = true)
@Component
@Data
public class RuleRuntimeData extends BaseEntity {
    @Id
    protected String id;
    private String group;

    private int version;

    private Map<String, List<Object>> data;

    private String caseDocumentId;

    private CaseDocument caseDocument;

    private Map<String, String> metadata;


    @Autowired
    ReadService readService;

    public List<RuleRuntimeData> read(Date lastProcessedDate, String lastProcessedId) {
        return readService.findDocumentsSorted(RuleRuntimeData.class,
                "rules.output.runtime",
                CREATED_DATE,
                lastProcessedDate,
                lastProcessedId,
                true
        );
    }
}
