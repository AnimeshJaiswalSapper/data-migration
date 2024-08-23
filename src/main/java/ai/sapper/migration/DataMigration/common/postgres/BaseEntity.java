
package ai.sapper.migration.DataMigration.common.postgres;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import java.io.Serializable;
import java.time.ZonedDateTime;

@Getter
@Setter
@MappedSuperclass
public class BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;


    @CreatedBy
    @Column(name = "created_by")
    private String createdBy;
    @CreationTimestamp
    @Column(name = "created_date")
    private ZonedDateTime createdDate;
    @LastModifiedBy
    @Column(name = "last_modified_by")
    private String lastModifiedBy;
    @UpdateTimestamp
    @Column(name = "last_modified_date")
    private ZonedDateTime lastModifiedDate = ZonedDateTime.now();


}
