package pl.scoutbook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pl.scoutbook.entities.NewPasswordCode;

@Repository 
public interface NewPasswordCodeRepository extends JpaRepository<NewPasswordCode, Long> {
    NewPasswordCode findByCode(String code);
}