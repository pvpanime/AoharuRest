package dev.nemi.aoharu.config;

import dev.nemi.aoharu.security.Custom403Handler;
import dev.nemi.aoharu.security.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.sql.DataSource;
import java.util.List;

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

//    http.formLogin(
//      s -> s.loginPage("/login")
//    );

    http.sessionManagement(s -> {
      s.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    });
    http.csrf( c -> c.disable() );
    http.cors( Customizer.withDefaults() );


    http.rememberMe( re -> {
      re.key("0sdfhiabbioafdonkavsfonkavsdiobnvasdipbnosdv")
        .tokenRepository(persistentTokenRepository())
        .userDetailsService(customUserDetailsService)
        .tokenValiditySeconds(60 * 60 * 24 * 30)
        ;
    });

    http.authorizeHttpRequests(
      auth -> {
//        auth.requestMatchers("/css/**", "/js/**", "/img/**", "/login").permitAll();
//        auth.requestMatchers("/board/write", "/board/edit/*", "/food/register", "/food/edit/*").authenticated();
//        auth.anyRequest().authenticated();
        auth
          .requestMatchers("/api/auth/**").permitAll()
          .anyRequest().permitAll()
//          .anyRequest().authenticated()
        ;
      }
    );

    http.exceptionHandling(
      ex -> ex.accessDeniedHandler(accessDeniedHandler())
    );

    return http.build();

  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(List.of("http://localhost:3000")); // Allow React dev server
    configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
    configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));
    configuration.setAllowCredentials(true);

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);

    return source;
  }

  @Bean
  public WebSecurityCustomizer webSecurityCustomizer() {

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
