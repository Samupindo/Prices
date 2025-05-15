package com.develop.prices.rest;

import com.develop.prices.dto.PageResponseDTO;
import com.develop.prices.dto.ProductDTO;
import com.develop.prices.dto.ProductNameDTO;
import com.develop.prices.dto.ProductWithShopsDTO;
import com.develop.prices.exception.InstanceNotFoundException;
import com.develop.prices.mapper.ProductRestMapper;
import com.develop.prices.service.ProductService;
import com.develop.prices.to.PageResponseTo;
import com.develop.prices.to.ProductNameTo;
import com.develop.prices.to.ProductTo;
import com.develop.prices.to.ProductWithShopsTo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @Mock
    private ProductService productService;

    @Mock
    private ProductRestMapper productRestMapper;

    private ProductController productController;

    @BeforeEach
    void setUp() {
        productController = new ProductController(productService, productRestMapper);
    }


    @Test
    void getProductsWithFilters() {
        Integer productId = 1;
        String name = "name";
        BigDecimal totalPriceMax = new BigDecimal("200.00");
        BigDecimal totalPriceMin = new BigDecimal("50.00");

        Pageable pageable = PageRequest.of(0, 10, Sort.Direction.ASC, "purchaseId");


        ProductWithShopsTo productWithShopsTo = new ProductWithShopsTo();
        productWithShopsTo.setProductId(1);
        productWithShopsTo.setName("name");

        List<ProductWithShopsTo> productToList = new ArrayList<>();
        productToList.add(productWithShopsTo);

        PageResponseTo<ProductWithShopsTo> pageResponseTo = new PageResponseTo<>(
                productToList,
                productToList.size(),
                1
        );

        ProductWithShopsDTO productWithShopsDTO = new ProductWithShopsDTO();
        productWithShopsDTO.setProductId(1);
        productWithShopsDTO.setName("name");

        when(productService.findAllProductsWithFilters(eq(name), eq(totalPriceMin), eq(totalPriceMax), eq(pageable)))
                .thenReturn(pageResponseTo);
        when(productRestMapper.toProductWithShopsDTO(any(ProductWithShopsTo.class))).thenReturn(productWithShopsDTO);

        ResponseEntity<PageResponseDTO<ProductWithShopsDTO>> response = productController.getProductsWithFilters(
                name, totalPriceMin, totalPriceMax, pageable);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        PageResponseDTO<ProductWithShopsDTO> body = response.getBody();
        assertNotNull(body);
        assertEquals(1, body.getContent().size());

    }


    @Test
    void getProductsWithoutFilters() {
        Integer productId = 1;
        String name = null;
        BigDecimal totalPriceMax = null;
        BigDecimal totalPriceMin = null;
        Pageable pageable = PageRequest.of(0, 10, Sort.Direction.ASC, "purchaseId");

        ProductWithShopsTo productWithShopsTo = new ProductWithShopsTo();
        productWithShopsTo.setProductId(1);
        productWithShopsTo.setName("name");

        List<ProductWithShopsTo> productToList = new ArrayList<>();
        productToList.add(productWithShopsTo);

        PageResponseTo<ProductWithShopsTo> pageResponseTo = new PageResponseTo<>(
                productToList,
                productToList.size(),
                1
        );

        ProductWithShopsDTO productWithShopsDTO = new ProductWithShopsDTO();
        productWithShopsDTO.setProductId(1);
        productWithShopsDTO.setName("name");

        when(productService.findAllProductsWithFilters(eq(name), eq(totalPriceMin), eq(totalPriceMax), eq(pageable)))
                .thenReturn(pageResponseTo);
        when(productRestMapper.toProductWithShopsDTO(any(ProductWithShopsTo.class))).thenReturn(productWithShopsDTO);

        ResponseEntity<PageResponseDTO<ProductWithShopsDTO>> response = productController.getProductsWithFilters(
                name, totalPriceMin, totalPriceMax, pageable);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        PageResponseDTO<ProductWithShopsDTO> body = response.getBody();
        assertNotNull(body);
        assertEquals(1, body.getContent().size());

    }
    @Test
    void getProductById() {
        Integer productId = 1;

        ProductWithShopsTo productWithShopsTo = new ProductWithShopsTo();
        productWithShopsTo.setProductId(productId);
        productWithShopsTo.setName("name");

        ProductWithShopsDTO productWithShopsDto = new ProductWithShopsDTO();
        productWithShopsDto.setProductId(productId);
        productWithShopsDto.setName("name");


        when(productService.findByProductById(eq(productId))).thenReturn(productWithShopsTo);
        when(productRestMapper.toProductWithShopsDTO(productWithShopsTo)).thenReturn(productWithShopsDto);

        ResponseEntity<ProductWithShopsDTO> response = productController.getProductById(productId);
    }

    @Test
    void getProductByIdNotFound() {
        Integer productId = 99;

        when(productService.findByProductById(eq(productId))).thenThrow(new InstanceNotFoundException());


        assertThrows(InstanceNotFoundException.class, () -> {
            productController.getProductById(productId);
        });

        verify(productService).findByProductById(eq(productId));

    }

    @Test
    void addProduct() {
        String name = "name";

        ProductNameTo productNameTo = new ProductNameTo();
        productNameTo.setName("name");

        ProductNameDTO productNameDTO = new ProductNameDTO();
        productNameDTO.setName("name");

        ProductDTO productDTO = new ProductDTO();
        productDTO.setProductId(1);
        productDTO.setName("name");

        ProductTo productTo = new ProductTo();
        productTo.setProductId(1);
        productTo.setName("name");

        when(productRestMapper.toProductNameTo(productNameDTO)).thenReturn(productNameTo);


        when(productService.saveProduct(productNameTo)).thenReturn(productTo);
        when(productRestMapper.toProductDTO(productTo)).thenReturn(productDTO);

        ResponseEntity<ProductDTO> response = productController.addProduct(productNameDTO);



    }

    @Test
    void addProductWithInvalidName() {
        String invalidName = "";

        ProductNameDTO invalidProductNameDTO = new ProductNameDTO();
        invalidProductNameDTO.setName(invalidName);


        ProductNameTo productNameTo = new ProductNameTo();
        productNameTo.setName(invalidName);

        when(productRestMapper.toProductNameTo(eq(invalidProductNameDTO))).thenReturn(productNameTo);
        when(productService.saveProduct(eq(productNameTo)))
                .thenThrow(new IllegalArgumentException());

        assertThrows(IllegalArgumentException.class, () -> {
            productController.addProduct(invalidProductNameDTO);
        });



        verify(productRestMapper).toProductNameTo(eq(invalidProductNameDTO));
        verify(productService).saveProduct(eq(productNameTo));


    }

    @Test
    void updateProduct() {
        Integer productId = 1;


        ProductNameDTO productNameDTO = new ProductNameDTO();
        productNameDTO.setName("name");


        ProductNameTo productNameTo = new ProductNameTo();
        productNameTo.setName("name");


        ProductTo productTo = new ProductTo();
        productTo.setName("name");

        ProductTo updatedProductTo = new ProductTo();
        updatedProductTo.setProductId(productId);
        updatedProductTo.setName("Producto actualizado");

        ProductDTO updatedProductDTO = new ProductDTO();
        updatedProductDTO.setProductId(productId);
        updatedProductDTO.setName("Producto actualizado");

        when(productRestMapper.toProductNameTo(eq(productNameDTO))).thenReturn(productNameTo);
        when(productService.updateProduct(eq(productId), eq(productNameTo))).thenReturn(updatedProductTo);
        when(productRestMapper.toProductDTO(eq(updatedProductTo))).thenReturn(updatedProductDTO);


        ResponseEntity<ProductDTO> response = productController.updateProduct(productId, productNameDTO);


        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        ProductDTO body = response.getBody();
        assertNotNull(body);
        assertEquals(productId, body.getProductId());
        assertEquals("Producto actualizado", body.getName());

    }


    @Test
    void updateProductWithInvalidName() {
        Integer productId = 1;
        String invalidName = "";

        ProductNameDTO invalidProductNameDTO = new ProductNameDTO();
        invalidProductNameDTO.setName(invalidName);

        ProductNameTo invalidProductNameTo = new ProductNameTo();
        invalidProductNameTo.setName(invalidName);

        when(productRestMapper.toProductNameTo(eq(invalidProductNameDTO))).thenReturn(invalidProductNameTo);
        when(productService.updateProduct(eq(productId), eq(invalidProductNameTo)))
                .thenThrow(new IllegalArgumentException());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            productController.updateProduct(productId, invalidProductNameDTO);
        });


        verify(productRestMapper).toProductNameTo(eq(invalidProductNameDTO));
        verify(productService).updateProduct(eq(productId), eq(invalidProductNameTo));
    }

    @Test
    void deleteProduct() {
        Integer productId = 1;

        ResponseEntity<Void> response = productController.deleteProduct(productId);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        verify(productService).deleteProduct(eq(productId));
    }

    @Test
    void deleteProductNotFound() {
        Integer productId = 99;

        doThrow(new InstanceNotFoundException())
                .when(productService).deleteProduct(eq(productId));

        assertThrows(InstanceNotFoundException.class, () -> {
            productController.deleteProduct(productId);
        });
        verify(productService).deleteProduct(eq(productId));
    }
}