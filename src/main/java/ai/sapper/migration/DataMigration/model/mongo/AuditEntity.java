package ai.sapper.migration.DataMigration.model.mongo;

import ai.sapper.migration.DataMigration.service.mongo.ReadService;
import ai.sapper.migration.DataMigration.constants.CaseType;
import com.unifiedframework.model.block.CaseDocument;
import lombok.Data;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "audit.entity")
@Data
@Component
@ToString(callSuper = true)
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

    @Autowired
    ReadService readService;

    public List<AuditEntity> read(String lastProcessedId) {
        return  readService.findDocumentsSorted(AuditEntity.class,
                "audit.entity",
                "createdAt",
                true,
                lastProcessedId
        );
    }

}
