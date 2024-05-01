package com.dillian.energymanagement_game_elements.dto.gameDto;

import com.dillian.energymanagement_game_elements.dto.apiDto.LoadSourceDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SourcesGridLoadDto {

    private List<LoadSourceDto> sources;
    private double totalGridLoad;
    private double distributionEfficiency;

}
