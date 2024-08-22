package ai.sapper.migration.DataMigration.model.mongo;

import ai.sapper.migration.DataMigration.constants.CaseType;
import ai.sapper.migration.DataMigration.service.mongo.ReadService;
import com.unifiedframework.model.block.CaseDocument;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import static ai.sapper.migration.DataMigration.constants.Collections.*;

@Document(collection = "audit.entity")
@Component
@ToString
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
    private Date createdAt;

    @Autowired
    ReadService readService;

    public List<AuditEntity> read(Date lastProcessedDate, String lastProcessedId) {
        return  readService.findDocumentsSorted(AuditEntity.class,
                "audit.entity",
                CREATED_AT,
                lastProcessedDate,
                lastProcessedId,
                true
        );
    }

}
