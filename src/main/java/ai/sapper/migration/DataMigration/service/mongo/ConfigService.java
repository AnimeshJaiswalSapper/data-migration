//package ai.sapper.migration.DataMigration.service.mongo;
//
//import ai.sapper.migration.DataMigration.Repository.mongo.ConfigRepository;
//import ai.sapper.migration.DataMigration.common.Migration;
//import ai.sapper.migration.DataMigration.model.mongo.Config;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//@Slf4j
//public class ConfigService implements Migration {
//    @Autowired
//    ConfigRepository configRepository;
//    @Override
//    public void migrate() {
//        List<Config> configs = configRepository.findAll();
//        log.info("size of status data got : {}", configs.size());
//        log.info("STATUS [{}]",configs.get(0).toString());
//    }
//}
