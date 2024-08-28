package ai.sapper.migration.DataMigration.model.postgres;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CaseRetention {

//    @Column(name = "file_deleted")
    private Boolean fileDeleted;

//    @Column(name = "file_deleted_at")
    private Long fileDeletedAt;

}
