package pl.scoutbook.serializer;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.IntNode;

import pl.scoutbook.entities.Event;
import pl.scoutbook.entities.Group;
import pl.scoutbook.entities.Post;
import pl.scoutbook.entities.UserProfile;
import pl.scoutbook.model.PostCategory;
import pl.scoutbook.repository.EventsRepository;
import pl.scoutbook.repository.GroupsRepository;
import pl.scoutbook.repository.UserProfileRepository;

public class CustomPostDeserializer extends StdDeserializer<Post> { 
	 
	@Autowired
	UserProfileRepository userProfileRepository;

	@Autowired
	EventsRepository eventsRepository;

	@Autowired
	GroupsRepository groupsRepository;

    public CustomPostDeserializer() { 
        this(null); 
    } 
 
    public CustomPostDeserializer(Class<?> vc) { 
        super(vc); 
    }
 
    @Override
    public Post deserialize(JsonParser jp, DeserializationContext ctxt) 
      throws IOException, JsonProcessingException {
        JsonNode node = jp.getCodec().readTree(jp);
        String content = node.get("content").asText();
        UserProfile owner = userProfileRepository.findOne((Long) ((IntNode) node.get("owner_id")).numberValue().longValue());
        PostCategory category = PostCategory.valueOf(node.get("category").asText());
        if(Optional.ofNullable(node.get("user_profile_id")).isPresent()){
        	UserProfile user_profile = userProfileRepository.findOne(
        			(Long) ((IntNode) node.get("user_profile_id")).numberValue().longValue());
            return new Post(content, user_profile, owner, category);
        }else if(Optional.ofNullable(node.get("groups_id")).isPresent()){
        	Group group = groupsRepository.findOne(
        			(Long) ((IntNode) node.get("groups_id")).numberValue().longValue());
        	return new Post(content, group, owner, category);
        }else{
        	Event event = eventsRepository.findOne(
        			(Long) ((IntNode) node.get("events_id")).numberValue().longValue());
        	return new Post(content, event, owner, category);
        }
    }
}