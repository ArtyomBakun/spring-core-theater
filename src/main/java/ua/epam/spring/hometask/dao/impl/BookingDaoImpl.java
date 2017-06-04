package ua.epam.spring.hometask.dao.impl;

import ua.epam.spring.hometask.dao.BookingDao;
import ua.epam.spring.hometask.domain.Auditorium;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.Ticket;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.service.DiscountService;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by artyom on 04.06.17.
 */
public class BookingDaoImpl implements BookingDao {

    private static Set<Ticket> bookedTickets = new HashSet<>();
    private DiscountService discountService;
    private double vipCOeff = 2.0;

    @Override
    public double getTicketsPrice(
            @Nonnull Event event,
            @Nonnull LocalDateTime dateTime,
            @Nullable User user,
            @Nonnull Auditorium auditorium,
            @Nonnull Set<Long> seats) {
        double totalPrice = 0;
        Set<Long> vips = new HashSet<>(seats);
        vips.retainAll(auditorium.getVipSeats());
        int numOfVipSeats = vips.size();
        totalPrice = (seats.size() + numOfVipSeats * (vipCOeff - 1.0)) * event.getBasePrice();
        totalPrice *= 1.0 - discountService.getDiscount(user, event, dateTime, seats.size());
        return totalPrice;
    }

    @Override
    public void bookTickets(@Nonnull Set<Ticket> tickets) {
        bookedTickets.addAll(tickets);
    }

    @Nonnull
    @Override
    public Set<Ticket> getPurchasedTicketsForEvent(@Nonnull Event event, @Nonnull LocalDateTime dateTime) {
        return bookedTickets.stream()
                .filter(t -> t.getDateTime().equals(dateTime) && t.getEvent().equals(event))
                .collect(Collectors.toSet());
    }

    public static Set<Ticket> getBookedTickets() {
        return bookedTickets;
    }

    public static void setBookedTickets(Set<Ticket> bookedTickets) {
        BookingDaoImpl.bookedTickets = bookedTickets;
    }

    public DiscountService getDiscountService() {
        return discountService;
    }

    public void setDiscountService(DiscountService discountService) {
        this.discountService = discountService;
    }
}
