package ai.sapper.migration.DataMigration.model.mongo;

import ai.sapper.migration.DataMigration.common.BaseEntity;
import ai.sapper.migration.DataMigration.constants.CaseType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.unifiedframework.model.block.CaseDocument;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

@Getter
@Setter
@NoArgsConstructor
@Document("case_document")
@CompoundIndexes({ @CompoundIndex(name = "caseId_type", def = "{'caseId':1, 'type':1}", unique = true)})
@JsonIgnoreProperties(ignoreUnknown = true)
@Component
@ToString
@Slf4j
public class CaseDocumentDO extends BaseEntity{

    private String id;
    private CaseDocument caseDocument;
    private String caseId;
    private CaseType type;

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
