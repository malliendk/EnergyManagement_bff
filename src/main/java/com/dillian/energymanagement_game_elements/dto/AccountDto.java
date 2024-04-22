package com.dillian.energymanagement_game_elements.dto;


import com.dillian.energymanagement_game_elements.util.enums.SupplyType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountDto {

    SupplyType supplyType;
    double supplyAmount;
    String localityName;
    String distributorName;
}
