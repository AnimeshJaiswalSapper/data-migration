package ai.sapper.migration.DataMigration.model.postgres;

import ai.sapper.migration.DataMigration.constants.CaseType;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Entity
@Slf4j
@Component("audit_entity")
@Table(name = "audit_entity")
public class AuditEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    private String id;

    //here which should we use id , caseId or both ?
    private String caseId;

    //storing this in the form of jsonb , it is case document in the audit entity.
//    @Column(name = "extraction_json" , columnDefinition = "jsonb")
//    @ColumnTransformer(write = "?::jsonb")
//    @Convert(converter = JsonbConverter.class)
//    private CaseDocument document;

    @Enumerated(EnumType.STRING)
    private CaseType caseType;

    @Column(name = "version")
    private int version;

    @CreatedBy
    @Column(name = "created_by")
    private String createdBy;

    @CreationTimestamp
    @Column(name = "created_date")
    private ZonedDateTime createdAt;

    public AuditEntity() {
        this.id = UUID.randomUUID().toString();
    }

    public AuditEntity(@NonNull String id) {
        if (id == null) {
            throw new NullPointerException("id is marked non-null but is null");
        } else {
            this.id = id;
        }
    }

    public AuditEntity convert(Object mongoDocument) {
        try {
            if (mongoDocument instanceof ai.sapper.migration.DataMigration.model.mongo.AuditEntity mongoConfig) {

                AuditEntity config = AuditEntity.builder()
                        .id(mongoConfig.getId())
                        .caseId(mongoConfig.getCaseId())
//                        .document(mongoConfig.getDocument())
                        .caseType(mongoConfig.getCaseType())
                        .version(mongoConfig.getVersion())
                        .createdAt(convertToZonedDateTime(mongoConfig.getCreatedAt()))
                        .createdBy(mongoConfig.getCreatedBy())
                        .build();

                return config;
            }
        } catch (Exception e) {
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
