//package ai.sapper.migration.DataMigration.service.mongo;
//
//import ai.sapper.migration.DataMigration.Repository.mongo.AuditSnapshotRepository;
//import ai.sapper.migration.DataMigration.common.Migration;
//import ai.sapper.migration.DataMigration.model.mongo.AuditEntity;
//import ai.sapper.migration.DataMigration.model.mongo.AuditSnapshot;
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
//public class AuditSnapshotService implements Migration {
//
//    @Autowired
//    AuditSnapshotRepository auditSnapshotRepository;
//    @Override
//    public void migrate() {
//        Pageable firstTen = PageRequest.of(0, 10);
//        List<AuditSnapshot> snapshots = auditSnapshotRepository.findAll(firstTen).getContent();
//        log.info("size of audit snapshot data got : {}", snapshots.size());
//        log.info("Entity [{}]",snapshots.get(0).toString());
//    }
//}
