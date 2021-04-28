package com.charlie.zoo.service;

import com.charlie.zoo.entity.HistoryDetails;

import java.util.List;

public interface HistoryDetailsService {
    HistoryDetails save(HistoryDetails details);
    HistoryDetails update(int historyId,HistoryDetails details);
    HistoryDetails findById(int id);
    List<HistoryDetails> findAll();
    void deleteByID(int id);
}
