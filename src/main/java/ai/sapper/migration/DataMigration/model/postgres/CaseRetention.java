package ai.sapper.migration.DataMigration.model.postgres;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CaseRetention {
    private Boolean fileDeleted;
    private Long fileDeletedAt;
}
