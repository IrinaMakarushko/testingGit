package com.exadel.borsch.service.impl;

import com.exadel.borsch.dao.OrderDao;
import com.exadel.borsch.domain.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: nik
 * Date: 05.08.13
 * Time: 17:35
 * To change this template use File | Settings | File Templates.
 */
@Service
public class OrderService {
    @Autowired
    private OrderDao orderDao;

    public List<Order> findOrdersOnWeek(String week){
        return orderDao.findOrdersOnWeek(week);
    }

}
