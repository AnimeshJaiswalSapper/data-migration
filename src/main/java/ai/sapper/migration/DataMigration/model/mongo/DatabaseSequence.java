package ai.sapper.migration.DataMigration.model.mongo;

import ai.sapper.migration.DataMigration.service.mongo.ReadService;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Document(collection = "database_sequences")
@Component
@ToString
public class DatabaseSequence {
    @Id
    private String id;
    private long seq;

    @Autowired
    ReadService readService;

    public List<DatabaseSequence> read(Date lastProcessedDate, String lastProcessedId) {
        return  readService.findDocumentsSorted(DatabaseSequence.class,
                "database_sequences",
                "id",
                true,
                lastProcessedDate,
                lastProcessedId,
                false
        );
    }
}

