package ua.epam.spring.hometask;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ua.epam.spring.hometask.dao.aspect.DiscountAspectDao;
import ua.epam.spring.hometask.dao.aspect.EventCounterAspectDao;
import ua.epam.spring.hometask.domain.Auditorium;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.EventRating;
import ua.epam.spring.hometask.domain.Ticket;
import ua.epam.spring.hometask.domain.discount.TenTicketStrategy;
import ua.epam.spring.hometask.service.AuditoriumService;
import ua.epam.spring.hometask.service.BookingService;
import ua.epam.spring.hometask.service.DiscountService;
import ua.epam.spring.hometask.service.EventService;
import ua.epam.spring.hometask.service.impl.BookingServiceImpl;
import ua.epam.spring.hometask.service.impl.DiscountServiceImpl;
import ua.epam.spring.hometask.spring.AppContext;
import ua.epam.spring.hometask.spring.aspect.DiscountAspect;
import ua.epam.spring.hometask.spring.aspect.EventCounterAspect;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by artyom on 03.06.17.
 */
public class Main {

    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(AppContext.class);
        AuditoriumService auditoriumService = ctx.getBean(AuditoriumService.class);
        auditoriumService.getByName("Little hall").getVipSeats().forEach(System.out::println);
        System.out.println("===========");
        demo(ctx);
    }

    private static void demo(ApplicationContext ctx){
        BookingService bookingService = ctx.getBean(BookingService.class);
        DiscountService discountService = bookingService.getDiscountService();

        discountService.getDiscount(null, new Event(), LocalDateTime.now(), 21);
        System.out.println(
                ctx.getBean(DiscountAspectDao.class).
                        getCounter(TenTicketStrategy.class.getSimpleName(), null));
        discountService.getDiscount(null, new Event(), LocalDateTime.now(), 12);
        System.out.println(
                ctx.getBean(DiscountAspectDao.class).
                        getCounter(TenTicketStrategy.class.getSimpleName(), null));

        Event event = new Event();
        event.setName("SomeEvent");
        event.setRating(EventRating.HIGH);
        EventService eventService = ctx.getBean(EventService.class);
        eventService.save(event);
        System.out.println(eventService.getByName("SomeEvent").getRating().toString());
        System.out.println(ctx.getBean(EventCounterAspectDao.class).getCallsCounter("SomeEvent"));
        Ticket ticket1 = new Ticket(null, event, LocalDateTime.now(), null, 12);
        Ticket ticket2 = new Ticket(null, event, LocalDateTime.now(), null, 22);
        Ticket ticket3 = new Ticket(null, event, LocalDateTime.now(), null, 32);
        Set<Ticket> tickets = new HashSet<>();
        tickets.add(ticket1);
        tickets.add(ticket2);
        tickets.add(ticket3);
        bookingService.bookTickets(tickets);
        System.out.println(ctx.getBean(EventCounterAspectDao.class).getBookTimesCounter("SomeEvent"));
    }
}
