package ua.epam.spring.hometask;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ua.epam.spring.hometask.domain.Auditorium;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.discount.TenTicketStrategy;
import ua.epam.spring.hometask.service.BookingService;
import ua.epam.spring.hometask.service.DiscountService;
import ua.epam.spring.hometask.service.EventService;
import ua.epam.spring.hometask.service.impl.BookingServiceImpl;
import ua.epam.spring.hometask.service.impl.DiscountServiceImpl;
import ua.epam.spring.hometask.spring.AppContext;
import ua.epam.spring.hometask.spring.aspect.DiscountAspect;
import ua.epam.spring.hometask.spring.aspect.EventCounterAspect;

import java.time.LocalDateTime;

/**
 * Created by artyom on 03.06.17.
 */
public class Main {
    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(AppContext.class);
        Auditorium a = (Auditorium) ctx.getBean("aud1");
        System.out.println(a.getName());

        BookingService bookingService = ctx.getBean(BookingService.class);
        DiscountService discountService = ((BookingServiceImpl)bookingService).getDiscountService();

        discountService.getDiscount(null, new Event(), LocalDateTime.now(), 21);
        System.out.println(DiscountAspect.totalCalls.get(TenTicketStrategy.class.getName()).get(null));
        discountService.getDiscount(null, new Event(), LocalDateTime.now(), 12);
        System.out.println(DiscountAspect.totalCalls.get(TenTicketStrategy.class.getName()).get(null));

        Event event = new Event();
        event.setName("SomeEvent");
        ctx.getBean(EventService.class).getByName("SomeEvent");
        System.out.println(EventCounterAspect.eventStatisticsMap.get("SomeEvent").getByNameCalls);
    }
}
