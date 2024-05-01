package com.dillian.energymanagement_game_elements.service.builder;

import com.dillian.energymanagement_game_elements.DtoService;
import com.dillian.energymanagement_game_elements.dto.gameDto.FundsPopularityDto;
import com.dillian.energymanagement_game_elements.dto.gameDto.GameDto;
import com.dillian.energymanagement_game_elements.dto.apiDto.LoadSourceDto;
import com.dillian.energymanagement_game_elements.dto.gameDto.InitiateGameDto;
import com.dillian.energymanagement_game_elements.util.enums.DifficultyLevel;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import static com.dillian.energymanagement_game_elements.util.constants.StartingResources.*;
import static com.dillian.energymanagement_game_elements.util.constants.StartingResources.POPULARITY_STARTINGAMOUNT_HARD;

@Service

public class FundsPopularityBuilder {

    private final RestClient restClient;
    private final DtoService dtoService;

    public FundsPopularityBuilder(final RestClient restClient, final DtoService dtoService) {
        this.restClient = restClient;
        this.dtoService = dtoService;
    }


    public FundsPopularityDto updateFundsAndPopularity(GameDto gameDto, LoadSourceDto newLoadSource) {
        FundsPopularityDto incomingDto = new FundsPopularityDto();
        incomingDto.setFunds(gameDto.getFunds());
        incomingDto.setPopularity(gameDto.getPopularity());
        incomingDto.setSourcePrice(newLoadSource.getPrice());
        FundsPopularityDto updatedFundsDto = restClient
                .post()
                .uri("")
                .contentType(MediaType.APPLICATION_JSON)
                .body(incomingDto)
                .retrieve()
                .body(FundsPopularityDto.class);
        return updatedFundsDto;
    }

    public FundsPopularityDto getInitialResources() {
        InitiateGameDto initDto = dtoService.getInitiateGameDto();
        FundsPopularityDto startingResources = new FundsPopularityDto();
        int startingFunds = decideFundsStartingAmount(initDto.getDifficultyLevel());
        startingResources.setFunds(startingFunds);
        int startingPopularity = decidePopularityStartingAmount(initDto.getDifficultyLevel());
        startingResources.setPopularity(startingPopularity);
        return startingResources;
    }

    private int decideFundsStartingAmount(DifficultyLevel difficultyLevel) {
        return switch (difficultyLevel) {
            case EASY -> FUNDS_STARTINGAMOUNT_EASY;
            case MEDIUM -> FUNDS_STARTINGAMOUNT_MEDIUM;
            case HARD -> FUNDS_STARTINGAMOUNT_HARD;
        };
    }

    private int decidePopularityStartingAmount(DifficultyLevel difficultyLevel) {
        return switch (difficultyLevel) {
            case EASY -> POPULARITY_STARTINGAMOUNT_EASY;
            case MEDIUM -> POPULARITY_STARTINGAMOUNT_MEDIUM;
            case HARD -> POPULARITY_STARTINGAMOUNT_HARD;
        };
    }
}
