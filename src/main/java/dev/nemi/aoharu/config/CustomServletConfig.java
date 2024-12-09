package dev.nemi.aoharu.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class CustomServletConfig implements WebMvcConfigurer {

  private static final String[] CLASSPATH_RESOURCE_LOCATIONS = {
    "css", "js", "img", "asset"
  };
  
  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    for (String path : CLASSPATH_RESOURCE_LOCATIONS) {
      registry.addResourceHandler(String.format("/%s/**", path)).addResourceLocations(String.format("classpath:/static/%s/", path));
    }
//    registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
  }
  
}
