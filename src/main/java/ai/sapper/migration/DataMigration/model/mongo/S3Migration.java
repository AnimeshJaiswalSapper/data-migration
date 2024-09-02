package ai.sapper.migration.DataMigration.model.mongo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@Document("s3_migration")
public class S3Migration {
    @Id
    private String id;
    private String collectionName;
    private String lastProcessedId;
    private Date lastProcessedDate;
    private List<Object> failedDocs;

}

