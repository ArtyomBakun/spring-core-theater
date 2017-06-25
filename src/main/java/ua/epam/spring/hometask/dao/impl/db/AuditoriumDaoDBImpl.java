package ua.epam.spring.hometask.dao.impl.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ua.epam.spring.hometask.dao.AuditoriumDao;
import ua.epam.spring.hometask.domain.Auditorium;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by artyom on 25.06.17.
 */
@Component
public class AuditoriumDaoDBImpl implements AuditoriumDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Nonnull
    @Override
    @Transactional
    public Set<Auditorium> getAll() {
        return new HashSet<>(jdbcTemplate.query("select * from auditorium", new AuditoriumRowMapper()));
    }

    @Nullable
    @Override
    @Transactional
    public Auditorium getByName(@Nonnull String name) {
        return jdbcTemplate.queryForObject(
                "select * from auditorium where name=?",
                new Object[]{name},
                new AuditoriumRowMapper()
        );
    }

    private class AuditoriumRowMapper implements RowMapper<Auditorium> {
        @Override
        public Auditorium mapRow(ResultSet rs, int i) throws SQLException {
            Auditorium aud = new Auditorium();
            aud.setName(rs.getString("name"));
            aud.setNumberOfSeats(rs.getLong("numberOfSeats"));
            Set<Long> vips = new HashSet<>();
            for (String s : rs.getString("vips").split(",")) {
                vips.add(Long.parseLong(s));
            }
            aud.setVipSeats(vips);
            return aud;
        }
    }
}
