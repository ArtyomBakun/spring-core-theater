package ua.epam.spring.hometask;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ua.epam.spring.hometask.domain.Auditorium;
import ua.epam.spring.hometask.service.BookingService;
import ua.epam.spring.hometask.service.impl.BookingServiceImpl;
import ua.epam.spring.hometask.service.impl.DiscountServiceImpl;

/**
 * Created by artyom on 03.06.17.
 */
public class Main {
    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring/service.xml");
        Auditorium a = (Auditorium) ctx.getBean("aud1");
        System.out.println(a.getName());
        BookingService bookingService = ctx.getBean(BookingService.class);
        ((DiscountServiceImpl)((BookingServiceImpl)bookingService).getDiscountService()).getStrategies().forEach(System.out::println);
    }
}
