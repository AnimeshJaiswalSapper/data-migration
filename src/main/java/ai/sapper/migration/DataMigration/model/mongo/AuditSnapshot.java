package ai.sapper.migration.DataMigration.model.mongo;

import ai.sapper.migration.DataMigration.Repository.ReadService;
import ai.sapper.migration.DataMigration.constants.CaseType;
import lombok.Data;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@Document(collection = "audit.snapshot")
@Component
@ToString(callSuper = true)
public class AuditSnapshot implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    private String caseId;

    private CaseType caseType;

    private int version;

    private Map<String, AuditDetail> data;

    @CreatedBy
    private String createdBy;

    @CreatedDate
    private LocalDateTime createdAt;

    @Autowired
    ReadService readService;

    public List<AuditSnapshot> read(String lastProcessedId) {
        return  readService.findDocumentsSorted(AuditSnapshot.class,
                "audit.snapshot",
                "createdAt",
                true,
                lastProcessedId
        );
    }

    public void incrementVersion() {
        version++;
    }
}
