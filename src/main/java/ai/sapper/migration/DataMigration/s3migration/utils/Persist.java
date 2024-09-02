package ai.sapper.migration.DataMigration.s3migration.utils;

import ai.sapper.migration.DataMigration.Repository.postgres.PostgresRepository;
import ai.sapper.migration.DataMigration.model.postgres.ArtefactReference;
import ai.sapper.migration.DataMigration.model.postgres.Document;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static ai.sapper.migration.DataMigration.s3migration.utils.Constants.*;

@Slf4j
@Component
public class Persist {
    @Autowired
    private PostgresRepository postgresRepository;

    @Transactional
    public Document addDocument(String caseID, String docName, String sourcePath, String docType, String mimeType,
                                   String createdBy, String modifiedBy, Long timeCreated, Long timeUpdated) {
        try{
            Document doc = Document.builder()
                    .docId(UUID.randomUUID().toString())
                    .caseId(caseID)
                    .collection(COLLECTION)
                    .docName(docName)
                    .docSourcePath(sourcePath)
                    .docState(DOC_STATE)
                    .docType(docType)
                    .mimeType(mimeType)
                    .createdBy(createdBy)
                    .modifiedBy(modifiedBy)
                    .timeCreated(timeCreated)
                    .timeUpdated(timeUpdated)
                    .build();

            postgresRepository.save(doc);

            return doc;
        }catch (Exception e){
            log.error("Error occurred while adding document : [{}]",e.getMessage());
            throw e;
        }
    }

    @Transactional
    public void addArtefact(String caseId,String docId){
        try{
            ArtefactReference artefactReference = ArtefactReference.builder()
                    .caseId(caseId)
                    .collection(COLLECTION)
                    .docId(docId)
                    .artefactType(ARTEFACT_TYPE)
                    .build();

            postgresRepository.save(artefactReference);
        }catch(Exception e){
            log.error("Error occurred while adding artefact : [{}]",e.getMessage());
            throw e;
        }

    }


}
