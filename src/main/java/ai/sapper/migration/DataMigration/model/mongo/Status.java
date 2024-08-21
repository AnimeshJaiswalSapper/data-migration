package ai.sapper.migration.DataMigration.model.mongo;

import ai.sapper.migration.DataMigration.service.mongo.ReadService;
import lombok.Data;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Document(collection = "status")
@Data
@Component
@ToString(callSuper = true)
public class Status {
    @Id
    private String id;
    private String taskId;
    private String username;
    private String status;

    @Autowired
    ReadService readService;

    public List<Status> read(Date lastProcessedDate, String lastProcessedId) {
        return  readService.findDocumentsSorted(Status.class,
                "status",
                "id",
                true,
                lastProcessedDate,
                lastProcessedId,
                false
        );
    }
}
