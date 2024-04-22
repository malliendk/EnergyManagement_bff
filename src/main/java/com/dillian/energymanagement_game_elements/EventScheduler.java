package com.dillian.energymanagement_game_elements;

import com.dillian.energymanagement_game_elements.dto.EventDto;
import com.dillian.energymanagement_game_elements.dto.GameDto;
import com.dillian.energymanagement_game_elements.dto.SourceDto;
import com.dillian.energymanagement_game_elements.service.ApiRetrieveService;
import com.dillian.energymanagement_game_elements.service.ListBuilderService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
@AllArgsConstructor
@Slf4j
public class EventScheduler implements InitializingBean {

    private final ApiRetrieveService retrieveService;
    private final ListBuilderService listBuilder;
    private final GridLoadCalculator gridLoadCalculator;
    private ScheduledExecutorService schedulerService;
    private GameDto gameDto;
    private List<EventDto> initialEvents;


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
                    EventDto newEvent = pickEventFromList();
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

    private GameDto updateGameDto(GameDto gameDto, EventDto newEvent) {
        List<EventDto> newEventList = listBuilder.addItemToList(newEvent, gameDto.getEvents());
        List<SourceDto> newSourceList = listBuilder.addItemToList(newEvent.getSource(), gameDto.getSources());
        double newGridLoad = gridLoadCalculator.forSources(newSourceList);
        gameDto.setEvents(newEventList);
        gameDto.setSources(newSourceList);
        gameDto.setTotalGridLoad(newGridLoad);
        return gameDto;
    }

    private EventDto pickEventFromList() {
        if (initialEvents != null) {
            initialEvents.forEach(event -> log.info(event.getName()));
        }
        int randomIndex = new Random().nextInt(initialEvents.size());
        final EventDto eventToUpload = initialEvents.get(randomIndex);
        initialEvents.remove(eventToUpload);
        return eventToUpload;
    }
}
