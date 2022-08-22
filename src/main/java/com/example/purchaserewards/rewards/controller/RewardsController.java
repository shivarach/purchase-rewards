package com.example.purchaserewards.rewards.controller;

import com.example.purchaserewards.rewards.dto.RewardsResponseDto;
import com.example.purchaserewards.rewards.service.RewardsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("rewards")
public class RewardsController {

    @Autowired
    private RewardsService rewardsService;

    /**
     *
     * @param customerId id of the customer Eg: shiva@test.com
     * @return Total credits and monthly of given customer
     */
    @GetMapping("{customerId}")
    public RewardsResponseDto getRewards(@PathVariable String customerId) {
        return rewardsService.getRewards(customerId);
    }
}
