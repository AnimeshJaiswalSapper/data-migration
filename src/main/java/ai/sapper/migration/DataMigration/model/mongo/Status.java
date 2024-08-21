package ai.sapper.migration.DataMigration.model.mongo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "status")
@Data
public class Status {
    @Id
    private String id;
    private String taskId;
    private String username;
    private String status;
}
