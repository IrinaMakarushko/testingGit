package com.exadel.borsch.web.controller;

import com.exadel.borsch.domain.MenuItem;
import com.exadel.borsch.service.impl.MenuService;
import com.exadel.borsch.utils.DataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by srw on 8/7/13.
 */

@Controller
@RequestMapping("/menu")
public class MenuController {
    @Autowired
    private MenuService menuService;

    @RequestMapping(value = "ajax/showCurrentWeek", method = RequestMethod.GET)
    public @ResponseBody String showCurrentWeekAjax(Integer weekDeviation) {
        return DataUtil.getInstance().getMondayOfWeek(weekDeviation);
    }

    @RequestMapping(value = "/ajax/menuItems", method = RequestMethod.GET)
    public @ResponseBody List<MenuItem> menuItemsAjax(Integer weekDeviation) {
        return menuService.getWeekMenu(weekDeviation);
    }

    @RequestMapping(value = "ajax/addMenuItem", method = RequestMethod.POST)
    public String addMenuItemAjax(Integer weekDeviation, String dishName,
                                Integer price, Integer day) {
        //VALIDATION MUST
        String err = menuService.addMenuItem(weekDeviation, dishName, price, day);
        System.err.println(err);
        return err;
    }



    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getMenuPage() {
        ModelAndView model = new ModelAndView("menu");
        return model;
    }




}
