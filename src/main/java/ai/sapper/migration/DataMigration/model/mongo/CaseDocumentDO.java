package ai.sapper.migration.DataMigration.model.mongo;

import ai.sapper.migration.DataMigration.service.mongo.ReadService;
import ai.sapper.migration.DataMigration.common.mongo.BaseEntity;
import ai.sapper.migration.DataMigration.constants.CaseType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.unifiedframework.model.block.CaseDocument;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

import static ai.sapper.migration.DataMigration.constants.Collections.*;


@Document("case_document")
@CompoundIndexes({@CompoundIndex(name = "caseId_type", def = "{'caseId':1, 'type':1}", unique = true)})
@JsonIgnoreProperties(ignoreUnknown = true)
@Component
@Data
@ToString(callSuper = true)
public class CaseDocumentDO extends BaseEntity {

    private String id;
    private CaseDocument caseDocument;
    private String caseId;
    private CaseType type;

    @Autowired
    @JsonIgnore
    ReadService readService;

    public List<CaseDocumentDO> read(Date lastProcessedDate, String lastProcessedId) {
        return readService.findDocumentsSorted(CaseDocumentDO.class,
                "case_document",
                CREATED_DATE,
                lastProcessedDate,
                lastProcessedId,
                true
        );
    }
}
