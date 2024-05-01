package com.dillian.energymanagement_game_elements.dto.gameDto;


import com.dillian.energymanagement_game_elements.dto.apiDto.LoadSourceDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class EventDto {

    private List<com.dillian.energymanagement_game_elements.dto.apiDto.EventDto> events;
    private List<LoadSourceDto> loadSources;
    private double totalGridLoad;
}
