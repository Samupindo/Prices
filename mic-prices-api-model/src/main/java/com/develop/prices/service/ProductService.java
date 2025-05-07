package com.develop.prices.service;

import com.develop.prices.dto.PageResponse;
import com.develop.prices.dto.ProductDTO;
import com.develop.prices.dto.ProductNameDTO;
import com.develop.prices.to.ProductNameTo;
import com.develop.prices.to.ProductTo;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {

    List<ProductTo> findAllProduct();

    List<ProductTo> findAllWithFilters(String name, BigDecimal priceMin, BigDecimal priceMax);

    ProductTo findByProductById(Integer productId);

    ProductTo saveProduct(ProductNameTo productNameTo);

    ProductTo updateProduct(Integer productId, ProductNameTo productNameTo);

    void deleteProduct(Integer productId);
}
