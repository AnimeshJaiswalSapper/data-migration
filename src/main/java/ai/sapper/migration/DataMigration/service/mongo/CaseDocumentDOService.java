package ai.sapper.migration.DataMigration.service.mongo;

import ai.sapper.migration.DataMigration.Repository.CaseDocumentRepository;
import ai.sapper.migration.DataMigration.common.Migration;
import ai.sapper.migration.DataMigration.constants.CaseType;
import ai.sapper.migration.DataMigration.model.mongo.CaseDocumentDO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class CaseDocumentDOService implements Migration {

    @Autowired
    CaseDocumentRepository caseDocumentRepository;

    @Override
    public void migrate() {
        log.info("Inside migrate method");
        Optional<CaseDocumentDO> optionalCaseDocumentDO = caseDocumentRepository.findByCaseIdAndType("C2308-2516", CaseType.valueOf("NORMALIZATION"));
        if (optionalCaseDocumentDO.isEmpty()) {
            log.info("Empty caseDocument got");
            return;
        }
        CaseDocumentDO caseDocumentDO = optionalCaseDocumentDO.get();
        log.info("Case Document got is {}",caseDocumentDO);
        log.info("Outside migrate method");

    }
}
