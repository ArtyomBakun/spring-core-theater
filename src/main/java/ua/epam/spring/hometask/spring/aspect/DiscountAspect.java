package ua.epam.spring.hometask.spring.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
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

    public static Map<String, Map<User, Long>> totalCalls = new HashMap<>();

    @Around("execution(* ua.epam.spring.hometask.domain.discount.DiscountStrategy+.getDiscount(..))")
    public Object countDiscounts(ProceedingJoinPoint strategy) throws Throwable {
        String discountType = strategy.getTarget().getClass().getName();
        User user = (User) strategy.getArgs()[0];

        Object result = strategy.proceed();

        if((double)result != 0.0){
            Map<User, Long> userLongMap = totalCalls.get(discountType);
            if(userLongMap == null){
                totalCalls.put(discountType, new HashMap<>());
                userLongMap = totalCalls.get(discountType);
            }
            Long times = userLongMap.get(user);
            userLongMap.put(user, times == null ? 1L : (times + 1));
        }

        return result;
    }
}
