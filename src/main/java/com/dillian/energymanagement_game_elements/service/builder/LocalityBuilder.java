package com.dillian.energymanagement_game_elements.service.builder;

import com.dillian.energymanagement_game_elements.dto.apiDto.LocalityDto;
import com.dillian.energymanagement_game_elements.service.ApiRetrieveService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LocalityBuilder {

    private final ApiRetrieveService apiRetrieveService;

    public LocalityDto getLocality(String localityName) {
        return apiRetrieveService.getLocality(localityName);
    }
}
