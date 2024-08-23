//package ai.sapper.migration.DataMigration.model.postgres;
//
//import ai.sapper.migration.DataMigration.constants.CaseType;
//import com.unifiedframework.model.block.CaseDocument;
//import jakarta.persistence.*;
//import jakarta.persistence.Entity;
//import lombok.*;
//import org.hibernate.annotations.ColumnTransformer;
//import org.hibernate.annotations.CreationTimestamp;
//import org.springframework.data.annotation.CreatedBy;
//import org.springframework.data.annotation.CreatedDate;
//
//import java.io.Serializable;
//import java.util.Date;
//import java.util.UUID;
//
//@Getter
//@Setter
//@Builder
//@AllArgsConstructor(access = AccessLevel.PACKAGE)
//@Entity
//@Table(name = "audit_entity")
//public class AuditEntity implements Serializable {
//
//    private static final long serialVersionUID = 1L;
//
//    @Id
//    @Column(name = "id")
//    private String id;
//
//    //here which should we use id , caseId or both ?
//    private String caseId;
//
//    //storing this in the form of jsonb , it is case document in the audit entity.
//    @Column(name = "extraction_json" , columnDefinition = "jsonb")
//    @ColumnTransformer(write = "?::jsonb")
//    private CaseDocument document;
//
//    @Enumerated(EnumType.STRING)
//    private CaseType caseType;
//
//    @Column(name = "version")
//    private int version;
//
//    @CreatedBy
//    @Column(name = "created_by")
//    private String createdBy;
//
//    @CreationTimestamp
//    @Column(name = "created_date")
//    private Date createdAt;
//
//    public AuditEntity() {
//        this.id = UUID.randomUUID().toString();
//    }
//
//    public AuditEntity(@NonNull String id) {
//        if (id == null) {
//            throw new NullPointerException("id is marked non-null but is null");
//        } else {
//            this.id = id;
//        }
//    }
//
//}
