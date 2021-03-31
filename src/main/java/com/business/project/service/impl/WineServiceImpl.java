package com.business.project.service.impl;

import com.business.project.exception.WineNotFoundException;
import com.business.project.model.entity.ReviewEntity;
import com.business.project.model.entity.WineEntity;
import com.business.project.model.entity.enums.TypeEnum;
import com.business.project.model.service.WineServiceModel;
import com.business.project.model.view.WineViewModel;
import com.business.project.repository.WineRepository;
import com.business.project.service.CloudinaryService;
import com.business.project.service.FactoryService;
import com.business.project.service.WineService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WineServiceImpl implements WineService {

    private final ModelMapper modelMapper;
    private final WineRepository wineRepository;
    private final FactoryService factoryService;
    private final CloudinaryService cloudinaryService;

    public WineServiceImpl(ModelMapper modelMapper, WineRepository wineRepository, FactoryService factoryService, CloudinaryService cloudinaryService) {
        this.modelMapper = modelMapper;
        this.wineRepository = wineRepository;
        this.factoryService = factoryService;
        this.cloudinaryService = cloudinaryService;
    }


    @Override
    public void addWine(WineServiceModel wineServiceModel) throws IOException {
        WineEntity wineEntity = this.modelMapper.map(wineServiceModel, WineEntity.class);

        MultipartFile img = wineServiceModel.getImg();
        String imageUrl = cloudinaryService.uploadImage(img);

        wineEntity.setImageUrl(imageUrl);
        wineEntity.setFactory(this.factoryService.getFactoryByName(wineServiceModel.getFactory()));
        wineEntity.setType(TypeEnum.valueOf(wineServiceModel.getType()));


        this.wineRepository.save(wineEntity);

    }

    @Override
    public List<WineViewModel> getWinesByType(String type) {

        return this.wineRepository.findAllByType(TypeEnum.valueOf(type)).stream().map(wineEntity -> {
            WineViewModel wineViewModel = this.modelMapper.map(wineEntity,WineViewModel.class);

            wineViewModel.setMadeDate(wineEntity.getMadeDate().toString());
            wineViewModel.setFactory(wineEntity.getFactory().getName());
            return wineViewModel;

        }).collect(Collectors.toList());
    }

    @Override
    public void addReviewToWine(ReviewEntity reviewEntity, String wineName) {

        WineEntity wineEntity = this.wineRepository.findByName(wineName)
                .orElseThrow(() -> new WineNotFoundException("This wine does not exist in the database!"));

        wineEntity.getReviews().add(reviewEntity);

        this.wineRepository.save(wineEntity);

    }

    @Override
    public WineEntity getWineByName(String name) {
        return this.wineRepository.findByName(name)
                .orElseThrow(() -> new WineNotFoundException("This wine does not exist in the database!"));
    }

    @Override
    public List<WineViewModel> getAllWines() {
        return this.wineRepository.findAll().stream()
                .map(wineEntity -> this.modelMapper.map(wineEntity,WineViewModel.class)).collect(Collectors.toList());
    }

    @Override
    public void buyWine(String name) {
        WineEntity wine = this.wineRepository.findByName(name)
                .orElseThrow(() -> new WineNotFoundException("This wine does not exist in the database!"));

            wine.setAmount(wine.getAmount()-1);
            this.wineRepository.save(wine);

    }

    @Override
    public WineServiceModel getWineById(Long wineId) {
        WineEntity wineEntity = this.wineRepository.findById(wineId).orElseThrow(() -> new WineNotFoundException("Wine with that id does not exist!"));
        return this.modelMapper.map(wineEntity,WineServiceModel.class);
    }

    @Override
    public String getCountOfAllWines() {
        int result = this.wineRepository.findAll().stream().mapToInt(WineEntity::getAmount).sum();
        if (result==0) {
            return "None";
        }
        return result+"";
    }

    @Override
    public void update(WineServiceModel wineServiceModel, String wineName) {
        WineEntity wineEntity = this.wineRepository.findByName(wineName)
                .orElseThrow(() -> new WineNotFoundException("This wine does not exist in the database!"));
        wineEntity.setAmount(wineServiceModel.getAmount() + wineEntity.getAmount());
        wineEntity.setPrice(wineServiceModel.getPrice());
        this.wineRepository.save(wineEntity);

    }
}
