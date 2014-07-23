package com.exadel.borsch.dao.impl;

import com.exadel.borsch.dao.OrderDao;
import com.exadel.borsch.domain.Order;
import com.exadel.borsch.domain.User;
import com.exadel.borsch.utils.DataUtil;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: nik
 * Date: 05.08.13
 * Time: 14:59
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class JdbcOrderDao  extends JdbcGenericDao implements OrderDao {

    @Override
    public List<Order> findOrdersOnWeek(String week) {
        String sql = "SELECT user_order.user_order_id, name, paid, sum(price) " +
                "FROM user_order " +
                "JOIN user ON user_order.user_id = user.user_id " +
                "JOIN user_order_item ON user_order.user_order_id = user_order_item.user_order_id " +
                "JOIN menu_item ON user_order_item.menu_item_id = menu_item.menu_item_id " +
                "JOIN menu ON menu_item.menu_id = menu.menu_id " +
                "WHERE menu.first_day_of_week = ?";
        return jdbc().query(sql, new OrderRowMapper(),week);
    }

    private static class OrderRowMapper implements RowMapper<Order> {
        @Override
        public Order mapRow(ResultSet rs, int i) throws SQLException {
            Order order = new Order();
            order.setOrderId(rs.getInt("user_order_id"));
            order.setPaid(rs.getBoolean("paid"));
            order.setName(rs.getString("name"));
            order.setOrderCost(rs.getInt("SUM(price)"));
            return order;
        }
    }
}
