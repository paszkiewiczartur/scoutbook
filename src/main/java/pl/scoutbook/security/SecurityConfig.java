package pl.scoutbook.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import pl.scoutbook.security.CustomUserDetailsService;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public UserDetailsService customUserDetailsService() {
        return new CustomUserDetailsService();
    }

	
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
        .httpBasic().and()
        .csrf()
            .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).and()
        .authorizeRequests()
        	.antMatchers(HttpMethod.POST, "/api/register").permitAll()
        	.antMatchers(HttpMethod.POST, "/api/retrievePassword").permitAll()
        	.antMatchers(HttpMethod.POST, "/api/changePassword").permitAll()
            .antMatchers(HttpMethod.POST).authenticated().and()
        .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

   /* @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
        .withUser("john").password("pass1").roles("USER").and()
        .withUser("lenny").password("pass2").roles("USER");
    }*/
}