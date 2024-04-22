package com.dillian.energymanagement_game_elements;

import com.dillian.energymanagement_game_elements.dto.AccountDto;
import com.dillian.energymanagement_game_elements.dto.GameDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Service
@AllArgsConstructor
@Slf4j
public class AccountScheduler {

    private final GridLoadCalculator gridLoadCalculator;
    private ScheduledExecutorService schedulerService;
    private GameDto gameDto;

    public GameDto getUpdatedGameDto() {
        return this.gameDto;
    }


    private void startFluctuatingAccountSupply(GameDto gameDto) {
        long initialDelay = 0;
        long period = 5;
        schedulerService = Executors.newScheduledThreadPool(1);

        schedulerService.scheduleAtFixedRate(() -> {
            try {
                this.gameDto = fluctuateGridLoad(gameDto);
            } catch (Exception e) {
                log.info("Error during event processing: ", e);
            }
        }, initialDelay, period, TimeUnit.SECONDS);
    }

    private GameDto fluctuateGridLoad(GameDto gameDto) {
        final List<AccountDto> updatedAccounts = fluctuateSupplyAmount(gameDto.getAccounts());
        final double newGridLoad = gridLoadCalculator.forAccounts(updatedAccounts);
        gameDto.setAccounts(updatedAccounts);
        gameDto.setTotalGridLoad(newGridLoad);
        return gameDto;
    }

    public List<AccountDto> fluctuateSupplyAmount(List<AccountDto> accounts) {
        accounts.forEach(account -> {
            double supplyAmount = account.getSupplyAmount();
            double newSupplyAmount = 0;
            switch (account.getSupplyType()) {
                case SHORTAGE -> newSupplyAmount = supplyAmount + supplyAmount * 0.25;
                case OPTIMAL -> newSupplyAmount = destabilizeOptimalSupply(account);
                case SURPLUS -> newSupplyAmount = supplyAmount - supplyAmount * 0.25;
                default -> throw new RuntimeException("supplyType has no valid category");
            }
            account.setSupplyAmount(newSupplyAmount);
        });
        return accounts;
    }

    private double destabilizeOptimalSupply(AccountDto account) {
        double supplyAmount = account.getSupplyAmount();
        if (supplyAmount < 1) {
            return supplyAmount + selectRandomValue(new double[] {0, 0.05, 0.1, 0.25, 0.5});
        } else {
            return supplyAmount + selectRandomValue(new double[] {-0.5, -0.25, -0.1, -0.05, 0});
        }
    }

    private double selectRandomValue(double[] addOrSubtractValues) {
        int randomIndex = new Random().nextInt(addOrSubtractValues.length);
        return addOrSubtractValues[randomIndex];
    }

}
