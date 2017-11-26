package pl.scoutbook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import pl.scoutbook.model.UserProfile;

@RepositoryRestResource 
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
}