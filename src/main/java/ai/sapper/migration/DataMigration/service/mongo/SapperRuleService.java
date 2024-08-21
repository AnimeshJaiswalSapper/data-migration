//package ai.sapper.migration.DataMigration.service.mongo;
//
//import ai.sapper.migration.DataMigration.Repository.mongo.SapperRuleRepository;
//import ai.sapper.migration.DataMigration.common.Migration;
//import ai.sapper.migration.DataMigration.model.mongo.SapperRule;
//import ai.sapper.migration.DataMigration.model.mongo.Status;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//@Slf4j
//public class SapperRuleService implements Migration {
//
//    @Autowired
//    SapperRuleRepository sapperRuleRepository;
//
//
//    @Override
//    public void migrate() {
//        List<SapperRule> rules = sapperRuleRepository.findAll();
//        log.info("size of rules data got : {}", rules.size());
//        log.info("RULE : [{}]",rules.get(0).toString());
//    }
//}
