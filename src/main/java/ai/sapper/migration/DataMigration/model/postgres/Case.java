package ai.sapper.migration.DataMigration.model.postgres;

import ai.sapper.migration.DataMigration.common.postgres.BaseEntity;
import ai.sapper.migration.DataMigration.common.postgres.ESpIntakeCaseState;
import ai.sapper.migration.DataMigration.constants.CaseStatus;
import ai.sapper.migration.DataMigration.constants.CaseType;
import ai.sapper.migration.DataMigration.convertor.CaseRetentionConverter;
import ai.sapper.migration.DataMigration.convertor.CaseStatusConverter;
import ai.sapper.migration.DataMigration.convertor.CaseTimesConverter;
import ai.sapper.migration.DataMigration.convertor.JsonbConverter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.Type;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Getter
@Setter
@Entity
@Table(name = "cm_cases")
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
@Component("cm_case")
@Builder
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Case implements Serializable {

    @Id
    @Column(name = "case_id")
    private String id;

    @Column(name = "company_id")
    private String entity;

    @Column(name = "coa_id")
    private String coaId;

    @Column(name = "coa_name")
    private String coaName;

    @Column(name = "assigned_to")
    private String assignee;

    @Column(name = "case_state")
    private String status;

    @Column(name = "type")
    private String type;

    @Column(name = "input_source")
    private String channel;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "closed_timestamp")
    private Long submitDate;

    @Column(name = "reject_reason", length = 512)
    private String rejectReason;

    @Column(name = "case_file_path")
    private String filePath;

    @Column(name = "auto_approval")
    private boolean autoApproval;

    @Column(name = "parent_case_id")
    private String parentCaseId;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_date")
    private Long createdDate;

    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    @Column(name = "last_modified_date")
    private Long lastModifiedDate;


    @Convert(converter = CaseTimesConverter.class)
    @Column(name = "case_times", columnDefinition = "jsonb")
    @ColumnTransformer(read = "case_times::TEXT", write = "?::jsonb")
    private CaseTimes caseTimes;


    @Convert(converter = CaseRetentionConverter.class)
    @Column(name = "retention", columnDefinition = "jsonb")
    @ColumnTransformer(read = "retention::TEXT", write = "?::jsonb")
    private CaseRetention caseRetention;

