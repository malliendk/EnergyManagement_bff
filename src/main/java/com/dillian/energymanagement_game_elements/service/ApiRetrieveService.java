package com.dillian.energymanagement_game_elements.service;

import com.dillian.energymanagement_game_elements.dto.apiDto.*;

import java.util.List;

public interface ApiRetrieveService {

    LocalityDto getLocality(String localityName);

    SupervisorDto getSupervisorByLocality(String localityName);

    List<AccountDto> getAccountsByLocality(String localityName);

    List<LoadSourceDto> getSources();

    List<EventDto> getEvents();

}
