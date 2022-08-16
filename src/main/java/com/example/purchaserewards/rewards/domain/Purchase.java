package com.example.purchaserewards.rewards.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Entity(name = "purchase_transaction")
@Getter
@Setter
public class Purchase {
    @Id
    private int id;

    @NotNull
    private String userId;

    @NotNull
    private Double amount;

    @NotNull
    private Timestamp purchasedOn;
}
