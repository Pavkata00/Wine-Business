package com.business.project.service.impl;

import com.business.project.model.entity.WineEntity;
import com.business.project.model.entity.enums.TypeEnum;
import com.business.project.model.service.WineServiceModel;
import com.business.project.model.view.WineViewModel;
import com.business.project.repository.WineRepository;
import com.business.project.service.FactoryService;
import com.business.project.service.WineService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WineServiceImpl implements WineService {

    private final ModelMapper modelMapper;
    private final WineRepository wineRepository;
    private final FactoryService factoryService;

    public WineServiceImpl(ModelMapper modelMapper, WineRepository wineRepository, FactoryService factoryService) {
        this.modelMapper = modelMapper;
        this.wineRepository = wineRepository;
        this.factoryService = factoryService;
    }

    @Override
    public void addWine(WineServiceModel wineServiceModel) {
        WineEntity wineEntity = this.modelMapper.map(wineServiceModel, WineEntity.class);

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
}
