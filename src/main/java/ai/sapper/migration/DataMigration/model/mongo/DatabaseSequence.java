package ai.sapper.migration.DataMigration.model.mongo;

import ai.sapper.migration.DataMigration.Repository.ReadService;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import java.util.List;

@Document(collection = "database_sequences")
@Data
@Component
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class DatabaseSequence {
    @Id
    private String id;
    private long seq;

    @Autowired
    ReadService readService;
    public List<DatabaseSequence> read(String lastProcessedId) {
        return  readService.findDocumentsSorted(DatabaseSequence.class,
                "database_sequences",
                "_id",
                true,
                lastProcessedId
        );
    }
}

