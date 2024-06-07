package com.dillian.energymanagement_game_elements.dto.gameDto;

import com.dillian.energymanagement_game_elements.util.enums.DifficultyLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class InitiateGameDto {

    private DifficultyLevel difficultyLevel;
    private String localityName;
    private double distributionEfficiency;
}
