package ai.sapper.migration.DataMigration.model.mongo;

import ai.sapper.migration.DataMigration.service.mongo.ReadService;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;
import static ai.sapper.migration.DataMigration.constants.Collections.*;


import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.List;


@Document("form_norm_template_mapping")
@Component
@ToString
public class TemplateMapping implements Serializable {


    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @Indexed(unique = true)
    private String id;

    private String formTemplate;
    private String normTemplate;

    @Autowired
    ReadService readService;

    public List<TemplateMapping> read(Date lastProcessedDate, String lastProcessedId) {
        return  readService.findDocumentsSorted(TemplateMapping.class,
                "form_norm_template_mapping",
                ID,
                lastProcessedDate,
                lastProcessedId,
                false
        );
    }


}
