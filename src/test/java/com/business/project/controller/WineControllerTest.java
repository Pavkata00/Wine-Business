package com.business.project.controller;

import com.business.project.model.entity.enums.TypeEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class WineControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void testAdd() throws Exception {
        mockMvc.perform(get("/wine/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("add-wine"))
                .andExpect(model().attributeExists("wineAddBindingModel"));
    }

    @Test
    public void testAddWithoutAccess() throws Exception {
        mockMvc.perform(get("/wine/add"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void testAddConfirmWrongParameters() throws Exception {

        //todo fix
        MockMultipartFile mockMultipartFile = new MockMultipartFile(
                "img",
                "file.txt",
                MediaType.TEXT_PLAIN_VALUE,
                "Random file words.".getBytes());

        mockMvc.perform(multipart("/wine/add").file(mockMultipartFile)
                .param("name","testWine")
                .param("description", "short description")
                .param("price", "5")
                .param("amount","45")
                .param("madeDate", LocalDateTime.now().toString())
                .param("type", TypeEnum.Mavrud.toString())
                .param("factory", "Naydenovi's Pirgos EOOD")
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:add"));



    }
}
