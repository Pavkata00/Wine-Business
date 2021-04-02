package com.business.project.service.impl;

import com.business.project.model.entity.RecordEntity;
import com.business.project.model.view.RecordViewModel;
import com.business.project.repository.RecordRepository;
import com.business.project.service.RecordService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecordServiceImpl implements RecordService {

    private final RecordRepository recordRepository;
    private final ModelMapper modelMapper;

    public RecordServiceImpl(RecordRepository repository, ModelMapper modelMapper) {
        this.recordRepository = repository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void addRecord(String wine) {

        if (this.recordRepository.findByName(wine).isPresent()) {
            RecordEntity recordEntity = this.recordRepository.findByName(wine).get();
            recordEntity.setAmount(recordEntity.getAmount()+1);

            recordRepository.save(recordEntity);
        } else {
            RecordEntity recordEntity = new RecordEntity();
            recordEntity.setName(wine);
            recordEntity.setAmount(1);
            recordRepository.save(recordEntity);
        }

    }

    @Override
    public RecordViewModel getMostBoughtWine() {
        List<RecordEntity> mostBoughtWineRecords = this.recordRepository.findMostBoughtWineRecords();

        if (mostBoughtWineRecords.isEmpty()) {
            RecordViewModel recordViewModel = new RecordViewModel();
            recordViewModel.setAmount(0);
            recordViewModel.setName("None");
            return recordViewModel;
        }

        return this.modelMapper.map(mostBoughtWineRecords.get(0),RecordViewModel.class);
    }
}
