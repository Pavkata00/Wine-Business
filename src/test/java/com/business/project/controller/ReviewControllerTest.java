package com.business.project.controller;

import com.business.project.model.view.WineViewModel;
import com.business.project.service.WineService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class ReviewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private WineService mockWineService;

    private WineViewModel wineViewModelOne;

    @BeforeEach
    public void setUp() {
        wineViewModelOne = getWine();
    }



    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void testBrowseMethodEmpty() throws Exception {
        when(mockWineService.getWinesByType("Mavrud")).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/reviews/browse-Mavrud"))
                .andExpect(status().isOk())
                .andExpect(view().name("type-empty"));
    }

    @Test
    public void testBrowseMethodWithoutAdminOrUserAccess() throws Exception {
        when(mockWineService.getWinesByType("Mavrud")).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/reviews/browse-Mavrud"))
                .andExpect(status().is3xxRedirection());
    }

//    @Test
//    @WithMockUser(username = "admin", roles = "ADMIN")
//    public void testBrowseMethodCorrectly() throws Exception {
//        when(mockWineService.getWinesByType("Mavrud")).thenReturn(List.of(wineViewModelOne));
//
//        mockMvc.perform(get("/reviews/browse-Mavrud"))
//                .andExpect(status().isOk())
//                .andExpect(model().attributeExists("typeWines"))
//                .andExpect(view().name("browse-wine"));
//    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void testAddReviewGetMethod() throws Exception {

        mockMvc.perform(get("/reviews/addReview"))
                .andExpect(status().isOk())
                .andExpect(view().name("add-review"))
                .andExpect(model().attributeExists("reviewAddBindingModel", "allWines"));
    }

    @Test
    public void testAddReviewWithoutAccess() throws Exception {
        when(mockWineService.getWinesByType("Mavrud")).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/reviews/addReview"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void AddReviewPostMethodShouldThrowException() throws Exception {

        mockMvc.perform(post("/reviews/addReview")
                .param("user", "Username")
                .param("description", "A long description for testing!!!!!")
                .param("rating", "5")
                .param("dateTime", LocalDate.now().toString())
                .param("wineName", "Special Mavrud")
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("wine-exception"));
    }

    private WineViewModel getWine() {
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
}
