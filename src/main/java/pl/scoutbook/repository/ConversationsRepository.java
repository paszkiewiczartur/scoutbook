package pl.scoutbook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pl.scoutbook.entities.Conversation;

@Repository
public interface ConversationsRepository extends JpaRepository<Conversation, Long> {
	Conversation findByUserAndFriend(Long user, Long friend);
}