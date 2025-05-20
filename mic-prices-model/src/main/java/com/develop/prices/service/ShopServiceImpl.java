package com.develop.prices.service;

import com.develop.prices.entity.ProductInShopModel;
import com.develop.prices.entity.ProductModel;
import com.develop.prices.entity.ShopModel;
import com.develop.prices.exception.ConflictException;
import com.develop.prices.exception.InstanceNotFoundException;
import com.develop.prices.mapper.ProductInShopModelMapper;
import com.develop.prices.mapper.ShopModelMapper;
import com.develop.prices.repository.ProductInShopRepository;
import com.develop.prices.repository.ProductRepository;
import com.develop.prices.repository.ShopLocationRepository;
import com.develop.prices.specification.ShopsSpecification;
import com.develop.prices.to.AddProductShopTo;
import com.develop.prices.to.PageResponseTo;
import com.develop.prices.to.ProductInShopPatchTo;
import com.develop.prices.to.ProductInShopTo;
import com.develop.prices.to.ShopAddTo;
import com.develop.prices.to.ShopPutTo;
import com.develop.prices.to.ShopTo;
import com.develop.prices.to.UpdateShopTo;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ShopServiceImpl implements ShopService {

  private final ProductInShopRepository productInShopRepository;

  private final ShopLocationRepository shopLocationRepository;

  private final ShopModelMapper shopModelMapper;

  private final ProductInShopModelMapper productInShopModelMapper;

  private final ProductRepository productRepository;

  public ShopServiceImpl(
      ProductInShopRepository productInShopRepository,
      ShopLocationRepository shopLocationRepository,
      ShopModelMapper shopModelMapper,
      ProductInShopModelMapper productInShopModelMapper,
      ProductRepository productRepository) {
    this.productInShopRepository = productInShopRepository;
    this.shopLocationRepository = shopLocationRepository;
    this.shopModelMapper = shopModelMapper;
    this.productInShopModelMapper = productInShopModelMapper;
    this.productRepository = productRepository;
  }

  @Override
  public PageResponseTo<ShopTo> findAllShopWithFilters(
      String country, String city, String address, Pageable pageable) {
    Specification<ShopModel> spec = Specification.where(null);

    if (country != null) {
      spec = spec.and(ShopsSpecification.findByCountry(country));
    }

    if (city != null) {
      spec = spec.and(ShopsSpecification.findByCity(city));
    }

    if (address != null) {
      spec = spec.and(ShopsSpecification.findByAddress(address));
    }
    Page<ShopModel> shopModels = shopLocationRepository.findAll(spec, pageable);

    List<ShopTo> shopTo = shopModels.getContent().stream().map(shopModelMapper::toShopTo).toList();

    return new PageResponseTo<>(shopTo, shopModels.getTotalElements(), shopModels.getTotalPages());
  }

  @Override
  public ShopTo findShopById(Integer shopId) {
    ShopModel shopModel = shopLocationRepository.findById(shopId).orElse(null);

    return shopModelMapper.toShopTo(shopModel);
  }

  @Override
  public ShopTo saveShop(ShopAddTo shopAddTo) {
    ShopModel shopModel = shopModelMapper.toShopModel(shopAddTo);

    shopModel.setCountry(shopAddTo.getCountry());
    shopModel.setCity(shopAddTo.getCity());
    shopModel.setAddress(shopAddTo.getAddress());

    ShopModel savedShop = shopLocationRepository.save(shopModel);

    return shopModelMapper.toShopTo(savedShop);
  }

  @Override
  public ProductInShopTo addProductToShop(
      Integer productId, Integer shopId, AddProductShopTo addProductShopTo) {
    Optional<ProductModel> optionalProductModel = productRepository.findById(productId);
    Optional<ShopModel> optionalShopModel = shopLocationRepository.findById(shopId);

    if (optionalShopModel.isEmpty() || optionalProductModel.isEmpty()) {
      throw new InstanceNotFoundException();
    }
    if (productInShopRepository
        .findByShop_ShopIdAndProduct_ProductId(shopId, productId)
        .isPresent()) {
      throw new ConflictException();
    }

    BigDecimal price = addProductShopTo.getPrice();

    ProductInShopModel productInShopModel = new ProductInShopModel();
    productInShopModel.setProduct(optionalProductModel.get());
    productInShopModel.setShop(optionalShopModel.get());
    productInShopModel.setPrice(price);

    ProductInShopModel productInShopModelDb = productInShopRepository.save(productInShopModel);

    return productInShopModelMapper.toProductInShopTo(productInShopModelDb);
  }

  @Override
  public ShopTo updateShop(Integer shopId, ShopPutTo shopPutTo) {
    Optional<ShopModel> optionalShopModel = shopLocationRepository.findById(shopId);

    if (optionalShopModel.isEmpty()) {
      throw new InstanceNotFoundException();
    }

    ShopModel shopModel = optionalShopModel.get();
    shopModel.setCountry(shopPutTo.getCountry());
    shopModel.setCity(shopPutTo.getCity());
    shopModel.setAddress(shopPutTo.getAddress());

    return shopModelMapper.toShopTo(shopModel);
  }

  @Override
  public ShopTo partialUpdateShop(Integer shopId, UpdateShopTo updateShopTo) {
    Optional<ShopModel> optionalShopModel = shopLocationRepository.findById(shopId);

    if (optionalShopModel.isEmpty()) {
      throw new InstanceNotFoundException();
    }
    ShopModel shopModel = optionalShopModel.get();

    if (updateShopTo.getCountry() != null) {
      shopModel.setCountry(updateShopTo.getCountry());
    }
    if (updateShopTo.getCity() != null) {
      shopModel.setCity(updateShopTo.getCity());
    }
    if (updateShopTo.getAddress() != null) {
      shopModel.setAddress(updateShopTo.getAddress());
    }

    return shopModelMapper.toShopTo(shopModel);
  }

  @Override
  public ProductInShopTo updateProductPriceInShop(
      Integer shopId, Integer productId, ProductInShopPatchTo productInShopPatchTo) {
    ProductInShopModel productInShopModel =
        productInShopRepository
            .findByShop_ShopIdAndProduct_ProductId(shopId, productId)
            .orElse(null);
    if (productInShopModel == null) {
      throw new InstanceNotFoundException();
    }

    BigDecimal price = productInShopPatchTo.getPrice();

    productInShopModel.setPrice(price);

    ProductInShopModel savedPriceModel = productInShopRepository.save(productInShopModel);

    return productInShopModelMapper.toProductInShopTo(savedPriceModel);
  }

  @Override
  public void deleteShop(Integer shopId) {
    Optional<ShopModel> optionalShopModel = shopLocationRepository.findById(shopId);

    if (optionalShopModel.isEmpty()) {
      throw new InstanceNotFoundException();
    }

    shopLocationRepository.deleteById(shopId);
  }

  @Override
  public void deleteProductFromShop(Integer shopId, Integer productId) {
    ProductInShopModel productInShopModel =
        productInShopRepository
            .findByShop_ShopIdAndProduct_ProductId(shopId, productId)
            .orElse(null);
    if (productInShopModel == null) {
      throw new InstanceNotFoundException();
    }

    shopLocationRepository.deleteById(shopId);
  }
}
