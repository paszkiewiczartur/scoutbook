package pl.scoutbook.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import pl.scoutbook.model.AddMember;
import pl.scoutbook.model.Event;
import pl.scoutbook.model.UserProfile;
import pl.scoutbook.repository.EventsRepository;
import pl.scoutbook.repository.UserProfileRepository;

@Controller
@RequestMapping("/api/events")
public class EventController {
	
	@Autowired
	private EventsRepository eventsRepository;
	
	@Autowired
	private UserProfileRepository userProfileRepository;
	
    @PostMapping("/addMember")
    @ResponseStatus(HttpStatus.CREATED)
    public void addGroupMember(@Valid @RequestBody AddMember addMember) {
    	Event event = eventsRepository.findOne(addMember.getGroupOrEventId());
    	UserProfile member = userProfileRepository.findOne(addMember.getMemberId());
    	member.getEvents().add(event);
    	userProfileRepository.save(member);
    }
}
