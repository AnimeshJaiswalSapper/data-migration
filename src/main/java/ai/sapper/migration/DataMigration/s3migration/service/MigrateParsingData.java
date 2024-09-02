package ai.sapper.migration.DataMigration.s3migration.service;

import ai.sapper.migration.DataMigration.model.mongo.OriginalPDFData;
import ai.sapper.migration.DataMigration.s3migration.utils.Persist;
import ai.sapper.migration.DataMigration.s3migration.utils.S3FileService;
import ai.sapper.migration.DataMigration.s3migration.utils.TokenUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static ai.sapper.migration.DataMigration.s3migration.utils.Constants.*;

@Slf4j
@Service
public class MigrateParsingData {
    @Autowired
    private Persist persist;
    @Autowired
    private S3FileService s3FileService;

    @Autowired
    private TokenUtil tokenUtil;

    private String caseId;

    public void migrate(List<OriginalPDFData> dataList,List<String> failed) {

        for(OriginalPDFData d : dataList){
            try{
                this.caseId = d.getRunId();

                if(d.getImagePath()!=null){
                    String imgPath = removeLeadingSlash(d.getImagePath());
                    moveImgToS3(imgPath);
                }

                //decompress
                OriginalPDFData decompressedData = getDecompressed(d);
                if(decompressedData!=null){
                    ObjectMapper objectMapper = new ObjectMapper();
                    String json = objectMapper.writeValueAsString(decompressedData);
                    moveJsonToS3(json,d.getPageNo().toString());
                }
            }catch(Exception e) {
                log.error(e.getMessage());
                failed.add(caseId);
            }
        }
    }

    public OriginalPDFData getDecompressed(OriginalPDFData data) {
        try{
            Long page = data.getPageNo();
            String documentId = data.getDocumentId();

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", tokenUtil.getToken());
            HttpEntity<?> entity = new HttpEntity<>(headers);

            String url = String.format("https://dev-ui.spr.sapper.ai/ids/v1/getSegments?documentId=%s&pageNo=%d", documentId, page);

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<OriginalPDFData[]> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    OriginalPDFData[].class
            );

            OriginalPDFData[] dataList = response.getBody();
            if (dataList != null && dataList.length > 0) {
                return dataList[0];
            } else {
                return null;
            }
        }catch (Exception e){
            log.error(e.getMessage());
            throw e;
        }
    }


    private String moveImgToS3(String oldFilePath) {
        String fileName = extractFileName(oldFilePath);
        String destinationPath = CASEPATH + caseId + IDS_IMAGE + fileName;
        return s3FileService.copyObject(oldFilePath, destinationPath);
    }

    private String moveJsonToS3(String json, String page) {
        String fileName = page + ".json";
        String destinationPath = CASEPATH + caseId + IDS_JSON + fileName;
        return s3FileService.upload(json,destinationPath);
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
