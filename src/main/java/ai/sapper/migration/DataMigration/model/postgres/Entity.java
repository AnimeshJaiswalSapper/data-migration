package ai.sapper.migration.DataMigration.model.postgres;

import ai.sapper.migration.DataMigration.Repository.postgres.PostgresRepository;
import ai.sapper.migration.DataMigration.common.postgres.BaseEntity;
import ai.sapper.migration.DataMigration.constants.ConfigLevel;
import ai.sapper.migration.DataMigration.constants.ConfigType;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Getter
@Setter
@Builder
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY,
        property = "@class")
@Slf4j
@Component("entity_postgres")
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Entity extends BaseEntity {

    private String id;
    private String name;


    @Autowired
    private PostgresRepository postgresRepository;

    public Config checkConfig() {
        Config config = postgresRepository.findConfigByType(ConfigType.COMPANY);

        if (config != null) {
            return config;

        } else {
            System.out.println("No Config with type 'COMPANY' found.");
            return null;
        }
    }

    public Config convert(Object mongoDocument) {
        try {
            if (mongoDocument instanceof ai.sapper.migration.DataMigration.model.mongo.Entity mongoConfig) {

                Config entity = checkConfig();
                if(entity == null) {
                        entity = Config.builder()
                            .id(UUID.randomUUID().toString())
                            .status(true)
                            .type(ConfigType.COMPANY)
                            .level(ConfigLevel.SYSTEM)
                            .userId("CASE_COMPANY")
                            .build();

                    List<Map<String, Object>> dataList = new ArrayList<>();

                    Map<String, Object> dataMap = new HashMap<>();
                    dataMap.put("id", mongoConfig.getId());
                    dataMap.put("name", mongoConfig.getName());

                    dataList.add(dataMap);

                    Map<String, Object> meta = new HashMap<>();
                    meta.put("data", dataList);

                    entity.setMeta(meta);
                    postgresRepository.save(entity);
                }else{
                    List<Map<String, Object>> dataList = (List<Map<String, Object>>) entity.getMeta().get("data");

                    Map<String, Object> newMetadata = Map.of(
                            "id",mongoConfig.getId(),
                            "name",mongoConfig.getName()
                    );

                    dataList.add(newMetadata);

                    postgresRepository.update(entity);

                }
                return entity;
            }
        }  catch (Exception e) {
            log.error("Error converting Entity document: {}", e.getMessage(), e);
            throw e;
        }
        return null;
    }
}
