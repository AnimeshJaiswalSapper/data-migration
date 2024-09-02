package ai.sapper.migration.DataMigration.model.mongo;

import ai.sapper.migration.DataMigration.service.mongo.ReadService;
import ai.sapper.migration.DataMigration.common.mongo.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

import static ai.sapper.migration.DataMigration.constants.Collections.*;


@Document
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString(callSuper = true)
@Component
@Getter
public class CaseMerge extends BaseEntity {
    @Id
    @Indexed(unique = true)
    protected String id;

    private String oldCaseId;
    private String mergeCaseId;

    @Autowired
    ReadService readService;

    public List<CaseMerge> read(Date lastProcessedDate, String lastProcessedId) {
        return readService.findDocumentsSorted(CaseMerge.class,
                "caseMerge",
                CREATED_DATE,
                lastProcessedDate,
                lastProcessedId,
                true
        );
    }

}
