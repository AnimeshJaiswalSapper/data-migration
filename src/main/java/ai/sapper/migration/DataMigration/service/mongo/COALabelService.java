package ai.sapper.migration.DataMigration.service.mongo;

import ai.sapper.migration.DataMigration.Repository.COALabelRepository;
import ai.sapper.migration.DataMigration.common.Migration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class COALabelService implements Migration {
    @Autowired
    COALabelRepository coaLabelRepository;

    @Override
    public void migrate() {
        log.info("Inside Migrate method");
//        List<Case> caseData = caseRepository.findAllByCoaId("64ca2d7c7e16e9677cebfba6");
//        log.info("Case Data got : {}",caseData);
//        log.info("size of case data got : {}", caseData.size());
//        log.info("Outside Migrate method");
    }
}
