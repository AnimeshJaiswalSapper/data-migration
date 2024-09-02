package ai.sapper.migration.DataMigration.s3migration.service;

import ai.sapper.migration.DataMigration.constants.SpDocumentType;
import ai.sapper.migration.DataMigration.model.mongo.AuditEntity;
import ai.sapper.migration.DataMigration.model.postgres.Document;
import ai.sapper.migration.DataMigration.s3migration.utils.Persist;
import ai.sapper.migration.DataMigration.s3migration.utils.S3FileService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.unifiedframework.model.block.CaseDocument;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static ai.sapper.migration.DataMigration.s3migration.utils.Constants.*;

@Service
@Slf4j
public class MigrateNormalization {
    @Autowired
    private Persist persist;
    @Autowired
    private S3FileService s3FileService;

    private String caseId;
    private String createdBy;
    private Long createdDate;

    public void migrate(AuditEntity entity, List<String> failed) {
        this.caseId = entity.getCaseId();
        this.createdBy = entity.getCreatedBy();
        this.createdDate = entity.getCreatedAt().getTime();

        try {
            CaseDocument doc = entity.getDocument();
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(doc);
            String path = uploadToS3(json,NORMALIZATION);
            addDocumentAndArtefact(path, SpDocumentType.NormalizationOutput.toString());
        }catch (Exception e){
            log.error(e.getMessage());
            failed.add(caseId);
        }
    }

    public String uploadToS3(String json, String folderName){
        String destinationPath = CASEPATH + caseId + folderName + "normalized.json";
        return s3FileService.upload(json,destinationPath);
    }

    public void addDocumentAndArtefact(String path,String docType){
        Document document = persist.addDocument(caseId,
                "normalized.json",
                path,
                docType,
                "JSON",
                createdBy,
                null,
                createdDate,
                null
        );

        if (document != null) {
            persist.addArtefact(caseId, document.getDocId());
        }
    }
}
