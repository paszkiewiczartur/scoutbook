package pl.scoutbook.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import pl.scoutbook.model.Post;

@RepositoryRestResource(path = "posts", collectionResourceRel = "posts")
public interface PostRepository extends JpaRepository<Post, Long> {
//	List<GroupPost> findByGroup(@Param(value = "group") Long group, Pageable p);

/*	  @Query(value = "SELECT * FROM group_post WHERE groups_id = ?1",
	    countQuery = "SELECT count(*) FROM group_post WHERE groups_id = ?1",
	    nativeQuery = true)
	  Page<GroupPost> findByGroup(Long group, Pageable pageable);*/
	/*@Query(
	        value = "SELECT * FROM group_post WHERE groups_id = ?1 ORDER BY ?#{#pageable}",
	        countQuery = "SELECT count(*) FROM group_post WHERE groups_id = ?1 ORDER BY ?#{#pageable}",
	        nativeQuery = true)
	    Page<GroupPost> findByGroup(@Param(value = "group") Long group, Pageable pageable);
	*/
	@Query(
	        value = "SELECT * FROM group_post WHERE groups_id = :group ORDER BY ?#{#pageable}",
	        countQuery = "SELECT count(*) FROM group_post WHERE groups_id = :group ORDER BY ?#{#pageable}",
	        nativeQuery = true)
	    Page<Post> findByGroup(@Param(value = "group") Long group, Pageable pageable);
	
	//:#{#group}
}