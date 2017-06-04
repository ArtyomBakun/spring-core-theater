package ua.epam.spring.hometask.domain;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDateTime;

/**
 * Created by artyom on 04.06.17.
 */
public enum DiscountStrategy {

    BIRTHDAY_STRATEGY {
        @Override
        public double getDiscount(
                @Nullable User user,
                @Nonnull Event event,
                @Nonnull LocalDateTime airDateTime,
                long numberOfTickets)
        {
            if(user != null) {
                int diff = airDateTime.getDayOfYear() - user.getBirthday().getDayOfYear();
                if(diff >= 0 && diff <= 5){
                    return 0.05;
                }
            }
            return 0;
        }
    },
    TEN_TICKET_STRATEGY {
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
    };

    public abstract double getDiscount(
            @Nullable User user,
            @Nonnull Event event,
            @Nonnull LocalDateTime airDateTime,
            long numberOfTickets);
}
