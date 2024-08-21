package ai.sapper.migration.DataMigration.model.mongo;

import ai.sapper.migration.DataMigration.common.BaseEntity;
import ai.sapper.migration.DataMigration.constants.ConfigLevel;
import ai.sapper.migration.DataMigration.constants.ConfigType;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Document("config")
@Data
public class Config extends BaseEntity {
    private static final long serialVersionUID = 1L;
    private ConfigType type;
    private ConfigLevel level;
    private boolean status;
    private String userId;
    private Map<String, ?> meta;
}
