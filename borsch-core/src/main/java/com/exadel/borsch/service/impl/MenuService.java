package com.exadel.borsch.service.impl;

import com.exadel.borsch.dao.MenuDAO;
import com.exadel.borsch.dao.MenuItemDAO;
import com.exadel.borsch.dao.ProductDAO;
import com.exadel.borsch.domain.Menu;
import com.exadel.borsch.domain.MenuItem;
import com.exadel.borsch.domain.Product;
import com.exadel.borsch.utils.DataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by srw on 8/2/13.
 */
@Service
public class MenuService {
    @Autowired
    private MenuDAO menuDAO;

    @Autowired
    private MenuItemDAO menuItemDAO;

    @Autowired
    private ProductDAO productDAO;

    public List<Menu> getAllMenus() {
        return menuDAO.getAllMenus();
    }

    public List<MenuItem> getAllMenuItemsForGivenWeek() {
        return null;
    }

    public List<MenuItem> getWeekMenu(Integer weekDeviation) {
        String firstDayOFWeek = DataUtil.getInstance().getMondayOfWeek(weekDeviation);
        return menuItemDAO.getAllMenuItemsOfWeek(firstDayOFWeek);
    }

    public String addMenuItem(Integer weekDeviation, String dishName,
                       Integer price, Integer day) {

        String firstDayOfWeek = DataUtil.getInstance().getMondayOfWeek(weekDeviation);
        Menu menuWhereToAdd = menuDAO.getMenuForGivenWeek(firstDayOfWeek);
        Product product =  productDAO.getProductByName(dishName);
        if(product == null) {
            return "Product is not found.";
        }
        if(menuWhereToAdd == null) {
            menuDAO.addMenu(firstDayOfWeek);
        }
        MenuItem menuItem = new MenuItem();
        menuItem.setProductId(product.getProductId());
        menuItem.setMenuId(menuWhereToAdd.getId());
        menuItem.setPrice(price);
        menuItem.setRank(0);
        menuItem.setDay(day);

        menuItemDAO.addMenuItemInGivenWeek(menuItem);
        return "";
    }

}
