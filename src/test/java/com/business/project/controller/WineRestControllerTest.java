package com.business.project.controller;

import com.business.project.model.view.WineViewModel;
import com.business.project.repository.WineRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class WineRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private WineRepository mockedWineRepository;

    private WineViewModel wineViewModelOne;
    private WineViewModel wineViewModelTwo;

    @BeforeEach
    public void setUp() {
        wineViewModelOne = getWineOne();
        wineViewModelTwo = getWineTwo();

    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void testReturnStatus() throws Exception {
        mockMvc.perform(get("/wine-rest/api/1"))
                .andExpect(status().isOk());
    }

    private WineViewModel getWineTwo() {
        WineViewModel wineViewModel = new WineViewModel();
        wineViewModel.setName("Test Mavrud Two");
        wineViewModel.setMadeDate(LocalDateTime.now().toString());
        wineViewModel.setAmount(35);
        wineViewModel.setDescription("long description for this wine as well!!!!");
        wineViewModel.setImageUrl("random URL2");
        wineViewModel.setFactory("New factory Ruse");
        wineViewModel.setPrice(BigDecimal.valueOf(75));

        return wineViewModel;
    }

    private WineViewModel getWineOne() {
        WineViewModel wineViewModel = new WineViewModel();
        wineViewModel.setName("Test Mavrud");
        wineViewModel.setMadeDate(LocalDateTime.now().toString());
        wineViewModel.setAmount(25);
        wineViewModel.setDescription("long description for this wine!!!!");
        wineViewModel.setImageUrl("random URL");
        wineViewModel.setFactory("New factory Vratsa");
        wineViewModel.setPrice(BigDecimal.valueOf(45));

        return wineViewModel;
    }
}
