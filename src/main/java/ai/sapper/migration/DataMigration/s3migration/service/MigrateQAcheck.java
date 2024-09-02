package ai.sapper.migration.DataMigration.s3migration.service;

import ai.sapper.migration.DataMigration.constants.SpDocumentType;
import ai.sapper.migration.DataMigration.model.mongo.RuleRuntimeData;
import ai.sapper.migration.DataMigration.model.postgres.Document;
import ai.sapper.migration.DataMigration.s3migration.utils.Persist;
import ai.sapper.migration.DataMigration.s3migration.utils.S3FileService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ai.sapper.migration.DataMigration.s3migration.utils.Constants.CASEPATH;
import static ai.sapper.migration.DataMigration.s3migration.utils.Constants.QA_CHECK;

@Service
@Slf4j
public class MigrateQAcheck {
    @Autowired
    private Persist persist;
    @Autowired
    private S3FileService s3FileService;

    private String caseId;
    private String createdBy;
    private Long createdDate;
    private String modifiedBy;
    private Long modifiedDate;

    public void migrate(RuleRuntimeData ruleRuntimeData, List<String> failed){
        this.caseId = ruleRuntimeData.getCaseDocumentId();
        this.createdDate = ruleRuntimeData.getCreatedDate().getTime();
        this.createdBy = ruleRuntimeData.getCreatedBy();
        this.modifiedDate = ruleRuntimeData.getLastModifiedDate().getTime();
        this.modifiedBy = ruleRuntimeData.getLastModifiedBy();

        try{
            Map<String, Object> dataWrapper = new HashMap<>();
            dataWrapper.put("data", ruleRuntimeData.getData());
            dataWrapper.put("group", ruleRuntimeData.getGroup());
            dataWrapper.put("version", ruleRuntimeData.getVersion());
            dataWrapper.put("metadata", ruleRuntimeData.getMetadata());
            dataWrapper.put("createdBy", ruleRuntimeData.getCreatedBy());
            dataWrapper.put("createdDate", ruleRuntimeData.getCreatedDate().getTime());
            dataWrapper.put("caseDocumentId", ruleRuntimeData.getCaseDocumentId());
            dataWrapper.put("lastModifiedBy", ruleRuntimeData.getLastModifiedBy());
            dataWrapper.put("lastModifiedDate", ruleRuntimeData.getLastModifiedDate().getTime());

            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(dataWrapper);

            String path = uploadToS3(json,QA_CHECK);

            addDocumentAndArtefact(path, SpDocumentType.QaCheckOutput.toString());

        }catch(Exception e){
            log.error(e.getMessage());
            failed.add(caseId);
        }
    }

    public String uploadToS3(String json, String folderName){
        String destinationPath = CASEPATH + caseId + folderName + "qacheck.json";
        return s3FileService.upload(json,destinationPath);
    }

    public void addDocumentAndArtefact(String path,String docType){
        Document document = persist.addDocument(caseId,
                "qacheck.json",
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
