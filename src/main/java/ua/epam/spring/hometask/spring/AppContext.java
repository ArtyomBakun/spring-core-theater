package ua.epam.spring.hometask.spring;

import org.hsqldb.util.DatabaseManagerSwing;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import ua.epam.spring.hometask.domain.Auditorium;
import ua.epam.spring.hometask.domain.discount.BirthdayStrategy;
import ua.epam.spring.hometask.domain.discount.DiscountStrategy;
import ua.epam.spring.hometask.domain.discount.TenTicketStrategy;
import ua.epam.spring.hometask.service.impl.*;

import javax.sql.DataSource;
import java.util.*;

/**
 * Created by artyom on 08.06.17.
 */
@Configuration
@EnableAspectJAutoProxy
@PropertySource({
        "classpath:/db/config.properties"
})
@ComponentScan("ua.epam.spring.hometask")
public class AppContext {

    @Value("${url}")
    private String url;

    @Value("${username}")
    private String username;

    @Value("${password}")
    private String password;

    @Bean
    public DataSource dataSource(){
        String dbname = url.split(":")[3];
        EmbeddedDatabase db = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.HSQL).setName(dbname)
                .addScript("db/sql/create.sql")
                .addScript("db/sql/insert.sql")
                .build();
//        DatabaseManagerSwing.main( //simple gui for HSQLDB
//                new String[] {
//                        "--url", url,
//                        "--user", username,
//                        "--password", password,
//                        "--noexit"
//                });
        return db;
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyPlaceholder() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public List<DiscountStrategy> strategies(){
        List<DiscountStrategy> strategies = new ArrayList<>();
        strategies.add(birthdayStrategy());
        strategies.add(tenTicketStrategy());
        return strategies;
    }

    @Bean
    public DiscountStrategy birthdayStrategy(){
        return new BirthdayStrategy();
    }

    @Bean
    public DiscountStrategy tenTicketStrategy(){
        return new TenTicketStrategy();
    }

    @Bean
    public DiscountServiceImpl discountService(){
        DiscountServiceImpl discountService = new DiscountServiceImpl();
        discountService.setStrategies(strategies());
        return discountService;
    }
}
