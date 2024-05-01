package com.dillian.energymanagement_game_elements.service.builder;

import com.dillian.energymanagement_game_elements.DtoService;
import com.dillian.energymanagement_game_elements.util.services.GridLoadCalculator;
import com.dillian.energymanagement_game_elements.dto.apiDto.AccountDto;
import com.dillian.energymanagement_game_elements.dto.gameDto.AccountLocalityDto;
import com.dillian.energymanagement_game_elements.dto.gameDto.InitiateGameDto;
import com.dillian.energymanagement_game_elements.service.ApiRetrieveService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

import java.util.List;

@Service
@Slf4j
public class AccountBuilder {

    private final ApiRetrieveService retrieveService;
    private final DtoService dtoService;
    private final GridLoadCalculator gridLoadCalculator;


    public AccountBuilder(final ApiRetrieveService retrieveService, final DtoService dtoService, final GridLoadCalculator gridLoadCalculator) {
        this.retrieveService = retrieveService;
        this.dtoService = dtoService;
        this.gridLoadCalculator = gridLoadCalculator;
    }

    public AccountLocalityDto getDto() {
        AccountLocalityDto dto = fetchInitialAccounts();
        dto.setTotalGridLoad(getTotalGridLoad());
        return dto;
    }

    private double getTotalGridLoad() {
        List<AccountDto> accounts = fetchInitialAccounts().getAccounts();
        return gridLoadCalculator.forAccounts(accounts);
    }

    private AccountLocalityDto fetchInitialAccounts() {
        final InitiateGameDto initDto = dtoService.getInitiateGameDto();
        final List<AccountDto> initialAccounts = retrieveService.getAccountsByLocality(initDto.getLocalityName());
        if (initialAccounts.isEmpty()) {
            throw new RestClientException("Couldn't retrieve accounts from gameDto");
        }
        return new AccountLocalityDto(initialAccounts, initDto.getLocalityName());
    }


}
