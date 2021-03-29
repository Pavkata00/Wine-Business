package com.business.project.event;

import com.business.project.repository.LogRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class LogClear {

    private final LogRepository logRepository;

    public LogClear(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    @Scheduled(cron = "0 0/5 * * * *")
    public void clearLogsEveryFiveMinutes() {

        System.out.println("clearing now");
        this.logRepository.deleteAll();

    }

}
