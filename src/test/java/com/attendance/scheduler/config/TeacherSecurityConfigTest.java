package com.attendance.scheduler.config;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RequiredArgsConstructor
class TeacherSecurityConfigTest {

    private MockMvc mockMvc;


    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void loginForm_ShouldReturnLoginFormPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/login"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("/template/login"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("login"));
    }

    @Test
    @WithMockUser(username = "sampleTeacher", password = "123", roles = "TEACHER")
    public void teacherLogin_ShouldReturnSuccessPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/login/Login"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("/manage/class.html"));

    }

    @Test
    @WithMockUser(username = "admin", password = "root123!@#", roles = "ADMIN")
    public void adminLogin_ShouldReturnSuccessPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/login/Login"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("admin/adminLogin"));

    }

}
