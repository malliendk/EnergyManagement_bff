package com.dillian.energymanagement_game_elements.service;

import com.dillian.energymanagement_game_elements.dto.apiDto.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
@AllArgsConstructor
public class ApiRetrieveServiceImpl implements ApiRetrieveService {

    private final WebClient webClient;

    @Override
    public LocalityDto getLocality(String localityName) {
        return webClient
                .get()
                .uri("/locality", localityName)
                .retrieve()
                .bodyToMono(LocalityDto.class)
                .block();
    }

    @Override
    public List<AccountDto> getAccountsByLocality(String localityName) {
        final List<AccountDto> accounts = webClient
                .get()
                .uri("/accounts/locality", localityName)
                .retrieve()
                .bodyToFlux(AccountDto.class)
                .collectList()
                .block();
        if (accounts == null || accounts.isEmpty()) {
            throw new RestClientException("Unable to retrieve Accounts by Locality");
        }
        return accounts;
    }

    @Override
    public SupervisorDto getSupervisorByLocality(String localityName) {
        return webClient
                .get()
                .uri("/supervisor/locality", localityName)
                .retrieve()
                .bodyToMono(SupervisorDto.class)
                .block();
    }

    @Override
    public List<LoadSourceDto> getSources() {
        final List<LoadSourceDto> sources = webClient
                .get()
                .uri("/source")
                .retrieve()
                .bodyToFlux(LoadSourceDto.class)
                .collectList()
                .block();
        if (sources == null || sources.isEmpty()) {
            throw new RestClientException("Unable to retrieve LoadSources");
        }
        return sources;
    }

    @Override
    public List<EventDto> getEvents() {
        final List<EventDto> events = webClient
                .get()
                .uri("/source")
                .retrieve()
                .bodyToFlux(EventDto.class)
                .collectList()
                .block();
        if (events == null || events.isEmpty()) {
            throw new RestClientException("Unable to retrieve Events");
        }
        return events;
    }
}
