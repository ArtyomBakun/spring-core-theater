package ua.epam.spring.hometask.dao.impl.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ua.epam.spring.hometask.dao.UserDao;
import ua.epam.spring.hometask.domain.Ticket;
import ua.epam.spring.hometask.domain.User;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

/**
 * Created by artyom on 25.06.17.
 */
@Component
public class UserDaoDBImpl implements UserDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Nullable
    @Override
    @Transactional
    public User getUserByEmail(@Nonnull String email) {
        User user = jdbcTemplate.queryForObject(
                "select * from user where email=?",
                new Object[]{email},
                new UserRowMapper()
        );
        jdbcTemplate.queryForList("select * from ticket where userId=?", user.getId(), Ticket.class);
        return user;
    }

    @Override
    @Transactional
    public User save(@Nonnull User user) {
        jdbcTemplate.update(
                "insert into user (firstName, lastName, email, birthday) values (?,?,?,?)",
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getBirthday());
        user.setId(jdbcTemplate.queryForObject("call identity()", Long.class));
        return user;
    }

    @Override
    @Transactional
    public void remove(@Nonnull User user) {
        jdbcTemplate.update("delete from user where id=?", user.getId());
    }

    @Override
    @Transactional
    public User getById(@Nonnull Long id) {
        return jdbcTemplate.queryForObject(
            "select * from user where id=?",
            new Object[]{id},
            new UserRowMapper()
        );
    }

    @Nonnull
    @Override
    @Transactional
    public Collection<User> getAll() {
        return jdbcTemplate.query("select * from user", new UserRowMapper());
    }

    private class UserRowMapper implements RowMapper<User>{
        @Override
        public User mapRow(ResultSet rs, int i) throws SQLException {
            User newUser = new User();
            newUser.setId(rs.getLong("id"));
            newUser.setFirstName(rs.getString("firstName"));
            newUser.setLastName(rs.getString("lastName"));
            newUser.setEmail(rs.getString("email"));
            newUser.setBirthday(rs.getTimestamp("birthday").toLocalDateTime());
            return newUser;
        }
    }
}
