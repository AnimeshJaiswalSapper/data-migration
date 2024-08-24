package ai.sapper.migration.DataMigration.model.mongo;

import ai.sapper.migration.DataMigration.common.mongo.BaseEntity;
import ai.sapper.migration.DataMigration.constants.Status;
import ai.sapper.migration.DataMigration.service.mongo.ReadService;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;
import static ai.sapper.migration.DataMigration.constants.Collections.*;


@Document
@ToString(callSuper = true)
@Component
@Getter
@Setter
public class COALabel extends BaseEntity {

    @Id
    @Indexed(unique = true)
    protected String id;

    private String coa;
    private String name;
    private String expression;
    private List<Entity> expressionEntities;
    private Status status;
    private String conditionExp;
    private Map<String, Object> condition;
    private String parentId;
    private String scope;
    private boolean mandatory;
    private int priority;
    private String section;
    private String subSection;
    private String tableName;

    @Autowired
    ReadService readService;

    public List<COALabel> read(Date lastProcessedDate, String lastProcessedId) {
        return  readService.findDocumentsSorted(COALabel.class,
                "cOALabel",
                CREATED_DATE,
                lastProcessedDate,
                lastProcessedId,
                true
        );
    }

}
