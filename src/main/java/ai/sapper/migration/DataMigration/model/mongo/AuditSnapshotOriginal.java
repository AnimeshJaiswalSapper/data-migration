package ai.sapper.migration.DataMigration.model.mongo;

import ai.sapper.migration.DataMigration.service.mongo.ReadService;
import ai.sapper.migration.DataMigration.constants.CaseType;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@ToString(callSuper = true)
@Document(collection = "audit.snapshot.original")
@Component
public class AuditSnapshotOriginal implements Serializable {

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

    @LastModifiedBy
    private String modifiedBy;

    @LastModifiedDate
    private LocalDateTime modifiedAt;

    @Autowired
    ReadService readService;

    public List<AuditSnapshot> read(String lastProcessedId) {
        return  readService.findDocumentsSorted(AuditSnapshot.class,
                "audit.snapshot.original",
                "createdAt",
                true,
                lastProcessedId
        );
    }

}
