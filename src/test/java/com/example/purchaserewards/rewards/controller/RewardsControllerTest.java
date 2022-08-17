package com.example.purchaserewards.rewards.controller;

import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = Replace.ANY)
class RewardsControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void shouldReturnLastThreeMonthsRewardsForGivenUser() throws Exception {

        mvc.perform(get("/rewards/shiva@test.com")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.total", is(110.0)))
                .andExpect(jsonPath("$.customer_id", is("shiva@test.com")))
                .andExpect(jsonPath("$.monthly_rewards[0].month", is("JUNE")))
                .andExpect(jsonPath("$.monthly_rewards[0].total", is(0.0)))
                .andExpect(jsonPath("$.monthly_rewards[1].month", is("JULY")))
                .andExpect(jsonPath("$.monthly_rewards[1].total", is(20.0)))
                .andExpect(jsonPath("$.monthly_rewards[2].month", is("AUGUST")))
                .andExpect(jsonPath("$.monthly_rewards[2].total", is(90.0)));
    }

    @Test
    void shouldReturnZeroRewardsIfThereAreNoPurchasesInLastThreeMonths() throws Exception {
        mvc.perform(get("/rewards/smith@test.com")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.total", is(0.0)))
                .andExpect(jsonPath("$.customer_id", is("smith@test.com")))
                .andExpect(jsonPath("$.monthly_rewards", empty()));
    }

    @Test
    void shouldReturnBadRequestWhenCustomerDoesNotExists() throws Exception {
        mvc.perform(get("/rewards/ram@test.com")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message", is("Customer ram@test.com not found!")));
    }
}