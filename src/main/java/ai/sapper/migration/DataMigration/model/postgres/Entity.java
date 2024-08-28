package ai.sapper.migration.DataMigration.model.postgres;

import ai.sapper.migration.DataMigration.common.postgres.BaseEntity;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

@Data
@Builder
@Slf4j
public class Entity extends BaseEntity {

    private String id;
    private String name;

    public Entity convert(Object mongoDocument) {
        try {
            if (mongoDocument instanceof ai.sapper.migration.DataMigration.model.mongo.Entity mongoConfig) {

                Entity entity = Entity.builder()
                        .id(mongoConfig.getId())
                        .name(mongoConfig.getName())
                        .build();

                return entity;
            }
        }  catch (Exception e) {
            log.error("Error converting Entity document: {}", e.getMessage(), e);
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
