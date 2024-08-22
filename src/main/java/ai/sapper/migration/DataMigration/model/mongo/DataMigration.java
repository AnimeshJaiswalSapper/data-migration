package ai.sapper.migration.DataMigration.model.mongo;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Document("dataMigration")
public class DataMigration {
    @Id
    private String id;
    private String collectionName;
    private String lastProcessedId;
    private Date lastProcessedDate;
    private List<String> failedIds;

}
