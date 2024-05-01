package com.dillian.energymanagement_game_elements.dto.gameDto;

import com.dillian.energymanagement_game_elements.dto.apiDto.AccountDto;
import com.dillian.energymanagement_game_elements.dto.apiDto.EventDto;
import com.dillian.energymanagement_game_elements.dto.apiDto.LoadSourceDto;
import com.dillian.energymanagement_game_elements.util.enums.DifficultyLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class GameDto {

    String localityName; //check
    double totalGridLoad; //check
    int amountOfStartingSources; //check; implemented by DifficultyLevel
    int funds; //check; starting funds implemented by DifficultyLevel
    int popularity;  //check; starting popularoty implementented by DifficultyLevel
    double distributionEfficiency; //check:
    List<LoadSourceDto> sources; //check
    List<AccountDto> accounts; //check
    List<EventDto> events; //check
    String supervisorName; //check: incoming with gameDto

    public GameDto() {

    }

    public GameDto(final DifficultyLevel difficultyLevel, final String localityName) {

    }
}
