package pl.scoutbook.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;

import pl.scoutbook.entities.Event;
import pl.scoutbook.entities.Group;
import pl.scoutbook.entities.UserProfile;

@Configuration
public class RepositoryConfig extends RepositoryRestConfigurerAdapter {
    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        config.exposeIdsFor(Group.class);
        config.exposeIdsFor(UserProfile.class);
        config.exposeIdsFor(Event.class);
    }
   /* @Override
    public void configureValidatingRepositoryEventListener(
      ValidatingRepositoryEventListener v) {
        v.addValidator("beforeCreate", new PostValidator());
    }*/
}