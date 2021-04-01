package com.business.project.service;

import com.business.project.model.view.RecordViewModel;

public interface RecordService {
    void addRecord(String wine);

    RecordViewModel getMostBoughtWine();
}
