package com.dillian.energymanagement_game_elements.dto.apiDto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class LocalityDto {

    private String name;
    private String description;
    private String supervisorName;
    private int startingSourcesAmount;
    private List<AccountDto> accounts;
    private List<EventDto> events;
}
