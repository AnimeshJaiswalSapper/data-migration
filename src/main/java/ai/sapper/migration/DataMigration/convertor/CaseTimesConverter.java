package ai.sapper.migration.DataMigration.convertor;

import ai.sapper.migration.DataMigration.model.postgres.CaseRetention;
import ai.sapper.migration.DataMigration.model.postgres.CaseTimes;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class CaseTimesConverter implements AttributeConverter<CaseTimes,String>{
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(CaseTimes caseTimes) {
        try {
            return objectMapper.writeValueAsString(caseTimes);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to convert CaseRetention to JSON", e);
        }
    }

    @Override
    public CaseTimes convertToEntityAttribute(String s) {
        try {
            return objectMapper.readValue(s, CaseTimes.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to convert JSON to CaseRetention", e);
        }
    }
}
