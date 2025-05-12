
package com.develop.prices.service;

import com.develop.prices.entity.ProductInShopModel;
import com.develop.prices.entity.ProductModel;
import com.develop.prices.entity.ShopModel;
import com.develop.prices.exception.InstanceNotFoundException;
import com.develop.prices.mapper.ProductModelMapper;
import com.develop.prices.repository.ProductRepository;
import com.develop.prices.specification.ProductInShopSpecification;
import com.develop.prices.to.PageResponseTo;
import com.develop.prices.to.ProductWithShopsTo;
import com.develop.prices.to.ProductNameTo;
import com.develop.prices.to.ProductTo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductModelMapper productModelMapper;

    private ProductServiceImpl productService;

    @BeforeEach
    void setUp() {
        productService = new ProductServiceImpl(productRepository, productModelMapper);
    }

    @Test
    void findAllProductsWithFiltersNoFilters() {
        Pageable pageable = PageRequest.of(0, 10);
        ProductModel product1 = CreateProductWithShops(1, "Producto 1",
                List.of(createProductInShop(1, 1, new BigDecimal("10.00"))));
        ProductModel product2 = CreateProductWithShops(2, "Producto 2",
                List.of(createProductInShop(2, 2, new BigDecimal("20.00"))));

        List<ProductModel> productList = Arrays.asList(product1, product2);
        Page<ProductModel> productPage = new PageImpl<>(productList, pageable, productList.size());

        when(productRepository.findAll(any(Specification.class), eq(pageable))).thenReturn(productPage);

        PageResponseTo<ProductWithShopsTo> result = productService.findAllProductsWithFilters(null, null, null, pageable);

        assertEquals(2, result.getContent().size());
        assertEquals(2, result.getTotalElements());
        assertEquals(1, result.getTotalPages());
        assertEquals(1, result.getContent().getFirst().getProductId());
        assertEquals("Producto 1", result.getContent().getFirst().getName());
        assertEquals(1, result.getContent().getFirst().getShop().size());
    }

    @Test
    void findAllProductsWithFiltersName() {
        String nombreFiltro = "Producto 1";
        Pageable pageable = PageRequest.of(0, 10);
        ProductModel product1 = CreateProductWithShops(1, "Producto 1",
                List.of(createProductInShop(1, 1, new BigDecimal("10.00"))));

        List<ProductModel> productList = List.of(product1);
        Page<ProductModel> productPage = new PageImpl<>(productList, pageable, productList.size());

        when(productRepository.findAll(any(Specification.class), eq(pageable))).thenReturn(productPage);

        PageResponseTo<ProductWithShopsTo> result = productService.findAllProductsWithFilters(nombreFiltro, null, null, pageable);

        assertEquals(1, result.getContent().size());
        assertEquals(1, result.getTotalElements());
        assertEquals(1, result.getTotalPages());
        assertEquals("Producto 1", result.getContent().get(0).getName());
    }

    @Test
    void findAllProductsWithFiltersPrice() {
        Pageable pageable = PageRequest.of(0, 10);
        BigDecimal precioMin = new BigDecimal("15.00");
        BigDecimal precioMax = new BigDecimal("25.00");

        ProductModel product1 = CreateProductWithShops(1, "Producto 1",
                List.of(createProductInShop(1, 1, new BigDecimal("10.00"))));
        ProductModel product2 = CreateProductWithShops(2, "Producto 2",
                List.of(createProductInShop(2, 2, new BigDecimal("20.00"))));

        List<ProductModel> productList = Arrays.asList(product1, product2);
        Page<ProductModel> productPage = new PageImpl<>(productList, pageable, productList.size());

        when(productRepository.findAll(any(Specification.class), eq(pageable))).thenReturn(productPage);

        PageResponseTo<ProductWithShopsTo> result = productService.findAllProductsWithFilters(null, precioMin, precioMax, pageable);

        assertEquals(2, result.getContent().size());
        assertEquals(0, result.getContent().get(0).getShop().size());
        assertEquals(1, result.getContent().get(1).getShop().size());
    }

    @Test
    void findByProductById() {
        Integer productId = 1;
        ProductModel product = CreateProductWithShops(productId, "Producto 1",
                List.of(createProductInShop(1, 1, new BigDecimal("10.00"))));

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        ProductWithShopsTo result = productService.findByProductById(productId);

        assertEquals(productId, result.getProductId());
        assertEquals("Producto 1", result.getName());
        assertEquals(1, result.getShop().size());
        assertEquals(new BigDecimal("10.00"), result.getShop().get(0).getPrice());
    }

    @Test
    void findByProductByIdNotFound() {
        Integer productId = 99;
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(InstanceNotFoundException.class, () -> {
            productService.findByProductById(productId);
        });
    }

    @Test
    void saveProduct() {
        ProductNameTo productNameTo = new ProductNameTo("Nuevo Producto");
        ProductModel productModel = new ProductModel("Nuevo Producto");
        productModel.setProductId(1);

        ProductTo expectedProductTo = new ProductTo(
                productModel.getProductId(),
                productModel.getName());

        when(productModelMapper.toProductModel(any(ProductNameTo.class))).thenReturn(productModel);
        when(productRepository.save(any(ProductModel.class))).thenReturn(productModel);
        when(productModelMapper.toProductTo(any(ProductModel.class))).thenReturn(expectedProductTo);

        ProductTo result = productService.saveProduct(productNameTo);

        assertEquals(expectedProductTo.getProductId(), result.getProductId());
        assertEquals(expectedProductTo.getName(), result.getName());

        verify(productModelMapper).toProductModel(productNameTo);
        verify(productRepository).save(productModel);
        verify(productModelMapper).toProductTo(productModel);
    }

    @Test
    void updateProduct() {
        Integer productId = 1;
        ProductNameTo productNameTo = new ProductNameTo("Producto Actualizado");

        ProductModel productModel = new ProductModel("Producto Original");
        productModel.setProductId(productId);

        ProductTo expectedProductTo = new ProductTo(productId, "Producto Actualizado");

        when(productRepository.findById(productId)).thenReturn(Optional.of(productModel));
        when(productModelMapper.toProductTo(any(ProductModel.class))).thenReturn(expectedProductTo);

        ProductTo result = productService.updateProduct(productId, productNameTo);

        assertEquals(productId, result.getProductId());
        assertEquals("Producto Actualizado", result.getName());

        verify(productRepository).findById(productId);
        verify(productModelMapper).toProductTo(productModel);

        ArgumentCaptor<ProductModel> modelCaptor = ArgumentCaptor.forClass(ProductModel.class);
        verify(productModelMapper).toProductTo(modelCaptor.capture());
        assertEquals("Producto Actualizado", modelCaptor.getValue().getName());
    }

    @Test
    void updateProductNotFound() {
        Integer productId = 99;
        ProductNameTo productNameTo = new ProductNameTo("Producto Actualizado");

        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(InstanceNotFoundException.class, () -> {
            productService.updateProduct(productId, productNameTo);
        });
    }

    @Test
    void deleteProduct() {
        Integer productId = 1;
        ProductModel productModel = new ProductModel("Producto a Eliminar");
        productModel.setProductId(productId);

        when(productRepository.findById(productId)).thenReturn(Optional.of(productModel));

        productService.deleteProduct(productId);

        verify(productRepository).deleteById(productId);
    }

    @Test
    void deleteProductNotFound() {
        Integer productId = 99;
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(InstanceNotFoundException.class, () -> {
            productService.deleteProduct(productId);
        });

        verify(productRepository, never()).deleteById(anyInt());
    }

    private ProductModel CreateProductWithShops(Integer productId, String name, List<ProductInShopModel> prices) {
        ProductModel product = new ProductModel(name);
        product.setProductId(productId);

        for (ProductInShopModel price : prices) {
            price.setProduct(product);
        }

        try {
            java.lang.reflect.Field pricesField = ProductModel.class.getDeclaredField("prices");
            pricesField.setAccessible(true);
            pricesField.set(product, prices);
        } catch (Exception e) {
            throw new RuntimeException("Error al configurar los precios del producto", e);
        }

        return product;
    }

    private ProductInShopModel createProductInShop(Integer productInShopId, Integer shopId, BigDecimal price) {
        ProductInShopModel productInShop = new ProductInShopModel();
        productInShop.setProductInShopId(productInShopId);

        ShopModel shop = new ShopModel();
        shop.setShopId(shopId);

        productInShop.setShop(shop);
        productInShop.setPrice(price);

        return productInShop;
    }
}