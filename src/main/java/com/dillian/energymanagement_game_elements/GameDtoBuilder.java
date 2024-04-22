package com.dillian.energymanagement_game_elements;

import com.dillian.energymanagement_game_elements.dto.GameDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GameDtoBuilder {

    private final SourceBuilder sourceBuilder;
    private final AccountBuilder accountBuilder;
    private final AccountScheduler accountScheduler;
    private final EventScheduler eventScheduler;


    public GameDto buildInitialDto(int startingSourceAmount, double distributionEfficiency) {
        GameDto gameDto = new GameDto();
        GameDto fromAccountBuilder = accountBuilder.getGameDto();
        GameDto fromSourceBuilder = sourceBuilder.getGameDto(startingSourceAmount);

        gameDto.setLocalityName(fromSourceBuilder.getLocalityName());
        gameDto.setAccounts(fromAccountBuilder.getAccounts());
        gameDto.setSources(fromSourceBuilder.getSources());
        final double totalGridLoad = fromAccountBuilder.getTotalGridLoad() + fromSourceBuilder.getTotalGridLoad()
                * gameDto.getDistributionEfficiency();
        gameDto.setTotalGridLoad(totalGridLoad);

        return gameDto;
    }

    public GameDto updateGameDto(GameDto gameDto){
        GameDto accountScheduled = accountScheduler.getUpdatedGameDto();
        GameDto eventScheduled = eventScheduler.getUpdatedGameDto();
        gameDto.setAccounts(accountScheduled.getAccounts());
        gameDto.setTotalGridLoad(accountScheduled.getTotalGridLoad());
        gameDto.setEvents(eventScheduled.getEvents());
        return gameDto;
    }

}
