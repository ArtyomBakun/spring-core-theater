package ua.epam.spring.hometask.dao.aspect;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ua.epam.spring.hometask.domain.User;

/**
 * Created by artyom on 25.06.17.
 */
@Component
public class DiscountAspectDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional
    public void incrementCounter(String name, User user){
        String userId = user == null ? "unknown" : user.getId().toString();
        createIfNotExist(name, userId);
        jdbcTemplate.update(
                "update totalCalls set calls=calls+1 where name=? and userId=?",
                name, userId);
    }

    @Transactional
    public long getCounter(String name, User user){
        String userId = user == null ? "unknown" : user.getId().toString();
        createIfNotExist(name, userId);
        return jdbcTemplate.queryForObject(
                "select calls from totalCalls where name=? and userId=?",
                new Object[]{name, userId}, Long.class);
    }

    @Transactional
    private void createIfNotExist(String name, String userId){
        if(jdbcTemplate.query(
                "select name from totalCalls where name=? and userId=?",
                new Object[]{name, userId}, ((rs, i) -> rs.getString("name"))).isEmpty()){
            jdbcTemplate.update(
                    "insert into totalCalls (name, userId, calls) " +
                            "values (?, ?, 0)", name, userId);
        }
    }

}
