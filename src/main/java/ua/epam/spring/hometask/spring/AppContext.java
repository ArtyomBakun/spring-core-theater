package ua.epam.spring.hometask.spring;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import ua.epam.spring.hometask.dao.impl.AuditoriumDaoImpl;
import ua.epam.spring.hometask.dao.impl.BookingDaoImpl;
import ua.epam.spring.hometask.dao.impl.EventDaoImpl;
import ua.epam.spring.hometask.dao.impl.UserDaoImpl;
import ua.epam.spring.hometask.domain.Auditorium;
import ua.epam.spring.hometask.domain.discount.BirthdayStrategy;
import ua.epam.spring.hometask.domain.discount.DiscountStrategy;
import ua.epam.spring.hometask.domain.discount.TenTicketStrategy;
import ua.epam.spring.hometask.service.EventService;
import ua.epam.spring.hometask.service.impl.*;

import java.util.*;

/**
 * Created by artyom on 08.06.17.
 */
@Configuration
@EnableAspectJAutoProxy
@PropertySource({
        "classpath:data/auditorium.properties"
})
@ComponentScan("ua.epam.spring.hometask")
public class AppContext {

    @Value("${name1}")
    private String name;

    @Value("${numberOfSeats1}")
    private long numberOfSeats;

    @Value("${vipSeats1}")
    private String vipSeats;

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyPlaceholder() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public List<Auditorium> auditoriums() {
        List<Auditorium> auditoriums = new ArrayList<>();
        auditoriums.add(aud1());
        return auditoriums;
    }

    @Bean
    public Auditorium aud1(){
        Auditorium aud1 = new Auditorium();
        aud1.setName(name);
        aud1.setNumberOfSeats(numberOfSeats);
        Set<Long> vips = new HashSet<>();
        for (String s : vipSeats.split(",")) {
            vips.add(Long.getLong(s));
        }
        aud1.setVipSeats(vips);
        return aud1;
    }

    @Bean
    public List<DiscountStrategy> strategies(){
        List<DiscountStrategy> strategies = new ArrayList<>();
        strategies.add(birthdayStrategy());
        strategies.add(tenTicketStrategy());
        return strategies;
    }

    @Bean
    public DiscountStrategy birthdayStrategy(){
        return new BirthdayStrategy();
    }

    @Bean
    public DiscountStrategy tenTicketStrategy(){
        return new TenTicketStrategy();
    }

    @Bean
    public DiscountServiceImpl discountService(){
        DiscountServiceImpl discountService = new DiscountServiceImpl();
        discountService.setStrategies(strategies());
        return discountService;
    }
}
