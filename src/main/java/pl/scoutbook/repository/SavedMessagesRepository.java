package pl.scoutbook.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import pl.scoutbook.entities.SavedMessage;

@RepositoryRestResource(path = "messages", collectionResourceRel = "messages")
public interface SavedMessagesRepository extends JpaRepository<SavedMessage, Long> {
	@Query(
	        value = "SELECT * FROM saved_message WHERE conversation = :conversation ORDER BY ?#{#pageable}",
	        countQuery = "SELECT count(*) FROM saved_message WHERE conversation = :conversation ORDER BY ?#{#pageable}",
	        nativeQuery = true)
	    Page<SavedMessage> findByConversation(@Param(value = "conversation") Long conversation, Pageable pageable);
	@Query(value = "SELECT COUNT(*) FROM saved_message WHERE conversation = :conversation",
			nativeQuery = true)
		long countMessages(@Param(value = "conversation") Long conversation);
}