package com.example.purchaserewards.rewards.dto;

import com.example.purchaserewards.rewards.domain.Reward;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Month;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@Getter
@Setter
@NoArgsConstructor
public class RewardsResponseDto {
    @JsonProperty("customer_id")
    private String customerId;
    private double total;
    @JsonProperty("monthly_rewards")
    private List<MonthlyRewardDto> monthlyRewards;

    public static RewardsResponseDto createFrom(List<Reward> rewards, String customerId) {
        RewardsResponseDto rewardsResponseDto = new RewardsResponseDto();
        rewardsResponseDto.setCustomerId(customerId);
        rewardsResponseDto.setTotal(rewards.stream().map(Reward::getValue).reduce(0.0, Double::sum));

        Map<Month, List<Reward>> monthlyRewards = rewards.stream()
                .collect(groupingBy(reward -> reward.getPurchase().getPurchasedOn().toLocalDateTime().getMonth()));

        List<MonthlyRewardDto> monthlyRewardsDto = monthlyRewards.keySet().stream()
                .map(month -> new MonthlyRewardDto(month, monthlyRewards.get(month).stream().mapToDouble(Reward::getValue).sum()))
                .sorted(Comparator.<MonthlyRewardDto, Month>comparing(MonthlyRewardDto::getMonth)).collect(Collectors.toList());
        rewardsResponseDto.setMonthlyRewards(monthlyRewardsDto);
        return rewardsResponseDto;
    }
}

@Getter
class MonthlyRewardDto {
    private final Month month;
    private final double total;

    public MonthlyRewardDto(Month month, double total) {
        this.total = total;
        this.month = month;
    }
}

