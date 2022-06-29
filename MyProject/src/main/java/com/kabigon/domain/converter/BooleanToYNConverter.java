package com.kabigon.domain.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class BooleanToYNConverter implements AttributeConverter<Boolean, Boolean> {

	@Override
	public Boolean convertToDatabaseColumn(Boolean attribute) {
		return (attribute != null && attribute) ? true : false;
	}

	@Override
	public Boolean convertToEntityAttribute(Boolean dbData) {
		return dbData;
	}

}
