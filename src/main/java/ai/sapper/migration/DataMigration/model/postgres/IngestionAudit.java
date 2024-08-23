//package ai.sapper.migration.DataMigration.model.postgres;
//
//import ai.sapper.migration.DataMigration.constants.IngestionState;
//import jakarta.persistence.*;
//import jakarta.persistence.Entity;
//import org.hibernate.annotations.CreationTimestamp;
//import org.springframework.data.annotation.CreatedBy;
//import org.springframework.data.annotation.CreatedDate;
//import org.springframework.data.annotation.Id;
//
//import java.io.Serializable;
//import java.util.Date;
//import java.util.Map;
//
//@Entity
//@Table(name = "ingestion_audit")
//public class IngestionAudit implements Serializable {
//
//    private static final long serialVersionUID = 1L;
//
//    @Id
//    @Column(name = "id")
//    private String id;
//
//    @Column(name = "source_path")
//    private String sourcePath;
//
//    @Column(name = "destination_path")
//    private String destinationPath;
//
//    @Column(name = "source_type")
//    private String sourceType;
//
//    @Enumerated(EnumType.STRING)
//    @Column(name = "ingestion_state")
//    private IngestionState ingestionState;
//
//
//    @Column(name = "state_history", columnDefinition = "VARCHAR")
//    private String stateHistory;
//    //WE will store this as VARCHAR
////    private Map<String, String> stateHistory; (CBRE)
//
//    @Column(name = "metadata", columnDefinition = "VARCHAR")
//    private String metadata;
//    //We will store this in the form of VARCHAR
////    private Map<String, String> metadata;(CBRE)
//
//    @CreatedBy
//    @Column(name = "created_by")
//    private String createdBy;
//
//    @CreationTimestamp
//    @Column(name = "ingestion_time")
//    private Date ingestionTime;
//
//}
//
