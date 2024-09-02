package ai.sapper.migration.DataMigration.s3migration.service;

import ai.sapper.migration.DataMigration.Repository.mongo.S3MigrationRepository;
import ai.sapper.migration.DataMigration.constants.CaseType;
import ai.sapper.migration.DataMigration.model.mongo.*;
import ai.sapper.migration.DataMigration.service.mongo.ReadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static ai.sapper.migration.DataMigration.constants.CaseType.EXTRACTION;
import static ai.sapper.migration.DataMigration.constants.Collections.CREATED_DATE;

@Service
@Slf4j
public class S3migrationService {
    @Autowired
    S3MigrationRepository s3MigrationRepository;
    @Autowired
    private ReadService readService;

    @Autowired
    private MigrateToOriginal migrateToOriginal;

    @Autowired
    private AuditEntity auditEntity;

    @Autowired
    private MigrateUDP migrateUDP;

    @Autowired
    private MigrateNormalization migrateNormalization;

    @Autowired
    private RuleRuntimeData ruleRuntimeData;

    @Autowired
    private MigrateQAcheck migrateQAcheck;

    @Autowired
    private OriginalPDFData originalPDFData;

    @Autowired
    private MigrateParsingData migrateParsingData;

    public void s3migration() {

        S3Migration s3Migration = s3MigrationRepository.findByCollectionName("Case");
        Date lastProcessedDate = s3Migration != null ? s3Migration.getLastProcessedDate() : null;
        String lastProcessedId = s3Migration != null ? s3Migration.getLastProcessedId() : null;

        List<Case> cases = readService.findDocumentsSorted(Case.class,
                "case",
                CREATED_DATE,
                lastProcessedDate,
                lastProcessedId,
                true
        );

        List<String> failedCases = new ArrayList<>();


        for (Case c : cases) {
                /* ----------- MIGRATE TO ORIGINAL ------- */

                migrateToOriginal.migrate(c,failedCases);

                /* ------------ DOC PARSER IMAGE ---------- */

                List<OriginalPDFData> originalPDFDataList = originalPDFData.readByCaseId(c.getId());
                migrateParsingData.migrate(originalPDFDataList,failedCases);


                /* ------------ UDP JSON MIGRATION -------- */

                List<AuditEntity> entities1 = auditEntity.readByCaseId(c.getId(), EXTRACTION.toString());

                if(!entities1.isEmpty()){
                    migrateUDP.migrateInitialUDP(entities1.get(0),failedCases);
                    migrateUDP.migrateFinalUDP(entities1.get(entities1.size()-1),failedCases);
                }

                /* -------- NORMALIZATION JSON MIGRATION -------*/

                List<AuditEntity> entities2 = auditEntity.readByCaseId(c.getId(), CaseType.NORMALIZATION.toString());

                if(!entities2.isEmpty())
                    migrateNormalization.migrate(entities2.get(entities2.size()-1),failedCases);

                /* -------- QA-CHECK JSON MIGRATION -------- */

                RuleRuntimeData data = ruleRuntimeData.readByCaseId(c.getId());
                if(data!=null)
                    migrateQAcheck.migrate(data,failedCases);
        }

        updateOrSaveS3Migration(cases.get(cases.size()-1),s3Migration,failedCases);
    }

    private void updateOrSaveS3Migration(Case c, S3Migration s3migration, List<String> failedDocs) {
        try {
            String processedId = c.getId();
            Date processedDate = c.getCreatedDate();

            if (s3migration != null) {
                updateS3Migration(processedId, processedDate, failedDocs, s3migration);
            } else {
                saveS3Migration(processedId, failedDocs, processedDate);
            }
        } catch (Exception e) {
            log.error("Error updating/saving DataMigration : {}", e.getMessage(), e);
        }
    }

    private void updateS3Migration(String processedId, Date processedDate, List<String> failedDocs, S3Migration existingS3Migration) {
        existingS3Migration.setLastProcessedId(processedId);
        existingS3Migration.setLastProcessedDate(processedDate);
        existingS3Migration.getFailedDocs().addAll(failedDocs);
        s3MigrationRepository.save(existingS3Migration);
    }

    private void saveS3Migration(String processedId, List<String> failedDocs, Date processedDate) {
        S3Migration s3Migration = new S3Migration();
        s3Migration.setCollectionName("Case");
        s3Migration.setLastProcessedId(processedId);
        s3Migration.setLastProcessedDate(processedDate);
        s3Migration.setFailedDocs(failedDocs);
        s3MigrationRepository.save(s3Migration);
    }
}
