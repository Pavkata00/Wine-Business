package com.business.project.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testRegister() throws Exception {
        mockMvc.perform(get("/users/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("register"))
                .andExpect(model().attributeExists("userRegisterBindingModel","usernameExists","emailExists"));
    }

    @Test
    public void testRegisterConfirm() throws Exception {

        mockMvc.perform(post("/users/register")
        .param("username","peshoTesta")
        .param("password","12345")
        .param("confirmPassword","12345")
        .param("fullName","Pesho Testa")
        .param("email","peshoemail@test.com")
        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:login"));
    }

    @Test
    public void testRegisterFailPassword() throws Exception {

        mockMvc.perform(post("/users/register")
                .param("username","peshoTesta")
                .param("password","123")
                .param("confirmPassword","123")
                .param("fullName","Pesho Testa")
                .param("email","peshoemail@test.com")
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:register"));
    }

    @Test
    public void testRegisterFailPasswordMatch() throws Exception {

        mockMvc.perform(post("/users/register")
                .param("username","peshoTesta")
                .param("password","12345")
                .param("confirmPassword","12354")
                .param("fullName","Pesho Testa")
                .param("email","peshoemail@test.com")
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:register"));
    }

    @Test
    public void testRegisterFailEmail() throws Exception {

        mockMvc.perform(post("/users/register")
                .param("username","peshoTesta")
                .param("password","12345")
                .param("confirmPassword","12354")
                .param("fullName","Pesho Testa")
                .param("email","peshoemailtest.com")
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:register"));
    }

    @Test
    public void testRegisterFailUsername() throws Exception {

        mockMvc.perform(post("/users/register")
                .param("username","pe")
                .param("password","12345")
                .param("confirmPassword","12354")
                .param("fullName","Pesho Testa")
                .param("email","peshoemail@test.com")
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:register"));
    }

    @Test
    public void testLogin() throws Exception {
        mockMvc.perform(get("/users/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"))
                .andExpect(model().attributeExists("bad_credentials","username"));
    }
}
