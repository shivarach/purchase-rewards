package com.example.purchaserewards.rewards.domain;

import lombok.Getter;

@Getter
public class Reward {
    private Double value;
    private Purchase purchase;

    public Reward(Double value, Purchase purchase) {
        this.value = value;
        this.purchase = purchase;
    }
}
