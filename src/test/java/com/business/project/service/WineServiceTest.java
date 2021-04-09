package com.business.project.service;

import com.business.project.exception.WineNotFoundException;
import com.business.project.model.entity.FactoryEntity;
import com.business.project.model.entity.ReviewEntity;
import com.business.project.model.entity.UserEntity;
import com.business.project.model.entity.WineEntity;
import com.business.project.model.entity.enums.TypeEnum;
import com.business.project.model.service.WineServiceModel;
import com.business.project.model.view.WineViewModel;
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

import java.io.IOException;
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
    private WineEntity wineEntityTwo;
    private WineViewModel wineViewModel;
    private WineServiceModel wineServiceModel;


    @BeforeEach
    public void setUp() {
        wineService= new WineServiceImpl(modelMapper,mockedWineRepository,factoryService,cloudinaryService);
        wineEntity = createWineEntity();
        wineEntityTwo = createWineEntityTwo();
        wineServiceModel = createWineServiceModel();
        wineViewModel = createWineViewModel();
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

    @Test
    public void testGetAllWines() {

        when(mockedWineRepository.findAll()).thenReturn(List.of(wineEntity,wineEntityTwo ));

        List<WineViewModel> allWines = wineService.getAllWines();

        Assertions.assertEquals(2,allWines.size());
    }

    @Test
    public void testGetWineByName() {
        when(mockedWineRepository.findByName("Mavrud")).thenReturn(Optional.of(wineEntity));

        WineEntity mavrud = wineService.getWineByName("Mavrud");

        Assertions.assertEquals(wineEntity.getName(),mavrud.getName());
        Assertions.assertEquals(wineEntity.getType(),mavrud.getType());
    }

    @Test
    public void testGetWineByInvalidName() {

        Assertions.assertThrows(WineNotFoundException.class, () -> {
            wineService.getWineByName("InvalidName");
        });
    }

    @Test
    public void testAddReviewToWine() {
        when(mockedWineRepository.findByName("Mavrud")).thenReturn(Optional.of(wineEntity));

        ReviewEntity reviewEntity = new ReviewEntity();

        wineService.addReviewToWine(reviewEntity,"Mavrud");

        Assertions.assertEquals(1, wineEntity.getReviews().size());
    }

    @Test
    public void testAddReviewToWineWithInvalidName() {

        ReviewEntity reviewEntity = new ReviewEntity();

        Assertions.assertThrows(WineNotFoundException.class, () -> {
            wineService.addReviewToWine(reviewEntity,"InvalidName");
        });
    }


    @Test
    public void testGetWinesByType() {

        when(mockedWineRepository.findAllByType(TypeEnum.Mavrud)).thenReturn(List.of(wineEntity));

        List<WineViewModel> winesByType = wineService.getWinesByType(TypeEnum.Mavrud.name());

        Assertions.assertEquals(1, winesByType.size());
        Assertions.assertEquals(wineEntity.getName(), winesByType.get(0).getName());

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

    private WineEntity createWineEntityTwo() {
        WineEntity wineEntity = new WineEntity();
        wineEntity.setType(TypeEnum.Rose);
        wineEntity.setImageUrl("testInvalid");
        wineEntity.setAmount(5);
        wineEntity.setDescription("random");
        wineEntity.setMadeDate(LocalDate.now());
        wineEntity.setPrice(BigDecimal.valueOf(10));
        wineEntity.setName("testRose");

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

    private WineViewModel createWineViewModel() {

        WineViewModel wineViewModel = new WineViewModel();

        wineViewModel.setAmount(1);
        wineViewModel.setDescription("random");
        wineViewModel.setMadeDate("2000");
        wineViewModel.setPrice(BigDecimal.valueOf(10));
        wineViewModel.setName("testMavrud");
        wineViewModel.setFactory("testFactory");

        return wineViewModel;
    }
}
