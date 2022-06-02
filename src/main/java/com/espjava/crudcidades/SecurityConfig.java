package com.espjava.crudcidades;

import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .csrf().disable()
        .authorizeRequests()
        // .antMatchers("/").hasAnyAuthority("listar", "admin")
        .antMatchers("/").authenticated()
        .antMatchers("/criar").hasAuthority("admin")
        .antMatchers("/excluir").hasAuthority("admin")
        .antMatchers("/preparaAlterar").hasAuthority("admin")
        .antMatchers("/alterar").hasAuthority("admin")
        .antMatchers("/mostrar").authenticated()
        .anyRequest().denyAll()
        .and()
        .oauth2Login().userInfoEndpoint().userAuthoritiesMapper((userAuthoritiesMapper()));
  }

  @Bean
  public GrantedAuthoritiesMapper userAuthoritiesMapper() {
    return (authorities) -> Set.of(new SimpleGrantedAuthority("admin"));
  }
}
