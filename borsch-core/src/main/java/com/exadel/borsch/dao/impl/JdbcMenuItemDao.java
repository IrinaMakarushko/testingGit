package com.exadel.borsch.dao.impl;

import com.exadel.borsch.dao.MenuItemDAO;
import com.exadel.borsch.domain.MenuItem;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by srw on 8/7/13.
 */
@Repository
public class JdbcMenuItemDao extends JdbcGenericDao implements MenuItemDAO {

    @Override
    public List<MenuItem> getAllMenuItemsOfWeek(String firstDayOfWeek) {
        String sql = "select product.name, first_day_of_week, product.product_id, menu_item.menu_item_id, day, rank, menu.menu_id, price from product\n" +
                "join menu_item\n" +
                "on product.product_id = menu_item.product_id\n" +
                "join menu\n" +
                "on menu_item.menu_id = menu.menu_id\n" +
                "where first_day_of_week = \"" + firstDayOfWeek + "\"";
        return jdbc().query(sql, new MenuItemRowMapper());
    }

    @Override
    public void addMenuItemInGivenWeek(MenuItem menuItem) {
        String sql = "INSERT INTO menu_item (menu_item_id, product_id, day, rank, menu_id, price) VALUES (NULL, ?, ?, ?, ?, ?);";
            Object []args = {menuItem.getProductId(), menuItem.getDay(), menuItem.getRank(), menuItem.getMenuId(), menuItem.getPrice()};
        jdbc().update(sql, args);
    }

    private static class MenuItemRowMapper implements RowMapper<MenuItem> {

        @Override
        public MenuItem mapRow(ResultSet resultSet, int i) throws SQLException {
            MenuItem menuItem = new MenuItem();
                menuItem.setDay(resultSet.getInt("day"));
                menuItem.setMenuId(resultSet.getLong("menu_id"));
                menuItem.setMenuItemId(resultSet.getLong("menu_item_id"));
                menuItem.setPrice(resultSet.getInt("price"));
                menuItem.setProductId(resultSet.getLong("product_id"));
                menuItem.setRank(resultSet.getInt("rank"));
                menuItem.setName(resultSet.getString("name"));
            return menuItem;
        }
    }
}
