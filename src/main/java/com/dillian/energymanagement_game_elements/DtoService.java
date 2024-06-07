package com.dillian.energymanagement_game_elements;

import com.dillian.energymanagement_game_elements.dto.gameDto.GameDto;
import com.dillian.energymanagement_game_elements.dto.gameDto.InitiateGameDto;
import com.dillian.energymanagement_game_elements.service.scheduler.AccountScheduler;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DtoService {

//    private final EventScheduler eventScheduler;
    private final AccountScheduler accountScheduler;
    private final GameDtoBuilder gameDtoBuilder;
    @Getter
    private InitiateGameDto initiateGameDto;

    public DtoService(final AccountScheduler accountScheduler, final GameDtoBuilder gameDtoBuilder) {
        this.accountScheduler = accountScheduler;
        this.gameDtoBuilder = gameDtoBuilder;
    }


    public GameDto getStartingDto() {
        GameDto startingGameDto = gameDtoBuilder.buildInitialDto();
        log.info("fully built DTO returning to Controller: {}", startingGameDto.toString());
        return startingGameDto;
    }

//    public GameDto getUpdatedDto(GameDto gameDto) {
//        log.info("going to update DTO: {}" ,gameDto);
//        GameDto updatedDto = dtoBuilder.updateGameDto(gameDto);
//        log.info("dto updated: {}", updatedDto);
//        return updatedDto;
//    }

//    public void startSchedulers(GameDto gameDto) {
//        log.info("schedulers started with gameDTO: {}", gameDto);
//        eventScheduler.startScheduledEventAdd(gameDto);
//        accountScheduler.startFluctuatingAccountSupply(gameDto);
//    }
}
