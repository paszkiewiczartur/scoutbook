package pl.scoutbook.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pl.scoutbook.model.User;

@Repository 
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}