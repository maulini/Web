package net.minedof.web.utils;

import java.util.UUID;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class UUIDConverter implements AttributeConverter<UUID, String>{

	@Override
	public String convertToDatabaseColumn(UUID arg0) {		
			return arg0.toString();		
	}

	@Override
	public UUID convertToEntityAttribute(String arg0) {		
		return UUID.fromString(arg0);
	}

}

