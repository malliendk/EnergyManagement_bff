package com.dillian.energymanagement_game_elements.service.scheduler;

import com.dillian.energymanagement_game_elements.service.builder.FundsPopularityBuilder;
import com.dillian.energymanagement_game_elements.util.services.GridLoadCalculator;
import com.dillian.energymanagement_game_elements.dto.gameDto.EventDto;
import com.dillian.energymanagement_game_elements.dto.gameDto.GameDto;
import com.dillian.energymanagement_game_elements.dto.apiDto.LoadSourceDto;
import com.dillian.energymanagement_game_elements.service.ApiRetrieveService;
import com.dillian.energymanagement_game_elements.util.services.ListBuilderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class EventScheduler implements InitializingBean {

    private final ApiRetrieveService retrieveService;
    private final ListBuilderService listBuilder;
    private final GridLoadCalculator gridLoadCalculator;
    private final FundsPopularityBuilder fundsPopularityBuilder;
    private ScheduledExecutorService schedulerService;
    private EventDto eventDto;
    private GameDto gameDto;
    private List<com.dillian.energymanagement_game_elements.dto.apiDto.EventDto> initialEvents;

    public EventScheduler(final ApiRetrieveService retrieveService, final ListBuilderService listBuilder, final GridLoadCalculator gridLoadCalculator, final FundsPopularityBuilder fundsPopularityBuilder) {
        this.retrieveService = retrieveService;
        this.listBuilder = listBuilder;
        this.gridLoadCalculator = gridLoadCalculator;
        this.fundsPopularityBuilder = fundsPopularityBuilder;
    }

    @Override
    public void afterPropertiesSet() {
        fetchInitialEvents();
    }

    private void fetchInitialEvents() {
        initialEvents = retrieveService.getEvents();
        initialEvents.forEach(event -> log.info(event.getName()));
        if (initialEvents.isEmpty()) {
            log.error("Failed to retrieve initial events.");
        }
    }

    public GameDto getUpdatedGameDto() {
        return this.gameDto;
    }

    public void startScheduledEventAdd(GameDto gameDto) {
        long initialDelay = 30;
        long period = 60;
        schedulerService = Executors.newScheduledThreadPool(1);

        schedulerService.scheduleAtFixedRate(() -> {
            try {
                if (!initialEvents.isEmpty()) {
                    log.info("event schedule started");
                    com.dillian.energymanagement_game_elements.dto.apiDto.EventDto newEvent = pickEventFromList();
                    fundsPopularityBuilder.updateFundsAndPopularity(gameDto, newEvent.getSource());
                    this.gameDto = updateGameDto(gameDto, newEvent);
                } else {
                    log.info("Scheduler shutdown");
                    schedulerService.shutdownNow();
                }

            } catch (Exception e) {
                log.info("Error during event processing: ", e);
            }
        }, initialDelay, period, TimeUnit.SECONDS);
    }

    private GameDto updateGameDto(GameDto gameDto, com.dillian.energymanagement_game_elements.dto.apiDto.EventDto newEvent) {
        List<com.dillian.energymanagement_game_elements.dto.apiDto.EventDto> newEventList = listBuilder.addItemToList(newEvent, gameDto.getEvents());
        List<LoadSourceDto> newSourceList = listBuilder.addItemToList(newEvent.getSource(), gameDto.getSources());
        double newGridLoad = gridLoadCalculator.forSources(newSourceList);
        gameDto.setEvents(newEventList);
        gameDto.setSources(newSourceList);
        gameDto.setTotalGridLoad(newGridLoad);
        return gameDto;
    }

    private com.dillian.energymanagement_game_elements.dto.apiDto.EventDto pickEventFromList() {
        if (initialEvents != null) {
            initialEvents.forEach(event -> log.info(event.getName()));
        }
        int randomIndex = new Random().nextInt(initialEvents.size());
        final com.dillian.energymanagement_game_elements.dto.apiDto.EventDto eventToUpload = initialEvents.get(randomIndex);
        initialEvents.remove(eventToUpload);
        return eventToUpload;
    }
}
