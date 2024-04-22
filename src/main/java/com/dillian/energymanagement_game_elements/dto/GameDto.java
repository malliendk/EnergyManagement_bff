package com.dillian.energymanagement_game_elements.dto;

import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GameDto {

    String localityName;
    double totalGridLoad;
    List<SourceDto> sources;
    List<AccountDto> accounts;
    List<EventDto> events;
    @Nullable
    String supervisorName;
    double distributionEfficiency;
}
