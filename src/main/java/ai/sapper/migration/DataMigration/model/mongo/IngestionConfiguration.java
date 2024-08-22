package ai.sapper.migration.DataMigration.model.mongo;

import ai.sapper.migration.DataMigration.service.mongo.ReadService;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static ai.sapper.migration.DataMigration.constants.Collections.*;



@Document(collection = "ingestion.config")
@Component
@ToString
public class IngestionConfiguration implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    private String sourceBucket;

    private String sourcePath;

    private String destinationBucket;

    private String destinationPath;

    private String sourceType;

    private String destinationType;

    private String publishDestination;

    private String pollerName;

    private String createdBy;

    @CreatedDate
    private Date createdAt;

    @LastModifiedDate
    private Date modifiedAt;

    private List<String> allowedExtensions;

    private Integer priority;

    private List<String> priorityExtensions;

    private Map<String, String> properties;

    @Autowired
    ReadService readService;

    public List<IngestionConfiguration> read(Date lastProcessedDate, String lastProcessedId) {
        return  readService.findDocumentsSorted(IngestionConfiguration.class,
                "ingestion.config",
                CREATED_AT,
                lastProcessedDate,
                lastProcessedId,
                true
        );
    }

}
