package com.develop.prices.rest;

import com.develop.prices.dto.PageResponseDTO;
import com.develop.prices.dto.PostPurchaseDTO;
import com.develop.prices.dto.PurchaseDTO;
import com.develop.prices.exception.InstanceNotFoundException;
import com.develop.prices.mapper.PurchaseRestMapper;
import com.develop.prices.service.PurchaseService;
import com.develop.prices.to.PageResponseTo;
import com.develop.prices.to.PostPurchaseTo;
import com.develop.prices.to.PurchaseTo;
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
class PurchaseControllerTest {


    @Mock
    private PurchaseService purchaseService;

    @Mock
    private PurchaseRestMapper purchaseRestMapper;

    private PurchaseController purchaseController;

    @BeforeEach
    void setUp() {
        purchaseController = new PurchaseController(purchaseService, purchaseRestMapper);
    }

    @Test
    void getPurchasesWithFilters() {

        Integer customerId = 1;
        List<Integer> productInShop = List.of(1);
        BigDecimal totalPriceMax = new BigDecimal("200.00");
        BigDecimal totalPriceMin = new BigDecimal("50.00");
        Boolean shopping = true;

        Pageable pageable = PageRequest.of(0, 10, Sort.Direction.ASC, "purchaseId");


        PurchaseTo purchaseTo = new PurchaseTo();
        purchaseTo.setPurchaseId(1);

        List<PurchaseTo> purchaseToList = new ArrayList<>();
        purchaseToList.add(purchaseTo);


        PageResponseTo<PurchaseTo> pageResponseTo = new PageResponseTo<>(
                purchaseToList,
                purchaseToList.size(),
                1
        );

        PurchaseDTO purchaseDTO = new PurchaseDTO();
        purchaseDTO.setPurchaseId(1);

        when(purchaseService.findAllWithFilters(eq(customerId), eq(productInShop),
                eq(totalPriceMax), eq(totalPriceMin), eq(shopping), eq(pageable)))
                .thenReturn(pageResponseTo);


        when(purchaseRestMapper.toPurchaseDTO(any(PurchaseTo.class))).thenReturn(purchaseDTO);


        ResponseEntity<PageResponseDTO<PurchaseDTO>> response = purchaseController.getPurchasesWithFilters(
                customerId, productInShop, totalPriceMax, totalPriceMin, shopping, pageable);


        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        PageResponseDTO<PurchaseDTO> body = response.getBody();
        assertNotNull(body);

        assertEquals(1, response.getBody().getContent().size());
        assertEquals(1, response.getBody().getTotalElements());


        verify(purchaseService).findAllWithFilters(eq(customerId), eq(productInShop),
                eq(totalPriceMax), eq(totalPriceMin), eq(shopping), eq(pageable));

    }

    @Test
    void getPurchasesWithNoFilters() {
        Integer customerId = null;
        List<Integer> productInShop = null;
        BigDecimal totalPriceMax = null;
        BigDecimal totalPriceMin = null;
        Boolean shopping = null;

        Pageable pageable = PageRequest.of(0, 10, Sort.Direction.ASC, "purchaseId");


        PurchaseTo purchaseTo = new PurchaseTo();
        purchaseTo.setPurchaseId(1);

        List<PurchaseTo> purchaseToList = new ArrayList<>();
        purchaseToList.add(purchaseTo);


        PageResponseTo<PurchaseTo> pageResponseTo = new PageResponseTo<>(
                purchaseToList,
                purchaseToList.size(),
                1
        );

        PurchaseDTO purchaseDTO = new PurchaseDTO();
        purchaseDTO.setPurchaseId(1);

        when(purchaseService.findAllWithFilters(eq(customerId), eq(productInShop),
                eq(totalPriceMax), eq(totalPriceMin), eq(shopping), eq(pageable)))
                .thenReturn(pageResponseTo);


        when(purchaseRestMapper.toPurchaseDTO(any(PurchaseTo.class))).thenReturn(purchaseDTO);

        ResponseEntity<PageResponseDTO<PurchaseDTO>> response = purchaseController.getPurchasesWithFilters(
                customerId, productInShop, totalPriceMax, totalPriceMin, shopping, pageable);


        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        PageResponseDTO<PurchaseDTO> body = response.getBody();
        assertNotNull(body);

        verify(purchaseService).findAllWithFilters(eq(customerId), eq(productInShop),
                eq(totalPriceMax), eq(totalPriceMin), eq(shopping), eq(pageable));
    }


