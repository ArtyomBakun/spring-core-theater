package ua.epam.spring.hometask.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ua.epam.spring.hometask.domain.Auditorium;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by artyom on 08.06.17.
 */
@Configuration
@ComponentScan("ua.epam.spring.hometask")
public class AppContext {

    @Bean
    public List<Auditorium> auditoriums(){
        List<Auditorium> auditoriums = new ArrayList<>();
        return auditoriums;
    }
}
