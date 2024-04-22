package com.dillian.energymanagement_game_elements;

import com.dillian.energymanagement_game_elements.dto.GameDto;
import com.dillian.energymanagement_game_elements.dto.SourceDto;
import com.dillian.energymanagement_game_elements.service.ListBuilderService;
import com.dillian.energymanagement_game_elements.service.ApiRetrieveService;
import com.dillian.energymanagement_game_elements.util.Constants;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@AllArgsConstructor
public class SourceBuilder {

    private final ApiRetrieveService retrieveService;
    private final ListBuilderService listBuilder;
    private final GridLoadCalculator gridLoadCalculator;

    private List<SourceDto> initialSourceList;


    public GameDto getGameDto(int startingSourcesAmount) {
        GameDto gameDto = new GameDto();
        final List<SourceDto> startingSources = buildInitialSourceList(startingSourcesAmount);
        gameDto.setSources(startingSources);
        final double totalGridLoad = gridLoadCalculator.forSources(startingSources);
        gameDto.setTotalGridLoad(totalGridLoad);
        return gameDto;
    }

    private List<SourceDto> buildInitialSourceList(int startingSourcesAmount) {
        SourceDto kolenCentrale = selectSource(Constants.KOLENCENTRALE_NAME);
        SourceDto aardgasCentrale = selectSource(Constants.AARDGASCENTRALE_NAME);
        List<SourceDto> randomSources = selectRandomSources(startingSourcesAmount);
        return listBuilder.combineLists(List.of(kolenCentrale, aardgasCentrale), randomSources);
    }

    private SourceDto selectSource(String powerPlantType) {
        return initialSourceList
                .stream()
                .filter(sourceDto -> sourceDto.getName().equals(powerPlantType))
                .findFirst()
                .orElseThrow();
    }

    private List<SourceDto> selectRandomSources(int startingSourcesAmount) {
        this.initialSourceList = retrieveService.getSources();
        int randomIndex = new Random().nextInt(initialSourceList.size());
        List<SourceDto> randomSources = new ArrayList<>();
        for (int i = 0; i < startingSourcesAmount; i++) {
            SourceDto source = initialSourceList.get(randomIndex);
            randomSources.add(source);
        }
        return randomSources;
    }
}
