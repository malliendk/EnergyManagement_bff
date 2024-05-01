package com.dillian.energymanagement_game_elements.service;

import com.dillian.energymanagement_game_elements.dto.apiDto.AccountDto;
import com.dillian.energymanagement_game_elements.dto.apiDto.EventDto;
import com.dillian.energymanagement_game_elements.dto.apiDto.LoadSourceDto;
import com.dillian.energymanagement_game_elements.dto.gameDto.GameDto;
import lombok.AllArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.util.List;

@Service
@AllArgsConstructor
public class ApiRetrieveServiceImpl implements ApiRetrieveService {

    private final RestClient restClient;
    public List<EventDto> events;



    @Override
    public List<AccountDto> getAccountsByLocality(String localityName) {
        final List<AccountDto> accounts = restClient
                .get()
                .uri("/accounts/locality", localityName)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});
        if (accounts == null || accounts.isEmpty()) {
            throw new RestClientException("Unable to retrieve Accounts by Locality");
        }
        return accounts;
    }

    @Override
    public List<LoadSourceDto> getSources() {
        final List<LoadSourceDto> sources = restClient
                .get()
                .uri("/source")
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});
        if (sources == null || sources.isEmpty()) {
            throw new RestClientException("Unable to retrieve LoadSources");
        }
        return sources;
    }

    @Override
    public List<EventDto> getEvents() {
        final List<EventDto> events = restClient
                .get()
                .uri("/source")
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });
        if (events == null || events.isEmpty()) {
            throw new RestClientException("Unable to retrieve Events");
        }
        return events;
    }

    @Override
    public GameDto getMoneyAndPopularityDto() {
        return restClient
                .get()
                .uri("http://localhost:8082/")
                .retrieve()
                .body(GameDto.class);
    }
}
