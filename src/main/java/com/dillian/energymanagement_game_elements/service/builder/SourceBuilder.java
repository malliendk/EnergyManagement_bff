package com.dillian.energymanagement_game_elements.service.builder;

import com.dillian.energymanagement_game_elements.dto.apiDto.LoadSourceDto;
import com.dillian.energymanagement_game_elements.dto.apiDto.LocalityDto;
import com.dillian.energymanagement_game_elements.dto.gameDto.InitiateGameDto;
import com.dillian.energymanagement_game_elements.dto.gameDto.SourcesGridLoadDto;
import com.dillian.energymanagement_game_elements.service.ApiRetrieveService;
import com.dillian.energymanagement_game_elements.service.InitiateDtoService;
import com.dillian.energymanagement_game_elements.util.constants.Constants;
import com.dillian.energymanagement_game_elements.util.services.GridLoadCalculator;
import com.dillian.energymanagement_game_elements.util.services.ListBuilderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service

@Slf4j
public class SourceBuilder {

    private final ApiRetrieveService retrieveService;
    private final ListBuilderService listBuilder;
    private final GridLoadCalculator gridLoadCalculator;
    private final InitiateDtoService initiateDtoService;
    private final LocalityBuilder localityBuilder;

    private List<LoadSourceDto> initialSourceList;

    public SourceBuilder(ApiRetrieveService retrieveService, ListBuilderService listBuilder, GridLoadCalculator gridLoadCalculator, InitiateDtoService initiateDtoService, LocalityBuilder localityBuilder) {
        this.retrieveService = retrieveService;
        this.listBuilder = listBuilder;
        this.gridLoadCalculator = gridLoadCalculator;
        this.initiateDtoService = initiateDtoService;
        this.localityBuilder = localityBuilder;
    }

    public SourcesGridLoadDto toGameDto() {
        SourcesGridLoadDto sourcesGridLoadDto = new SourcesGridLoadDto();
        final List<LoadSourceDto> startingSources = buildInitialSourceList();
        sourcesGridLoadDto.setSources(startingSources);
        final double totalGridLoad = gridLoadCalculator.forSources(startingSources);
        sourcesGridLoadDto.setTotalGridLoad(totalGridLoad);
        log.info("current Dto: {}" , sourcesGridLoadDto);
        return sourcesGridLoadDto;
    }

    public List<LoadSourceDto> buildInitialSourceList() {
        List<LoadSourceDto> randomSources = selectRandomSources();
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

    private List<LoadSourceDto> selectRandomSources() {
        this.initialSourceList = retrieveService.getSources();
        int randomIndex = new Random().nextInt(initialSourceList.size());
        List<LoadSourceDto> randomSources = new ArrayList<>();
        final LocalityDto locality = localityBuilder.getLocality(getInitiateGameDto().getLocalityName());
        for (int i = 0; i < locality.getStartingSourcesAmount() ; i++) {
            LoadSourceDto source = initialSourceList.get(randomIndex);
            randomSources.add(source);
        }
        return randomSources;
    }

    private InitiateGameDto getInitiateGameDto() {
        final InitiateGameDto initDto = initiateDtoService.getStoredInitiateGameDto();
        log.info("init Dto in sourceBuilder: {}" , initDto);
        return initDto;
    }
}
