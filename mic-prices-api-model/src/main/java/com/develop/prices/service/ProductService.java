package com.develop.prices.service;

import com.develop.prices.to.PageResponseTo;
import com.develop.prices.to.ProductNameTo;
import com.develop.prices.to.ProductTo;
import com.develop.prices.to.ProductWithShopsTo;
import org.springframework.data.domain.Pageable;
import java.math.BigDecimal;

public interface ProductService {

    PageResponseTo<ProductWithShopsTo> findAllProductsWithFilters(String name, BigDecimal priceMin, BigDecimal priceMax, Pageable pageable);

    ProductWithShopsTo findByProductById(Integer productId);

    ProductTo saveProduct(ProductNameTo productNameTo);

    ProductTo updateProduct(Integer productId, ProductNameTo productNameTo);

    void deleteProduct(Integer productId);
}
