package com.exadel.borsch.dao.impl;

import com.exadel.borsch.dao.MenuDAO;
import com.exadel.borsch.domain.Menu;
import com.exadel.borsch.domain.MenuItem;
import com.exadel.borsch.domain.User;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * Created by srw on 8/1/13.
 */
@Repository
public class JdbcMenuDao extends JdbcGenericDao implements MenuDAO {

    @Override
    public List<Menu> getAllMenus() {
        String sql = "select * from menu";
        return jdbc().query(sql, new MenuRowMapper());
    }

    @Override
    public Menu getMenuForGivenWeek(String firstDayOfWeek) {
        String sql = "select * from menu where first_day_of_week = \"" + firstDayOfWeek + "\"";
        try {
            return jdbc().queryForObject(sql, new MenuRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public void addMenu(String firstDayOfWeek) {
        String sql = "INSERT INTO menu (menu_id, first_day_of_week, status) VALUES (NULL, ?, 0);";
        Object []args = {firstDayOfWeek};
        jdbc().update(sql, args);
    }

    private static class MenuRowMapper implements RowMapper<Menu> {
        @Override
        public Menu mapRow(ResultSet resultSet, int i) throws SQLException {
            Menu menu = new Menu();
                //filling fields
                menu.setId(resultSet.getLong("menu_id"));
                menu.setFirst_day_of_week(resultSet.getDate("first_day_of_week"));
                menu.setStatus(resultSet.getBoolean("status"));
            return menu;
        }
    }

}
