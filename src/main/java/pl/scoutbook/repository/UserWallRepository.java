package pl.scoutbook.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import pl.scoutbook.model.UserWall;

@RepositoryRestResource(path = "userWall", collectionResourceRel = "userWall")
public interface UserWallRepository extends JpaRepository<UserWall, Long> {
	@Query(
	        value = "SELECT * FROM user_wall WHERE user_profile_id = :user AND shown = :shown ORDER BY ?#{#pageable}",
	        countQuery = "SELECT count(*) FROM user_wall WHERE user_profile_id = :user AND shown = :shown ORDER BY ?#{#pageable}",
	        nativeQuery = true)
	    Page<UserWall> findByUser(@Param(value = "user") Long user, @Param(value = "shown") Boolean shown, Pageable pageable);

}