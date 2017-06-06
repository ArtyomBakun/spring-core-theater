package ua.epam.spring.hometask.service.impl;

import ua.epam.spring.hometask.dao.BookingDao;
import ua.epam.spring.hometask.domain.Auditorium;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.Ticket;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.service.BookingService;
import ua.epam.spring.hometask.service.DiscountService;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * Created by artyom on 04.06.17.
 */
public class BookingServiceImpl implements BookingService
{
    private DiscountService discountService;
    private BookingDao dao;

    public BookingServiceImpl(DiscountService discountService)
    {
        this.discountService = discountService;
    }

    @Override
    public double getTicketsPrice(@Nonnull Event event, @Nonnull LocalDateTime dateTime, @Nullable User user,
            @Nonnull Auditorium auditorium, @Nonnull Set<Long> seats)
    {
        return dao.getTicketsPrice(event, dateTime, user, auditorium, seats, discountService);
    }

    @Override
    public void bookTickets(@Nonnull Set<Ticket> tickets)
    {
        dao.bookTickets(tickets);
    }

    @Nonnull
    @Override
    public Set<Ticket> getPurchasedTicketsForEvent(@Nonnull Event event, @Nonnull LocalDateTime dateTime)
    {
        return dao.getPurchasedTicketsForEvent(event, dateTime);
    }

    public DiscountService getDiscountService()
    {
        return discountService;
    }

    public void setDiscountService(DiscountService discountService)
    {
        this.discountService = discountService;
    }

    public BookingDao getDao()
    {
        return dao;
    }

    public void setDao(BookingDao dao)
    {
        this.dao = dao;
    }
}
