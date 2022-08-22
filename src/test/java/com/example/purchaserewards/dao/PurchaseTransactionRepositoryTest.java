package com.example.purchaserewards.dao;

import com.example.purchaserewards.rewards.dao.PurchaseTransactionRepository;
import com.example.purchaserewards.rewards.domain.Purchase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class PurchaseTransactionRepositoryTest {

    @Autowired
    private PurchaseTransactionRepository purchaseTransactionRepository;

    @Test
    void shouldReturnLastThreeMonthsRewardsForGivenUser() {
        List<Purchase> purchases = purchaseTransactionRepository.findAllPurchasesByUserIdAndNumberOfMonths("shiva@test.com", 3);
        System.out.println(purchases);

        assertEquals(3, purchases.size());

        Purchase purchase = purchases.get(0);
        assertEquals("shiva@test.com", purchase.getUserId());
        assertEquals(120.0, purchase.getAmount());
        assertEquals("2022-08-22", purchase.getPurchasedOn().toLocalDateTime().toLocalDate().toString());

        Purchase secondPurchase = purchases.get(1);
        assertEquals("shiva@test.com", secondPurchase.getUserId());
        assertEquals(70.0, secondPurchase.getAmount());
        assertEquals("2022-07-17 11:31:52.8", secondPurchase.getPurchasedOn().toString());

        Purchase thirdPurchase = purchases.get(2);
        assertEquals("shiva@test.com", thirdPurchase.getUserId());
        assertEquals(40.0, thirdPurchase.getAmount());
        assertEquals("2022-06-17 14:42:52.49", thirdPurchase.getPurchasedOn().toString());
    }
}
