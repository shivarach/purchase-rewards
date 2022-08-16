package com.example.purchaserewards.rewards.dto;

import com.example.purchaserewards.rewards.domain.Reward;
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

    public static RewardsResponseDto createFrom(List<Reward> rewards) {
        return null;
    }
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

