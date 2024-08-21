package ai.sapper.migration.DataMigration.model.mongo;

import ai.sapper.migration.DataMigration.constants.Category;
import ai.sapper.migration.DataMigration.constants.RuleType;
import lombok.*;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FieldLevelData {

    private String ruleId;

    private String ruleExpression;

    private String section;

    private String targetId;

    private String triggerExpression;

    private String target;

    private List<String> labelUsed;

    private Category category;

    private RuleType ruleType;

    private Boolean ruleOutput;

    private String ruleOutputData;

    private Map<String, Object> metadata;

}
