package ai.sapper.migration.DataMigration.model.postgres;

import ai.sapper.migration.DataMigration.constants.SpDocumentType;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.ColumnTransformer;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Builder
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY,
        property = "@class")
@Entity
@Table(name = "cm_udp_extraction_data")
@Slf4j
@Component("cm_udp_extraction_data")
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@ToString(callSuper = true)
public class CaseDocumentDO {
    @EmbeddedId
    private CaseDataId id;
    @Column(name = "extraction_json", columnDefinition = "jsonb")
    @ColumnTransformer(write = "?::jsonb")
    private String data;
    @Column(
            name = "time_created"
    )
    private long createdTime;
    @Column(
            name = "time_updated"
    )
    private long updatedTime;

    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "version")
    private Integer version;

    public CaseDocumentDO convert(Object mongoDocument) {
        try {
            if (mongoDocument instanceof ai.sapper.migration.DataMigration.model.mongo.CaseDocumentDO mongoCaseDoc) {
                SpDocumentType type = SpDocumentType.UDPJson;
                if (mongoCaseDoc.getType() != null) {
                    type = SpDocumentType.fromCaseType(mongoCaseDoc.getType());
                }
                if (mongoCaseDoc.getCaseDocument().getDocuments() == null || mongoCaseDoc.getCaseDocument().getDocuments().isEmpty()) {
                    log.error("The case document doesn't contains any document for caseID : [{}]", mongoCaseDoc.getCaseId());
                    return null;
                }
                CaseDocumentDO caseDoc = CaseDocumentDO.builder()
                        .id(new CaseDataId(mongoCaseDoc.getCaseId(), type))
                        .createdTime(mongoCaseDoc.getCreatedDate().getTime())
                        .createdBy(mongoCaseDoc.getCreatedBy())
                        .updatedTime(mongoCaseDoc.getLastModifiedDate().getTime())
                        .lastModifiedBy(mongoCaseDoc.getLastModifiedBy())
                        .version(0)
                        .build();

                ObjectMapper objectMapper = new ObjectMapper();
                String jsonString = objectMapper.writeValueAsString(mongoCaseDoc.getCaseDocument());
                caseDoc.setData(jsonString);

                return caseDoc;
            }
        } catch (Exception e) {
            log.error("Error converting MongoDB document: {}", e.getMessage(), e);
            throw new RuntimeException("Conversion failed", e);
        }
        return null;
    }

}
