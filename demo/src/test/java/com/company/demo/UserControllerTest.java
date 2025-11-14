package com.company.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.company.common.exception.config.GlobalExceptionAutoConfiguration;
import com.company.demo.controller.UserController;

@WebMvcTest(UserController.class)
@Import(GlobalExceptionAutoConfiguration.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void testInvalidRequest() throws Exception {
        mvc.perform(get("/api/users/0"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").value("Invalid ID"));
    }

    @Test
    void testValidUser() throws Exception {
        mvc.perform(get("/api/users/10"))
                .andExpect(status().isOk())
                .andExpect(content().string("User 10"));
    }
}
