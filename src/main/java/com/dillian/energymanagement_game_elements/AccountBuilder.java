package com.dillian.energymanagement_game_elements;

import com.dillian.energymanagement_game_elements.dto.AccountDto;
import com.dillian.energymanagement_game_elements.dto.GameDto;
import com.dillian.energymanagement_game_elements.service.ApiRetrieveService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class AccountBuilder implements InitializingBean {

    private final ApiRetrieveService retrieveService;
    private final DtoService dtoService;
    private final GridLoadCalculator gridLoadCalculator;
    private List<AccountDto> accounts;
    private GameDto gameDto;

    @Override
    public void afterPropertiesSet() throws Exception {
        this.gameDto = dtoService.passGameDto();
        final List<AccountDto> initialAccounts = fetchInitialAccounts();
        if (initialAccounts.isEmpty()) {
            throw new RestClientException("Couldn't retrieve accounts from gameDto");
        }
        this.accounts = initialAccounts;
    }

    private List<AccountDto> fetchInitialAccounts() {
        return retrieveService.getAccountsByLocality(gameDto.getLocalityName());
    }

    public GameDto getGameDto() {
        double totalGridLoad = gridLoadCalculator.forAccounts(accounts);
        this.gameDto.setAccounts(accounts);
        gameDto.setTotalGridLoad(totalGridLoad);
        return gameDto;
    }
}
