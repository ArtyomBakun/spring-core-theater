package ua.epam.spring.hometask;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ua.epam.spring.hometask.domain.Auditorium;

/**
 * Created by artyom on 03.06.17.
 */
public class Main {
    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-context/auditorium-beans.xml");
        Auditorium a = (Auditorium) ctx.getBean("aud1");
        System.out.println(a.getName());
    }
}
