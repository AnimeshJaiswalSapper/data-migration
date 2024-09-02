package ai.sapper.migration.DataMigration.model.postgres;

import ai.sapper.migration.DataMigration.constants.SpDocumentType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.Id;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Slf4j
@Builder
@Component("ruleRunTime_Data")
public class RuleRuntimeData {
    private static final long serialVersionUID = 1L;

    @Id
    private String id;


    private String caseDocumentId;


    private int version;

    private String type;


    private String data;


    private Map<String, String> metadata;


    private Long createdDate;


    private Long lastModifiedDate;

    public CaseDocumentDO convert(Object mongoDocument) throws JsonProcessingException {
        try {
            if (mongoDocument instanceof ai.sapper.migration.DataMigration.model.mongo.RuleRuntimeData mongoConfig) {
                if (mongoConfig.getData().isEmpty()) {
                    log.error("The following rule output doesn't contains any data for caseID : [{}]", mongoConfig.getCaseDocumentId());
                    return null;
                }
                CaseDocumentDO ruleRuntimeData = CaseDocumentDO.builder()
                        .id(new CaseDataId(mongoConfig.getCaseDocumentId(), SpDocumentType.QaCheckOutput))
                        .createdBy(mongoConfig.getCreatedBy())
                        .createdTime(mongoConfig.getCreatedDate().getTime())
                        .lastModifiedBy(mongoConfig.getLastModifiedBy())
                        .updatedTime(mongoConfig.getLastModifiedDate().getTime())
                        .version(mongoConfig.getVersion())
                        .build();

                Map<String, Object> dataWrapper = new HashMap<>();
                dataWrapper.put("data", mongoConfig.getData());
                dataWrapper.put("group", mongoConfig.getGroup());
                dataWrapper.put("version", mongoConfig.getVersion());
                dataWrapper.put("metadata", mongoConfig.getMetadata());
                dataWrapper.put("createdBy", mongoConfig.getCreatedBy());
                dataWrapper.put("createdDate", mongoConfig.getCreatedDate().getTime());
                dataWrapper.put("caseDocumentId", mongoConfig.getCaseDocumentId());
                dataWrapper.put("lastModifiedBy", mongoConfig.getLastModifiedBy());
                dataWrapper.put("lastModifiedDate", mongoConfig.getLastModifiedDate().getTime());

                ObjectMapper objectMapper = new ObjectMapper();
                String jsonString = objectMapper.writeValueAsString(dataWrapper);
                ruleRuntimeData.setData(jsonString);


                return ruleRuntimeData;
            }
        } catch (Exception e) {
            log.error("Error converting RuleRuntimeData document: [{}] exception : [{}]", mongoDocument, e.getMessage(), e);
            throw new RuntimeException("Conversion failed", e);
        }
        return null;
    }
}
