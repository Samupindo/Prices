package com.develop.prices.rest;

import com.develop.prices.dto.ShopAddDTO;
import com.develop.prices.dto.ShopDTO;
import com.develop.prices.mapper.ProductRestMapper;
import com.develop.prices.mapper.ShopRestMapper;
import com.develop.prices.service.ShopService;
import com.develop.prices.to.ShopAddTo;
import com.develop.prices.to.ShopTo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ShopControllerTest {

    private ShopService shopService;

    private ShopController shopController;

    @BeforeEach
    void init() {

        shopService = mock(ShopService.class);

        shopController = new ShopController(shopService, Mappers.getMapper(ShopRestMapper.class), Mappers.getMapper(ProductRestMapper.class));
    }

    @Test
    void testGetShopLocationWithFilters() {

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
    }

    @Test
    void testUpdateShop() {
    }

    @Test
    void testPartialUpdateShop() {
    }

    @Test
    void updateProductInShop() {
    }

    @Test
    void testDeleteShop() {
    }

    @Test
    void testDeleteProductFromShop() {
    }
}