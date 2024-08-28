package ai.sapper.migration.DataMigration.model.postgres;

import ai.sapper.migration.DataMigration.common.postgres.BaseEntity;
import ai.sapper.migration.DataMigration.common.postgres.Condition;
import ai.sapper.migration.DataMigration.constants.Status;
//import ai.sapper.migration.DataMigration.convertor.ConditionConvertor;
import ai.sapper.migration.DataMigration.convertor.ConditionConvertor;
import ai.sapper.migration.DataMigration.convertor.EntityConvertor;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

@Entity
@Table(name = "coa_label")
@Slf4j
@Builder
@Component("coa_label")
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class COALabel extends BaseEntity implements Serializable {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "coa_id")
    private String coa;

    @Column(name = "name")
    private String name;

    @Column(name = "expression", length = 512)
    private String expression;


    @Column(name = "expression_entities")
    @Convert(converter = EntityConvertor.class)
    private List<Object> expressionEntities; // no usage found in CBRE and all fields are null in PG

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "condition_exp")
    private String conditionExp;// no usage found in CBRE , it contains expression =

    @Column(name = "condition")
    @Convert(converter = ConditionConvertor.class)
    private Map<String, Object> condition;

    @Column(name = "parent_id")
    private String parentId;// inside CBRE code we use it but on collection it's not present.

    @Column(name = "mandatory")
    private boolean mandatory; // used

    @Column(name = "priority")
    private int priority; // used

    @Column(name = "scope")
    private String scope;

    @Column(name = "section")
    private String section;

    @Column(name = "sub_section")
    private String subSection;

    @Column(name = "table_name")
    private String tableName;



    public COALabel convert(Object mongoDocument) {
        try {
            if (mongoDocument instanceof ai.sapper.migration.DataMigration.model.mongo.COALabel mongoCOALabel) {

                COALabel coaLabel = COALabel.builder()
                        .id(mongoCOALabel.getId())
                        .coa(mongoCOALabel.getCoa())
                        .name(mongoCOALabel.getName())
                        .expression(mongoCOALabel.getExpression())
                        .expressionEntities(Collections.singletonList(mongoCOALabel.getExpressionEntities()))
                        .status(mongoCOALabel.getStatus())
                        .conditionExp(mongoCOALabel.getConditionExp())
                        .condition(mongoCOALabel.getCondition())
                        .parentId(mongoCOALabel.getParentId())
                        .mandatory(mongoCOALabel.isMandatory())
                        .priority(mongoCOALabel.getPriority())
                        .scope(mongoCOALabel.getScope())
                        .section(mongoCOALabel.getSection())
                        .subSection(mongoCOALabel.getSubSection())
                        .subSection(mongoCOALabel.getTableName())
                        .build();


                coaLabel.setCreatedBy(mongoCOALabel.getCreatedBy());
                coaLabel.setCreatedDate(convertToZonedDateTime(mongoCOALabel.getCreatedDate()));
                coaLabel.setLastModifiedBy(mongoCOALabel.getLastModifiedBy());
                coaLabel.setLastModifiedDate(convertToZonedDateTime(mongoCOALabel.getLastModifiedDate()));

                return coaLabel;
            }
        }  catch (Exception e) {
            log.error("Error converting COA document: {}", e.getMessage(), e);
            throw e;
        }
        return null;
    }


    private ZonedDateTime convertToZonedDateTime(Date date) {
        if (date != null) {
            return ZonedDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        }
        return null;
    }



}
