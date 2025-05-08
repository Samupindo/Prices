package com.develop.prices.service;

import com.develop.prices.entity.ProductModel;
import com.develop.prices.exception.InstanceNotFoundException;
import com.develop.prices.mapper.ProductModelMapper;
import com.develop.prices.repository.ProductRepository;
import com.develop.prices.specification.ProductInShopSpecification;
import com.develop.prices.to.ProductNameTo;
import com.develop.prices.to.ProductTo;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final ProductModelMapper productModelMapper;


    public ProductServiceImpl(ProductRepository productRepository, ProductModelMapper productModelMapper) {
        this.productModelMapper = productModelMapper;
        this.productRepository = productRepository;
    }

    /**
     * AÑADIR LA EXCEPCION CREADA EN LA CAPA API-MODEL
     **/
    @Override
    public List<ProductTo> findAllProduct() {
        return productModelMapper.toProductTo(productRepository.findAll());
    }

    @Override
    public List<ProductTo> findAllWithFilters(String name, BigDecimal priceMin, BigDecimal priceMax) {

        Specification<ProductModel> spec = Specification.where(null);

        if (name != null && !name.isBlank()) {
            spec = spec.and(ProductInShopSpecification.hasName(name));
        }

        if (priceMin != null) {
            spec = spec.and(ProductInShopSpecification.hasPriceMin(priceMin));
        }

        if (priceMax != null) {
            spec = spec.and(ProductInShopSpecification.hasPriceMax(priceMax));
        }

        List<ProductModel> productModels = productRepository.findAll(spec);

        return productModelMapper.toProductTo(productModels);
    }

    @Override
    public ProductTo findByProductById(Integer productId) {
        ProductModel productModel = productRepository.findById(productId).orElse(null);
        return productModelMapper.toProductTo(productModel);
    }

    @Override
    public ProductTo saveProduct(ProductNameTo productNameTo) {
        ProductModel productModel = productModelMapper.toProductModel(productNameTo);
        productModel.setName(productNameTo.getName());

        ProductModel savedProduct = productRepository.save(productModel);

        return productModelMapper.toProductTo(savedProduct);
    }

    @Override
    public ProductTo updateProduct(Integer productId, ProductNameTo productNameTo) {
        ProductModel productModel = productRepository.findById(productId).orElse(null);
        if (productModel == null) {
            throw new InstanceNotFoundException();
        }
        productModel.setName(productNameTo.getName());

        return productModelMapper.toProductTo(productModel);
    }

    @Override
    public void deleteProduct(Integer productId) {
        ProductModel productModel = productRepository.findById(productId).orElse(null);
        if (productModel == null) {
            throw new InstanceNotFoundException();
        }

        productRepository.deleteById(productId);
    }
}
