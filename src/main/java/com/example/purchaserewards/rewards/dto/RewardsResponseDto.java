package com.example.purchaserewards.rewards.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Month;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class RewardsResponseDto {
    String customerId;
    int total;
    List<MonthlyRewardDto> monthlyRewards;
}

@Getter
class MonthlyRewardDto {
    private final int total;
    private final Month month;

    public MonthlyRewardDto(int total, Month month) {
        this.total = total;
        this.month = month;
    }
}

