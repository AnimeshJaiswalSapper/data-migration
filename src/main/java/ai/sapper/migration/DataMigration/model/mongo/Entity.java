package ai.sapper.migration.DataMigration.model.mongo;

import ai.sapper.migration.DataMigration.common.mongo.BaseEntity;
import ai.sapper.migration.DataMigration.service.mongo.ReadService;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import static ai.sapper.migration.DataMigration.constants.Collections.*;


import java.util.Date;
import java.util.List;


@Document
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString(callSuper = true)
@Component
@Getter
public class Entity extends BaseEntity {
    private static final long serialVersionUID = 1L;
    @Id
    protected String id;

    private String name;

    @Autowired
    ReadService readService;

    public List<Entity> read(Date lastProcessedDate, String lastProcessedId) {
        return readService.findDocumentsSorted(Entity.class,
                "entity",
                LAST_MODIFIED_DATE,
                lastProcessedDate,
                lastProcessedId,
                true
        );
    }
}

