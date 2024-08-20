package ai.sapper.migration.DataMigration.model.mongo;

import lombok.Data;

@Data
public class Condition {
    private String operator;
    private String value;
}
