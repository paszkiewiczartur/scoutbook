package pl.scoutbook.serializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import pl.scoutbook.model.Event;

public class CustomEventSerializer extends StdSerializer<Event> {
    
    public CustomEventSerializer() {
        this(null);
    }
   
    public CustomEventSerializer(Class<Event> t) {
        super(t);
    }
 
    @Override
    public void serialize(
      Event value, JsonGenerator jgen, SerializerProvider provider) 
      throws IOException, JsonProcessingException {
  
        jgen.writeStartObject();
        jgen.writeNumberField("id", value.getId());
        jgen.writeNumberField("organizer", value.getOrganizer().getId());
        jgen.writeStringField("name", value.getName());
        jgen.writeStringField("place", value.getPlace());
        jgen.writeObjectField("start", value.getStart());
        jgen.writeObjectField("end", value.getEnd());
        jgen.writeStringField("info", value.getInfo());
        jgen.writeEndObject();
    }
}