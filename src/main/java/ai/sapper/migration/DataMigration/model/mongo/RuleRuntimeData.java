package ai.sapper.migration.DataMigration.model.mongo;

import ai.sapper.migration.DataMigration.service.mongo.ReadService;
import ai.sapper.migration.DataMigration.common.BaseEntity;
import com.unifiedframework.model.block.CaseDocument;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
@ToString(callSuper = true)
@Document(collection = "rules.output.runtime")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Component
public class RuleRuntimeData extends BaseEntity {
    @Id
    protected String id;
    private String group;

    private int version;

    @NonNull
    private Map<String, List<FieldLevelData>> data;

    private String caseDocumentId;

    private CaseDocument caseDocument;

    private Map<String, String> metadata;

    public void appendFieldLevelData(String key, FieldLevelData fieldLevelData) {
        List<FieldLevelData> listOfFieldLevelData = data.getOrDefault(key, new ArrayList<>());
        if (!listOfFieldLevelData.isEmpty()) {
            listOfFieldLevelData.removeIf(data -> data.getRuleId().equals(fieldLevelData.getRuleId()));
        }
        listOfFieldLevelData.add(fieldLevelData);
        data.put(key, listOfFieldLevelData);
    }

    public void cleanDataMap() {
        data.clear();
    }

    public void incrementVersion() {
        version++;
    }

    @Autowired
    ReadService readService;

    public List<RuleRuntimeData> read(Date lastProcessedDate, String lastProcessedId) {
        return  readService.findDocumentsSorted(RuleRuntimeData.class,
                "rules.output.runtime",
                "createdDate",
                true,
                lastProcessedDate,
                lastProcessedId,
                true
        );
    }
}
