package br.com.zup.proposta.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers(GET, "/api/proposal/**").hasAuthority("SCOPE_proposal:read")
                .antMatchers(POST, "/api/proposal/**").hasAuthority("SCOPE_proposal:write")
                .antMatchers(GET, "/api/card/**").hasAuthority("SCOPE_card:read")
                .antMatchers(POST, "/api/card/**").hasAuthority("SCOPE_card:write")
                .antMatchers(GET, "/actuator/**").permitAll()
                .anyRequest().authenticated()
            .and()
                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);
    }

}
