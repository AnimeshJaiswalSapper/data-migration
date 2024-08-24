package ai.sapper.migration.DataMigration.common.postgres;

import lombok.Data;

@Data
public class Condition {
    private String operator;
    private String value;
}
