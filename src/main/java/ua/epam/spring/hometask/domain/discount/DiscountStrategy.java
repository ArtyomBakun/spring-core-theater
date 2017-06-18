package ua.epam.spring.hometask.domain.discount;

import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.User;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDateTime;

/**
 * Created by artyom on 18.06.17.
 */
public interface DiscountStrategy {

    double getDiscount(
            @Nullable User user,
            @Nonnull Event event,
            @Nonnull LocalDateTime airDateTime,
            long numberOfTickets);
}