    @Test
    void getPurchaseById() {
        Integer purchaseId = 1;

        PurchaseTo purchaseTo = new PurchaseTo();
        purchaseTo.setPurchaseId(1);

        PurchaseDTO purchaseDTO = new PurchaseDTO();
        purchaseDTO.setPurchaseId(1);

        when(purchaseService.findPurchaseById(eq(purchaseId))).thenReturn(purchaseTo);
        when(purchaseRestMapper.toPurchaseDTO(purchaseTo)).thenReturn(purchaseDTO);

        ResponseEntity<PurchaseDTO> response = purchaseController.getPurchaseById(purchaseId);


        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        PurchaseDTO body = response.getBody();
        assertNotNull(body);
        assertEquals(purchaseId, body.getPurchaseId());


        verify(purchaseService).findPurchaseById(eq(purchaseId));
        verify(purchaseRestMapper).toPurchaseDTO(purchaseTo);

    }

    @Test
    void getPurchaseByIdNotFound() {
        Integer purchaseId = 99;

        when(purchaseService.findPurchaseById(eq(purchaseId))).thenThrow(new InstanceNotFoundException());

        assertThrows(InstanceNotFoundException.class, () -> {
            purchaseController.getPurchaseById(purchaseId);
        });

        verify(purchaseService).findPurchaseById(eq(purchaseId));
    }

