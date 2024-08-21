package ai.sapper.migration.DataMigration.model.mongo;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@ToString(callSuper = true)
@Document(collection = "audit.snapshot.original")
public class AuditSnapshotOriginal extends AuditSnapshot {

    @LastModifiedBy
    private String modifiedBy;

    @LastModifiedDate
    private LocalDateTime modifiedAt;

}
