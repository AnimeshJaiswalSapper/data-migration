package ai.sapper.migration.DataMigration.model.postgres;

import ai.sapper.migration.DataMigration.common.postgres.BaseEntity;
import ai.sapper.migration.DataMigration.constants.Status;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

@Getter
@Setter
@Slf4j
@JsonTypeInfo(
        use = JsonTypeInfo.Id.CLASS,
        include = JsonTypeInfo.As.PROPERTY,
        property = "@class"
)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Entity
@Table(name = "coa")
@Builder
@Component("coa_postgres")
public class COA extends BaseEntity {
    @Id
    @Column(name = "coa_id")
    protected String coaId;

    @Column(name = "name")
    private String name;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    public COA convert(Object mongoDocument) {
        try {
            if (mongoDocument instanceof ai.sapper.migration.DataMigration.model.mongo.COA mongoCOA) {

                COA coa = COA.builder()
                        .coaId(mongoCOA.getId())
                        .name(mongoCOA.getName())
                        .status(Status.valueOf(String.valueOf(mongoCOA.getStatus())))
                        .build();

                coa.setCreatedBy(mongoCOA.getCreatedBy());
                coa.setCreatedDate(convertToZonedDateTime(mongoCOA.getCreatedDate()));
                coa.setLastModifiedBy(mongoCOA.getLastModifiedBy());
                coa.setLastModifiedDate(convertToZonedDateTime(mongoCOA.getLastModifiedDate()));

                return coa;
            }
        }  catch (Exception e) {
            log.error("Error converting COA document: {}", e.getMessage(), e);
            throw e;
        }
        return null;
    }


    private ZonedDateTime convertToZonedDateTime(Date date) {
        if (date != null) {
            return ZonedDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        }
        return null;
    }

}
