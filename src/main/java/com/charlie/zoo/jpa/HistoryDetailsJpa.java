package com.charlie.zoo.jpa;

import com.charlie.zoo.entity.HistoryDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoryDetailsJpa extends JpaRepository<HistoryDetails,Integer> {
}
