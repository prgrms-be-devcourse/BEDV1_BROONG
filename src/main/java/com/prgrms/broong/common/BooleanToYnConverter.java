package com.prgrms.broong.common;

import javax.persistence.AttributeConverter;
import javax.persistence.Convert;

@Convert
public class BooleanToYnConverter implements AttributeConverter<Boolean, String> {

    //boolean 값을 y,n으로 컨버트
    @Override
    public String convertToDatabaseColumn(Boolean attribute) {
        return (attribute != null && attribute) ? "Y" : "N";
    }

    //y,n 값을 boolean으로 컨버트
    @Override
    public Boolean convertToEntityAttribute(String dbData) {
        return "Y".equalsIgnoreCase(dbData);
    }

}
