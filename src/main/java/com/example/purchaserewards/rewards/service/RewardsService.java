package com.example.purchaserewards.rewards.service;

import com.example.purchaserewards.exception.CustomerNotFoundException;
import com.example.purchaserewards.rewards.RewardsCalculator;
import com.example.purchaserewards.rewards.dao.PurchaseTransactionRepository;
import com.example.purchaserewards.rewards.domain.Purchase;
import com.example.purchaserewards.rewards.domain.Reward;
import com.example.purchaserewards.rewards.dto.RewardsResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.purchaserewards.rewards.dto.RewardsResponseDto.createFrom;
import static java.util.Collections.emptyList;

@Service
@Slf4j
public class RewardsService {

    @Value("${rewards.recent-no-of-months}")
    private int numberOfMonths;

    @Autowired
    private PurchaseTransactionRepository purchaseTransactionRepository;

    /**
     *
     * @param customerId The id of the customer
     * @return Returns Rewards for given customer for last numberOfMonths (eg: 3)
     */
    public RewardsResponseDto getRewards(String customerId) {
        boolean anyPurchasesExist = purchaseTransactionRepository.existsByUserId(customerId);
        if (!anyPurchasesExist) {
            throw new CustomerNotFoundException(String.format("Customer %s not found!", customerId));
        }

        List<Purchase> customerPurchases = purchaseTransactionRepository.findAllPurchasesByUserIdAndNumberOfMonths(customerId, numberOfMonths);
        if (customerPurchases.isEmpty()) {
            log.info(String.format("No purchases found for the user %s in last %s months!", customerId, numberOfMonths));
            RewardsResponseDto rewardsResponseDto = new RewardsResponseDto();
            rewardsResponseDto.setCustomerId(customerId);
            rewardsResponseDto.setMonthlyRewards(emptyList());
            return rewardsResponseDto;
        }
        List<Reward> rewards = customerPurchases.stream().map(RewardsCalculator::calculate).collect(Collectors.toList());
        return createFrom(rewards, customerId);
    }
}
