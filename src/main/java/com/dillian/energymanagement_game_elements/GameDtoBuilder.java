package com.dillian.energymanagement_game_elements;

import com.dillian.energymanagement_game_elements.dto.gameDto.AccountLocalityDto;
import com.dillian.energymanagement_game_elements.dto.gameDto.FundsPopularityDto;
import com.dillian.energymanagement_game_elements.dto.gameDto.GameDto;
import com.dillian.energymanagement_game_elements.dto.gameDto.SourcesGridLoadDto;
import com.dillian.energymanagement_game_elements.service.ApiRetrieveService;
import com.dillian.energymanagement_game_elements.service.builder.AccountBuilder;
import com.dillian.energymanagement_game_elements.service.builder.FundsPopularityBuilder;
import com.dillian.energymanagement_game_elements.service.builder.SourceBuilder;
import com.dillian.energymanagement_game_elements.service.scheduler.AccountScheduler;
import com.dillian.energymanagement_game_elements.service.scheduler.EventScheduler;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GameDtoBuilder {

    private final SourceBuilder sourceBuilder;
    private final AccountBuilder accountBuilder;
    private final AccountScheduler accountScheduler;
    private final EventScheduler eventScheduler;
    private final ApiRetrieveService retrieveService;
    private final FundsPopularityBuilder fundsPopularityBuilder;


    public GameDto buildInitialDto(int startingSourceAmount) {
        GameDto startingGameDto = new GameDto();
        final AccountLocalityDto fromAccountBuilder = accountBuilder.getDto();
        startingGameDto.setAccounts(fromAccountBuilder.getAccounts());
        startingGameDto.setLocalityName(fromAccountBuilder.getLocalityName());
        final SourcesGridLoadDto fromSourceBuilder = sourceBuilder.toGameDto();
        startingGameDto.setSources(fromSourceBuilder.getSources());
        startingGameDto.setDistributionEfficiency(fromSourceBuilder.getDistributionEfficiency());
        final double totalGridLoad = fromAccountBuilder.getTotalGridLoad() + fromSourceBuilder.getTotalGridLoad()
                * startingGameDto.getDistributionEfficiency();
        startingGameDto.setTotalGridLoad(totalGridLoad);
        final FundsPopularityDto startingResourcesDto = fundsPopularityBuilder.getInitialResources();
        startingGameDto.setFunds(startingResourcesDto.getFunds());
        startingGameDto.setPopularity(startingResourcesDto.getPopularity());
        startingGameDto.setAmountOfStartingSources(startingSourceAmount);
        return startingGameDto;
    }

    public GameDto updateGameDto(GameDto gameDto){
        GameDto accountScheduled = accountScheduler.getUpdatedGameDto();
        GameDto eventScheduled = eventScheduler.getUpdatedGameDto();
        gameDto.setAccounts(accountScheduled.getAccounts());
        gameDto.setTotalGridLoad(accountScheduled.getTotalGridLoad());
        gameDto.setEvents(eventScheduled.getEvents());
        final GameDto moneyAndPopularityDto = retrieveService.getMoneyAndPopularityDto();
        gameDto.setFunds(moneyAndPopularityDto.getFunds());
        gameDto.setPopularity(moneyAndPopularityDto.getPopularity());
        return gameDto;
    }

}