    @Test
    void postPurchase() {
        PostPurchaseDTO postPurchaseDTO = new PostPurchaseDTO();
        postPurchaseDTO.setCustomerId(1);

        PostPurchaseTo postPurchaseTo = new PostPurchaseTo();
        postPurchaseTo.setCustomerId(1);

        PurchaseTo purchaseTo = new PurchaseTo();
        purchaseTo.setPurchaseId(1);


        PurchaseDTO purchaseDTO = new PurchaseDTO();
        purchaseDTO.setPurchaseId(1);

        when(purchaseRestMapper.toPostPurchaseTo(eq(postPurchaseDTO))).thenReturn(postPurchaseTo);
        when(purchaseService.savePurchase(eq(postPurchaseTo))).thenReturn(purchaseTo);
        when(purchaseRestMapper.toPurchaseDTO(eq(purchaseTo))).thenReturn(purchaseDTO);


        ResponseEntity<PurchaseDTO> response = purchaseController.postPurchase(postPurchaseDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        PurchaseDTO body = response.getBody();
        assertNotNull(body);
        assertEquals(1, body.getPurchaseId());

        verify(purchaseRestMapper).toPostPurchaseTo(postPurchaseDTO);
        verify(purchaseService).savePurchase(postPurchaseTo);
        verify(purchaseRestMapper).toPurchaseDTO(purchaseTo);

    }

    @Test
    void postPurchaseWithInvalidCustomer() {
        PostPurchaseDTO postPurchaseDTO = new PostPurchaseDTO();
        postPurchaseDTO.setCustomerId(99);

        PostPurchaseTo postPurchaseTo = new PostPurchaseTo();
        postPurchaseTo.setCustomerId(99);

        when(purchaseRestMapper.toPostPurchaseTo(eq(postPurchaseDTO))).thenReturn(postPurchaseTo);
        when(purchaseService.savePurchase(eq(postPurchaseTo))).thenThrow(new InstanceNotFoundException());

        // Verificar que se lanza la excepción
        assertThrows(InstanceNotFoundException.class, () -> {
            purchaseController.postPurchase(postPurchaseDTO);
        });

        verify(purchaseRestMapper).toPostPurchaseTo(postPurchaseDTO);
        verify(purchaseService).savePurchase(postPurchaseTo);
    }

    @Test
    void addProductPurchase() {
        Integer purchaseId = 1;
        Integer productInShopId = 1;

        PurchaseTo purchaseTo = new PurchaseTo();
        purchaseTo.setPurchaseId(1);

        PurchaseDTO purchaseDTO = new PurchaseDTO();
        purchaseDTO.setPurchaseId(1);

        when(purchaseService.savePurchaseAndPurchaseLine(purchaseId, productInShopId)).thenReturn(purchaseTo);
        when(purchaseRestMapper.toPurchaseDTO(purchaseTo)).thenReturn(purchaseDTO);

        ResponseEntity<PurchaseDTO> response = purchaseController.addProductPurchase(purchaseId, productInShopId);


        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        PurchaseDTO body = response.getBody();
        assertNotNull(body);
        assertEquals(1, body.getPurchaseId());


        verify(purchaseService).savePurchaseAndPurchaseLine(purchaseId, productInShopId);
        verify(purchaseRestMapper).toPurchaseDTO(purchaseTo);

    }

    @Test
    void addProductPurchaseWithNonExistentProduct() {
        Integer purchaseId = 1;
        Integer productInShopId = 999;

        when(purchaseService.savePurchaseAndPurchaseLine(eq(purchaseId), eq(productInShopId)))
                .thenThrow(new InstanceNotFoundException());

        assertThrows(InstanceNotFoundException.class, () -> {
            purchaseController.addProductPurchase(purchaseId, productInShopId);
        });

        verify(purchaseService).savePurchaseAndPurchaseLine(purchaseId, productInShopId);
    }


    @Test
    void finishPurchase() {
        Integer purchaseId = 1;

        PurchaseTo purchaseTo = new PurchaseTo();
        purchaseTo.setPurchaseId(purchaseId);
        purchaseTo.setShopping(false);


        PurchaseDTO purchaseDTO = new PurchaseDTO();
        purchaseDTO.setPurchaseId(purchaseId);
        purchaseDTO.setShopping(false);

        when(purchaseService.updatePurchaseStatusToFinishes(eq(purchaseId))).thenReturn(purchaseTo);
        when(purchaseRestMapper.toPurchaseDTO(purchaseTo)).thenReturn(purchaseDTO);

        ResponseEntity<PurchaseDTO> response = purchaseController.finishPurchase(purchaseId);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        PurchaseDTO body = response.getBody();
        assertNotNull(body);
        assertEquals(purchaseId, body.getPurchaseId());
        assertFalse(body.isShopping());

        verify(purchaseService).updatePurchaseStatusToFinishes(eq(purchaseId));
        verify(purchaseRestMapper).toPurchaseDTO(purchaseTo);
    }

    @Test
    void finishPurchaseNotFound() {
        Integer purchaseId = 999;

        when(purchaseService.updatePurchaseStatusToFinishes(eq(purchaseId)))
                .thenThrow(new InstanceNotFoundException());

        assertThrows(InstanceNotFoundException.class, () -> {
            purchaseController.finishPurchase(purchaseId);
        });

        verify(purchaseService).updatePurchaseStatusToFinishes(purchaseId);
    }

    @Test
    void finishPurchaseAlreadyFinished() {
        Integer purchaseId = 1;

        when(purchaseService.updatePurchaseStatusToFinishes(eq(purchaseId)))
                .thenThrow(new IllegalStateException());

        assertThrows(IllegalStateException.class, () -> {
            purchaseController.finishPurchase(purchaseId);
        });

        verify(purchaseService).updatePurchaseStatusToFinishes(purchaseId);
    }

    @Test
    void deletePurchase() {
        Integer purchaseId = 1;

        ResponseEntity<Void> response = purchaseController.deletePurchase(purchaseId);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        verify(purchaseService).deletePurchase(eq(purchaseId));


    }

    @Test
    void deletePurchaseNotFound() {
        Integer purchaseId = 999;

        doThrow(new InstanceNotFoundException()).when(purchaseService).deletePurchase(eq(purchaseId));

        assertThrows(InstanceNotFoundException.class, () -> {
            purchaseController.deletePurchase(purchaseId);
        });

        verify(purchaseService).deletePurchase(purchaseId);
    }

    @Test
    void deleteProductPurchase() {
        Integer purchaseId = 1;
        Integer productInShopId = 1;

        ResponseEntity<Void> response = purchaseController.deleteProductPurchase(purchaseId, productInShopId);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        verify(purchaseService).deleteProductToPurchase(eq(purchaseId), eq(productInShopId));
    }


    @Test
    void deleteProductPurchaseNotFound() {
        Integer purchaseId = 999;
        Integer productInShopId = 999;

        doThrow(new InstanceNotFoundException()).when(purchaseService).deleteProductToPurchase(eq(purchaseId), eq(productInShopId));


        assertThrows(InstanceNotFoundException.class, () -> {
            purchaseController.deleteProductPurchase(purchaseId, productInShopId);
        });

        verify(purchaseService).deleteProductToPurchase(eq(purchaseId), eq(productInShopId));

    }
}