//    private Map<String, Object> attributes = new HashMap<>();
    @Embedded
    private CaseAttributes caseAttributes;

    public Case convert(Object mongoDocument) {
        try {
            if (mongoDocument instanceof ai.sapper.migration.DataMigration.model.mongo.Case mongoCase) {

                Case caseEntity = Case.builder()
                        .id(mongoCase.getId())
                        .entity(mongoCase.getEntity() != null ? mongoCase.getEntity().getId() : null)
                        .coaId(mongoCase.getCoaId())
                        .coaName(mongoCase.getCoaName())
                        .assignee(mongoCase.getAssignee())
                        .status(CaseStatusConverter.convert(mongoCase.getStatus()).toString())
                        .type(convertType(mongoCase.getType()))
                        .channel(mongoCase.getChannel())
                        .submitDate(mongoCase.getSubmitDate() != null ? mongoCase.getSubmitDate().getTime() : null)
                        .rejectReason(mongoCase.getRejectReason())
                        .filePath(mongoCase.getFilePath())
                        .autoApproval(mongoCase.isAutoApproval())
                        .parentCaseId(mongoCase.getParentCaseId())
                        .createdBy(mongoCase.getCreatedBy())
                        .createdDate(mongoCase.getCreatedDate() != null ? mongoCase.getCreatedDate().getTime() : null)
                        .lastModifiedBy(mongoCase.getLastModifiedBy())
                        .lastModifiedDate(mongoCase.getLastModifiedDate() != null ? mongoCase.getLastModifiedDate().getTime() : null)
                        .build();

                // set attributes
                if(mongoCase.getAttributes() != null)
                    setAttributes(mongoCase.getAttributes(), caseEntity);

                // Extract metadata
                setMetaData(mongoCase.getMetadata(), caseEntity);

                return caseEntity;
            }
        } catch (Exception e) {
            log.error("Error converting MongoDB document: {}", e.getMessage(), e);
            throw new RuntimeException("Conversion failed", e);
        }
        return null;
    }

    private void setMetaData(Map<String, ?> metaData, Case caseEntity) {
        try {
            CaseTimes caseTimes = new CaseTimes();
            CaseRetention caseRetention = new CaseRetention();

            if (metaData.containsKey("systemProcessTimeStart")) {
               caseTimes.setSystemProcessTimeStart(convertToMillis(metaData.get("systemProcessTimeStart")));
            }
            if (metaData.containsKey("systemProcessTimeEnd")) {
                caseTimes.setSystemProcessTimeEnd(convertToMillis(metaData.get("systemProcessTimeEnd")));
            }
            if (metaData.containsKey("systemProcessTimeInSeconds")) {
                caseTimes.setSystemProcessTimeInSeconds(convertToSeconds(metaData.get("systemProcessTimeInSeconds")));
            }
            if (metaData.containsKey("touchTimeStart")) {
                caseTimes.setTouchTimeStart(convertToMillis(metaData.get("touchTimeStart")));
            }
            if (metaData.containsKey("touchTimeEnd")) {
                caseTimes.setTouchTimeEnd(convertToMillis(metaData.get("touchTimeEnd")));
            }
            if (metaData.containsKey("touchTimeInSeconds")) {
                caseTimes.setTouchTimeInSeconds(convertToSeconds(metaData.get("touchTimeInSeconds")));
            }
            if (metaData.containsKey("processTimeStart")) {
                caseTimes.setProcessTimeStart(convertToMillis(metaData.get("processTimeStart")));
            }
            if (metaData.containsKey("processTimeEnd")) {
                caseTimes.setProcessTimeEnd(convertToMillis(metaData.get("processTimeEnd")));
            }
            if (metaData.containsKey("processTimeInSeconds")) {
                caseTimes.setProcessTimeInSeconds(convertToSeconds(metaData.get("processTimeInSeconds")));
            }
            if (metaData.containsKey("fileDeletedAt")) {
                caseRetention.setFileDeletedAt(convertToMillis(metaData.get("fileDeletedAt")));
            }

            if(metaData.containsKey("fileDeleted")){
                caseRetention.setFileDeleted(Boolean.valueOf((String) metaData.get("fileDeleted")));
            }

            caseEntity.setCaseTimes(caseTimes);
            caseEntity.setCaseRetention(caseRetention);

        } catch (Exception e) {
            log.error("Error processing metadata: {}", e.getMessage(), e);
            throw new RuntimeException("Metadata processing failed", e);
        }
    }


    private void setAttributes(Map<String, ?> attributes, Case caseEntity) {
        try {
            CaseAttributes caseAttributes = new CaseAttributes();

            if (attributes.containsKey("financialIndicator")) {
                Object financialIndicatorObj = attributes.get("financialIndicator");
                if (financialIndicatorObj instanceof Map) {
                    Map<String, Object> financialIndicatorMap = (Map<String, Object>) financialIndicatorObj;
                    caseAttributes.setFinancialIndicator((String) financialIndicatorMap.get("id"));
                }
            }

            if (attributes.containsKey("database")) {
                Object databaseObj = attributes.get("database");
                if (databaseObj instanceof Map) {
                    Map<String, Object> databaseMap = (Map<String, Object>) databaseObj;
                    caseAttributes.setDatabase((String) databaseMap.get("id"));
                }
            }

            if (attributes.containsKey("dateType")) {
                Object dateTypeObj = attributes.get("dateType");
                if (dateTypeObj instanceof Map) {
                    Map<String, Object> dateTypeMap = (Map<String, Object>) dateTypeObj;
                    caseAttributes.setDateType((String) dateTypeMap.get("id"));
                }
            }

            if (attributes.containsKey("documentType")) {
                Object documentTypeObj = attributes.get("documentType");
                if (documentTypeObj instanceof Map) {
                    Map<String, Object> documentTypeMap = (Map<String, Object>) documentTypeObj;
                    caseAttributes.setDocumentType((String) documentTypeMap.get("id"));
                }
            }

            if (attributes.containsKey("purpose")) {
                Object purposeObj = attributes.get("purpose");
                if (purposeObj instanceof Map) {
                    Map<String, Object> purposeMap = (Map<String, Object>) purposeObj;
                    caseAttributes.setPurpose((String) purposeMap.get("id"));
                }
            }

            if (attributes.containsKey("startDate")) {
                String startDateStr = (String) attributes.get("startDate");
                caseAttributes.setStartDate(convertToMillis(startDateStr));
            }

            if (attributes.containsKey("endDate")) {
                String endDateStr = (String) attributes.get("endDate");
                caseAttributes.setEndDate(convertToMillis(endDateStr));
            }

            if (attributes.containsKey("frequencyType")) {
                Object frequencyTypeObj = attributes.get("frequencyType");
                if (frequencyTypeObj instanceof Map) {
                    Map<String, Object> frequencyTypeMap = (Map<String, Object>) frequencyTypeObj;
                    caseAttributes.setFrequencyType((String) frequencyTypeMap.get("id"));
                }
            }

            if (attributes.containsKey("language")) {
                Object languageObj = attributes.get("language");
                if (languageObj instanceof Map) {
                    Map<String, Object> languageMap = (Map<String, Object>) languageObj;
                    caseAttributes.setLanguage((String) languageMap.get("id"));
                }
            }



            caseEntity.setCaseAttributes(caseAttributes);

        } catch (Exception e) {
            log.error("Error processing metadata: {}", e.getMessage(), e);
            throw new RuntimeException("Metadata processing failed", e);
        }
    }

    String convertType(CaseType type){
        if(type != null){
            return type == CaseType.FINANCIAL_STATEMENT ? "FS" : type.toString();
        }
        return null;
    }

    private Long convertToMillis(Object dateTime) {
        try {
            if (dateTime instanceof String) {
                ZonedDateTime zonedDateTime = ZonedDateTime.parse((String) dateTime, DateTimeFormatter.ISO_DATE_TIME);
                return zonedDateTime.toInstant().toEpochMilli();
            } else if (dateTime instanceof Date) {
                return ((Date) dateTime).getTime();
            }
        } catch (Exception e) {
            log.error("Error converting date to milliseconds: {}", e.getMessage(), e);
        }
        return null;
    }


    private Long convertToSeconds(Object time) {
        try {
            if (time instanceof Number) {
                return ((Number) time).longValue();
            }
        } catch (Exception e) {
            log.error("Error converting time to seconds: {}", e.getMessage(), e);
        }
        return null;
    }

}