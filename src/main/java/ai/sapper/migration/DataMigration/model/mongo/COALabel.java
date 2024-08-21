package ai.sapper.migration.DataMigration.model.mongo;

import ai.sapper.migration.DataMigration.service.mongo.ReadService;
import ai.sapper.migration.DataMigration.common.BaseEntity;
import ai.sapper.migration.DataMigration.constants.Status;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Data
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@ToString(callSuper = true)
@Component
public class COALabel extends BaseEntity {

    @Id
    @Indexed(unique = true)
    protected String id;

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

    public List<COALabel> read(Date lastProcessedDate) {
        return  readService.findDocumentsSorted(COALabel.class,
                "cOALabel",
                "createdDate",
                true,
                lastProcessedDate
        );
    }

    public List<COALabel> castList(List<Object> originalList) {
        return originalList.stream()
                .filter(COALabel.class::isInstance)
                .map(COALabel.class::cast)
                .collect(Collectors.toList());
    }

}
