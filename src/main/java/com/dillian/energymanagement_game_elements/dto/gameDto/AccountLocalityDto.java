package com.dillian.energymanagement_game_elements.dto.gameDto;

import com.dillian.energymanagement_game_elements.dto.apiDto.AccountDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AccountLocalityDto {

    private List<AccountDto> accounts;
    private double totalGridLoad;
    private String localityName;

    public AccountLocalityDto(final List<AccountDto> initialAccounts, final String localityName) {

    }
}
