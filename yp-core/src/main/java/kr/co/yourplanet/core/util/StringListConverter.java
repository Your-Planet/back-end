package kr.co.yourplanet.core.util;

import javax.persistence.AttributeConverter;
import javax.persistence.Convert;
import java.util.Arrays;
import java.util.List;

@Convert
public class StringListConverter implements AttributeConverter<List<String>, String> {

    private static final String SPLIT_CHAR = ",";

    @Override
    public String convertToDatabaseColumn(List<String> attribute) {
        if (attribute.isEmpty()) {
            return "";
        }
        return String.join(SPLIT_CHAR, attribute);
    }

    @Override
    public List<String> convertToEntityAttribute(String dbData) {
        return Arrays.asList(dbData.split(SPLIT_CHAR));
    }
}
