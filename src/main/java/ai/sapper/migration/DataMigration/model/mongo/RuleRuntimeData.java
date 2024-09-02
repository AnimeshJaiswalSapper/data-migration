package ai.sapper.migration.DataMigration.model.mongo;

import ai.sapper.migration.DataMigration.common.mongo.BaseEntity;
import ai.sapper.migration.DataMigration.service.mongo.ReadService;
import com.unifiedframework.model.block.CaseDocument;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;

import static ai.sapper.migration.DataMigration.constants.Collections.*;


@Document(collection = "rules.output.runtime")
@ToString(callSuper = true)
@Component
@Getter
@Setter
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

    public RuleRuntimeData readByCaseId(String caseId){
        List<RuleRuntimeData> ruleRuntimeDataList = readService.findDocuments(RuleRuntimeData.class,
                "rules.output.runtime",
                VERSION,
                caseId,
                null,
                CASE_DOCUMENT_ID,
                null
                );

        if(!ruleRuntimeDataList.isEmpty())
            return ruleRuntimeDataList.get(ruleRuntimeDataList.size()-1);

        return null;
    }
}
