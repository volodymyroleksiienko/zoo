package com.charlie.zoo.jpa;

import com.charlie.zoo.entity.OrderInfo;
import com.charlie.zoo.enums.StatusOfEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface OrderJPA extends JpaRepository<OrderInfo, UUID> {
    @Query("select obj from OrderInfo obj where obj.statusOfEntity=?1")
    List<OrderInfo> findByStatus(StatusOfEntity status);
}
