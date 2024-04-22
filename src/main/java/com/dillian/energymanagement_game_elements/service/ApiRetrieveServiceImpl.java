package com.dillian.energymanagement_game_elements.service;

import com.dillian.energymanagement_game_elements.dto.AccountDto;
import com.dillian.energymanagement_game_elements.dto.EventDto;
import com.dillian.energymanagement_game_elements.dto.LocalityDto;
import com.dillian.energymanagement_game_elements.dto.SourceDto;
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
    public List<SourceDto> getSources() {
        final List<SourceDto> sources = restClient
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

}
