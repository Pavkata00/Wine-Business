package com.business.project.aop;

import com.business.project.service.LogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogInformationAspect {

    private final LogService logService;

    public LogInformationAspect(LogService logService) {
        this.logService = logService;
    }

    @Pointcut("execution(* com.business.project.web.ReviewController.getReviews(..))")
    public void getReviewsPointcut() {}

    @After("getReviewsPointcut()")
    public void afterAdvice(JoinPoint joinPoint) {

        Object[] info = joinPoint.getArgs();

        String wineName = (String) info[0];

        String action = joinPoint.getSignature().getName();

        this.logService.createLog(action, wineName);
    }


    @Pointcut("execution(* com.business.project.web.ReviewController.buyOne(..))")
    public void buyOnePointcut() {}

    @After("buyOnePointcut()")
    public void afterAdviceTwo(JoinPoint joinPoint) {

        Object[] info = joinPoint.getArgs();

        String wineName = (String) info[0];

        String action = joinPoint.getSignature().getName();

        this.logService.createLog(action, wineName);
    }

}
