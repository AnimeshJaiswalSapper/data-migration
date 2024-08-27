package ai.sapper.migration.DataMigration.model.postgres;

import ai.sapper.migration.DataMigration.constants.CaseType;
import jakarta.persistence.*;
import jakarta.persistence.Id;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@jakarta.persistence.Entity
@Component("audit_snapshot_original_new")
@Slf4j
@Table(name = "audit_snapshot_original")
public class AuditSnapshotOriginal implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    private String id;

    //here which should we use id , caseId or both ?
    private String caseId;

    @Enumerated(EnumType.STRING)
    private CaseType caseType;

    @Column(name = "version")
    private int version;

    @Column(name = "data", columnDefinition = "VARCHAR")
    private String data;
    //its being used inside the code inside process persistance
    //I converted it to String from Map , as we are going to store this as varchar.
//    private Map<String, Object> data; (CBRE)

    @CreatedBy
    private String createdBy;

    @CreationTimestamp
    @Column(name = "created_date")
    private ZonedDateTime createdAt;

    @LastModifiedBy
    private String modifiedBy;

    @UpdateTimestamp
    @Column(name = "last_modified_date")
    private ZonedDateTime modifiedAt;

    public AuditSnapshotOriginal() {
        this.id = UUID.randomUUID().toString();
    }

    public AuditSnapshotOriginal(@NonNull String id) {
        if (id == null) {
            throw new NullPointerException("id is marked non-null but is null");
        } else {
            this.id = id;
        }
    }

    public AuditSnapshotOriginal convert(Object mongoDocument) {
        try {
            if (mongoDocument instanceof ai.sapper.migration.DataMigration.model.mongo.AuditSnapshotOriginal mongoConfig) {

                AuditSnapshotOriginal config = AuditSnapshotOriginal.builder()
                        .id(mongoConfig.getId())
                        .caseId(mongoConfig.getCaseId())
//                        .document(mongoConfig.getDocument())
                        .caseType(mongoConfig.getCaseType())
                        .version(mongoConfig.getVersion())
                        .data(mongoConfig.getData().toString())
                        .createdAt(convertToZonedDateTime(mongoConfig.getCreatedAt()))
                        .createdBy(mongoConfig.getCreatedBy())
                        .modifiedBy(mongoConfig.getModifiedBy())
                        .modifiedAt(convertToZonedDateTime(mongoConfig.getModifiedAt()))
                        .build();

                return config;
            }
        }  catch (Exception e) {
            log.error("Error converting Audit Entity document: {}", e.getMessage(), e);
            throw e;
        }
        return null;
    }

    private ZonedDateTime convertToZonedDateTime(Date date) {
        if (date != null) {
            return ZonedDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        }
        return null;
    }

}
