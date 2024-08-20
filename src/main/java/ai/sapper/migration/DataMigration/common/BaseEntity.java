package ai.sapper.migration.DataMigration.common;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.index.Indexed;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Date;

@Getter
@Setter
public class BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Indexed(unique = true)
    protected String id;
    @CreatedBy
    private String createdBy;
    @CreatedDate
    private Date createdDate;
    @LastModifiedBy
    private String lastModifiedBy;
    @LastModifiedDate
    private Date lastModifiedDate;
}
