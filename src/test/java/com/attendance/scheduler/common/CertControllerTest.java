package com.attendance.scheduler.common;

import com.attendance.scheduler.infra.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@IntegrationTest
class CertControllerTest {

    @Autowired MockMvc mockMvc;

    @Test
    void findId() throws Exception {
        mockMvc.perform(get("/help/findId"))
                        .andExpect(status().is2xxSuccessful());
    }

    @Test
    void findPassword() throws Exception {
        mockMvc.perform(get("/help/findPassword"))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void updateCompletionForm() throws Exception {
        mockMvc.perform(get("/help/completion"))
                .andExpect(status().is2xxSuccessful());
    }
}