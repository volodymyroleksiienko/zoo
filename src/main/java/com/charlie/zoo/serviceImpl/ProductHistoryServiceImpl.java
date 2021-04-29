package com.charlie.zoo.serviceImpl;

import com.charlie.zoo.entity.HistoryDetails;
import com.charlie.zoo.entity.ProductHistory;
import com.charlie.zoo.jpa.ProductHistoryJpa;
import com.charlie.zoo.service.ProductHistoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductHistoryServiceImpl implements ProductHistoryService {
    private final ProductHistoryJpa productHistoryJpa;

    @Override
    public ProductHistory save(ProductHistory productHistory) {
        return productHistoryJpa.save(productHistory);
    }

    @Override
    public ProductHistory update(ProductHistory history) {
        ProductHistory historyDB = findById(history.getId());
        if(historyDB!=null){
            historyDB.setProducer(history.getProducer());
            historyDB.setDate(history.getDate());
            countSummaryPrice(history.getId());
            return save(historyDB);
        }
        return null;
    }

    @Override
    public ProductHistory countSummaryPrice(int id) {
        ProductHistory history = findById(id);
        if(history!=null){
            double sum = 0;
            for(HistoryDetails details:history.getHistoryDetails()){
                sum+= details.getCount()*details.getPrice();
            }
            history.setSum(sum);
            return save(history);
        }
        return null;
    }

    @Override
    public ProductHistory findById(int id) {
        return productHistoryJpa.findById(id).orElse(null);
    }

    @Override
    public List<ProductHistory> findAll() {
        return productHistoryJpa.findAll();
    }

    @Override
    public void deleteByID(int id) {
        productHistoryJpa.deleteById(id);
    }
}
