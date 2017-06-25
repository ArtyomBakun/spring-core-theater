package ua.epam.spring.hometask.dao.impl.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ua.epam.spring.hometask.dao.AuditoriumDao;
import ua.epam.spring.hometask.dao.EventDao;
import ua.epam.spring.hometask.domain.Auditorium;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.EventRating;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Created by artyom on 25.06.17.
 */
@Component
public class EventDaoDBImpl implements EventDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    private AuditoriumDao auditoriumDao;

    @Nullable
    @Override
    @Transactional
    public Event getByName(@Nonnull String name) {
        return jdbcTemplate.queryForObject(
                "select * from event where name=?",
                new Object[]{name},
                new EventRowMapper()
        );
    }

    @Override
    @Transactional
    public Event save(@Nonnull Event event) {
        jdbcTemplate.update(
                "insert into event (name, basePrice, eventRating) values (?, ?, ?)",
                event.getName(),
                event.getBasePrice(),
                event.getRating().name()
        );
        event.setId(jdbcTemplate.queryForObject("call identity()", Long.class));
        return null;
    }

    @Override
    @Transactional
    public void remove(@Nonnull Event event) {
        jdbcTemplate.update("delete from event where id=?", event.getId());
    }

    @Override
    @Transactional
    public Event getById(@Nonnull Long id) {
        return jdbcTemplate.queryForObject(
                "select * from event where id=?",
                new Object[]{id},
                new EventRowMapper()
        );
    }

    @Nonnull
    @Override
    @Transactional
    public Collection<Event> getAll() {
        return jdbcTemplate.query("select * from event", new EventRowMapper());
    }

    private class EventRowMapper implements RowMapper<Event>{

        @Override
        public Event mapRow(ResultSet rs, int i) throws SQLException {
            Event event = new Event();
            event.setId(rs.getLong("id"));
            event.setName(rs.getString("name"));
            event.setBasePrice(rs.getDouble("basePrice"));
            event.setRating(EventRating.valueOf(rs.getString("eventRating")));

            NavigableSet<LocalDateTime> airTimes = new TreeSet<>();
            jdbcTemplate.queryForList(
                    "select airDate from eventAirDate where eventId=?",
                    new Object[]{event.getId()}, LocalDateTime.class)
                    .forEach(airTimes::add);
            event.setAirDates(airTimes);

            NavigableMap<LocalDateTime, Auditorium> auditoriums = new TreeMap<>();
            jdbcTemplate.query(
                    "select airDate, auditoriumName from eventAuditorium where eventId=?",
                    new Object[]{event.getId()},
                    (resultSet -> {
                        auditoriums.put(
                                resultSet.getTimestamp("airDate").toLocalDateTime(),
                                auditoriumDao.getByName(resultSet.getString("auditoriumName"))
                        );
                    }));
            event.setAuditoriums(auditoriums);
            return event;
        }
    }
}
