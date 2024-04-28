package com.attendance.scheduler.student.controller;

import com.attendance.scheduler.infra.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@IntegrationTest
class BasicControllerTest {

    @Autowired MockMvc mockMvc;

    @Test
    void basic() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }
}