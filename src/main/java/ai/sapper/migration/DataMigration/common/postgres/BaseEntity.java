
package ai.sapper.migration.DataMigration.common.postgres;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.ZonedDateTime;

@Getter
@Setter
@MappedSuperclass
public class BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_date")
    private ZonedDateTime createdDate;

    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    @Column(name = "last_modified_date")
    private ZonedDateTime lastModifiedDate;


}
