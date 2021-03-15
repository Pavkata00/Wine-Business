package com.business.project.service;

import com.business.project.model.entity.FactoryEntity;

public interface FactoryService {
    void initFactories();

    FactoryEntity getFactoryByName(String factory);
}
