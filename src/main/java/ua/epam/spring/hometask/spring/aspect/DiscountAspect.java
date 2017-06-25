package ua.epam.spring.hometask.spring.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.epam.spring.hometask.dao.aspect.DiscountAspectDao;
import ua.epam.spring.hometask.domain.DiscountStrategy;
import ua.epam.spring.hometask.domain.User;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by artyom on 18.06.17.
 */
@Component
@Aspect
public class DiscountAspect {

    @Autowired
    private DiscountAspectDao dao;

    @Around("execution(* ua.epam.spring.hometask.domain.discount.DiscountStrategy+.getDiscount(..))")
    public Object countDiscounts(ProceedingJoinPoint strategy) throws Throwable {
        String discountType = strategy.getTarget().getClass().getSimpleName();
        User user = (User) strategy.getArgs()[0];

        Object result = strategy.proceed();

        if((double)result != 0.0){
            dao.incrementCounter(discountType, user);
        }

        return result;
    }
}
