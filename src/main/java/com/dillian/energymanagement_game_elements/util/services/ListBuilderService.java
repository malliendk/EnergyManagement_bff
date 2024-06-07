package com.dillian.energymanagement_game_elements.util.services;

import java.util.List;

public interface ListBuilderService {
    <S> List<S> addItemToList(S newItem, List<S> existingList);

    <S> List<S> combineLists(List<S> newItems, List<S> existingList);
}
