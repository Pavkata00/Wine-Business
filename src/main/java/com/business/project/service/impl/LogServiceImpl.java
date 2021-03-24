package com.business.project.service.impl;

import com.business.project.model.entity.LogEntity;
import com.business.project.repository.LogRepository;
import com.business.project.service.LogService;
import com.business.project.service.UserService;
import com.business.project.service.WineService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class LogServiceImpl implements LogService {

    private final LogRepository logRepository;
    private final WineService wineService;

    public LogServiceImpl(LogRepository logRepository, WineService wineService) {
        this.logRepository = logRepository;

        this.wineService = wineService;
    }

    @Override
    public void createLog(String action, String wineName) {

        LogEntity logEntity = new LogEntity();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        logEntity.setUsername(authentication.getName());

        logEntity.setWine(wineService.getWineByName(wineName).getName());
        logEntity.setAction(action);
        logEntity.setLocalDateTime(LocalDateTime.now());

        this.logRepository.save(logEntity);
    }
}
