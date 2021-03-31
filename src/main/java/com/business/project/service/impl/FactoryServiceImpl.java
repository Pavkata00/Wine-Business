package com.business.project.service.impl;

import com.business.project.exception.FactoryNotFoundException;
import com.business.project.model.entity.FactoryEntity;
import com.business.project.model.view.FactoryViewModel;
import com.business.project.repository.FactoryRepository;
import com.business.project.service.FactoryService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
            factoryAsenovgrad.setDescription("The urban winery is a recent phenomenon whereby a wine producer chooses to locate their winemaking facility in an urban setting within a city rather than in the traditional rural setting near the vineyards. With advances in technology and transportation, it is not a problem for an urban winery to grow their grapes in a remote location and then transport them to the urban facility for crushing, fermentation and aging. Urban wineries have been opened in cities across the United States including San Francisco; Sacramento; Portland, Oregon; Seattle; Frederick, Maryland; New York; Cincinnati; San Diego; and Los Angeles to name a few. Wilridge Winery was the first urban winery in Seattle.\n" +
                    "\n" +
                    "Wine aficionados traditionally had to travel to remote areas to learn about winemaking firsthand and to taste the offerings of a wine producer in the setting in which they were made. Now, many urban dwellers can hop in their car for a short drive or take public transportation or even walk, and have an authentic winery experience. Many urban wineries offer productions tours and a traditional tasting room for this purpose and also offer retail sales. This allows the consumer to purchase directly from the source ensuring that wines have been stored correctly and not subjected to extreme conditions that can occur in transport which can occasionally result in spoiled wines.\n" +
                    "\n" +
                    "A few urban wineries are also incorporating full-service restaurants or venues for live entertainment. Many also offer their customers the ability to make their own wine under the guidance of their winemaking team. Amateur winemakers can choose the grape varieties, select an appellation, make production decisions along the way and participate in the final blending, bottling and even design their own labels. This has spawned a new generation of boutique wines that are available in micro quantities as small as 30 bottles. description.");

            FactoryEntity factorySofia = new FactoryEntity();
            factorySofia.setName("Naydenovi's Serdika EOOD");
            factorySofia.setFoundedYear(2011);
            factorySofia.setDescription("The urban winery is a recent phenomenon whereby a wine producer chooses to locate their winemaking facility in an urban setting within a city rather than in the traditional rural setting near the vineyards. With advances in technology and transportation, it is not a problem for an urban winery to grow their grapes in a remote location and then transport them to the urban facility for crushing, fermentation and aging. Urban wineries have been opened in cities across the United States including San Francisco; Sacramento; Portland, Oregon; Seattle; Frederick, Maryland; New York; Cincinnati; San Diego; and Los Angeles to name a few. Wilridge Winery was the first urban winery in Seattle.\n" +
                    "\n" +
                    "Wine aficionados traditionally had to travel to remote areas to learn about winemaking firsthand and to taste the offerings of a wine producer in the setting in which they were made. Now, many urban dwellers can hop in their car for a short drive or take public transportation or even walk, and have an authentic winery experience. Many urban wineries offer productions tours and a traditional tasting room for this purpose and also offer retail sales. This allows the consumer and not subjected to extreme conditions that can occur in transport which can occasionally result in spoiled wines.\n" +
                    "\n" +
                    "A few urban wineries are also incorporating full-service restaurants or venues for live entertainment. Many also offer their customers the ability to make their own wine under the guidance of their winemaking team. Amateur winemakers can choose the grape varieties, select an appellation, make production decisions along the way and participate in the final blending, bottling and even design their own labels. This has spawned a new generation of boutique wines that are available in micro quantities as small as 30 bottles.");

            FactoryEntity factoryBurgas= new FactoryEntity();
            factoryBurgas.setName("Naydenovi's Pirgos EOOD");
            factoryBurgas.setFoundedYear(2017);
            factoryBurgas.setDescription("The urban winery is a recent phenomenon whereby a wine producer chooses to locate their winemaking facility in an urban setting within a city rather than in the traditional rural setting near the vineyards. With advances in technology and transportation, it is not a problem for an urban winery to grow their grapes in a remote location and then transport them to the urban facility for crushing, fermentation and aging. Urban wineries have been opened in cities across the United States including San Francisco; Sacramento; Portland, Oregon; Seattle; Frederick, Maryland; New York; Cincinnati; San Diego; and Los Angeles to name a few. Wilridge Winery was the first urban winery in Seattle.\n" +
                    "\n" +
                    "Wine aficionados traditionally had to travel to remote areas to learn about winemaking firsthand and to taste the offerings of a wine producer in the setting in which they were made. Now, many urban dwellers can hop in their car for a short drive or take public transportation or even walk, and have an authentic winery experience. Many urban wineries offer productions tours and a traditional tasting room for this purpose and also offer retail sales. This allows the consumer to purchase and not subjected to extreme conditions that can occur in transport which can occasionally result in spoiled wines.\n" +
                    "\n" +
                    "A few urban wineries are also incorporating full-service restaurants or venues for live entertainment. Many also offer their customers the ability to make their own wine under the guidance of their winemaking team. Amateur winemakers can choose the grape varieties, select an appellation, make production decisions along the way and participate in the final blending, bottling and even design their own labels. This has spawned a new generation of boutique wines that are available");

            this.factoryRepository.save(factoryAsenovgrad);
            this.factoryRepository.save(factorySofia);
            this.factoryRepository.save(factoryBurgas);
        }
    }

    @Override
    public FactoryEntity getFactoryByName(String factory) {
        return this.factoryRepository.findByName(factory)
                .orElseThrow(() -> new FactoryNotFoundException("Something happened! This factory does not exist!"));
    }

    @Override
    public List<FactoryViewModel> getAllFactories() {
        return this.factoryRepository.findAll().stream().map(factoryEntity -> this.modelMapper.map(factoryEntity,FactoryViewModel.class)).collect(Collectors.toList());
    }
}
