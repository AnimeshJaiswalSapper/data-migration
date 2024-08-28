package ai.sapper.migration.DataMigration.convertor;

import ai.sapper.migration.DataMigration.model.postgres.CaseRetention;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class CaseRetentionConverter implements AttributeConverter<CaseRetention, String> {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(CaseRetention caseRetention) {
        try {
            return objectMapper.writeValueAsString(caseRetention);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to convert CaseRetention to JSON", e);
        }
    }

    @Override
    public CaseRetention convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, CaseRetention.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to convert JSON to CaseRetention", e);
        }
    }
}

