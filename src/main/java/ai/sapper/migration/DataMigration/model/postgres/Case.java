package ai.sapper.migration.DataMigration.model.postgres;

import ai.sapper.migration.DataMigration.common.postgres.BaseEntity;
import ai.sapper.migration.DataMigration.constants.CaseStatus;
import ai.sapper.migration.DataMigration.convertor.JsonbConverter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.persistence.*;
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

    @Convert(converter = JsonbConverter.class)
    @Column(name = "attributes", columnDefinition = "jsonb")
    @ColumnTransformer(write = "?::jsonb")
    private Map<String, Object> attributes = new HashMap<>();

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

    @Column(name = "system_process_time_start")
    private Long systemProcessTimeStart;

    @Column(name = "system_process_time_end")
    private Long systemProcessTimeEnd;

    @Column(name = "system_process_time_in_seconds")
    private Long systemProcessTimeInSeconds;

    @Column(name = "touch_time_start")
    private Long touchTimeStart;

    @Column(name = "touch_time_end")
    private Long touchTimeEnd;

    @Column(name = "touch_time_in_seconds")
    private Long touchTimeInSeconds;

    @Column(name = "process_time_start")
    private Long processTimeStart;

    @Column(name = "process_time_end")
    private Long processTimeEnd;

    @Column(name = "process_time_in_seconds")
    private Long processTimeInSeconds;

    @Column(name = "file_deleted")
    private Boolean fileDeleted;

    @Column(name = "file_deleted_at")
    private Long fileDeletedAt;

    public Case convert(Object mongoDocument) {
        try {
            if (mongoDocument instanceof ai.sapper.migration.DataMigration.model.mongo.Case mongoCase) {

                Case caseEntity = Case.builder()
                        .id(mongoCase.getId())
                        .entity(mongoCase.getEntity() != null ? mongoCase.getEntity().getId() : null)
                        .coaId(mongoCase.getCoaId())
                        .coaName(mongoCase.getCoaName())
                        .assignee(mongoCase.getAssignee())
                        .status(String.valueOf(mongoCase.getStatus()))
                        .type(mongoCase.getType() != null ? String.valueOf(mongoCase.getType()) : null)
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
                    caseEntity.setAttributes((Map<String, Object>) mongoCase.getAttributes());

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
            if (metaData.containsKey("systemProcessTimeStart")) {
                caseEntity.setSystemProcessTimeStart(convertToMillis(metaData.get("systemProcessTimeStart")));
            }
            if (metaData.containsKey("systemProcessTimeEnd")) {
                caseEntity.setSystemProcessTimeEnd(convertToMillis(metaData.get("systemProcessTimeEnd")));
            }
            if (metaData.containsKey("systemProcessTimeInSeconds")) {
                caseEntity.setSystemProcessTimeInSeconds(convertToSeconds(metaData.get("systemProcessTimeInSeconds")));
            }
            if (metaData.containsKey("touchTimeStart")) {
                caseEntity.setTouchTimeStart(convertToMillis(metaData.get("touchTimeStart")));
            }
            if (metaData.containsKey("touchTimeEnd")) {
                caseEntity.setTouchTimeEnd(convertToMillis(metaData.get("touchTimeEnd")));
            }
            if (metaData.containsKey("touchTimeInSeconds")) {
                caseEntity.setTouchTimeInSeconds(convertToSeconds(metaData.get("touchTimeInSeconds")));
            }
            if (metaData.containsKey("processTimeStart")) {
                caseEntity.setProcessTimeStart(convertToMillis(metaData.get("processTimeStart")));
            }
            if (metaData.containsKey("processTimeEnd")) {
                caseEntity.setProcessTimeEnd(convertToMillis(metaData.get("processTimeEnd")));
            }
            if (metaData.containsKey("processTimeInSeconds")) {
                caseEntity.setProcessTimeInSeconds(convertToSeconds(metaData.get("processTimeInSeconds")));
            }
            if (metaData.containsKey("fileDeletedAt")) {
                caseEntity.setFileDeletedAt(convertToMillis(metaData.get("fileDeletedAt")));
            }

            if(metaData.containsKey("fileDeleted")){
                caseEntity.setFileDeleted(Boolean.valueOf((String) metaData.get("fileDeleted")));
            }

        } catch (Exception e) {
            log.error("Error processing metadata: {}", e.getMessage(), e);
            throw new RuntimeException("Metadata processing failed", e);
        }
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