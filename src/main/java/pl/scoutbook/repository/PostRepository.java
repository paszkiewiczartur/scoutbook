package pl.scoutbook.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import pl.scoutbook.entities.Post;

@RepositoryRestResource(path = "posts", collectionResourceRel = "posts")
public interface PostRepository extends JpaRepository<Post, Long> {

	@Query(
	        value = "SELECT * FROM post WHERE groups_id = :group ORDER BY ?#{#pageable}",
	        countQuery = "SELECT count(*) FROM post WHERE groups_id = :group ORDER BY ?#{#pageable}",
	        nativeQuery = true)
	    Page<Post> findByGroup(@Param(value = "group") Long group, Pageable pageable);
	@Query(
	        value = "SELECT * FROM post WHERE user_profile_id = :user_profile ORDER BY ?#{#pageable}",
	        countQuery = "SELECT count(*) FROM post WHERE user_profile_id = :user_profile ORDER BY ?#{#pageable}",
	        nativeQuery = true)
	    Page<Post> findByUserWall(@Param(value = "user_profile") Long user_profile, Pageable pageable);
	
}