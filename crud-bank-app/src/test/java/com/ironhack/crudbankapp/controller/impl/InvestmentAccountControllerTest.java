package com.ironhack.crudbankapp.controller.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironhack.crudbankapp.model.InvestmentAccount;
import com.ironhack.crudbankapp.repository.InvestmentAccountRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class InvestmentAccountControllerTest {

    @Autowired
    InvestmentAccountRepository investmentAccountRepository;

    @Autowired
    WebApplicationContext webApplicationContext;

    MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();

    InvestmentAccount investmentAccount;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        investmentAccount = new InvestmentAccount("Guillermo Aviles", BigDecimal.valueOf(10));
        investmentAccountRepository.save(investmentAccount);
    }

    @AfterEach
    public void tearDown() {
        investmentAccountRepository.deleteAll();
    }

    @Test
    void getInvestmentAccountByAccountNumber() throws Exception {
        ResultActions resultActions = mockMvc.perform(get("/api/accounts/investment/" + investmentAccount.getAccountNumber()))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.owner").value(investmentAccount.getOwner()))
                .andExpect(jsonPath("$.balance").value(investmentAccount.getBalance().doubleValue()));

        Optional<InvestmentAccount> retrievedAccount = investmentAccountRepository.findById(investmentAccount.getAccountNumber());
        assertTrue(retrievedAccount.isPresent());
        InvestmentAccount expectedAccount = retrievedAccount.get();
        resultActions.andExpect(jsonPath("$.accountNumber").value(expectedAccount.getAccountNumber()));
    }
}
