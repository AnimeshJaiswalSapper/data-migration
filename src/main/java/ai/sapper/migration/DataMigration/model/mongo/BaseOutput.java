package ai.sapper.migration.DataMigration.model.mongo;

import ai.sapper.migration.DataMigration.service.mongo.ReadService;
import ai.sapper.migration.DataMigration.common.output.File;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Document("BaseOutput")
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@JsonIgnoreProperties(ignoreUnknown = true)
@Component
public class BaseOutput {

    private String runName;
    private String project;
    private List<File> files;


    @Autowired
    ReadService readService;
    public List<BaseOutput> read(String lastProcessedId) {
        return  readService.findDocumentsSorted(BaseOutput.class,
                "BaseOutput",
                "id",
                true,
                lastProcessedId
        );
    }

}
