package com.business.project.service.impl;

import com.business.project.model.entity.ReviewEntity;
import com.business.project.model.service.ReviewServiceModel;
import com.business.project.model.view.ReviewViewModel;
import com.business.project.repository.ReviewRepository;
import com.business.project.service.ReviewService;
import com.business.project.service.UserService;
import com.business.project.service.WineService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final WineService wineService;
    private final ModelMapper modelMapper;
    private final UserService userService;

    public ReviewServiceImpl(ReviewRepository reviewRepository, WineService wineService, ModelMapper modelMapper, UserService userService) {
        this.reviewRepository = reviewRepository;
        this.wineService = wineService;
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    @Override
    public List<ReviewViewModel> getReviewsOfWine(String name) {
        return this.wineService.getWineByName(name).getReviews().stream().map(reviewEntity -> {
            ReviewViewModel reviewViewModel = this.modelMapper.map(reviewEntity,ReviewViewModel.class);
            reviewViewModel.setUser(reviewEntity.getUser().getFullName());
            reviewViewModel.setDateTime(reviewEntity.getDateTime().toString());
            reviewViewModel.setImage("/img/"+reviewEntity.getRating()+".png");

            return reviewViewModel;
        }).collect(Collectors.toList());
    }

    @Override
    public void addReviewToWine(ReviewServiceModel reviewServiceModel, String wineName) {
        ReviewEntity reviewEntity = this.modelMapper.map(reviewServiceModel,ReviewEntity.class);
        reviewEntity.setDateTime(LocalDateTime.now());
        reviewEntity.setUser(this.userService.getCurrentUser());

        this.reviewRepository.save(reviewEntity);

        this.wineService.addReviewToWine(reviewEntity, wineName);
    }
}
