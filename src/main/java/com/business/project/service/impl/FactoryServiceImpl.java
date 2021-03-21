package com.business.project.service.impl;

import com.business.project.model.entity.FactoryEntity;
import com.business.project.model.view.FactoryViewModel;
import com.business.project.repository.FactoryRepository;
import com.business.project.service.FactoryService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FactoryServiceImpl implements FactoryService {

    private final FactoryRepository factoryRepository;
    private final ModelMapper modelMapper;

    public FactoryServiceImpl(FactoryRepository factoryRepository, ModelMapper modelMapper) {
        this.factoryRepository = factoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void initFactories() {

        if (this.factoryRepository.count()==0) {
            FactoryEntity factoryAsenovgrad = new FactoryEntity();
            factoryAsenovgrad.setName("Naydenovi's Stanimaka EOOD");
            factoryAsenovgrad.setFoundedYear(2009);
            factoryAsenovgrad.setDescription("Very very long description.Very very long description.Very very long description.Very very long description.Very very long description.");

            FactoryEntity factorySofia = new FactoryEntity();
            factorySofia.setName("Naydenovi's Serdika EOOD");
            factorySofia.setFoundedYear(2011);
            factorySofia.setDescription("Very very long description.Very very long description.Very very long description.Very very long description.");

            FactoryEntity factoryBurgas= new FactoryEntity();
            factoryBurgas.setName("Naydenovi's Pirgos EOOD");
            factoryBurgas.setFoundedYear(2017);
            factoryBurgas.setDescription("Very very long description.Very very long description.Very very long description.Very very long description.Very very long description.Very very long description.");

            this.factoryRepository.save(factoryAsenovgrad);
            this.factoryRepository.save(factorySofia);
            this.factoryRepository.save(factoryBurgas);
        }
    }

    @Override
    public FactoryEntity getFactoryByName(String factory) {
        return this.factoryRepository.findByName(factory).orElse(null);
    }

    @Override
    public List<FactoryViewModel> getAllFactories() {
        return this.factoryRepository.findAll().stream().map(factoryEntity -> this.modelMapper.map(factoryEntity,FactoryViewModel.class)).collect(Collectors.toList());
    }
}
