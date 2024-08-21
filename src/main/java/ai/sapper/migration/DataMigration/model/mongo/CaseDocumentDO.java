package ai.sapper.migration.DataMigration.model.mongo;

import ai.sapper.migration.DataMigration.Repository.ReadService;
import ai.sapper.migration.DataMigration.common.BaseEntity;
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

import java.util.List;

@Data
@NoArgsConstructor
@Document("case_document")
@CompoundIndexes({ @CompoundIndex(name = "caseId_type", def = "{'caseId':1, 'type':1}", unique = true)})
@JsonIgnoreProperties(ignoreUnknown = true)
@Component
@ToString(callSuper = true)
public class CaseDocumentDO extends BaseEntity{

    private String id;
    private CaseDocument caseDocument;
    private String caseId;
    private CaseType type;

    @Autowired
    @JsonIgnore
    ReadService readService;

    public List<CaseDocumentDO> read(String lastProcessedId) {
        return  readService.findDocumentsSorted(CaseDocumentDO.class,
                "case_document",
                "createdDate",
                true,
                lastProcessedId
        );
    }

    public CaseDocumentDO(String id , CaseDocument caseDocument, String caseId)
    {
        this.id = id;
        this.caseId = caseDocument.getCaseId();
        this.caseDocument = caseDocument;
    }

    public CaseDocumentDO(CaseDocument caseDocument, String caseId , CaseType type)
    {
        this.caseId = caseDocument.getCaseId();
        this.caseDocument = caseDocument;
        this.type = type;
    }

    public CaseDocumentDO(String id , CaseDocument caseDocument)
    {
        this.id = id;
        this.caseId = caseDocument.getCaseId();
        this.caseDocument = caseDocument;
    }

    public CaseDocumentDO(CaseDocument caseDocument)
    {
        this.caseId = caseDocument.getCaseId();
        this.caseDocument = caseDocument;
    }

    public CaseDocumentDO(CaseDocument caseDocument, CaseType type)
    {
        this.type = type;
        this.caseId = caseDocument.getCaseId();
        this.caseDocument = caseDocument;
    }
}
