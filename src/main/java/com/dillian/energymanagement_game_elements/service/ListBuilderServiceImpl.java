package com.dillian.energymanagement_game_elements.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

@Service
public class ListBuilderServiceImpl implements ListBuilderService {

    @Override
    public <S> List<S> addItemToList(S newItem, List<S> existingList) {
        return Stream.of(List.of(newItem), existingList)
                .flatMap(List::stream)
                .toList();
    }

    @Override
    public <S> List<S> combineLists(List<S> newItems, List<S> existingList) {
        return Stream.of(newItems, existingList)
                .flatMap(List::stream)
                .toList();
    }
}
