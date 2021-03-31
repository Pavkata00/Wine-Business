package com.business.project.service;

import com.business.project.model.entity.ReviewEntity;
import com.business.project.model.entity.WineEntity;
import com.business.project.model.service.WineServiceModel;
import com.business.project.model.view.WineViewModel;

import java.io.IOException;
import java.util.List;

public interface WineService {
    void addWine(WineServiceModel wineServiceModel) throws IOException;

    List<WineViewModel> getWinesByType(String type);


    void addReviewToWine(ReviewEntity reviewEntity, String wineName);


    WineEntity getWineByName(String name);

    List<WineViewModel> getAllWines();

    void buyWine(String name);

    WineServiceModel getWineById(Long wineId);

    String getCountOfAllWines();

    void update(WineServiceModel wineServiceModel, String wineName);
}
