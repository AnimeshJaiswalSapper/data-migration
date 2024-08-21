package ai.sapper.migration.DataMigration.model.mongo;

import ai.sapper.migration.DataMigration.common.BaseEntity;
import ai.sapper.migration.DataMigration.service.mongo.ReadService;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Entity extends BaseEntity {
    private static final long serialVersionUID = 1L;
    @Id
    @Indexed(unique = true)
    protected String id;
    private String name;

    @Autowired
    ReadService readService;

    public List<Entity> read(Date lastProcessedDate, String lastProcessedId) {
        return  readService.findDocumentsSorted(Entity.class,
                "entity",
                "_id",
                true,
                lastProcessedDate,
                lastProcessedId,
                false
        );
    }
}

