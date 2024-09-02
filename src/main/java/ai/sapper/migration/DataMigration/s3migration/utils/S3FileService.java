package ai.sapper.migration.DataMigration.s3migration.utils;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;

@Slf4j
@Component
public class S3FileService {
    @Autowired
    private AmazonS3 amazonS3;
    @Value("${s3.old.bucket:}")
    private String oldBucket;


    @Value("${s3.new.bucket}")
    private String newBucket;

    public String copyObject(String oldFilePath,String newFilePath){
        try{
            CopyObjectRequest copyRequest = new CopyObjectRequest(oldBucket,oldFilePath,newBucket,newFilePath);
            CopyObjectResult res = amazonS3.copyObject(copyRequest);

            String fullPath = "s3://" + newBucket + "/" + newFilePath;
            log.info("File successfully copied to Bucket : [{}]",fullPath);
            return fullPath;
        }catch (Exception e){
            log.error(e.getMessage());
            throw e;
        }
    }

    public String upload(String content, String s3key) {
        try {
            byte[] contentBytes = content.getBytes();
            ByteArrayInputStream inputStream = new ByteArrayInputStream(contentBytes);

            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(contentBytes.length);
            metadata.setContentType("application/json");

            PutObjectRequest request = new PutObjectRequest(newBucket, s3key, inputStream, metadata);
            PutObjectResult result = amazonS3.putObject(request);

            String fullPath = "s3://" + newBucket + "/" + s3key;
            log.info("File successfully uploaded to : [{}]", fullPath);
            return fullPath;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }
}
