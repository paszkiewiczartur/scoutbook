package pl.scoutbook.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.core.event.ValidatingRepositoryEventListener;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;

import pl.scoutbook.model.Group;
import pl.scoutbook.model.UserProfile;
import pl.scoutbook.validation.PostValidator;

@Configuration
public class RepositoryConfig extends RepositoryRestConfigurerAdapter {
    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        config.exposeIdsFor(Group.class);
        config.exposeIdsFor(UserProfile.class);
    }
   /* @Override
    public void configureValidatingRepositoryEventListener(
      ValidatingRepositoryEventListener v) {
        v.addValidator("beforeCreate", new PostValidator());
    }*/
}