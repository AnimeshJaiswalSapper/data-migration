package ai.sapper.migration.DataMigration.model.mongo;

import ai.sapper.migration.DataMigration.service.mongo.ReadService;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Document("form_norm_template_mapping")
@Component
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
    public List<TemplateMapping> read(String lastProcessedId) {
        return  readService.findDocumentsSorted(TemplateMapping.class,
                "form_norm_template_mapping",
                "_id",
                true,
                lastProcessedId
        );
    }
}
