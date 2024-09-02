package ai.sapper.migration.DataMigration.model.mongo;

import ai.sapper.migration.DataMigration.constants.CaseType;
import ai.sapper.migration.DataMigration.service.mongo.ReadService;
import lombok.Getter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static ai.sapper.migration.DataMigration.constants.Collections.CREATED_AT;


@Document(collection = "audit.snapshot.original")
@Component
@Getter
@ToString
public class AuditSnapshotOriginal implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    private String caseId;

    private CaseType caseType;

    private int version;

    private Map<String, Object> data;

    @CreatedBy
    private String createdBy;

    @CreatedDate
    private Date createdAt;

    @LastModifiedBy
    private String modifiedBy;

    @LastModifiedDate
    private Date modifiedAt;

    @Autowired
    ReadService readService;

    public List<AuditSnapshotOriginal> read(Date lastProcessedDate, String lastProcessedId) {
        return readService.findDocumentsSorted(AuditSnapshotOriginal.class,
                "audit.snapshot.original",
                CREATED_AT,
                lastProcessedDate,
                lastProcessedId,
                true
        );
    }
}
