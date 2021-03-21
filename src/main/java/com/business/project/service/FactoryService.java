package com.business.project.service;

import com.business.project.model.entity.FactoryEntity;
import com.business.project.model.view.FactoryViewModel;

import java.util.List;

public interface FactoryService {
    void initFactories();

    FactoryEntity getFactoryByName(String factory);

    List<FactoryViewModel> getAllFactories();
}
