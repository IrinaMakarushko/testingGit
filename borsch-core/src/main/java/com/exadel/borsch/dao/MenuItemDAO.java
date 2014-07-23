package com.exadel.borsch.dao;

import com.exadel.borsch.domain.MenuItem;

import java.util.List;

/**
 * Created by srw on 8/7/13.
 */
public interface MenuItemDAO {
    public List<MenuItem> getAllMenuItemsOfWeek(String firstDayOfWeek);
    public void addMenuItemInGivenWeek(MenuItem menuItem);
}
