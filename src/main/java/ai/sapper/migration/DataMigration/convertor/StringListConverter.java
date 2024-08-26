package ai.sapper.migration.DataMigration.convertor;

import jakarta.persistence.AttributeConverter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StringListConverter implements AttributeConverter<List<String>, String> {

        private static final String JOIN = "<##>,";
    private static final String SEPARATOR = "<##>,";


        @Override
        public String convertToDatabaseColumn(List<String> attribute) {
            if (attribute == null || attribute.isEmpty()) {
                return "";
            }
            return attribute.stream().collect(Collectors.joining(JOIN));
        }

        @Override
        public List<String> convertToEntityAttribute(String dbData) {
            if (dbData == null || dbData.isEmpty()) {
                return Arrays.asList();
            }
            if(dbData.endsWith("<##>")) {
                return Arrays.asList(dbData.substring(0,dbData.length()-4).split(SEPARATOR));
            }
            return Arrays.asList(dbData.split(SEPARATOR));
        }

}
