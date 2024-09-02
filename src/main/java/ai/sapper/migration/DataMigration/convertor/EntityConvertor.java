package ai.sapper.migration.DataMigration.convertor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Converter
public class EntityConvertor implements AttributeConverter<List<Object>, String> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<Object> attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Object> convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return Collections.emptyList();
        }
        try {
            return objectMapper.readValue(dbData, new TypeReference<List<Object>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
