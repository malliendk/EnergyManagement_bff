package com.dillian.energymanagement_game_elements;

import com.dillian.energymanagement_game_elements.dto.gameDto.GameDto;
import com.dillian.energymanagement_game_elements.dto.gameDto.InitiateGameDto;
import com.dillian.energymanagement_game_elements.service.scheduler.AccountScheduler;
import com.dillian.energymanagement_game_elements.service.scheduler.EventScheduler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
@Slf4j
public class DtoService {

    private final EventScheduler eventScheduler;
    private final AccountScheduler accountScheduler;
    private final GameDtoBuilder dtoBuilder;
    private final RestClient restClient;
    private InitiateGameDto initiateGameDto;

    public DtoService(final EventScheduler eventScheduler, final AccountScheduler accountScheduler, final GameDtoBuilder dtoBuilder, final RestClient restClient) {
        this.eventScheduler = eventScheduler;
        this.accountScheduler = accountScheduler;
        this.dtoBuilder = dtoBuilder;
        this.restClient = restClient;
    }

    public InitiateGameDto initiateGameDto(InitiateGameDto initDto) {
        log.info("initiated DTO recieved: {}", initDto.toString());
        this.initiateGameDto = initDto;
        return initDto;
    }

    public InitiateGameDto getInitiateGameDto() {
        return this.initiateGameDto;
    }

    public GameDto passGameDto() {
        log.info("passing DTO: {}", gameDto);
        return this.gameDto;
    }

    public GameDto getStartingDto(GameDto gameDto) {
        log.info("fully built DTO returning to Controller: {}", gameDto);
        return dtoBuilder.buildInitialDto(gameDto.getAmountOfStartingSources());
    }

    public GameDto getUpdatedDto(GameDto gameDto) {
        log.info("going to update DTO: {}" ,gameDto);
        GameDto updatedDto = dtoBuilder.updateGameDto(gameDto);
        log.info("dto updated: {}", updatedDto);
        return updatedDto;
    }

    public void startSchedulers(GameDto gameDto) {
        log.info("schedulers started with gameDTO: {}", gameDto);
        eventScheduler.startScheduledEventAdd(gameDto);
        accountScheduler.startFluctuatingAccountSupply(gameDto);
    }


}
