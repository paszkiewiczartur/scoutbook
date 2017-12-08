package pl.scoutbook.serializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import pl.scoutbook.model.UserWall;

public class CustomUserWallSerializer extends StdSerializer<UserWall> {
    
    public CustomUserWallSerializer() {
        this(null);
    }
   
    public CustomUserWallSerializer(Class<UserWall> t) {
        super(t);
    }
 
    @Override
    public void serialize(
      UserWall value, JsonGenerator jgen, SerializerProvider provider) 
      throws IOException, JsonProcessingException {
  
        jgen.writeStartObject();
        jgen.writeNumberField("id", value.getId());
        jgen.writeNumberField("user_profile_id", value.getUserProfile().getId());
        jgen.writeNumberField("post_id", value.getPost().getId());
        jgen.writeEndObject();
    }
}