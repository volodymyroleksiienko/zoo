package com.charlie.zoo.serviceImpl;

import com.charlie.zoo.entity.HistoryDetails;
import com.charlie.zoo.entity.ProductHistory;
import com.charlie.zoo.jpa.HistoryDetailsJpa;
import com.charlie.zoo.service.HistoryDetailsService;
import com.charlie.zoo.service.ProductHistoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class HistoryDetailsServiceImpl implements HistoryDetailsService {
    private final HistoryDetailsJpa historyDetailsJpa;
    private final ProductHistoryService productHistoryService;


    @Override
    public HistoryDetails save(HistoryDetails details) {
        HistoryDetails detailsDB = historyDetailsJpa.save(details);
        if(detailsDB.getProductHistory()!=null) {
            productHistoryService.countSummaryPrice(detailsDB.getProductHistory().getId());
        }
        return detailsDB;
    }

    @Override
    public HistoryDetails update(int historyId,HistoryDetails details) {
        HistoryDetails detailsDB = findById(details.getId());
        if(detailsDB!=null){
            detailsDB.setCount(details.getCount());
            detailsDB.setPrice(details.getPrice());
            detailsDB.setSum(details.getCount()*details.getPrice());
            detailsDB = save(detailsDB);
            productHistoryService.countSummaryPrice(historyId);
            return detailsDB;

        }
        return null;
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
    @Transactional
    public void deleteByID(int id) {
        historyDetailsJpa.deleteById(id);
    }
}
