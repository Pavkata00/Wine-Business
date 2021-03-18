package com.business.project.service.impl;

import com.business.project.model.entity.ReviewEntity;
import com.business.project.model.view.ReviewViewModel;
import com.business.project.repository.ReviewRepository;
import com.business.project.service.ReviewService;
import com.business.project.service.WineService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final WineService wineService;
    private final ModelMapper modelMapper;

    public ReviewServiceImpl(ReviewRepository reviewRepository, WineService wineService, ModelMapper modelMapper) {
        this.reviewRepository = reviewRepository;
        this.wineService = wineService;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<ReviewViewModel> getReviewsOfWine(String name) {
        return this.wineService.getReviewsOfWine(name).stream().map(reviewEntity -> {
            ReviewViewModel reviewViewModel = this.modelMapper.map(reviewEntity,ReviewViewModel.class);
            reviewViewModel.setUser(reviewEntity.getUser().getFullName());
            reviewViewModel.setDateTime(reviewEntity.getDateTime().toString());

            return reviewViewModel;
        }).collect(Collectors.toList());
    }
}
