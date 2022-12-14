package com.example.purchaserewards.rewards.dao;

import com.example.purchaserewards.rewards.domain.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PurchaseTransactionRepository extends JpaRepository<Purchase, Integer> {

    /**
     *
     * @param userId The id of the customer
     * @param numberOfMonths recent number of months to consider
     * @return Returns Purchases for given customer for last numberOfMonths (eg: 3)
     */
    @Query(value = "SELECT * FROM purchase_transaction WHERE user_id=:userId and purchased_on > (NOW() - :numberOfMonths MONTH);", nativeQuery = true)
    List<Purchase> findAllPurchasesByUserIdAndNumberOfMonths(String userId, int numberOfMonths);

    boolean existsByUserId(String customerId);
}
