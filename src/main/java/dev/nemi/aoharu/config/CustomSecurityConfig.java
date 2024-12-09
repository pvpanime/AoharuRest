package dev.nemi.aoharu.config;

import dev.nemi.aoharu.security.Custom403Handler;
import dev.nemi.aoharu.security.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Log4j2
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class CustomSecurityConfig {

  private final DataSource dataSource;
  private final CustomUserDetailsService customUserDetailsService;


  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

    log.info("----------configure----------");

    http.formLogin(
      s -> s.loginPage("/login")
    );

    http.csrf( c -> c.disable() );

    http.rememberMe( re -> {
      re.key("0sdfhiabbioafdonkavsfonkavsdiobnvasdipbnosdv")
        .tokenRepository(persistentTokenRepository())
        .userDetailsService(customUserDetailsService)
        .tokenValiditySeconds(60 * 60 * 24 * 30)
        ;
    });

    http.authorizeHttpRequests(
      auth -> {
        auth.requestMatchers("/css/**", "/js/**", "/img/**", "/login").permitAll();
        auth.requestMatchers("/board/write", "/board/edit/*", "/food/register", "/food/edit/*").authenticated();
//        auth.anyRequest().authenticated();
        auth.anyRequest().permitAll();
      }
    );

    http.exceptionHandling(
      ex -> ex.accessDeniedHandler(accessDeniedHandler())
    );

    return http.build();

  }

  @Bean
  public WebSecurityCustomizer webSecurityCustomizer() {

    log.info("----------web configure----------");
    return web -> web.ignoring().requestMatchers(
      PathRequest.toStaticResources().atCommonLocations()
    );

  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public PersistentTokenRepository persistentTokenRepository() {
    JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
    tokenRepository.setDataSource(dataSource);
    return tokenRepository;
  }

  @Bean
  public AccessDeniedHandler accessDeniedHandler() {
    return new Custom403Handler();
  }

}
