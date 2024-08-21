package ai.sapper.migration.DataMigration.model.mongo;

import ai.sapper.migration.DataMigration.Repository.ReadService;
import ai.sapper.migration.DataMigration.common.BaseEntity;
import ai.sapper.migration.DataMigration.constants.Status;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;


@Data
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@ToString(callSuper = true)
@Component
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

    @Autowired
    ReadService readService;

    public List<COALabel> read(String lastProcessedId) {
        return  readService.findDocumentsSorted(COALabel.class,
                "cOALabel",
                "createdDate",
                true,
                lastProcessedId
        );
    }

}
