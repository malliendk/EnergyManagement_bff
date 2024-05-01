package com.dillian.energymanagement_game_elements.dto.apiDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoadSourceDto {

    private Long id;
    private String name;
    private String description;
    private double gridLoad;
    private int price;
}
