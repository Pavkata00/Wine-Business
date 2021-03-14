package com.business.project.service.impl;

import com.business.project.model.entity.FactoryEntity;
import com.business.project.repository.FactoryRepository;
import com.business.project.service.FactoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FactoryServiceImpl implements FactoryService {

    private final FactoryRepository factoryRepository;

    public FactoryServiceImpl(FactoryRepository factoryRepository) {
        this.factoryRepository = factoryRepository;
    }

    @Override
    public void initFactories() {

        if (this.factoryRepository.count()==0) {
            FactoryEntity factoryAsenovgrad = new FactoryEntity();
            factoryAsenovgrad.setName("Naydenovi's Stanimaka EOOD");
            factoryAsenovgrad.setFoundedYear(2009);

            FactoryEntity factorySofia = new FactoryEntity();
            factorySofia.setName("Naydenovi's Serdika EOOD");
            factorySofia.setFoundedYear(2011);

            FactoryEntity factoryBurgas= new FactoryEntity();
            factoryBurgas.setName("Naydenovi's Pirgos EOOD");
            factoryBurgas.setFoundedYear(2017);

            this.factoryRepository.save(factoryAsenovgrad);
            this.factoryRepository.save(factorySofia);
            this.factoryRepository.save(factoryBurgas);
        }
    }
}
