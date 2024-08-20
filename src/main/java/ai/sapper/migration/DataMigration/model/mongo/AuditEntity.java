package ai.sapper.migration.DataMigration.model.mongo;

import ai.sapper.migration.DataMigration.constants.CaseType;
import com.unifiedframework.model.block.CaseDocument;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.LocalDateTime;

@Document(collection = "audit.entity")
@Data
public class AuditEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    private String caseId;

    private CaseDocument document;

    private CaseType caseType;

    private int version;

    private String createdBy;

    @CreatedDate
    private LocalDateTime createdAt;

}
