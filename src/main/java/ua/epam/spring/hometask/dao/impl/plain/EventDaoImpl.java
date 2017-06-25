package ua.epam.spring.hometask.dao.impl.plain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.epam.spring.hometask.dao.EventDao;
import ua.epam.spring.hometask.domain.Event;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by artyom on 03.06.17.
 */
//@Component
public class EventDaoImpl implements EventDao {

    @Autowired
    private static List<Event> events = new ArrayList<>();

    @Nullable
    @Override
    public Event getByName(@Nonnull String name) {
        return events.stream()
                .filter(e -> name.equals(e.getName()))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Event save(@Nonnull Event object) {
        return events.add(object) ? object : null;
    }

    @Override
    public void remove(@Nonnull Event object) {
        events.remove(object);
    }

    @Override
    public Event getById(@Nonnull Long id) {
        return events.stream()
                .filter(e -> e.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Nonnull
    @Override
    public Collection<Event> getAll() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }
}
