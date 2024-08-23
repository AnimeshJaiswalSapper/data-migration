package ai.sapper.migration.DataMigration.convertor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import jakarta.persistence.AttributeConverter;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class JsonConverter<T> implements AttributeConverter<T, String> {

    private TypeReference<T> typeReference;

    @Autowired
    private ObjectMapper objectMapper;

    public JsonConverter(TypeReference<T> typeReference) {
        this.typeReference = typeReference;
    }

    @Override
    public String convertToDatabaseColumn(T object) {
        if(object == null) return null;

        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public T convertToEntityAttribute(String json) {
        try {
            return objectMapper.readValue(json, typeReference);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
