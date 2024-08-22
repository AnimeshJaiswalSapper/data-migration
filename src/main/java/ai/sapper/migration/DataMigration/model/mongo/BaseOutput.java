package ai.sapper.migration.DataMigration.model.mongo;

import ai.sapper.migration.DataMigration.service.mongo.ReadService;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import static ai.sapper.migration.DataMigration.constants.Collections.*;


@Document("BaseOutput")
@JsonIgnoreProperties(ignoreUnknown = true)
@Component
@ToString
public class BaseOutput {

    private String runName;
    private String project;
    private List<Object> files;

    @Autowired
    ReadService readService;

    public List<BaseOutput> read(Date lastProcessedDate, String lastProcessedId) {
        return  readService.findDocumentsSorted(BaseOutput.class,
                "BaseOutput",
                ID,
                lastProcessedDate,
                lastProcessedId,
                false
        );
    }

}
