package com.example.purchaserewards.rewards;

import com.example.purchaserewards.rewards.domain.Purchase;
import com.example.purchaserewards.rewards.domain.Reward;

public class RewardsCalculator {

    private static final int REWARD_OVER_FIFTY_DOLLAR = 1;
    private static final int FIFTY_DOLLAR_LIMIT = 50;
    private static final int REWARD_OVER_HUNDRED_DOLLAR = 2;
    private static final int HUNDRED_DOLLAR_LIMIT = 100;

    /**
     *
     * @param customerPurchase Customer Purchase
     * @return Reward for the given Purchase
     */
    public static Reward calculate(Purchase customerPurchase) {
        Double amount = customerPurchase.getAmount();
        double rewardValue = 0.0;

        if (amount > FIFTY_DOLLAR_LIMIT && amount <= HUNDRED_DOLLAR_LIMIT) {
            rewardValue += (amount - FIFTY_DOLLAR_LIMIT) * REWARD_OVER_FIFTY_DOLLAR;
        }
        if (amount > HUNDRED_DOLLAR_LIMIT) {
            rewardValue += (amount - HUNDRED_DOLLAR_LIMIT) * REWARD_OVER_HUNDRED_DOLLAR;
            rewardValue += FIFTY_DOLLAR_LIMIT * REWARD_OVER_FIFTY_DOLLAR;
        }
        return new Reward(rewardValue, customerPurchase);
    }
}
