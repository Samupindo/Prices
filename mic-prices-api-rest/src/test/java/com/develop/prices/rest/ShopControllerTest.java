package com.develop.prices.rest;

import com.develop.prices.dto.*;
import com.develop.prices.mapper.ProductRestMapper;
import com.develop.prices.mapper.ShopRestMapper;
import com.develop.prices.service.ShopService;
import com.develop.prices.to.*;
import org.apache.coyote.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class ShopControllerTest {

   private final ShopRestMapper shopRestMapper = Mappers.getMapper(ShopRestMapper.class);

   private final ProductRestMapper productRestMapper = Mappers.getMapper(ProductRestMapper.class);

    private ShopService shopService;

    private ShopController shopController;

    @BeforeEach
    void init() {

        shopService = mock(ShopService.class);

        shopController = new ShopController(shopService, shopRestMapper, productRestMapper);
    }

    @Test
    void testGetShopLocationWithFilters() {
        // Preparar datos de prueba
        Pageable pageable = PageRequest.of(0, 10);
        String country = "España";
        String city = "Madrid";
        String address = "Gran Vía";

        ShopTo shopTo1 = new ShopTo();
        shopTo1.setShopId(1);
        shopTo1.setCountry("España");
        shopTo1.setCity("Madrid");
        shopTo1.setAddress("Calle Gran Vía 1");

        ShopTo shopTo2 = new ShopTo();
        shopTo2.setShopId(2);
        shopTo2.setCountry("España");
        shopTo2.setCity("Madrid");
        shopTo2.setAddress("Calle Gran Vía 2");

        List<ShopTo> shopToList = Arrays.asList(shopTo1, shopTo2);

        PageResponseTo<ShopTo> pageResponseTo = new PageResponseTo<>(
                shopToList,
                2L,
                1
        );

        when(shopService.findAllShopWithFilters(eq(country), eq(city), eq(address), eq(pageable)))
                .thenReturn(pageResponseTo);

        ResponseEntity<PageResponseDTO<ShopDTO>> response =
                shopController.getShopLocationWithFilters(country, city, address, pageable);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        PageResponseDTO<ShopDTO> pageResponseDTO = response.getBody();
        assertNotNull(pageResponseDTO);
        assertEquals(2, pageResponseDTO.getContent().size());
        assertEquals(2L, pageResponseDTO.getTotalElements());
        assertEquals(1, pageResponseDTO.getTotalPages());

        ShopDTO shopDTO1 = pageResponseDTO.getContent().get(0);
        assertEquals(1, shopDTO1.getShopId());
        assertEquals("España", shopDTO1.getCountry());
        assertEquals("Madrid", shopDTO1.getCity());
        assertEquals("Calle Gran Vía 1", shopDTO1.getAddress());

    }

    @Test
    void testGetShopLocationWithNoFilters() {
        Pageable pageable = PageRequest.of(0, 10);

        ShopTo shopTo1 = new ShopTo();
        shopTo1.setShopId(1);
        shopTo1.setCountry("España");
        shopTo1.setCity("Madrid");
        shopTo1.setAddress("Calle Principal 1");

        ShopTo shopTo2 = new ShopTo();
        shopTo2.setShopId(2);
        shopTo2.setCountry("España");
        shopTo2.setCity("Barcelona");
        shopTo2.setAddress("Passeig de Gràcia 1");

        List<ShopTo> shopToList = Arrays.asList(shopTo1, shopTo2);

        PageResponseTo<ShopTo> pageResponseTo = new PageResponseTo<>(
                shopToList,
                2L,
                1
        );

        when(shopService.findAllShopWithFilters(null, null, null, pageable))
                .thenReturn(pageResponseTo);

        ResponseEntity<PageResponseDTO<ShopDTO>> response =
                shopController.getShopLocationWithFilters(null, null, null, pageable);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        PageResponseDTO<ShopDTO> pageResponseDTO = response.getBody();
        assertNotNull(pageResponseDTO);
        assertEquals(2, pageResponseDTO.getContent().size());

        List<String> cities = pageResponseDTO.getContent().stream()
                .map(ShopDTO::getCity)
                .toList();
        assertTrue(cities.contains("Madrid"));
        assertTrue(cities.contains("Barcelona"));

        verify(shopService).findAllShopWithFilters(null, null, null, pageable);
    }

    @Test
    void testGetShopLocationWithPagination() {
        Pageable pageable = PageRequest.of(1, 1);

        ShopTo shopTo = new ShopTo();
        shopTo.setShopId(2);
        shopTo.setCountry("España");
        shopTo.setCity("Barcelona");
        shopTo.setAddress("Passeig de Gràcia 1");

        List<ShopTo> shopToList = List.of(shopTo);

        PageResponseTo<ShopTo> pageResponseTo = new PageResponseTo<>(
                shopToList,
                2L,
                2
        );

        when(shopService.findAllShopWithFilters(null, null, null, pageable))
                .thenReturn(pageResponseTo);

        // Ejecutar
        ResponseEntity<PageResponseDTO<ShopDTO>> response =
                shopController.getShopLocationWithFilters(null, null, null, pageable);

        // Verificar
        assertEquals(HttpStatus.OK, response.getStatusCode());

        PageResponseDTO<ShopDTO> pageResponseDTO = response.getBody();
        assertNotNull(pageResponseDTO);
        assertEquals(1, pageResponseDTO.getContent().size());
        assertEquals(2L, pageResponseDTO.getTotalElements());
        assertEquals(2, pageResponseDTO.getTotalPages());

        assertEquals(2, pageResponseDTO.getContent().get(0).getShopId());
        assertEquals("Barcelona", pageResponseDTO.getContent().get(0).getCity());

        verify(shopService).findAllShopWithFilters(null, null, null, pageable);
    }

    @Test
    void testGetShopById() {
        Integer shopId = 1;

        ShopTo shopTo = new ShopTo();
        shopTo.setShopId(shopId);

        when(shopService.findShopById(shopId)).thenReturn(shopTo);

        ResponseEntity<ShopDTO> response = shopController.getShopById(shopId);

        assertEquals(HttpStatus.OK,response.getStatusCode());

        ShopDTO shopDTO = response.getBody();

        assertNotNull(shopDTO);
        assertEquals(shopId, shopDTO.getShopId());
    }

    @Test
    void testGetShopByIdNotFound() {
        Integer shopId = 99;

        when(shopService.findShopById(shopId)).thenReturn(null);

        ResponseEntity<ShopDTO> response = shopController.getShopById(shopId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testAddShop() {
        Integer shopId = 1;

        ShopAddDTO shopAddDTO = new ShopAddDTO();
        shopAddDTO.setCountry("España");
        shopAddDTO.setCity("Ciudad 1");
        shopAddDTO.setAddress("Calle 1");

        ShopAddTo shopAddTo = new ShopAddTo();
        shopAddTo.setCountry(shopAddDTO.getCountry());
        shopAddTo.setCity(shopAddDTO.getCity());
        shopAddTo.setAddress(shopAddDTO.getAddress());

        ShopTo shopTo = new ShopTo();
        shopTo.setShopId(shopId);
        shopTo.setCountry(shopAddDTO.getCountry());
        shopTo.setCity(shopAddDTO.getCity());
        shopTo.setAddress(shopAddDTO.getAddress());

        when(shopService.saveShop(any(ShopAddTo.class))).thenReturn(shopTo);

        ResponseEntity<ShopDTO> response = shopController.addShop(shopAddDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        ShopDTO shopDTO = response.getBody();

        assertNotNull(shopDTO);

        assertEquals(shopId, shopDTO.getShopId());
        assertEquals("España", shopDTO.getCountry());
        assertEquals("Ciudad 1", shopDTO.getCity());
        assertEquals("Calle 1", shopDTO.getAddress());

    }

    @Test
    void testAddProductShop() {
        Integer productInShopId = 1;
        Integer productId = 1;
        Integer shopId = 1;
        BigDecimal price = new BigDecimal("10.00");

        AddProductShopDTO addProductShopDTO = new AddProductShopDTO();
        addProductShopDTO.setPrice(price);

        AddProductShopTo addProductShopTo = new AddProductShopTo();
        addProductShopTo.setPrice(addProductShopDTO.getPrice());

        ProductInShopTo productInShopTo = new ProductInShopTo();
        productInShopTo.setProductInShopId(productInShopId);
        productInShopTo.setProductId(productId);
        productInShopTo.setShopId(shopId);
        productInShopTo.setPrice(addProductShopTo.getPrice());

        when(shopService.addProductToShop(eq(productId), eq(shopId), any(AddProductShopTo.class))).thenReturn(productInShopTo);

        ResponseEntity<ProductInShopDTO> response = shopController.addProductShop(productId, shopId, addProductShopDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        ProductInShopDTO productInShopDTO = response.getBody(); // esta cagada coge el body como null
        assertNotNull(productInShopDTO);

        assertEquals(productInShopId, productInShopDTO.getProductInShopId());
        assertEquals(productId, productInShopDTO.getProductId());
        assertEquals(shopId, productInShopDTO.getShopId());
        assertEquals(price, productInShopDTO.getPrice());

    }

    @Test
    void testUpdateShop() {
        Integer shopId = 1;

        ShopPutDTO shopPutDTO = new ShopPutDTO();
        shopPutDTO.setAddress("Calle 2");
        shopPutDTO.setCity("Ciudad 2");
        shopPutDTO.setCountry("País 2");

        ShopTo shopTo = new ShopTo();
        shopTo.setShopId(shopId);
        shopTo.setAddress("Calle 1");
        shopTo.setCity("Ciudad 1");
        shopTo.setCountry("País 1");

        when(shopService.updateShop(eq(shopId), any(ShopPutTo.class))).thenReturn(shopTo);

        ResponseEntity<ShopDTO> response = shopController.updateShop(shopId, shopPutDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        ShopDTO shopDTO = response.getBody();

        assertNotNull(shopDTO);

        assertEquals(shopId, shopDTO.getShopId());
        assertEquals("Calle 1", shopDTO.getAddress());
        assertEquals("Ciudad 1", shopDTO.getCity());
        assertEquals("País 1", shopDTO.getCountry());
    }

    @Test
    void testPartialUpdateShop() {
        Integer shopId = 1;

        UpdateShopDTO updateShopDTO = new UpdateShopDTO();
        updateShopDTO.setAddress("Calle 2");
        updateShopDTO.setCity("Ciudad 2");
        updateShopDTO.setCountry("País 2");

        ShopTo shopTo = new ShopTo();
        shopTo.setShopId(shopId);
        shopTo.setAddress("Calle 1");
        shopTo.setCity("Ciudad 1");
        shopTo.setCountry("País 1");

        when(shopService.partialUpdateShop(eq(shopId), any(UpdateShopTo.class))).thenReturn(shopTo);

        ResponseEntity<ShopDTO> response = shopController.partialUpdateShop(shopId, updateShopDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        ShopDTO shopDTO = response.getBody();

        assertNotNull(shopDTO);

        assertEquals(shopId, shopDTO.getShopId());
        assertEquals("Calle 1", shopDTO.getAddress());
        assertEquals("Ciudad 1", shopDTO.getCity());
        assertEquals("País 1", shopDTO.getCountry());
    }

    @Test
    void updateProductInShop() {
        Integer shopId = 1;
        Integer productId = 1;
        BigDecimal price = new BigDecimal("10.00");

        ProductInShopPatchDTO productInShopPatchDTO = new ProductInShopPatchDTO();
        productInShopPatchDTO.setPrice(price);

        ProductInShopTo productInShopTo = new ProductInShopTo();
        productInShopTo.setProductInShopId(1);
        productInShopTo.setProductId(1);
        productInShopTo.setShopId(1);
        productInShopTo.setPrice(price);

        when(shopService.updateProductPriceInShop(eq(shopId), eq(productId), any(ProductInShopPatchTo.class))).thenReturn(productInShopTo);

        ResponseEntity<ProductInShopDTO> response = shopController.updateProductInShop(shopId, productId, productInShopPatchDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        ProductInShopDTO productInShopDTO = response.getBody();

        assertNotNull(productInShopDTO);

        assertEquals(shopId, productInShopDTO.getShopId());
        assertEquals(productId, productInShopDTO.getProductId());
        assertEquals(price, productInShopDTO.getPrice());
    }

    @Test
    void testDeleteShop() {
        Integer shopId = 1;

        doNothing().when(shopService).deleteShop(shopId);

        ResponseEntity<ShopDTO> response = shopController.deleteShop(shopId);

        assertEquals(HttpStatus.OK, response.getStatusCode());

    }

    @Test
    void testDeleteProductFromShop() {
        Integer shopId = 1;
        Integer productId = 1;

        doNothing().when(shopService).deleteProductFromShop(shopId, productId);

        ResponseEntity<ProductInShopDTO> response = shopController.deleteProductFromShop(shopId, productId);

        assertEquals(HttpStatus.OK, response.getStatusCode());

    }
}