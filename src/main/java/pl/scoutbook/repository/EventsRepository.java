package pl.scoutbook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import pl.scoutbook.model.Event;

@RepositoryRestResource(path = "events", collectionResourceRel = "events")
public interface EventsRepository extends JpaRepository<Event, Long> {
}