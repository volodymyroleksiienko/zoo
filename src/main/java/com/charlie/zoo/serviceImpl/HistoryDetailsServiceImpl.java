package com.charlie.zoo.serviceImpl;

import com.charlie.zoo.entity.HistoryDetails;
import com.charlie.zoo.jpa.HistoryDetailsJpa;
import com.charlie.zoo.service.HistoryDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class HistoryDetailsServiceImpl implements HistoryDetailsService {
    private final HistoryDetailsJpa historyDetailsJpa;

    @Override
    public HistoryDetails save(HistoryDetails details) {
        return historyDetailsJpa.save(details);
    }

    @Override
    public HistoryDetails findById(int id) {
        return historyDetailsJpa.findById(id).orElse(null);
    }

    @Override
    public List<HistoryDetails> findAll() {
        return historyDetailsJpa.findAll();
    }

    @Override
    public void deleteByID(int id) {
        historyDetailsJpa.findById(id);
    }
}
