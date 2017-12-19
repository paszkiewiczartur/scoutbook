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
import pl.scoutbook.model.Group;
import pl.scoutbook.model.UserProfile;
import pl.scoutbook.repository.GroupsRepository;
import pl.scoutbook.repository.UserProfileRepository;

@Controller
@RequestMapping("/api/groups")
public class GroupController {
	
	@Autowired
	private GroupsRepository groupsRepository;
	
	@Autowired
	private UserProfileRepository userProfileRepository;
	
    @PostMapping("/addMember")
    @ResponseStatus(HttpStatus.CREATED)
    public void addGroupMember(@Valid @RequestBody AddMember addMember) {
    	Group group = groupsRepository.findOne(addMember.getGroupOrEventId());
    	UserProfile member = userProfileRepository.findOne(addMember.getMemberId());
    	member.getGroups().add(group);
    	userProfileRepository.save(member);
    }
}
