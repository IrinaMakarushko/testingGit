package com.exadel.borsch.dao.impl;

import com.exadel.borsch.dao.ProductDAO;
import com.exadel.borsch.domain.Product;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by srw on 8/8/13.
 */
@Repository
public class JdbcProductDao extends JdbcGenericDao implements ProductDAO{
    @Override
    public Product getProductByName(String name) {
        String sql = "select * from product where name=\"" + name + "\"";
        try {
            return jdbc().queryForObject(sql, new ProductRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }

    }

    private static class ProductRowMapper implements RowMapper<Product> {

        @Override
        public Product mapRow(ResultSet resultSet, int i) throws SQLException {
            Product product = new Product();
                product.setName(resultSet.getString("name"));
                product.setDesription(resultSet.getString("description"));
                product.setProductId(resultSet.getLong("product_id"));
            return product;
        }
    }
}
