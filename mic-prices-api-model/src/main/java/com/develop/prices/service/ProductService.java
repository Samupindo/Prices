package com.develop.prices.service;

import com.develop.prices.dto.PageResponse;
import com.develop.prices.dto.ProductDTO;
import com.develop.prices.dto.ProductNameDTO;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {

    List<ProductDTO> findAllProduct();

    PageResponse<ProductDTO> findAllWithFilters(String name, BigDecimal priceMin, BigDecimal priceMax);

    ProductDTO findByProductById(Integer productId);

    ProductDTO saveProduct(ProductNameDTO productNameDTO);

    ProductDTO updateProduct(Integer productId, ProductNameDTO productNameDTO);

    void deleteProduct(Integer productId);
}
