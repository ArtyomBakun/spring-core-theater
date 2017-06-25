package ua.epam.spring.hometask.dao.aspect;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by artyom on 25.06.17.
 */
@Component
public class EventCounterAspectDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Transactional
    public void incrementCallsCounter(String name){
        createIfNotExist(name);
        jdbcTemplate.update(
                "update eventStatistic set getByNameCalls=getByNameCalls+1 where name=?",
                name);
    }

    @Transactional
    public Long getCallsCounter(String name){
        createIfNotExist(name);
        return jdbcTemplate.queryForObject(
                "select getByNameCalls from eventStatistic where name=?",
                new Object[]{name}, Long.class);
    }

    @Transactional
    public void incrementBookTimesCounter(String name){
        createIfNotExist(name);
        jdbcTemplate.update(
                "update eventStatistic set bookTimes=bookTimes+1 where name=?",
                name);
    }

    @Transactional
    public long getBookTimesCounter(String name){
        createIfNotExist(name);
        return jdbcTemplate.queryForObject(
                "select bookTimes from eventStatistic where name=?",
                new Object[]{name}, Long.class);
    }

    @Transactional
    public void incrementQueryPriceCallsCounter(String name){
        createIfNotExist(name);
        jdbcTemplate.update(
                "update eventStatistic set queryPriceCalls=queryPriceCalls+1 where name=?",
                name);
    }

    @Transactional
    public long getQueryPriceCallsCounter(String name){
        createIfNotExist(name);
        return jdbcTemplate.queryForObject(
                "select queryPriceCalls from eventStatistic where name=?",
                new Object[]{name}, Long.class);
    }

    @Transactional
    private void createIfNotExist(String name){
        if(jdbcTemplate.query(
                "select name from eventStatistic where name=?",
                new Object[]{name}, ((rs, i) -> rs.getString("name"))).isEmpty()){
            jdbcTemplate.update(
                    "insert into eventStatistic (name, getByNameCalls, bookTimes, queryPriceCalls) " +
                            "values (?, 0, 0, 0)", name);
        }
    }
}
