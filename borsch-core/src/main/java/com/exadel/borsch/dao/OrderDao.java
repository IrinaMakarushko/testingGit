package com.exadel.borsch.dao;

import com.exadel.borsch.domain.Order;

import java.util.List;

public interface OrderDao {

    List<Order> findOrdersOnWeek(String week);
}
