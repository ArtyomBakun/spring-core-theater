package ua.epam.spring.hometask.dao;

import ua.epam.spring.hometask.domain.Event;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by artyom on 03.06.17.
 */
public interface EventDao extends AbstractDao<Event> {

    @Nullable
    Event getByName(@Nonnull String name);
}
