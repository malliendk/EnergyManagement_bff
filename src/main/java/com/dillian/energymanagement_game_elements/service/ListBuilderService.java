package com.dillian.energymanagement_game_elements.service;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ListBuilderService {
    <S> List<S> addItemToList(S newItem, List<S> existingList);

    <S> List<S> combineLists(List<S> newItems, List<S> existingList);
}
