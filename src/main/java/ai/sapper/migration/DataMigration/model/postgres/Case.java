//package ai.sapper.migration.DataMigration.model.postgres;
//
//import ai.sapper.migration.DataMigration.common.mongo.BaseEntity;
//import ai.sapper.migration.DataMigration.constants.CaseStatus;
//import ai.sapper.migration.DataMigration.constants.CaseType;
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//import com.fasterxml.jackson.annotation.JsonInclude;
//import jakarta.persistence.*;
//import lombok.ToString;
//import org.springframework.data.annotation.Id;
//import org.springframework.stereotype.Component;
//
//import java.io.Serializable;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//
//@Entity
//@Table(name = "cases")
//@JsonIgnoreProperties(ignoreUnknown = true)
//@ToString(callSuper = true)
//@Component
//public class Case extends BaseEntity implements Serializable {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id", updatable = false, nullable = false)
//    private Long id;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "entity_id")
//    private Entity entity; // Assume EntityEntity is the corresponding JPA entity
//
//    @Column(name = "coa_id")
//    private String coaId;
//
//    @Column(name = "coa_name")
//    private String coaName;
//
//    @Column(name = "assignee")
//    private String assignee;
//
//    @Enumerated(EnumType.STRING)
//    @Column(name = "status")
//    private CaseStatus status = CaseStatus.DRAFT;
//
//    @Enumerated(EnumType.STRING)
//    @Column(name = "type")
//    private CaseType type;
//
//    @Column(name = "channel")
//    private String channel;
//
//    @Column(name = "file_name")
//    private String fileName;
//
//    @ElementCollection
//    @CollectionTable(name = "case_attributes", joinColumns = @JoinColumn(name = "case_id"))
//    @MapKeyColumn(name = "attribute_key")
//    @Column(name = "attribute_value")
//    private Map<String, ?> attributes = new HashMap<>();
//
//    @ElementCollection
//    @CollectionTable(name = "case_metadata", joinColumns = @JoinColumn(name = "case_id"))
//    @MapKeyColumn(name = "metadata_key")
//    @Column(name = "metadata_value")
//    private Map<String, ?> metadata = new HashMap<>();
//
//    @Temporal(TemporalType.TIMESTAMP)
//    @Column(name = "submit_date")
//    private Date submitDate;
//
//    @Column(name = "reject_reason")
//    private String rejectReason;
//
//    @Column(name = "file_path")
//    private String filePath;
//
//    @ElementCollection
//    @CollectionTable(name = "case_pdf_paths", joinColumns = @JoinColumn(name = "case_id"))
//    @Column(name = "pdf_path")
//    private List<String> pdfPath;
//
//    @Column(name = "auto_approval")
//    private boolean autoApproval = false;
//
//    @JsonInclude(JsonInclude.Include.NON_NULL)
//    @Column(name = "parent_case_id")
//    private String parentCaseId;
//}
