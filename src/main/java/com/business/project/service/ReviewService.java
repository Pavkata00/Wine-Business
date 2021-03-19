package com.business.project.service;

import com.business.project.model.service.ReviewServiceModel;
import com.business.project.model.view.ReviewViewModel;

import java.util.List;

public interface ReviewService {
    List<ReviewViewModel> getReviewsOfWine(String name);

    void addReviewToWine(ReviewServiceModel reviewServiceModel, String name);
}
