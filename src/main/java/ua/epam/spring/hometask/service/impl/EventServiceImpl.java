package ua.epam.spring.hometask.service.impl;

import ua.epam.spring.hometask.dao.EventDao;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.service.EventService;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;

/**
 * Created by artyom on 03.06.17.
 */
public class EventServiceImpl implements EventService {

    private EventDao dao;

    @Nullable
    @Override
    public Event getByName(@Nonnull String name) {
        return dao.getByName(name);
    }

    @Override
    public Event save(@Nonnull Event object) {
        return dao.save(object);
    }

    @Override
    public void remove(@Nonnull Event object) {
        dao.remove(object);
    }

    @Override
    public Event getById(@Nonnull Long id) {
        return dao.getById(id);
    }

    @Nonnull
    @Override
    public Collection<Event> getAll() {
        return dao.getAll();
    }

    public void setDao(EventDao dao) {
        this.dao = dao;
    }
}
