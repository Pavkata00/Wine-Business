package com.business.project.service;

import com.business.project.model.view.ReviewViewModel;

import java.util.List;

public interface ReviewService {
    List<ReviewViewModel> getReviewsOfWine(String name);
}
