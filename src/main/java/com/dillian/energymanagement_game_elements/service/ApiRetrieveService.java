package com.dillian.energymanagement_game_elements.service;

import com.dillian.energymanagement_game_elements.dto.apiDto.AccountDto;
import com.dillian.energymanagement_game_elements.dto.apiDto.EventDto;
import com.dillian.energymanagement_game_elements.dto.apiDto.LoadSourceDto;
import com.dillian.energymanagement_game_elements.dto.gameDto.GameDto;

import java.util.List;

public interface ApiRetrieveService {


    List<AccountDto> getAccountsByLocality(String localityName);


    List<LoadSourceDto> getSources();

    List<EventDto> getEvents();

    GameDto getMoneyAndPopularityDto();
}
