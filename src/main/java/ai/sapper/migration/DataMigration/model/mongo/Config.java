package ai.sapper.migration.DataMigration.model.mongo;

import ai.sapper.migration.DataMigration.service.mongo.ReadService;
import ai.sapper.migration.DataMigration.common.mongo.BaseEntity;
import ai.sapper.migration.DataMigration.constants.ConfigLevel;
import ai.sapper.migration.DataMigration.constants.ConfigType;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;

import static ai.sapper.migration.DataMigration.constants.Collections.*;

@Document("config")
@Component
@ToString(callSuper = true)
public class Config extends BaseEntity {
    private static final long serialVersionUID = 1L;
    @Id
    protected String id;
    private ConfigType type;
    private ConfigLevel level;
    private boolean status;
    private String userId;
    private Map<String, ?> meta;

    @Autowired
    ReadService readService;

    public List<Config> read(Date lastProcessedDate, String lastProcessedId) {
        return  readService.findDocumentsSorted(Config.class,
                "config",
                LAST_MODIFIED_DATE,
                lastProcessedDate,
                lastProcessedId,
                true
        );
    }
}
