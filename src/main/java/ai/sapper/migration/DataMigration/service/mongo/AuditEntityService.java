//package ai.sapper.migration.DataMigration.service.mongo;
//
//import ai.sapper.migration.DataMigration.Repository.mongo.AuditEntityRepository;
//import ai.sapper.migration.DataMigration.common.Migration;
//import ai.sapper.migration.DataMigration.model.mongo.AuditEntity;
//import ai.sapper.migration.DataMigration.model.mongo.Status;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//@Slf4j
//public class AuditEntityService implements Migration {
//
//    @Autowired
//    AuditEntityRepository auditEntityRepository;
//    @Override
//    public void migrate() {
//        Pageable firstTen = PageRequest.of(0, 10);
//        List<AuditEntity> entities = auditEntityRepository.findAll(firstTen).getContent();
//        log.info("size of audit entities data got : {}", entities.size());
//        log.info("Entity [{}]",entities.get(0).toString());
//    }
//}
