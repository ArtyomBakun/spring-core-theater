package ua.epam.spring.hometask.spring.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
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

    public static Map<String, EventStatistics> eventStatisticsMap = new HashMap<>();

    @Before("execution(* ua.epam.spring.hometask.service.EventService+.getByName(..))")
    public void countAccessEventByName(JoinPoint jp) throws Throwable{
        String name = (String) jp.getArgs()[0];
        EventStatistics statistics = getEventStatisticsIfPresentOrSetNew(name);
        statistics.getByNameCalls++;
    }

//    @Before("execution(* ua.epam.spring.hometask.service.BookingService+.bookTickets(..))")
//    public void countBookingEventByName(JoinPoint jp) throws Throwable{
//        Set<Ticket> tickets = (Set<Ticket>) jp.getArgs()[0];
//        tickets.forEach(ticket -> {
//            String name = ticket.getEvent().getName();
//            EventStatistics statistics = getEventStatisticsIfPresentOrSetNew(name);
//            statistics.bookTimes++;
//        });
//    }

//    @Before("execution(* ua.epam.spring.hometask.service.BookingService+.getTicketsPrice(..))")
//    public void countQueryPriceOfEventByName(JoinPoint jp) throws Throwable{
//        String name = ((Event) jp.getArgs()[0]).getName();
//        EventStatistics statistics = getEventStatisticsIfPresentOrSetNew(name);
//        statistics.queryPriceCalls++;
//    }

    private EventStatistics getEventStatisticsIfPresentOrSetNew(String name){
        EventStatistics statistics = eventStatisticsMap.get(name);
        if(statistics == null){
            statistics = new EventStatistics();
            eventStatisticsMap.put(name, statistics);
        }
        return statistics;
    }

    public static class EventStatistics{
        public long getByNameCalls = 0;
        public long bookTimes = 0;
        public long queryPriceCalls = 0;
    }

}
