package com.example.purchaserewards.rewards.service;

import com.example.purchaserewards.exception.CustomerNotFoundException;
import com.example.purchaserewards.rewards.dao.PurchaseTransactionRepository;
import com.example.purchaserewards.rewards.domain.Purchase;
import com.example.purchaserewards.rewards.dto.RewardsResponseDto;
import com.example.purchaserewards.rewards.dto.RewardsResponseDto.MonthlyRewardDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import static java.time.Month.AUGUST;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(SpringExtension.class)
class RewardsServiceTest {

    @Mock
    private PurchaseTransactionRepository purchaseTransactionRepository;

    @InjectMocks
    RewardsService rewardsService = new RewardsService();


    @Test
    void shouldReturnThreeMonthsRewardsForGivenUser() {
        String customerId = "shiva@test.com";
        Mockito.when(purchaseTransactionRepository.existsByUserId(customerId)).thenReturn(true);

        Purchase augPurchase = new Purchase();
        augPurchase.setUserId(customerId);
        augPurchase.setPurchasedOn(Timestamp.valueOf(LocalDateTime.now()));
        augPurchase.setAmount(120.0);
        Mockito.when(purchaseTransactionRepository.findAllPurchasesByUserIdAndNumberOfMonths(any(String.class), any(Integer.class)))
                .thenReturn(singletonList(augPurchase));

        RewardsResponseDto rewards = rewardsService.getRewards(customerId);

        assertEquals(customerId, rewards.getCustomerId());
        assertEquals(90, rewards.getTotal());
        List<MonthlyRewardDto> monthlyRewards = rewards.getMonthlyRewards();
        assertEquals(1, monthlyRewards.size());
        assertEquals(AUGUST, monthlyRewards.get(0).getMonth());
        assertEquals(90, monthlyRewards.get(0).getTotal());
    }

    @Test
    void shouldReturnZeroRewardsWhenThereAreNoPurchasesInLastThreeMonthsForGivenUser() {
        String customerId = "shiva@test.com";
        Mockito.when(purchaseTransactionRepository.existsByUserId(customerId)).thenReturn(true);


        Mockito.when(purchaseTransactionRepository.findAllPurchasesByUserIdAndNumberOfMonths(any(String.class), any(Integer.class)))
                .thenReturn(emptyList());

        RewardsResponseDto rewards = rewardsService.getRewards(customerId);

        assertEquals(customerId, rewards.getCustomerId());
        assertEquals(0.0, rewards.getTotal());
        List<MonthlyRewardDto> monthlyRewards = rewards.getMonthlyRewards();
        assertEquals(0, monthlyRewards.size());
    }

    @Test
    void shouldReturnCustomerNotFoundExceptionIfThereAreNoPurchasesForGivenCustomer() {
        String customerId = "smith@test.com";
        Mockito.when(purchaseTransactionRepository.existsByUserId(customerId)).thenReturn(false);

        assertThrows(CustomerNotFoundException.class, () -> rewardsService.getRewards(customerId), "Customer smith@test.com not found!");
    }
}