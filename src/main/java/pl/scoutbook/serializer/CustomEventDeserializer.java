package pl.scoutbook.serializer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.IntNode;

import pl.scoutbook.model.Event;
import pl.scoutbook.model.Group;
import pl.scoutbook.model.Post;
import pl.scoutbook.model.PostCategory;
import pl.scoutbook.model.UserProfile;
import pl.scoutbook.repository.EventsRepository;
import pl.scoutbook.repository.GroupsRepository;
import pl.scoutbook.repository.UserProfileRepository;

public class CustomEventDeserializer extends StdDeserializer<Event> { 
	 
	@Autowired
	UserProfileRepository userProfileRepository;

	@Autowired
	EventsRepository eventsRepository;

	@Autowired
	GroupsRepository groupsRepository;

    public CustomEventDeserializer() { 
        this(null); 
    } 
 
    public CustomEventDeserializer(Class<?> vc) { 
        super(vc); 
    }
 
    @Override
    public Event deserialize(JsonParser jp, DeserializationContext ctxt) 
      throws IOException, JsonProcessingException {
        JsonNode node = jp.getCodec().readTree(jp);
        Event event = new Event();
        UserProfile organizer = userProfileRepository.findOne((Long) ((IntNode) node.get("organizer")).numberValue().longValue());
        event.setOrganizer(organizer);
        String name = node.get("name").asText();
        event.setName(name);
        String place = node.get("place").asText();
        event.setPlace(place);
        System.out.println(place);
        System.out.println(node.get("start").asText());
        LocalDateTime start = LocalDateTime.parse(node.get("start").asText());
        event.setStart(start);
        System.out.println(node.get("end").asText());
        LocalDateTime end = LocalDateTime.parse(node.get("end").asText());
        event.setEnd(end);
        if(Optional.ofNullable(node.get("image")).isPresent()){
        	String image = node.get("image").asText();
        	event.setImage(image);
        }
        if(Optional.ofNullable(node.get("info")).isPresent()){
        	String info = node.get("info").asText();
        	event.setInfo(info);
        }
        return event;
    }
}