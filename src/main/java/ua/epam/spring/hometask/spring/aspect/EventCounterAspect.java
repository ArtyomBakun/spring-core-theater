package ua.epam.spring.hometask.spring.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.epam.spring.hometask.dao.aspect.EventCounterAspectDao;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.Ticket;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by artyom on 18.06.17.
 */
@Component
@Aspect
public class EventCounterAspect {

    @Autowired
    private EventCounterAspectDao dao;

    @Before("execution(* ua.epam.spring.hometask.service.EventService.getByName(..))")
    public void countAccessEventByName(JoinPoint jp) throws Throwable{
        String name = (String) jp.getArgs()[0];
        dao.incrementCallsCounter(name);
    }

    @Before("execution(* ua.epam.spring.hometask.service.BookingService.bookTickets(..))")
    public void countBookingEventByName(JoinPoint jp) throws Throwable{
        Set<Ticket> tickets = (Set<Ticket>) jp.getArgs()[0];
        tickets.forEach(ticket -> {
            String name = ticket.getEvent().getName();
            dao.incrementBookTimesCounter(name);
        });
    }

    @Before("execution(* ua.epam.spring.hometask.service.BookingService.getTicketsPrice(..))")
    public void countQueryPriceOfEventByName(JoinPoint jp) throws Throwable{
        String name = ((Event) jp.getArgs()[0]).getName();
        dao.incrementQueryPriceCallsCounter(name);
    }
}
