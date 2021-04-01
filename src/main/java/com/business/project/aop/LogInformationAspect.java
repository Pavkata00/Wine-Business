package com.business.project.aop;

import com.business.project.model.view.LogViewModel;
import com.business.project.service.LogService;
import com.business.project.service.RecordService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.List;

@Aspect
@Component
public class LogInformationAspect {

    private final LogService logService;
    private final RecordService recordService;

    public LogInformationAspect(LogService logService, RecordService recordService) {
        this.logService = logService;
        this.recordService = recordService;
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

    @Pointcut("execution(* com.business.project.event.LogClear.clearLogsEveryFiveMinutes(..))")
    public void clearPointcut() {}

    @Before("clearPointcut()")
    public void beforeAdviceThree() {

        List<LogViewModel> allLogs = this.logService.getAllLogs();

        allLogs.stream().forEach(logViewModel -> {
            this.recordService.addRecord(logViewModel.getWine());
        });

    }

}
