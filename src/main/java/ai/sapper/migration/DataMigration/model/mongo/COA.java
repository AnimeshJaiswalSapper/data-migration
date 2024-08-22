package ai.sapper.migration.DataMigration.model.mongo;

import ai.sapper.migration.DataMigration.service.mongo.ReadService;
import ai.sapper.migration.DataMigration.common.BaseEntity;
import ai.sapper.migration.DataMigration.constants.Status;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;



@Document("coa")
@ToString(callSuper = true)
@Component
public class COA extends BaseEntity {
    @Id
    protected String id;
    private String name;
    private Status status;

    @Autowired
    ReadService readService;

    public List<COA> read(Date lastProcessedDate, String lastProcessedId) {
        return  readService.findDocumentsSorted(COA.class,
                "coa",
                "createdDate",
                lastProcessedDate,
                lastProcessedId,
                true
        );
    }
}
