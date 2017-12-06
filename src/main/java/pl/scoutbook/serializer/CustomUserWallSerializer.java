package pl.scoutbook.serializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import pl.scoutbook.model.UserProfile;

public class CustomUserWallSerializer extends JsonSerializer<UserProfile> {

	@Override
	public void serialize(UserProfile value, JsonGenerator jgen,
        SerializerProvider provider) throws IOException,
        JsonProcessingException {
		  jgen.writeNumber(value.getId());
	}

}