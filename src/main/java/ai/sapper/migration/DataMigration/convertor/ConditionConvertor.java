//package ai.sapper.migration.DataMigration.convertor;
//
//import ai.sapper.migration.DataMigration.model.postgres.Condition;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import jakarta.persistence.AttributeConverter;
//import jakarta.persistence.Converter;
//
//import java.io.IOException;
//import java.util.Collections;
//import java.util.HashMap;
//import java.util.Map;
//
//@Converter
//public class ConditionConvertor implements AttributeConverter<Map<String, Condition>, String> {
//    private final ObjectMapper objectMapper = new ObjectMapper();
//
//    @Override
//    public String convertToDatabaseColumn(Map<String, Condition> attribute) {
//        try {
//            return objectMapper.writeValueAsString(attribute);
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    @Override
//    public Map<String, Condition> convertToEntityAttribute(String dbData) {
//        if (dbData == null) {
//            return Collections.emptyMap();
//        }
//        try {
//            return objectMapper.readValue(dbData, new TypeReference<Map<String, Condition>>() {});
//        } catch (IOException e) {
//            e.printStackTrace();
//            return new HashMap<>();
//        }
//    }
//}
