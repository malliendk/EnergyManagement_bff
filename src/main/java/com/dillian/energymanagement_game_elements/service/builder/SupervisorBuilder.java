package com.dillian.energymanagement_game_elements.service.builder;

import com.dillian.energymanagement_game_elements.dto.apiDto.SupervisorDto;
import com.dillian.energymanagement_game_elements.service.ApiRetrieveService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SupervisorBuilder {

    private final ApiRetrieveService apiRetrieveService;

    public SupervisorDto getSupervisor(String localityName) {
        return apiRetrieveService.getSupervisorByLocality(localityName);
    }
}
