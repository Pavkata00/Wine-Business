package com.business.project.service;

import com.business.project.exception.WineNotFoundException;
import com.business.project.model.entity.FactoryEntity;
import com.business.project.model.entity.ReviewEntity;
import com.business.project.model.entity.UserEntity;
import com.business.project.model.entity.WineEntity;
import com.business.project.model.entity.enums.TypeEnum;
import com.business.project.model.service.WineServiceModel;
import com.business.project.repository.WineRepository;
import com.business.project.service.impl.WineServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class WineServiceTest {

    @Mock
    private WineRepository mockedWineRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CloudinaryService cloudinaryService;
    @Autowired
    private FactoryService factoryService;

    private WineServiceImpl wineService;


    private WineEntity wineEntity;
    private WineServiceModel wineServiceModel;
    @BeforeEach
    public void setUp() {
        wineService= new WineServiceImpl(modelMapper,mockedWineRepository,factoryService,cloudinaryService);
        wineEntity = createWineEntity();
        wineServiceModel = createWineServiceModel();
    }

    @Test
    public void testGetCountOfAllWines() {
        when(mockedWineRepository.findAll()).thenReturn(List.of(wineEntity));

        String result = wineService.getCountOfAllWines();

        Assertions.assertEquals("1",result);
    }

    @Test
    public void testGetCountOfAllWinesEmptyList() {
        when(mockedWineRepository.findAll()).thenReturn(List.of());

        String result = wineService.getCountOfAllWines();

        Assertions.assertEquals("None",result);
    }


    @Test
    public void testGetWineById() {
        when(mockedWineRepository.findById(1L)).thenReturn(Optional.of(wineEntity));

        WineServiceModel wineById = wineService.getWineById(1L);

        Assertions.assertEquals(wineServiceModel.getName(),wineById.getName());
        Assertions.assertEquals(wineServiceModel.getType(),wineById.getType());
    }

    @Test
    public void testGetWineByInvalidId()  {
//        when(mockedWineRepository.findById(1L)).thenThrow(WineNotFoundException.class);

        Assertions.assertThrows(WineNotFoundException.class, ()-> {
            wineService.getWineById(10L);
        });
    }

    @Test
    public void testBuyWine() {

        when(mockedWineRepository.findByName("testMavrud")).thenReturn(Optional.of(wineEntity));

        wineService.buyWine("testMavrud");

        Assertions.assertEquals(0,mockedWineRepository.findByName("testMavrud").get().getAmount());
    }

    @Test
    public void testBuyInvalidWine() {

        Assertions.assertThrows(WineNotFoundException.class, () -> {
            wineService.buyWine("Invalid");
        });

    }

    private WineEntity createWineEntity() {
        WineEntity wineEntity = new WineEntity();
        wineEntity.setType(TypeEnum.Mavrud);
        wineEntity.setImageUrl("testInvalid");
        wineEntity.setAmount(1);
        wineEntity.setDescription("random");
        wineEntity.setMadeDate(LocalDate.now());
        wineEntity.setPrice(BigDecimal.valueOf(10));
        wineEntity.setName("testMavrud");

        FactoryEntity factoryEntity = new FactoryEntity();
        factoryEntity.setDescription("testFactory");
        factoryEntity.setFoundedYear(2000);
        factoryEntity.setName("Factory");

        wineEntity.setFactory(factoryEntity);

        wineEntity.setReviews(new ArrayList<>());
        return wineEntity;
    }

    private WineServiceModel createWineServiceModel() {
        WineServiceModel wineServiceModel = new WineServiceModel();
        wineServiceModel.setType("Mavrud");
        wineServiceModel.setAmount(1);
        wineServiceModel.setDescription("random");
        wineServiceModel.setMadeDate(LocalDate.now());
        wineServiceModel.setPrice(BigDecimal.valueOf(10));
        wineServiceModel.setName("testMavrud");
        wineServiceModel.setFactory("testFactory");


        return wineServiceModel;
    }
}
