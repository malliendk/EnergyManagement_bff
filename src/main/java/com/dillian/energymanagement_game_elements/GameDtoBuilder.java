package com.dillian.energymanagement_game_elements;

import com.dillian.energymanagement_game_elements.dto.apiDto.LocalityDto;
import com.dillian.energymanagement_game_elements.dto.apiDto.SupervisorDto;
import com.dillian.energymanagement_game_elements.dto.gameDto.*;
import com.dillian.energymanagement_game_elements.service.InitiateDtoService;
import com.dillian.energymanagement_game_elements.service.builder.*;
import com.dillian.energymanagement_game_elements.service.scheduler.AccountScheduler;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GameDtoBuilder {

    private final SourceBuilder sourceBuilder;
    private final AccountBuilder accountBuilder;
    private final LocalityBuilder localityBuilder;
    private final SupervisorBuilder supervisorBuilder;
    private final AccountScheduler accountScheduler;
//    private final EventScheduler eventScheduler;
    private final FundsPopularityBuilder fundsPopularityBuilder;
    private final InitiateDtoService initiateDtoService;


    public GameDto buildInitialDto() {
        GameDto startingGameDto = new GameDto();
        InitiateGameDto initDto = initiateDtoService.getStoredInitiateGameDto();
        final LocalityDto locality = localityBuilder.getLocality(initDto.getLocalityName());
        startingGameDto.setLocalityName(locality.getName());
        startingGameDto.setStartingSourcesAmount(locality.getStartingSourcesAmount());
        final SupervisorDto supervisor = supervisorBuilder.getSupervisor(locality.getName());
        startingGameDto.setSupervisorName(String.format("%s %s", supervisor.getFirstName(), supervisor.getLastName()));
        startingGameDto.setDistributionEfficiency(supervisor.getDistributionEfficiency());
        final FundsPopularityDto startingResourcesDto = fundsPopularityBuilder.getInitialResources();
        startingGameDto.setFunds(startingResourcesDto.getFunds());
        startingGameDto.setPopularity(startingResourcesDto.getPopularity());
        startingGameDto.setStartingSourcesAmount(locality.getStartingSourcesAmount());
        final AccountLocalityDto accountBuilderDto = accountBuilder.toGameDto();
        startingGameDto.setAccounts(accountBuilderDto.getAccounts());
        final SourcesGridLoadDto sourceBuilderDto = sourceBuilder.toGameDto();
        startingGameDto.setSources(sourceBuilderDto.getSources());
        final double totalGridLoad = accountBuilderDto.getTotalGridLoad() + sourceBuilderDto.getTotalGridLoad()
                * supervisor.getDistributionEfficiency();
        startingGameDto.setTotalGridLoad(totalGridLoad);
        return startingGameDto;
    }

//    public GameDto updateGameDto(GameDto gameDto){
//        GameDto accountScheduled = accountScheduler.getUpdatedGameDto();
//        GameDto eventScheduled = eventScheduler.getUpdatedGameDto();
//        gameDto.setAccounts(accountScheduled.getAccounts());
//        gameDto.setTotalGridLoad(accountScheduled.getTotalGridLoad());
//        gameDto.setEvents(eventScheduled.getEvents());
//        final GameDto moneyAndPopularityDto = retrieveService.getMoneyAndPopularityDto();
//        gameDto.setFunds(moneyAndPopularityDto.getFunds());
//        gameDto.setPopularity(moneyAndPopularityDto.getPopularity());
//        return gameDto;
//    }

}
