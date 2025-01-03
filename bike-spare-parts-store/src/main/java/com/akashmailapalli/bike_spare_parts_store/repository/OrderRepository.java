package com.akashmailapalli.bike_spare_parts_store.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.akashmailapalli.bike_spare_parts_store.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long>{

    List<Order> findByUserId(Long userId);
}
