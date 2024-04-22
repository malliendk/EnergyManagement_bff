package com.dillian.energymanagement_game_elements.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class LocalityDto {

    String name;
    String description;
    String supervisorName;
    List<AccountDto> accounts;
    List<EventDto> events;
}
