//package ai.sapper.migration.DataMigration.model.postgres;
//
//import ai.sapper.migration.DataMigration.service.mongo.ReadService;
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.Table;
//import lombok.ToString;
//import org.hibernate.annotations.CreationTimestamp;
//import org.hibernate.annotations.UpdateTimestamp;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.annotation.CreatedDate;
//import org.springframework.data.annotation.Id;
//import org.springframework.data.annotation.LastModifiedDate;
//import org.springframework.data.mongodb.core.mapping.Document;
//import org.springframework.stereotype.Component;
//
//import java.io.Serializable;
//import java.util.Date;
//import java.util.List;
//import java.util.Map;
//
//import static ai.sapper.migration.DataMigration.constants.Collections.CREATED_AT;
//
//@Entity
//@Table(name = "ingestion_configuration")
//public class IngestionConfiguration implements Serializable {
//
//    private static final long serialVersionUID = 1L;
//
//    @Id
//    @Column(name = "id")
//    private String id;
//
//    @Column(name = "source_bucket")
//    private String sourceBucket;
//
//    @Column(name = "source_path")
//    private String sourcePath;
//
//    @Column(name = "destination_bucket")
//    private String destinationBucket;
//
//    @Column(name = "destination_path")
//    private String destinationPath;
//
//    @Column(name = "source_type")
//    private String sourceType;
//
//    @Column(name = "destination_type")
//    private String destinationType;
//
//    @Column(name = "publish_destination")
//    private String publishDestination;
//
//    @Column(name = "poller_name")
//    private String pollerName;
//
//    @Column(name = "created_by")
//    private String createdBy;
//
//    @CreationTimestamp
//    @Column(name = "created_date")
//    private Date createdAt;
//
//    @UpdateTimestamp
//    @Column(name = "last_modified_date")
//    private Date modifiedAt;
//
//
//    @Column(name = "extension", columnDefinition = "VARCHAR")
//    private String allowedExtensions;
////    private List<String> allowedExtensions;(CBRE)
//
//    @Column(name = "priority")
//    private Integer priority;
//
//
//    @Column(name = "extension", columnDefinition = "VARCHAR")
//    private String priorityExtensions;
////    private List<String> priorityExtensions; (CBRE)
//
//
//    @Column(name = "properties", columnDefinition = "VARCHAR")
//    private String propeties;
//    //private Map<String, String> properties;(CBRE)
//
//}