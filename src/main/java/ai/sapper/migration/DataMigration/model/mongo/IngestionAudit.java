package ai.sapper.migration.DataMigration.model.mongo;

import ai.sapper.migration.DataMigration.service.mongo.ReadService;
import ai.sapper.migration.DataMigration.constants.IngestionState;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Document(collection = "ingestion.audit")
@Component
public class IngestionAudit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String ingestionId;

    private String sourcePath;

    private String destinationPath;

    private String sourceType;

    private IngestionState ingestionState;

    private Map<String, String> stateHistory;

    private Map<String, String> metadata;

    private String createdBy;

    @CreatedDate
    private LocalDateTime ingestionTime;

    @Autowired
    ReadService readService;

    public List<IngestionAudit> read(Date lastProcessedDate, String lastProcessedId) {
        return  readService.findDocumentsSorted(IngestionAudit.class,
                "ingestion.audit",
                "_id",
                true,
                lastProcessedDate,
                lastProcessedId,
                false
        );
    }

}
