package com.business.project.service;

import com.business.project.model.service.WineServiceModel;
import com.business.project.model.view.WineViewModel;

import java.util.List;

public interface WineService {
    void addWine(WineServiceModel wineServiceModel);

    List<WineViewModel> getWinesByType(String type);
}
