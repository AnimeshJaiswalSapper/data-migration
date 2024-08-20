package ai.sapper.migration.DataMigration.model.mongo;

import ai.sapper.migration.DataMigration.common.BaseEntity;
import ai.sapper.migration.DataMigration.constants.Status;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Document("coa")
@ToString
public class COA extends BaseEntity {

    private String name;
    private Status status;
}
