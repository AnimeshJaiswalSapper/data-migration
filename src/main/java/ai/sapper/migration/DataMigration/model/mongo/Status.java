package ai.sapper.migration.DataMigration.model.mongo;

import ai.sapper.migration.DataMigration.service.mongo.ReadService;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;
import static ai.sapper.migration.DataMigration.constants.Collections.*;


import java.util.Date;
import java.util.List;

@Document(collection = "status")
@Component
@ToString
@Getter
@Setter
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
                ID,
                lastProcessedDate,
                lastProcessedId,
                false
        );
    }
}
