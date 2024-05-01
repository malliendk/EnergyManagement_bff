package com.dillian.energymanagement_game_elements.service.builder;

import com.dillian.energymanagement_game_elements.DtoService;
import com.dillian.energymanagement_game_elements.dto.gameDto.InitiateGameDto;
import com.dillian.energymanagement_game_elements.dto.gameDto.SourcesGridLoadDto;
import com.dillian.energymanagement_game_elements.util.services.GridLoadCalculator;
import com.dillian.energymanagement_game_elements.dto.apiDto.LoadSourceDto;
import com.dillian.energymanagement_game_elements.util.services.ListBuilderService;
import com.dillian.energymanagement_game_elements.service.ApiRetrieveService;
import com.dillian.energymanagement_game_elements.util.constants.Constants;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@AllArgsConstructor
@Slf4j
public class SourceBuilder {

    private final ApiRetrieveService retrieveService;
    private final ListBuilderService listBuilder;
    private final GridLoadCalculator gridLoadCalculator;
    private final DtoService dtoService;

    private List<LoadSourceDto> initialSourceList;


    public SourcesGridLoadDto toGameDto() {
        SourcesGridLoadDto sourcesGridLoadDto = new SourcesGridLoadDto();
        final InitiateGameDto initDto = dtoService.getInitiateGameDto();
        sourcesGridLoadDto.setDistributionEfficiency(initDto.getDistributionEfficiency());
        final List<LoadSourceDto> startingSources = buildInitialSourceList(initDto.getStartingSourcesAmount());
        sourcesGridLoadDto.setSources(startingSources);
        final double totalGridLoad = gridLoadCalculator.forSources(startingSources);
        sourcesGridLoadDto.setTotalGridLoad(totalGridLoad);
        log.info("current Dto: {}" , sourcesGridLoadDto);
        return sourcesGridLoadDto;
    }

    public List<LoadSourceDto> buildInitialSourceList(int startingSourcesAmount) {
        List<LoadSourceDto> randomSources = selectRandomSources(startingSourcesAmount);
        LoadSourceDto kolenCentrale = selectSource(Constants.KOLENCENTRALE_NAME);
        LoadSourceDto aardgasCentrale = selectSource(Constants.AARDGASCENTRALE_NAME);
        return listBuilder.combineLists(List.of(kolenCentrale, aardgasCentrale), randomSources);
    }

    private LoadSourceDto selectSource(String powerPlantType) {
        return initialSourceList
                .stream()
                .filter(sourceDto -> sourceDto.getName().equals(powerPlantType))
                .findFirst()
                .orElseThrow();
    }

    private List<LoadSourceDto> selectRandomSources(int startingSourcesAmount) {
        this.initialSourceList = retrieveService.getSources();
        int randomIndex = new Random().nextInt(initialSourceList.size());
        List<LoadSourceDto> randomSources = new ArrayList<>();
        for (int i = 0; i < startingSourcesAmount; i++) {
            LoadSourceDto source = initialSourceList.get(randomIndex);
            randomSources.add(source);
        }
        return randomSources;
    }
}
