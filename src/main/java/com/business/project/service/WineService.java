package com.business.project.service;

import com.business.project.model.entity.ReviewEntity;
import com.business.project.model.entity.WineEntity;
import com.business.project.model.service.WineServiceModel;
import com.business.project.model.view.WineViewModel;

import java.util.List;
import java.util.Optional;

public interface WineService {
    void addWine(WineServiceModel wineServiceModel);

    List<WineViewModel> getWinesByType(String type);

    List<ReviewEntity> getReviewsOfWine(String name);
}
