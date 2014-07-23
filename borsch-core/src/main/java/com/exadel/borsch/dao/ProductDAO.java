package com.exadel.borsch.dao;

import com.exadel.borsch.domain.Product;

/**
 * Created by srw on 8/8/13.
 */
public interface ProductDAO {
    public Product getProductByName(String name);
}
