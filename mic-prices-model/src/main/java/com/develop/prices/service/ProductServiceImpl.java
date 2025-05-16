package com.develop.prices.service;

import com.develop.prices.entity.ProductInShopModel;
import com.develop.prices.entity.ProductModel;
import com.develop.prices.exception.InstanceNotFoundException;
import com.develop.prices.mapper.ProductModelMapper;
import com.develop.prices.repository.ProductRepository;
import com.develop.prices.specification.ProductInShopSpecification;
import com.develop.prices.to.PageResponseTo;
import com.develop.prices.to.ProductNameTo;
import com.develop.prices.to.ProductTo;
import com.develop.prices.to.ProductWithShopsTo;
import com.develop.prices.to.ShopInfoTo;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final ProductModelMapper productModelMapper;


    public ProductServiceImpl(ProductRepository productRepository, ProductModelMapper productModelMapper) {
        this.productModelMapper = productModelMapper;
        this.productRepository = productRepository;
    }


    @Override
    public PageResponseTo<ProductWithShopsTo> findAllProductsWithFilters(String name, BigDecimal priceMin, BigDecimal priceMax, Pageable pageable) {

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

        Page<ProductModel> productModels = productRepository.findAll(spec,pageable);
        List<ProductWithShopsTo> productWithShopsTos = new ArrayList<>();

        for (ProductModel productModel : productModels.getContent()){
            List<ShopInfoTo> shopInfoTos = new ArrayList<>();

            if(productModel.getPrices() != null){
                for (ProductInShopModel productInShopModel : productModel.getPrices()){
                    if ((priceMin == null || productInShopModel.getPrice().compareTo(priceMin) >= 0) &&
                            (priceMax == null || productInShopModel.getPrice().compareTo(priceMax) <= 0)) {

                        shopInfoTos.add(new ShopInfoTo(productInShopModel.getProductInShopId(),productInShopModel.getShop().getShopId(), productInShopModel.getPrice()));
                    }
                }
            }
            productWithShopsTos.add(new ProductWithShopsTo(
                    productModel.getProductId(),
                    productModel.getName(),
                    shopInfoTos
            ));
        }



        return new PageResponseTo<>(
                productWithShopsTos,
                productModels.getTotalElements(),
                productModels.getTotalPages());
    }

    @Override
    public ProductWithShopsTo findByProductById(Integer productId) {
        Optional<ProductModel> optionalProductModel = productRepository.findById(productId);
        if(optionalProductModel.isEmpty()){
            throw new InstanceNotFoundException();
        }

        ProductModel productModel = optionalProductModel.get();

        ProductWithShopsTo productWithShopsTo = new ProductWithShopsTo();
        productWithShopsTo.setProductId(productModel.getProductId());
        productWithShopsTo.setName(productModel.getName());

        List<ShopInfoTo> shopInfoToList = new ArrayList<>();

        for (ProductInShopModel productInShopModel : productModel.getPrices()){
            shopInfoToList.add(new ShopInfoTo(productInShopModel.getProductInShopId(), productInShopModel.getShop().getShopId(), productInShopModel.getPrice()));
        }

        productWithShopsTo.setShop(shopInfoToList);

        return productWithShopsTo;
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
