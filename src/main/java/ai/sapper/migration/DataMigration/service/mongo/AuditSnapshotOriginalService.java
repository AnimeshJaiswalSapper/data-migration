package ai.sapper.migration.DataMigration.service.mongo;

import ai.sapper.migration.DataMigration.Repository.mongo.AuditSnapshotOriginalRepository;
import ai.sapper.migration.DataMigration.common.Migration;
import ai.sapper.migration.DataMigration.model.mongo.AuditSnapshot;
import ai.sapper.migration.DataMigration.model.mongo.AuditSnapshotOriginal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class AuditSnapshotOriginalService implements Migration {
    @Autowired
    AuditSnapshotOriginalRepository auditSnapshotOriginalRepository;
    @Override
    public void migrate() {
        Pageable firstTen = PageRequest.of(0, 10);
        List<AuditSnapshotOriginal> originalSnapshots = auditSnapshotOriginalRepository.findAll(firstTen).getContent();
        log.info("size of original audit snapshot data got : {}", originalSnapshots.size());
        log.info("snapshot [{}]",originalSnapshots.get(0).toString());
    }
}
