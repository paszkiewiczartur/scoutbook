package pl.scoutbook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import pl.scoutbook.entities.UserProfile;

@RepositoryRestResource 
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
	@Query("SELECT max(e.id) FROM UserProfile e")
	Long findMaxId();
}