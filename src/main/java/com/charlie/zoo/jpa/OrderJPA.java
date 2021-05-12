package com.charlie.zoo.jpa;

import com.charlie.zoo.entity.OrderInfo;
import com.charlie.zoo.entity.Users;
import com.charlie.zoo.enums.StatusOfEntity;
import com.charlie.zoo.enums.StatusOfOrder;
import com.charlie.zoo.enums.StatusOfPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;


public interface OrderJPA extends JpaRepository<OrderInfo, UUID> {
    @Query("select obj from OrderInfo obj where obj.statusOfEntity=?1")
    List<OrderInfo> findByStatus(StatusOfEntity status);

    Set<OrderInfo> findByStatusOfOrderInAndStatusOfEntityAndCreatedById(List<StatusOfOrder> list,StatusOfEntity status,int userId);
    Set<OrderInfo> findByPaymentInAndStatusOfEntityAndCreatedById(List<StatusOfPayment> list,StatusOfEntity status, int userId);
    Set<OrderInfo> findByStatusOfOrderInAndPaymentInAndStatusOfEntityAndCreatedById(List<StatusOfOrder> list,List<StatusOfPayment> payment,StatusOfEntity status,int userId);

    Set<OrderInfo> findByStatusOfOrderInAndStatusOfEntity(List<StatusOfOrder> list,StatusOfEntity status);
    Set<OrderInfo> findByPaymentInAndStatusOfEntity(List<StatusOfPayment> list,StatusOfEntity status);
    Set<OrderInfo> findByStatusOfOrderInAndPaymentInAndStatusOfEntity(List<StatusOfOrder> list,List<StatusOfPayment> payment,StatusOfEntity status);


    List<OrderInfo> findByDateBetweenAndCreatedByIn(String from, String to, List<Users> createdBy);
    List<OrderInfo> findByDateBeforeAndCreatedByIn(String from,List<Users> createdBy);
}
