package org.example.library.configs;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ApplicationBeanConfiguration implements WebMvcConfigurer {
    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }




}
