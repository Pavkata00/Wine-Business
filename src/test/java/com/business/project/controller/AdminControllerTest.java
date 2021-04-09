package com.business.project.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void testControl() throws Exception {

        mockMvc.perform(get("/admin/control"))
                .andExpect(status().isOk())
                .andExpect(view().name("control"))
                .andExpect(model().attributeExists("userCommandBindingModel", "isAlreadyAdmin",
                        "isAlreadyGuestUser", "userFound", "userFound"));
    }

    @Test
    public void testControlWithoutAccess() throws Exception {

        mockMvc.perform(get("/admin/control"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void testLogs() throws Exception {

        mockMvc.perform(get("/admin/logs"))
                .andExpect(status().isOk())
                .andExpect(view().name("log"))
                .andExpect(model().attributeExists("allLogs", "mostBoughtWine"));
    }

    @Test
    public void testLogsWithoutAccess() throws Exception {

        mockMvc.perform(get("/admin/logs"))
                .andExpect(status().is3xxRedirection());
    }
}
