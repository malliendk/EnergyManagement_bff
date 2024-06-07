package com.dillian.energymanagement_game_elements.service.builder;

import com.dillian.energymanagement_game_elements.dto.gameDto.FundsPopularityDto;
import com.dillian.energymanagement_game_elements.dto.gameDto.InitiateGameDto;
import com.dillian.energymanagement_game_elements.service.InitiateDtoService;
import com.dillian.energymanagement_game_elements.util.enums.DifficultyLevel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import static com.dillian.energymanagement_game_elements.util.constants.StartingResources.*;

@Service
@AllArgsConstructor
public class FundsPopularityBuilder {

    private final InitiateDtoService initiateDtoService;


//    public FundsPopularityDto updateFundsAndPopularity(GameDto gameDto, LoadSourceDto newLoadSource) {
//        FundsPopularityDto incomingDto = new FundsPopularityDto();
//        incomingDto.setFunds(gameDto.getFunds());
//        incomingDto.setPopularity(gameDto.getPopularity());
//        incomingDto.setSourcePrice(newLoadSource.getPrice());
//        FundsPopularityDto updatedFundsDto = restClient
//                .post()
//                .uri("")
//                .contentType(MediaType.APPLICATION_JSON)
//                .body(incomingDto)
//                .retrieve()
//                .body(FundsPopularityDto.class);
//        return updatedFundsDto;
//    }

    public FundsPopularityDto getInitialResources() {
        InitiateGameDto initDto = initiateDtoService.getStoredInitiateGameDto();
        FundsPopularityDto startingResources = new FundsPopularityDto();
        final int startingFunds = decideFundsStartingAmount(initDto.getDifficultyLevel());
        startingResources.setFunds(startingFunds);
        final int startingPopularity = decidePopularityStartingAmount(initDto.getDifficultyLevel());
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
