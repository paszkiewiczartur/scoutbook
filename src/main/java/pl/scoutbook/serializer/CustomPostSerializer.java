package pl.scoutbook.serializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import pl.scoutbook.model.Post;
import pl.scoutbook.model.UserWall;

public class CustomPostSerializer extends StdSerializer<Post> {
    
    public CustomPostSerializer() {
        this(null);
    }
   
    public CustomPostSerializer(Class<Post> t) {
        super(t);
    }
 
    @Override
    public void serialize(
      Post value, JsonGenerator jgen, SerializerProvider provider) 
      throws IOException, JsonProcessingException {
  
        jgen.writeStartObject();
        jgen.writeNumberField("id", value.getId());
        jgen.writeStringField("content", value.getContent());
        jgen.writeObjectField("created_at", value.getCreatedAt());
        //jgen.writeNumberField("groups_id", value.getGroup().getId());
        jgen.writeNumberField("owner_id", value.getOwner().getId());
        //jgen.writeStringField("owner_name", value.getOwner().getFirstname() + " " + value.getOwner().getLastname());
        jgen.writeStringField("category", value.getCategory().toString());
        jgen.writeEndObject();
    }
}