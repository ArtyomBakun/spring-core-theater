package ua.epam.spring.hometask.domain.discount;

import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.User;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDateTime;

/**
 * Created by artyom on 18.06.17.
 */
public class BirthdayStrategy implements DiscountStrategy{
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
}
