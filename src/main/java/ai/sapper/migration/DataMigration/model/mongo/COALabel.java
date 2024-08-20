package ai.sapper.migration.DataMigration.model.mongo;

import ai.sapper.migration.DataMigration.common.BaseEntity;
import ai.sapper.migration.DataMigration.constants.Status;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.List;
import java.util.Map;

@Document
@Data
public class COALabel extends BaseEntity {

    @DocumentReference
    private COA coa;
    private String name;
    private String expression;
    private List<Entity> expressionEntities;
    private Status status;
    private String conditionExp;
    private Map<String, Condition> condition;
    private String parentId;
    private boolean mandatory;
    private int priority;

}
