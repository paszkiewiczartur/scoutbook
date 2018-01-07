package pl.scoutbook.security;

import java.util.Collections;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import pl.scoutbook.entities.User;
import pl.scoutbook.repository.UserRepository;

@Component
public class WebSocketAuthenticatorService {
	
	@Autowired
	UserRepository userRepository;
	
    public UsernamePasswordAuthenticationToken getAuthenticatedOrFail(
    		final String username, final String userId, final String password)
    				throws AuthenticationException {
        if (username == null || username.trim().length() == 0) {
            throw new AuthenticationCredentialsNotFoundException("Username was null or empty.");
        }
        if (password == null || password.trim().length() == 0) {
            throw new AuthenticationCredentialsNotFoundException("Password was null or empty.");
        }
        if (userId == null || userId.trim().length() == 0) {
            throw new AuthenticationCredentialsNotFoundException("UserId was null or empty.");
        }
        // Add your own logic for retrieving user in fetchUserFromDb()
        if (!findUser(username, password)) {
            throw new BadCredentialsException("Bad credentials for user " + username);
        }

        // null credentials, we do not pass the password along to prevent security flaw
        return new UsernamePasswordAuthenticationToken(
                userId,
                null,
                Collections.singleton((GrantedAuthority) () -> "USER")
        );
    }
	
	private boolean findUser(String username, String password){
		boolean result = false;
		Optional<User> user = Optional.of(userRepository.findByEmail(username));
		if(user.isPresent())
			if(user.get().getPassword().equals(password))
				result = true;
		return result;
	}
}