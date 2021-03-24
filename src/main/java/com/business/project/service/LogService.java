package com.business.project.service;

import com.business.project.model.view.LogViewModel;

import java.util.List;

public interface LogService {
    void createLog(String action, String wineName);

    List<LogViewModel> getAllLogs();
}
