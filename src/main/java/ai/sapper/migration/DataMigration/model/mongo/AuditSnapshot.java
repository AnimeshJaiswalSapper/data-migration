package ai.sapper.migration.DataMigration.model.mongo;

import ai.sapper.migration.DataMigration.constants.CaseType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;

@Data
@Document(collection = "audit.snapshot")
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

    public void incrementVersion() {
        version++;
    }
}
