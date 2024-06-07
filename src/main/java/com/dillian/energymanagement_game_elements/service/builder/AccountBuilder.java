package com.dillian.energymanagement_game_elements.service.builder;

import com.dillian.energymanagement_game_elements.dto.apiDto.AccountDto;
import com.dillian.energymanagement_game_elements.dto.gameDto.AccountLocalityDto;
import com.dillian.energymanagement_game_elements.dto.gameDto.InitiateGameDto;
import com.dillian.energymanagement_game_elements.service.ApiRetrieveService;
import com.dillian.energymanagement_game_elements.service.InitiateDtoService;
import com.dillian.energymanagement_game_elements.util.services.GridLoadCalculator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class AccountBuilder {

    private final ApiRetrieveService retrieveService;
    private final InitiateDtoService initiateDtoService;
    private final GridLoadCalculator gridLoadCalculator;


    public AccountLocalityDto toGameDto() {
        AccountLocalityDto dto = new AccountLocalityDto();
        List<AccountDto> accounts = fetchInitialAccounts();
        dto.setAccounts(accounts);
        double totalGridLoad = calculateTotalGridLoad(accounts);
        dto.setTotalGridLoad(totalGridLoad);
        return dto;
    }

    private double calculateTotalGridLoad(List<AccountDto> accounts) {
        return gridLoadCalculator.forAccounts(accounts);
    }

    private List<AccountDto> fetchInitialAccounts() {
        return retrieveService.getAccountsByLocality(getInitiateGameDto().getLocalityName());
    }

    private InitiateGameDto getInitiateGameDto() {
        final InitiateGameDto initDto = initiateDtoService.getStoredInitiateGameDto();
        log.info("Initiate game dto AccountBuilder: {}", initDto);
        return initDto;
    }
}
