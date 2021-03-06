package pl.scoutbook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import pl.scoutbook.entities.Group;

@RepositoryRestResource(path = "groups", collectionResourceRel = "groups")
public interface GroupsRepository extends JpaRepository<Group, Long> {
}