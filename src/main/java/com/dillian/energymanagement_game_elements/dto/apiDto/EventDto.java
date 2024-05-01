package com.dillian.energymanagement_game_elements.dto.apiDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventDto {

    Long id;
    String name;
    String description;
    LoadSourceDto source;
}
