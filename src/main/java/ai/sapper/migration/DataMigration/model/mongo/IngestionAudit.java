package ai.sapper.migration.DataMigration.model.mongo;

import ai.sapper.migration.DataMigration.constants.IngestionState;
import ai.sapper.migration.DataMigration.service.mongo.ReadService;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;
import static ai.sapper.migration.DataMigration.constants.Collections.*;


import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Document(collection = "ingestion.audit")
@Component
@ToString
public class IngestionAudit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    private String sourcePath;

    private String destinationPath;

    private String sourceType;

    private IngestionState ingestionState;

    private Map<String, String> stateHistory;

    private Map<String, String> metadata;

    private String createdBy;

    @CreatedDate
    private Date ingestionTime;

    @Autowired
    ReadService readService;

    public List<IngestionAudit> read(Date lastProcessedDate, String lastProcessedId) {
        return  readService.findDocumentsSorted(IngestionAudit.class,
                "ingestion.audit",
                ID,
                lastProcessedDate,
                lastProcessedId,
                false
        );
    }

}
