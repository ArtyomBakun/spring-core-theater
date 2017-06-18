package ua.epam.spring.hometask.domain.discount;

import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.User;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDateTime;

/**
 * Created by artyom on 18.06.17.
 */
public class TenTicketStrategy implements DiscountStrategy {
    @Override
    public double getDiscount(
            @Nullable User user,
            @Nonnull Event event,
            @Nonnull LocalDateTime airDateTime,
            long numberOfTickets)
    {
        if(user == null){
            return tensTicketsToDiscount(numberOfTickets/10, numberOfTickets);
        } else {
            return tensTicketsToDiscount(
                    ((user.getTickets().size()+numberOfTickets)/10 - user.getTickets().size()/10),
                    numberOfTickets);
        }
    }

    private double tensTicketsToDiscount(long tens, long total){
        return ((double) tens)/(2 * total);
    }
}
