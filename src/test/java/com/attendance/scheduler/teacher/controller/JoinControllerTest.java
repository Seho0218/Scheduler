package com.attendance.scheduler.teacher.controller;

import com.attendance.scheduler.infra.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@IntegrationTest
class JoinControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void joinForm() throws Exception {
        mockMvc.perform(get("/join/teacher"))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }

    @Test
    void approved() {

    }
}