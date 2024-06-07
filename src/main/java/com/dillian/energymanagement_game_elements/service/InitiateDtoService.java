package com.dillian.energymanagement_game_elements.service;

import com.dillian.energymanagement_game_elements.dto.gameDto.InitiateGameDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class InitiateDtoService {

    private InitiateGameDto initDto;

    public void storeInitiateGameDto(InitiateGameDto initDto) {
        log.info("initiated DTO recieved: {}", initDto.toString());
        this.initDto = initDto;
    }

    public InitiateGameDto getStoredInitiateGameDto() {
        return this.initDto;
    }
}
