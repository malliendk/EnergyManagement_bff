package com.dillian.energymanagement_game_elements;

import com.dillian.energymanagement_game_elements.dto.GameDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DtoService {

    private final EventScheduler eventScheduler;
    private final AccountBuilder accountBuilder;

    private GameDto gameDto;

    public GameDto getGameDtoFromController(GameDto gameDto) {
        this.gameDto = gameDto;
        return gameDto;
    }

    public void startEventScheduler(GameDto gameDto) {
        eventScheduler.startScheduledEventAdd(gameDto);
    }

    public GameDto passGameDto() {
        return this.gameDto;
    }

}
