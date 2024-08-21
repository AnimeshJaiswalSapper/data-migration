package ai.sapper.migration.DataMigration.model.mongo;

import ai.sapper.migration.DataMigration.Repository.ReadService;
import ai.sapper.migration.DataMigration.common.BaseEntity;
import ai.sapper.migration.DataMigration.constants.CaseStatus;
import ai.sapper.migration.DataMigration.constants.CaseType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@ToString(callSuper = true)
@Component
public class Case extends BaseEntity {
    @Id
    @Indexed(unique = true)
    protected String id;
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
    @JsonIgnore
    ReadService readService;

    public List<Case> read(Date lastProcessedDate) {
       return  readService.findDocumentsSorted(Case.class,
                "case",
                "createdDate",
                true,
               lastProcessedDate
        );
    }

    public List<Case> castList(List<Object> originalList) {
        return originalList.stream()
                .filter(Case.class::isInstance)
                .map(Case.class::cast)
                .collect(Collectors.toList());
    }
}
