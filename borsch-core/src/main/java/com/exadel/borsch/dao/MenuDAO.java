package com.exadel.borsch.dao;

import com.exadel.borsch.domain.Menu;
import com.exadel.borsch.domain.MenuItem;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by srw on 8/1/13.
 */

public interface MenuDAO {
    public List<Menu> getAllMenus();
    public Menu getMenuForGivenWeek(String firstDayOfWeek);
    public void addMenu(String firstDayOfWeek);
}
