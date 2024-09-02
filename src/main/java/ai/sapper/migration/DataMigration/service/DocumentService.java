package ai.sapper.migration.DataMigration.service;

import ai.sapper.migration.DataMigration.Repository.postgres.PostgresRepository;
import ai.sapper.migration.DataMigration.model.postgres.CaseDocumentDO;
import ai.sapper.migration.DataMigration.model.postgres.CaseMerge;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Method;
import java.util.List;

@Service
@Slf4j
public class DocumentService {

    @Autowired
    private PostgresRepository postgresRepository;

    @Value("${isSkip}")
    private boolean isSkip;

    @Transactional(rollbackFor = Exception.class)
    public void saveDocuments(Object postgresModelObj, List<Object> fetchedDocuments, List<Object> failedDocs, String collection) throws Exception {
        Method convertMethod = postgresModelObj.getClass().getMethod("convert", Object.class);
        for (Object document : fetchedDocuments) {
            try{
                Object postgresEntity = convertMethod.invoke(postgresModelObj, document);
                if (postgresEntity != null) {
                    if ("RuleRuntimeData".equals(collection)) {
                        CaseDocumentDO caseDocumentDO = (CaseDocumentDO) postgresEntity;
                        postgresRepository.saveOrUpdateRuleRuntimeData(caseDocumentDO);
                    }else if("CaseDocumentDO".equals(collection)){
                        CaseDocumentDO caseDocumentDO = (CaseDocumentDO) postgresEntity;
                        postgresRepository.saveOrUpdateCaseDocumentDO(caseDocumentDO);
                    } else if("CaseMerge".equals(collection)){
                        CaseMerge caseMerge = (CaseMerge) postgresEntity;
                        postgresRepository.saveOrUpdateCaseMerge(caseMerge);
                    }else if (!"Entity".equals(collection)) {
                        postgresRepository.save(postgresEntity);
                    }
                }
            }catch (Exception e){
                failedDocs.add(document);
                log.error("Unable to save the document [{}] for collection [{}] due to exception [{}]", document, collection, e.getMessage(), e);
                if (!isSkip) {
                    throw new RuntimeException("Transaction failed and was rolled back due to a save error.", e);
                }
            }
        }
    }

}
