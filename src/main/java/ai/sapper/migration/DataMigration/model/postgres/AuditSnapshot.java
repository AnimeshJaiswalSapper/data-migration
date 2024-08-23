//package ai.sapper.migration.DataMigration.model.postgres;
//
//import ai.sapper.migration.DataMigration.constants.CaseType;
//import jakarta.persistence.Entity;
//import jakarta.persistence.*;
//import lombok.*;
//import org.hibernate.annotations.CreationTimestamp;
//import org.springframework.data.annotation.CreatedBy;
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
//@Table(name = "audit_snapshot")
//public class AuditSnapshot implements Serializable {
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
//    @Enumerated(EnumType.STRING)
//    private CaseType caseType;
//
//    @Column(name = "version")
//    private int version;
//
//
//    @Column(name = "data", columnDefinition = "VARCHAR")
//    private String data;
//    //its being used inside the code inside process persistance
//    //I converted it to String from Map , as we are going to store this as varchar.
////    private Map<String, Object> data; (CBRE)
//
//    @CreatedBy
//    private String createdBy;
//
//    @CreationTimestamp
//    @Column(name = "created_date")
//    private Date createdAt;
//
//    public AuditSnapshot() {
//        this.id = UUID.randomUUID().toString();
//    }
//
//    public AuditSnapshot(@NonNull String id) {
//        if (id == null) {
//            throw new NullPointerException("id is marked non-null but is null");
//        } else {
//            this.id = id;
//        }
//    }
//
//}
