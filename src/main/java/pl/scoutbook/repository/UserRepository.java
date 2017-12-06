package pl.scoutbook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pl.scoutbook.model.User;

@Repository 
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(@Param(value = "email") String email);
}