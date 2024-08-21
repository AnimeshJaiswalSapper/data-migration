package ai.sapper.migration.DataMigration.common.output;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document("BaseOutput")
@JsonIgnoreProperties(ignoreUnknown = true)
public class BaseOutput {

    private String runName;
    private String project;
    private List<File> files;

}