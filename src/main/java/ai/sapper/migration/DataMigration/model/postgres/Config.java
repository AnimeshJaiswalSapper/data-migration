package ai.sapper.migration.DataMigration.model.postgres;

import ai.sapper.migration.DataMigration.constants.ConfigLevel;
import ai.sapper.migration.DataMigration.constants.ConfigType;
import ai.sapper.migration.DataMigration.constants.Status;
import ai.sapper.migration.DataMigration.convertor.JsonbConverter;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Map;

@Getter
@Setter
@Builder
@Slf4j
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Entity
@Component("config_postgres")
@Table(name = "config")
public class Config {
    private static final long serialVersionUID = 1L;
    @Id
    private String id;
    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private ConfigType type;
    @Enumerated(EnumType.STRING)
    @Column(name = "level")
    private ConfigLevel level;
    @Column(name = "status")
    private boolean status;
    @Column(name = "user_id")
    private String userId;
    @Column(name = "meta", columnDefinition = "jsonb")
    @Convert(converter = JsonbConverter.class)
    @ColumnTransformer(write = "?::jsonb")
    private Map<String, Object> meta;
    @CreatedBy
    @Column(name = "created_by")
    private String createdBy;
    @CreatedDate
    @Column(name = "created_date")
    private ZonedDateTime createdDate;
    @LastModifiedBy
    @Column(name = "last_modified_by")
    private String lastModifiedBy;
    @Column(name = "last_modified_date")
    @Builder.Default
    private ZonedDateTime lastModifiedDate = ZonedDateTime.now();

    public Config convert(Object mongoDocument) {
        try {
            if (mongoDocument instanceof ai.sapper.migration.DataMigration.model.mongo.Config mongoConfig) {

                Config config = Config.builder()
                        .id(mongoConfig.getId())
                        .type(mongoConfig.getType())
                        .level(mongoConfig.getLevel())
                        .status(mongoConfig.isStatus())
                        .userId(mongoConfig.getUserId())
                        .meta(mongoConfig.getMeta())
                        .build();

                config.setCreatedBy(mongoConfig.getCreatedBy());
                config.setCreatedDate(convertToZonedDateTime(mongoConfig.getCreatedDate()));
                config.setLastModifiedBy(mongoConfig.getLastModifiedBy());
                config.setLastModifiedDate(convertToZonedDateTime(mongoConfig.getLastModifiedDate()));

                return config;
            }
        }  catch (Exception e) {
            log.error("Error converting Config document: {}", e.getMessage(), e);
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
