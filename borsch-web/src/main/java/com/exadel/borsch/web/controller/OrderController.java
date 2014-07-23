package com.exadel.borsch.web.controller;

import com.exadel.borsch.domain.MenuItem;
import com.exadel.borsch.domain.Order;
import com.exadel.borsch.service.impl.OrderService;
import com.exadel.borsch.service.impl.UserService;
import com.exadel.borsch.utils.DataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


@Controller
@RequestMapping(value = "/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView headOrderRequest() {
        ModelAndView model = new ModelAndView("order");
        return model;
    }

    @RequestMapping(value = "/ajax/orders", method = RequestMethod.GET)
    public @ResponseBody List<Order> ordersAjax(@RequestParam String date) {
        return orderService.findOrdersOnWeek(date);
    }

}
