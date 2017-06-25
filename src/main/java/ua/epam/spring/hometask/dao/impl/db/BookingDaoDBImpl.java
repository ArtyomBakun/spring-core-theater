package ua.epam.spring.hometask.dao.impl.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ua.epam.spring.hometask.dao.AuditoriumDao;
import ua.epam.spring.hometask.dao.BookingDao;
import ua.epam.spring.hometask.dao.UserDao;
import ua.epam.spring.hometask.domain.*;
import ua.epam.spring.hometask.service.DiscountService;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by artyom on 25.06.17.
 */
@Component
public class BookingDaoDBImpl implements BookingDao{

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    private UserDao userDao;

    @Autowired
    private AuditoriumDao auditoriumDao;

    private final double vipCoeff = 2.0, highRatingCoeff = 1.2;

    @Override
    public double getTicketsPrice(
            @Nonnull Event event,
            @Nonnull LocalDateTime dateTime,
            @Nullable User user,
            @Nonnull Auditorium auditorium,
            @Nonnull Set<Long> seats,
            @Nonnull DiscountService discountService)
    {
        double totalPrice = 0;
        Set<Long> vips = new HashSet<>(seats);
        vips.retainAll(auditorium.getVipSeats());
        int numOfVipSeats = vips.size();
        totalPrice = (seats.size() + numOfVipSeats * (vipCoeff - 1.0)) * event.getBasePrice();
        totalPrice *= 1.0 - discountService.getDiscount(user, event, dateTime, seats.size());
        totalPrice *= event.getRating().equals(EventRating.HIGH) ? highRatingCoeff : 1.0;
        return totalPrice;
    }

    @Override
    @Transactional
    public void bookTickets(@Nonnull Set<Ticket> tickets) {
        tickets.forEach(t ->
        {
            jdbcTemplate.update("update ticket set booked=true where id=?", t.getId());
            if (t.getUser() != null)
            {
                t.getUser().getTickets().add(t);
                jdbcTemplate.update("update ticket set userId=? where id=?", t.getUser().getId(), t.getId());
            }
        });
    }

    @Nonnull
    @Override
    @Transactional
    public Set<Ticket> getPurchasedTicketsForEvent(@Nonnull Event event, @Nonnull LocalDateTime dateTime) {
        return jdbcTemplate.query("select * from ticket where eventId=? and dateTime=? and booked=true",
                new Object[]{event.getId(), dateTime},
                (rs, i) -> {
                    Ticket ticket = new Ticket(
                            userDao.getById(rs.getLong("userId")),
                            event,
                            dateTime,
                            auditoriumDao.getByName(rs.getString("auditoriumName")),
                            rs.getLong("seats")
                    );
                    return ticket;
                }
        ).stream().collect(Collectors.toSet());
    }
}
