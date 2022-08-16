package com.example.purchaserewards.rewards.service;

import com.example.purchaserewards.exception.CustomerNotFoundException;
import com.example.purchaserewards.rewards.RewardsCalculator;
import com.example.purchaserewards.rewards.dao.PurchaseTransactionRepository;
import com.example.purchaserewards.rewards.domain.Purchase;
import com.example.purchaserewards.rewards.domain.Reward;
import com.example.purchaserewards.rewards.dto.RewardsResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.purchaserewards.rewards.dto.RewardsResponseDto.createFrom;
import static java.util.Collections.emptyList;

@Service
public class RewardsService {

    @Value("${rewards.recent-no-of-months}")
    private int numberOfMonths;

    @Autowired
    private PurchaseTransactionRepository purchaseTransactionRepository;

    public RewardsResponseDto getRewards(String customerId) {
        boolean anyPurchasesExist = purchaseTransactionRepository.existsByUserId(customerId);
        if (!anyPurchasesExist) {
            throw new CustomerNotFoundException(String.format("Customer %s not found!", customerId));
        }

        List<Purchase> customerPurchases = purchaseTransactionRepository.findAllPurchasesByUserIdAndNumberOfMonths(customerId, numberOfMonths);
        if (customerPurchases.isEmpty()) {
            RewardsResponseDto rewardsResponseDto = new RewardsResponseDto();
            rewardsResponseDto.setCustomerId(customerId);
            rewardsResponseDto.setMonthlyRewards(emptyList());
            return rewardsResponseDto;
        }
        List<Reward> rewards = customerPurchases.stream().map(RewardsCalculator::calculate).collect(Collectors.toList());
        return createFrom(rewards, customerId);
    }
}
