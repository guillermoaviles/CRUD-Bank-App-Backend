package com.ironhack.crudbankapp.controller.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironhack.crudbankapp.model.CheckingAccount;
import com.ironhack.crudbankapp.repository.CheckingAccountRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class CheckingAccountControllerTest {

    @Autowired
    CheckingAccountRepository checkingAccountRepository;

    @Autowired
    WebApplicationContext webApplicationContext;

    MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();

    CheckingAccount checkingAccount1;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        checkingAccount1 = new CheckingAccount("Guillermo Aviles");
    }

    @AfterEach
    public void tearDown() {
//        checkingAccountRepository.deleteById(100001);
    }

    @Test
    void saveCheckingAccount_validBody_checkingAccountSaved() throws Exception {
        String body = objectMapper.writeValueAsString(checkingAccount1);

        mockMvc.perform(post("/api/accounts/checking").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        assertTrue(checkingAccountRepository.findAll().toString().contains("Guillermo Aviles"));
    }

    @Test
    void updateCheckingAccount_validBody_checkingAccountUpdated() throws Exception {
        checkingAccountRepository.save(checkingAccount1);
        checkingAccount1.setBalance(BigDecimal.valueOf(50.0));

        String body = objectMapper.writeValueAsString(checkingAccount1);

        mockMvc.perform(put("/api/accounts/checking/" + checkingAccount1.getAccountNumber()).content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andReturn();

        assertTrue(checkingAccountRepository.findAll().toString().contains("50.0"));
    }

    @Test
    void deleteCheckingAccount() throws Exception {
        checkingAccountRepository.save(checkingAccount1);

        mockMvc.perform(delete("/api/accounts/checking/" + checkingAccount1.getAccountNumber()))
                .andExpect(status().isNoContent())
                .andReturn();

        assertFalse(checkingAccountRepository.findAll().toString().contains("Guillermo Aviles"));
    }
}