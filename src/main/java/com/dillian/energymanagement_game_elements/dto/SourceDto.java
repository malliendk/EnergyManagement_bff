package com.dillian.energymanagement_game_elements.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SourceDto {

    Long id;
    String name;
    String description;
    double gridLoad;
}
