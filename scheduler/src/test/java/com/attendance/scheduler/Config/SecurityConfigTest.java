package com.attendance.scheduler.Config;

import com.attendance.scheduler.Controller.LoginController;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(LoginController.class)
@RequiredArgsConstructor
class SecurityConfigTest {

    @Autowired
    private MockMvc mockMvc;


    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void loginForm_ShouldReturnLoginFormPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/login"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("login"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("login"));
    }

    @Test
    @WithMockUser(username = "teacher", roles = "TEACHER")
    public void teacherLogin_ShouldReturnSuccessPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/login/Login"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("login"));

    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void adminLogin_ShouldReturnSuccessPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/login/adminLogin/Login"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("admin/adminLogin"));

    }

}
