package kr.co.yourplanet.core.util;

import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Converter(autoApply = true)
public class LocalDateListConverter implements AttributeConverter<List<LocalDate>, String> {

    private static final String SPLIT_CHAR = ",";

    @Override
    public String convertToDatabaseColumn(List<LocalDate> attribute) {
        if (CollectionUtils.isEmpty(attribute)) {
            return "";
        }
        return attribute.stream()
                .map(LocalDate::toString)
                .collect(Collectors.joining(SPLIT_CHAR));
    }

    @Override
    public List<LocalDate> convertToEntityAttribute(String dbData) {
        if (!StringUtils.hasText(dbData)) {
            return new ArrayList<>();
        }
        return Arrays.stream(dbData.split(SPLIT_CHAR))
                .map(dateString -> {
                    try {
                        return LocalDate.parse(dateString);
                    } catch (DateTimeParseException e) {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
