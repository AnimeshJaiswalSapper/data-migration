package ai.sapper.migration.DataMigration.s3migration.service;

import ai.sapper.migration.DataMigration.constants.SpDocumentType;
import ai.sapper.migration.DataMigration.model.mongo.Case;
import ai.sapper.migration.DataMigration.model.postgres.Document;
import ai.sapper.migration.DataMigration.s3migration.utils.Persist;
import ai.sapper.migration.DataMigration.s3migration.utils.S3FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static ai.sapper.migration.DataMigration.s3migration.utils.Constants.CASEPATH;
import static ai.sapper.migration.DataMigration.s3migration.utils.Constants.ORIGINAL;

@Service
@Slf4j
public class MigrateToOriginal {
    @Autowired
    private Persist persist;
    @Autowired
    private S3FileService s3FileService;

    private String caseId;
    private String createdBy;
    private Long createdDate;
    private String modifiedBy;
    private Long modifiedDate;

    public void migrate(Case c, List<String> failed) {
        try{
            this.caseId = c.getId();
            this.createdBy = c.getCreatedBy();
            this.createdDate = c.getCreatedDate().getTime();
            this.modifiedBy = c.getLastModifiedBy();
            this.modifiedDate = c.getLastModifiedDate().getTime();

            if(c.getPdfPath()!=null){
                String pdfPath = removeLeadingSlash(c.getPdfPath().get(0));
                handlePdf(pdfPath);
            }


            String zipPath = removeLeadingSlash(c.getFilePath());
            if (zipPath != null)
                handleZip(zipPath);
        }catch (Exception e){
            log.error(e.getMessage());
            failed.add(c.getId());
        }
    }

    private void handlePdf(String pdfPath) {
        String destinationPath = moveFileToS3(pdfPath);

        if (destinationPath != null) {
            Document document = persist.addDocument(
                    caseId,
                    extractFileName(pdfPath),
                    destinationPath,
                    SpDocumentType.CASE.toString(),
                    "PDF",
                    createdBy,
                    modifiedBy,
                    createdDate,
                    modifiedDate
            );

            if (document != null) {
                persist.addArtefact(caseId, document.getDocId());
            }
        }
    }

    private void handleZip(String zipPath) {
        String destinationPath = moveFileToS3(zipPath);

        if (destinationPath != null) {

            Document document = persist.addDocument(
                    caseId,
                    extractFileName(zipPath),
                    destinationPath,
                    SpDocumentType.CaseZIP.toString(),
                    "ZIP",
                    createdBy,
                    modifiedBy,
                    createdDate,
                    modifiedDate
            );

            if (document != null) {
                persist.addArtefact(caseId, document.getDocId());
            }

        }
    }

    private String moveFileToS3(String oldFilePath) {
        String fileName = extractFileName(oldFilePath);
        String destinationPath = CASEPATH + caseId + ORIGINAL + fileName;
        return s3FileService.copyObject(oldFilePath, destinationPath);
    }

    private String extractFileName(String path) {
        return path != null ? path.substring(path.lastIndexOf("/") + 1) : null;
    }

    public String removeLeadingSlash(String filePath) {
        if (filePath != null && filePath.startsWith("/")) {
            return filePath.substring(1);
        }
        return filePath;
    }

}
