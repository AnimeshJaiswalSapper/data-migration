package ai.sapper.migration.DataMigration.model;

import ai.sapper.migration.DataMigration.Repository.CaseRepository;
import ai.sapper.migration.DataMigration.common.Migration;
import ai.sapper.migration.DataMigration.constants.CaseStatus;
import ai.sapper.migration.DataMigration.constants.CaseType;
import ai.sapper.migration.DataMigration.common.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Slf4j
@Component
public class Case extends BaseEntity implements Migration {
    @DocumentReference
    private Entity entity;
    private String coaId;
    private String coaName;
    private String assignee;
    @Builder.Default
    private CaseStatus status = CaseStatus.DRAFT;
    private CaseType type;
    private String channel;
    private String fileName;
    private Map<String,?> attributes;
    @Builder.Default
    private Map<String, ?> metadata = new HashMap<>();
    private Date submitDate;
    private String rejectReason;
    private String filePath;
    private List<String> pdfPath;
    private boolean autoApproval = false;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String parentCaseId;

    @Autowired
    CaseRepository caseRepository;

    @Override
    public void migrate() {
        log.info("Inside migrate method");
        List<Case> cases = caseRepository.findAllByCoaId("64ca2d7c7e16e9677cebfba6");
        log.info("Got all cases : {}", cases);
        log.info("size of cases got : {}", cases.size());
        log.info("Printed all cases");
    }

    public void setCaseRepository(CaseRepository caseRepository) {
        this.caseRepository = caseRepository;
    }

    @Override
    public String toString() {
        return "Case{" +
                "entity=" + entity +
                ", coaId='" + coaId + '\'' +
                ", coaName='" + coaName + '\'' +
                ", assignee='" + assignee + '\'' +
                ", status=" + status +
                ", type=" + type +
                ", channel='" + channel + '\'' +
                ", fileName='" + fileName + '\'' +
                ", attributes=" + attributes +
                ", metadata=" + metadata +
                ", submitDate=" + submitDate +
                ", rejectReason='" + rejectReason + '\'' +
                ", filePath='" + filePath + '\'' +
                ", pdfPath=" + pdfPath +
                ", autoApproval=" + autoApproval +
                ", parentCaseId='" + parentCaseId + '\'' +
                '}';
    }
}
