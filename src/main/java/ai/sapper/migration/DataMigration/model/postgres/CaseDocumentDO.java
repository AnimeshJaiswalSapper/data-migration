package ai.sapper.migration.DataMigration.model.postgres;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.ColumnTransformer;
import org.springframework.stereotype.Component;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Entity
@Table(name = "cm_udp_extraction_data")
@Slf4j
@Builder
@Component("cm_udp_extraction_data")
public class CaseDocumentDO {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "case_id")
    String caseId;

    @Column(name = "extraction_json" , columnDefinition = "jsonb")
    @ColumnTransformer(write = "?::jsonb")
    private String data;

    @Column(name = "type")
    private String type;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "time_created")
    private Long createdDate;

    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    @Column(name = "time_updated")
    private Long lastModifiedDate;

    @Column(name = "version")
    private Integer version;

    public CaseDocumentDO convert(Object mongoDocument) {
        try {
            if (mongoDocument instanceof ai.sapper.migration.DataMigration.model.mongo.CaseDocumentDO mongoCaseDoc) {

                CaseDocumentDO caseDoc = CaseDocumentDO.builder()
                        .id(mongoCaseDoc.getId())
                        .caseId(mongoCaseDoc.getCaseId())
                        .type(String.valueOf(mongoCaseDoc.getType()))
                        .createdDate(mongoCaseDoc.getCreatedDate().getTime())
                        .createdBy(mongoCaseDoc.getCreatedBy())
                        .lastModifiedDate(mongoCaseDoc.getLastModifiedDate().getTime())
                        .lastModifiedBy(mongoCaseDoc.getLastModifiedBy())
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
