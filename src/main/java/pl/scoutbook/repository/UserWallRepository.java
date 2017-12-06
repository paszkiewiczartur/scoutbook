package pl.scoutbook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import pl.scoutbook.model.UserWall;

@RepositoryRestResource(path = "userWall", collectionResourceRel = "userWall")
public interface UserWallRepository extends JpaRepository<UserWall, Long> {
}