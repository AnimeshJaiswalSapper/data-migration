package ai.sapper.migration.DataMigration.service.mongo;

import ai.sapper.migration.DataMigration.Repository.mongo.StatusRepository;
import ai.sapper.migration.DataMigration.common.Migration;
import ai.sapper.migration.DataMigration.model.mongo.Status;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class StatusService implements Migration {
    @Autowired
    StatusRepository statusRepository;


    @Override
    public void migrate() {
        List<Status> status = statusRepository.findAll();
        log.info("size of status data got : {}", status.size());
        log.info("STATUS [{}]",status.get(0).toString());
    }
}
