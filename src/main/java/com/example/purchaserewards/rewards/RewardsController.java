package com.example.purchaserewards.rewards;

import com.example.purchaserewards.rewards.dto.RewardsResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("rewards")
public class RewardsController {

    @GetMapping("{customerId}")
    public RewardsResponseDto getRewardsForLastThreeMonths(@PathVariable String customerId) {
        return new RewardsResponseDto();
    }
}
