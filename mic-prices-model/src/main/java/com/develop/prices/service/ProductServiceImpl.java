package com.develop.prices.service;

import com.develop.prices.mapper.ProductModelMapper;
import com.develop.prices.repository.ProductRepository;

public class ProductServiceImpl implements ProductService{

    private ProductRepository productRepository;

    private ProductModelMapper productModelMapper;


    public ProductServiceImpl(ProductRepository productRepository, ProductModelMapper productModelMapper){
        this.productModelMapper = productModelMapper;
        this.productRepository = productRepository;
    }


}
