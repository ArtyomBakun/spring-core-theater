package ua.epam.spring.hometask.service.impl;

import ua.epam.spring.hometask.domain.discount.DiscountStrategy;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.service.DiscountService;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by artyom on 04.06.17.
 */
public class DiscountServiceImpl implements DiscountService {

    private List<DiscountStrategy> strategies;

    public List<DiscountStrategy> getStrategies() {
        return strategies;
    }

    public void setStrategies(List<DiscountStrategy> strategies) {
        this.strategies = strategies;
    }

    @Override
    public double getDiscount(@Nullable User user, @Nonnull Event event, @Nonnull LocalDateTime airDateTime, long numberOfTickets) {
        double maxSale = 0;
        for (DiscountStrategy s : strategies) {
            double currDisc = s.getDiscount(user, event, airDateTime, numberOfTickets);
            maxSale = (maxSale < currDisc) ? currDisc : maxSale;
        }
        return maxSale;
    }
}
