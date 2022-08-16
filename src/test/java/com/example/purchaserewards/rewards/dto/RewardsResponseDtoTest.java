package com.example.purchaserewards.rewards.dto;

import com.example.purchaserewards.rewards.domain.Purchase;
import com.example.purchaserewards.rewards.domain.Reward;
import com.example.purchaserewards.rewards.dto.RewardsResponseDto.MonthlyRewardDto;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import static java.time.Month.AUGUST;
import static java.time.Month.JULY;
import static org.junit.jupiter.api.Assertions.assertEquals;

class RewardsResponseDtoTest {

    @Test
    void shouldCreateRewardsResponseDtoFromGivenRewards() throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.s");

        Purchase augPurchase = new Purchase();
        augPurchase.setUserId("siva@test.com");
        augPurchase.setAmount(120.0);
        augPurchase.setPurchasedOn(new Timestamp(dateFormat.parse("2022-08-15 18:47:52.69").getTime()));
        Reward augReward = new Reward(90.0, augPurchase);

        Purchase anotherAugPurchase = new Purchase();
        anotherAugPurchase.setUserId("siva@test.com");
        anotherAugPurchase.setAmount(90.0);
        anotherAugPurchase.setPurchasedOn(new Timestamp(dateFormat.parse("2022-08-10 18:47:52.69").getTime()));
        Reward anotherAugustReward = new Reward(40.0, anotherAugPurchase);

        Purchase julyPurchase = new Purchase();
        julyPurchase.setUserId("siva@test.com");
        julyPurchase.setAmount(60.0);
        julyPurchase.setPurchasedOn(new Timestamp(dateFormat.parse("2022-07-15 18:47:52.69").getTime()));
        Reward julyReward = new Reward(10.0, julyPurchase);

        RewardsResponseDto rewardsResponseDto = RewardsResponseDto.createFrom(Arrays.asList(augReward, anotherAugustReward, julyReward), "siva@test.com");

        assertEquals(140.0, rewardsResponseDto.getTotal());
        MonthlyRewardDto julyRewardDto = rewardsResponseDto.getMonthlyRewards().get(0);

        assertEquals(JULY, julyRewardDto.getMonth());
        assertEquals(10, julyRewardDto.getTotal());

        MonthlyRewardDto augRewardDto = rewardsResponseDto.getMonthlyRewards().get(1);

        assertEquals(AUGUST, augRewardDto.getMonth());
        assertEquals(130, augRewardDto.getTotal());

    }
}