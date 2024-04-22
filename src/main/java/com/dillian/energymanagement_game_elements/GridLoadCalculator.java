package com.dillian.energymanagement_game_elements;

import com.dillian.energymanagement_game_elements.dto.AccountDto;
import com.dillian.energymanagement_game_elements.dto.SourceDto;
import com.dillian.energymanagement_game_elements.util.enums.SupplyType;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GridLoadCalculator {


    public double forAccounts(List<AccountDto> accounts) {
        double totalSurplusAmount = accounts
                .stream()
                .mapToDouble(accountDto -> {
                    if (accountDto.getSupplyType().equals(SupplyType.SHORTAGE)) {
                        return accountDto.getSupplyAmount() - 0.3;
                    } else if (accountDto.getSupplyType().equals(SupplyType.SURPLUS)) {
                        return accountDto.getSupplyAmount() - 1.1;
                    } else {
                        return 0;
                    }
                })
                .sum();
        return totalSurplusAmount / 50;
    }

    public double forSources(List<SourceDto> startingSources) {
        return startingSources
                .stream()
                .mapToDouble(SourceDto::getGridLoad)
                .sum();
    }
}
