package ai.sapper.migration.DataMigration.service.mongo;

import ai.sapper.migration.DataMigration.Repository.COARepository;
import ai.sapper.migration.DataMigration.Repository.CaseRepository;
import ai.sapper.migration.DataMigration.common.Migration;
import ai.sapper.migration.DataMigration.model.mongo.COA;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class COAService implements Migration{
    @Autowired
    COARepository coaRepository;

    @Override
    public void migrate() {
        log.info("Inside Migrate method");
        COA coa = coaRepository.findByName("MultiFamily_COA");
        log.info("GOT COA : {}", coa);
        log.info("Outside Migrate method");
    }

}
