package ai.sapper.migration.DataMigration.model.postgres;

import ai.sapper.migration.DataMigration.common.postgres.BaseEntity;
import ai.sapper.migration.DataMigration.convertor.StringListConverter;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * This entity is used for persistence of rules configured by user
 *
 */
@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Entity
@Table(name = "rules_config")
@Slf4j
@Builder
@Component("rules_config")
public class SapperRule extends BaseEntity {
    @Id
    @Column(name = "id")
    protected String id;

    @Column(name = "name")
    private String name;

    @Column(name = "group_id")
    private String group;

    @Column(name="rule_expression", length = 10000)
    private String ruleExpression;

    @Column(name = "rule_condition", length = 5000)
    private String ruleCondition;

    @Column(name = "derived_field_name")
    private String derivedFieldName;


    @Column(name = "section")
    private String section;

    @Column(name = "sub_section")
    @Convert(converter = StringListConverter.class)
    private List<String> subSection;

    @Column(name = "label_used", length = 5000)
    @Convert(converter = StringListConverter.class)
    private List<String> labelUsed;

    @Column(name = "category")
    private String category;

    @Column(name = "rule_type")
    private String ruleType;

    @Column(name = "enabled")
    private boolean enabled;

    @Column(name = "rule_order")
    private long order;



    public SapperRule convert(Object mongoDocument) {
        try {
            if (mongoDocument instanceof ai.sapper.migration.DataMigration.model.mongo.SapperRule mongoRule) {

                SapperRule rule = SapperRule.builder()
                        .id(mongoRule.getId())
                        .name(mongoRule.getName())
                        .group(mongoRule.getGroup())
                        .ruleExpression(mongoRule.getRuleExpression())
                        .ruleCondition(mongoRule.getRuleCondition())
                        .derivedFieldName(mongoRule.getDerivedFieldName())
                        .section(mongoRule.getSection())
                        .subSection(mongoRule.getSubSection())
                        .labelUsed(mongoRule.getLabelUsed())
                        .category(String.valueOf(mongoRule.getCategory()))
                        .ruleType(String.valueOf(mongoRule.getRuleType()))
                        .enabled(mongoRule.isEnabled())
                        .order(mongoRule.getOrder())
                        .build();


                rule.setCreatedBy(mongoRule.getCreatedBy());
                rule.setCreatedDate(convertToZonedDateTime(mongoRule.getCreatedDate()));
                rule.setLastModifiedBy(mongoRule.getLastModifiedBy());
                rule.setLastModifiedDate(convertToZonedDateTime(mongoRule.getLastModifiedDate()));

                return rule;
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

