package ai.sapper.migration.DataMigration.model.mongo;

import ai.sapper.migration.DataMigration.common.BaseEntity;
import ai.sapper.migration.DataMigration.constants.Category;
import ai.sapper.migration.DataMigration.constants.RuleType;
import ai.sapper.migration.DataMigration.service.mongo.ReadService;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import static ai.sapper.migration.DataMigration.constants.Collections.*;


import java.util.Date;
import java.util.List;

@Document(collection = "rule")
@Component
@ToString(callSuper = true)
public class SapperRule extends BaseEntity {

    private static final long serialVersionUID = 1L;
    @Id
    protected String id;

    private String name;

    private String group;

    private String ruleExpression;

    private String ruleCondition;

    private String derivedFieldName;

    @Nullable
    private String section;

    @Deprecated
    @Nullable
    private List<String> subSection;

    private List<String> labelUsed;

    private Category category;

    private RuleType ruleType;

    private boolean enabled;

    private long order;

    @Autowired
    ReadService readService;

    public List<SapperRule> read(Date lastProcessedDate, String lastProcessedId) {
        return  readService.findDocumentsSorted(SapperRule.class,
                "rule",
                LAST_MODIFIED_DATE,
                lastProcessedDate,
                lastProcessedId,
                true
        );
    }
}
