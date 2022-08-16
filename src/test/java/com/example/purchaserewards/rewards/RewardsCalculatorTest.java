package com.example.purchaserewards.rewards;

import com.example.purchaserewards.rewards.domain.Purchase;
import com.example.purchaserewards.rewards.domain.Reward;
import org.junit.jupiter.api.Test;

import java.text.DecimalFormat;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RewardsCalculatorTest {

    private DecimalFormat decimalFormat = new DecimalFormat("#.##");

    @Test
    void shouldReturnRewardValueForTheAboveHundredPurchaseAmount() {
        Purchase purchase = new Purchase();
        purchase.setAmount(120.0);

        Reward reward = RewardsCalculator.calculate(purchase);

        assertEquals(90, reward.getValue());
        assertEquals(purchase, reward.getPurchase());
    }

    @Test
    void shouldReturnRewardForTheBelowHundredAndAboveFiftyPurchaseAmount() {
        Purchase purchase = new Purchase();
        purchase.setAmount(67.42);

        Reward reward = RewardsCalculator.calculate(purchase);

        assertEquals(17.42, Double.valueOf(decimalFormat.format(reward.getValue())));
        assertEquals(purchase, reward.getPurchase());
    }

    @Test
    void shouldReturnRewardForTheBelowFiftyPurchaseAmount() {
        Purchase purchase = new Purchase();
        purchase.setAmount(34.5);

        Reward reward = RewardsCalculator.calculate(purchase);

        assertEquals(0.0, Double.valueOf(decimalFormat.format(reward.getValue())));
        assertEquals(purchase, reward.getPurchase());
    }

    @Test
    void shouldReturnRewardForThePurchaseAmountFifty() {
        Purchase purchase = new Purchase();
        purchase.setAmount(50.0);

        Reward reward = RewardsCalculator.calculate(purchase);

        assertEquals(0.00, Double.valueOf(decimalFormat.format(reward.getValue())));
        assertEquals(purchase, reward.getPurchase());
    }


    @Test
    void shouldReturnRewardForThePurchaseAmountHundred() {
        Purchase purchase = new Purchase();
        purchase.setAmount(100.0);

        Reward reward = RewardsCalculator.calculate(purchase);

        assertEquals(50.00, Double.valueOf(decimalFormat.format(reward.getValue())));
        assertEquals(purchase, reward.getPurchase());
    }
}