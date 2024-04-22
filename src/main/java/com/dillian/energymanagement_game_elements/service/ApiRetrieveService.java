package com.dillian.energymanagement_game_elements.service;

import com.dillian.energymanagement_game_elements.dto.AccountDto;
import com.dillian.energymanagement_game_elements.dto.EventDto;
import com.dillian.energymanagement_game_elements.dto.LocalityDto;
import com.dillian.energymanagement_game_elements.dto.SourceDto;

import java.util.List;

public interface ApiRetrieveService {


    List<AccountDto> getAccountsByLocality(String localityName);


    List<SourceDto> getSources();

    List<EventDto> getEvents();
}
