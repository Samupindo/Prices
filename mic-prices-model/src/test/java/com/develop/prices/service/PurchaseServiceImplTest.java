package com.develop.prices.service;

import com.develop.prices.entity.CustomerModel;
import com.develop.prices.entity.ProductInShopModel;
import com.develop.prices.entity.PurchaseLineModel;
import com.develop.prices.entity.PurchaseModel;
import com.develop.prices.exception.BadRequestException;
import com.develop.prices.exception.InstanceNotFoundException;
import com.develop.prices.mapper.ProductInShopModelMapper;
import com.develop.prices.mapper.PurchaseModelMapper;
import com.develop.prices.repository.CustomerRepository;
import com.develop.prices.repository.ProductInShopRepository;
import com.develop.prices.repository.PurchaseRepository;
import com.develop.prices.to.PageResponseTo;
import com.develop.prices.to.PostPurchaseTo;
import com.develop.prices.to.PurchaseTo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PurchaseServiceImplTest {

    @Mock
    private PurchaseRepository purchaseRepository;

    @Mock
    private ProductInShopRepository productInShopRepository;

    @Mock
    private CustomerRepository customerRepository;


    private PurchaseServiceImpl purchaseService;

    @BeforeEach
    void setUp() {
        purchaseService = new PurchaseServiceImpl(Mappers.getMapper(PurchaseModelMapper.class),Mappers.getMapper(ProductInShopModelMapper.class),purchaseRepository,  productInShopRepository,  customerRepository);
    }

    @Test
    void findAllWithFilters() {

        // Preparar datos específicos para este test
        CustomerModel customerModel = new CustomerModel();
        customerModel.setCustomerId(1);


        ProductInShopModel productInShopModel = new ProductInShopModel();
        productInShopModel.setProductInShopId(1);

        PurchaseLineModel purchaseLineModel = new PurchaseLineModel();
        purchaseLineModel.setProductInShop(productInShopModel);

        List<PurchaseLineModel> purchaseLineModelList = new ArrayList<>();
        purchaseLineModelList.add(purchaseLineModel);

        PurchaseModel purchaseModel = new PurchaseModel();
        purchaseModel.setPurchaseId(1);
        purchaseModel.setCustomer(customerModel);
        purchaseModel.setTotalPrice(new BigDecimal("100.00"));
        purchaseModel.setShopping(true);
        purchaseModel.setPurchaseLineModels(purchaseLineModelList);

        List<PurchaseModel> purchaseModelList = new ArrayList<>();
        purchaseModelList.add(purchaseModel);

        //Creas la paginación
        Pageable pageable = PageRequest.of(0, 10);
        Page<PurchaseModel> mockPage = new PageImpl<>(purchaseModelList, pageable, purchaseModelList.size());

        when(purchaseRepository.findAll(any(Specification.class), eq(pageable))).thenReturn(mockPage);

        PageResponseTo<PurchaseTo> result = purchaseService.findAllWithFilters(1, List.of(1),
                new BigDecimal("200.00"), new BigDecimal("50.00"), true, pageable);

        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertEquals(1, result.getTotalElements());
        verify(purchaseRepository).findAll(any(Specification.class), eq(pageable));
    }

    @Test
    void findWithOutFilters() {

        CustomerModel customerModel = new CustomerModel();
        customerModel.setCustomerId(1);

        ProductInShopModel productInShopModel = new ProductInShopModel();
        productInShopModel.setProductInShopId(1);

        PurchaseLineModel purchaseLineModel = new PurchaseLineModel();
        purchaseLineModel.setProductInShop(productInShopModel);

        List<PurchaseLineModel> purchaseLineModelList = new ArrayList<>();
        purchaseLineModelList.add(purchaseLineModel);

        PurchaseModel purchaseModel = new PurchaseModel();
        purchaseModel.setPurchaseId(1);
        purchaseModel.setCustomer(customerModel);
        purchaseModel.setTotalPrice(new BigDecimal("100.00"));
        purchaseModel.setShopping(true);
        purchaseModel.setPurchaseLineModels(purchaseLineModelList);

        List<PurchaseModel> purchaseModelList = new ArrayList<>();
        purchaseModelList.add(purchaseModel);

        Pageable pageable = PageRequest.of(0, 10);
        Page<PurchaseModel> mockPage = new PageImpl<>(purchaseModelList, pageable, purchaseModelList.size());

        when(purchaseRepository.findAll(any(Specification.class), eq(pageable))).thenReturn(mockPage);

        PageResponseTo<PurchaseTo> result = purchaseService.findAllWithFilters(
                null,
                null,
                null,
                null,
                null,
                pageable);

        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertEquals(1, result.getTotalElements());
        verify(purchaseRepository).findAll(any(Specification.class), eq(pageable));
    }

    @Test
    void findPurchaseById() {
        // Preparar datos específicos para este test
        CustomerModel customerModel = new CustomerModel();
        customerModel.setCustomerId(1);

        ProductInShopModel productInShopModel = new ProductInShopModel();
        productInShopModel.setProductInShopId(1);

        PurchaseLineModel purchaseLineModel = new PurchaseLineModel();
        purchaseLineModel.setProductInShop(productInShopModel);

        List<PurchaseLineModel> purchaseLineModelList = new ArrayList<>();
        purchaseLineModelList.add(purchaseLineModel);

        PurchaseModel purchaseModel = new PurchaseModel();
        purchaseModel.setPurchaseId(1);
        purchaseModel.setCustomer(customerModel);
        purchaseModel.setTotalPrice(new BigDecimal("100.00"));
        purchaseModel.setShopping(true);
        purchaseModel.setPurchaseLineModels(purchaseLineModelList);

        when(purchaseRepository.findById(1)).thenReturn(Optional.of(purchaseModel));

        PurchaseTo result = purchaseService.findPurchaseById(1);

        assertNotNull(result);
        assertEquals(1, result.getPurchaseId());
        verify(purchaseRepository).findById(1);
    }

    @Test
    void findPurchaseById_shouldThrowExceptionWhenNotFound() {
        when(purchaseRepository.findById(999)).thenReturn(Optional.empty());

        assertThrows(InstanceNotFoundException.class, () -> {
            purchaseService.findPurchaseById(999);
        });
    }

    @Test
    void savePurchase() {
        // Preparar datos específicos para este test
        CustomerModel customerModel = new CustomerModel();
        customerModel.setCustomerId(1);

        PurchaseModel purchaseModel = new PurchaseModel();
        purchaseModel.setPurchaseId(1);
        purchaseModel.setCustomer(customerModel);
        purchaseModel.setTotalPrice(new BigDecimal("100.00"));
        purchaseModel.setShopping(true);

        PostPurchaseTo postPurchaseTo = new PostPurchaseTo();
        postPurchaseTo.setCustomerId(1);

        when(customerRepository.findById(1)).thenReturn(Optional.of(customerModel));
        when(purchaseRepository.save(any(PurchaseModel.class))).thenReturn(purchaseModel);

        PurchaseTo result = purchaseService.savePurchase(postPurchaseTo);

        assertNotNull(result);
        assertEquals(1, result.getPurchaseId());
        verify(customerRepository).findById(1);
        verify(purchaseRepository).save(any(PurchaseModel.class));
    }

    @Test
    void savePurchase_shouldThrowExceptionWhenCustomerNotFound() {
        PostPurchaseTo postPurchaseTo = new PostPurchaseTo();
        postPurchaseTo.setCustomerId(999);

        when(customerRepository.findById(999)).thenReturn(Optional.empty());

        assertThrows(InstanceNotFoundException.class, () -> {
            purchaseService.savePurchase(postPurchaseTo);
        });
    }

    @Test
    void savePurchaseAndPurchaseLine() {
        // Preparar datos específicos para este test
        CustomerModel customerModel = new CustomerModel();
        customerModel.setCustomerId(1);

        ProductInShopModel productInShopModel = new ProductInShopModel();
        productInShopModel.setProductInShopId(1);

        PurchaseLineModel purchaseLineModel = new PurchaseLineModel();
        purchaseLineModel.setProductInShop(productInShopModel);

        List<PurchaseLineModel> purchaseLineModelList = new ArrayList<>();
        purchaseLineModelList.add(purchaseLineModel);

        PurchaseModel purchaseModel = new PurchaseModel();
        purchaseModel.setPurchaseId(1);
        purchaseModel.setCustomer(customerModel);
        purchaseModel.setTotalPrice(new BigDecimal("100.00"));
        purchaseModel.setShopping(true);
        purchaseModel.setPurchaseLineModels(purchaseLineModelList);

        when(purchaseRepository.findById(1)).thenReturn(Optional.of(purchaseModel));
        when(productInShopRepository.findById(1)).thenReturn(Optional.of(productInShopModel));
        when(purchaseRepository.save(any(PurchaseModel.class))).thenReturn(purchaseModel);

        PurchaseTo result = purchaseService.savePurchaseAndPurchaseLine(1, 1);

        assertNotNull(result);
        verify(purchaseRepository).findById(1);
        verify(productInShopRepository).findById(1);
        verify(purchaseRepository).save(any(PurchaseModel.class));
    }

    @Test
    void savePurchaseAndPurchaseLine_shouldThrowExceptionWhenPurchaseNotFound() {
        when(purchaseRepository.findById(999)).thenReturn(Optional.empty());

        assertThrows(InstanceNotFoundException.class, () -> {
            purchaseService.savePurchaseAndPurchaseLine(999, 1);
        });
    }

    @Test
    void savePurchaseAndPurchaseLine_shouldThrowExceptionWhenProductNotFound() {
        // Preparar datos específicos para este test
        PurchaseModel purchaseModel = new PurchaseModel();
        purchaseModel.setPurchaseId(1);
        purchaseModel.setShopping(true);

        when(purchaseRepository.findById(1)).thenReturn(Optional.of(purchaseModel));
        when(productInShopRepository.findById(999)).thenReturn(Optional.empty());

        assertThrows(InstanceNotFoundException.class, () -> {
            purchaseService.savePurchaseAndPurchaseLine(1, 999);
        });
    }

    @Test
    void savePurchaseAndPurchaseLine_shouldThrowExceptionWhenPurchaseNotShopping() {
        // Preparar datos específicos para este test
        PurchaseModel notShoppingPurchase = new PurchaseModel();
        notShoppingPurchase.setPurchaseId(1);
        notShoppingPurchase.setShopping(false);

        ProductInShopModel productInShopModel = new ProductInShopModel();
        productInShopModel.setProductInShopId(1);

        when(purchaseRepository.findById(1)).thenReturn(Optional.of(notShoppingPurchase));
        when(productInShopRepository.findById(1)).thenReturn(Optional.of(productInShopModel));

        assertThrows(BadRequestException.class, () -> {
            purchaseService.savePurchaseAndPurchaseLine(1, 1);
        });
    }

    @Test
    void updatePurchaseStatusToFinishes() {
        // Preparar datos específicos para este test
        CustomerModel customerModel = new CustomerModel();
        customerModel.setCustomerId(1);

        ProductInShopModel productInShopModel = new ProductInShopModel();
        productInShopModel.setProductInShopId(1);

        PurchaseLineModel purchaseLineModel = new PurchaseLineModel();
        purchaseLineModel.setProductInShop(productInShopModel);

        List<PurchaseLineModel> purchaseLineModelList = new ArrayList<>();
        purchaseLineModelList.add(purchaseLineModel);

        PurchaseModel purchaseModel = new PurchaseModel();
        purchaseModel.setPurchaseId(1);
        purchaseModel.setCustomer(customerModel);
        purchaseModel.setTotalPrice(new BigDecimal("100.00"));
        purchaseModel.setShopping(true);
        purchaseModel.setPurchaseLineModels(purchaseLineModelList);

        PurchaseModel purchaseModelUpdated = new PurchaseModel();
        purchaseModelUpdated.setPurchaseId(1);
        purchaseModelUpdated.setCustomer(customerModel);
        purchaseModelUpdated.setTotalPrice(new BigDecimal("100.00"));
        purchaseModelUpdated.setShopping(false);
        purchaseModelUpdated.setPurchaseLineModels(purchaseLineModelList);

        when(purchaseRepository.findById(1)).thenReturn(Optional.of(purchaseModel));
        when(purchaseRepository.save(any(PurchaseModel.class))).thenReturn(purchaseModelUpdated);

        PurchaseTo result = purchaseService.updatePurchaseStatusToFinishes(1);

        assertNotNull(result);
        verify(purchaseRepository).findById(1);
        assertFalse(result.isShopping());
        
        // Añadir verificación de que se llamó a save con el modelo actualizado
        verify(purchaseRepository).save(any(PurchaseModel.class));
    }

    @Test
    void updatePurchaseStatusToFinishes_shouldThrowExceptionWhenNotFound() {
        when(purchaseRepository.findById(999)).thenReturn(Optional.empty());

        assertThrows(InstanceNotFoundException.class, () -> {
            purchaseService.updatePurchaseStatusToFinishes(999);
        });
    }

    @Test
    void deletePurchase() {
        // Preparar datos específicos para este test
        PurchaseModel purchaseModel = new PurchaseModel();
        purchaseModel.setPurchaseId(1);

        when(purchaseRepository.findById(1)).thenReturn(Optional.of(purchaseModel));

        assertDoesNotThrow(() -> purchaseService.deletePurchase(1));

        verify(purchaseRepository).findById(1);
        verify(purchaseRepository).delete(purchaseModel);
    }

    @Test
    void deletePurchase_shouldThrowExceptionWhenNotFound() {
        when(purchaseRepository.findById(999)).thenReturn(Optional.empty());

        assertThrows(InstanceNotFoundException.class, () -> {
            purchaseService.deletePurchase(999);
        });
    }

    @Test
    void deleteProductToPurchase() {
        // Preparar datos específicos para este test
        ProductInShopModel productInShopModel = new ProductInShopModel();
        productInShopModel.setProductInShopId(1);

        PurchaseLineModel purchaseLineModel = new PurchaseLineModel();
        purchaseLineModel.setProductInShop(productInShopModel);

        List<PurchaseLineModel> purchaseLineModelList = new ArrayList<>();
        purchaseLineModelList.add(purchaseLineModel);

        PurchaseModel purchaseModel = new PurchaseModel();
        purchaseModel.setPurchaseId(1);
        purchaseModel.setShopping(true);
        purchaseModel.setPurchaseLineModels(purchaseLineModelList);

        when(purchaseRepository.findById(1)).thenReturn(Optional.of(purchaseModel));

        purchaseService.deleteProductToPurchase(1, 1);

        verify(purchaseRepository).save(purchaseModel);
    }

    @Test
    void deleteProductToPurchase_shouldThrowExceptionWhenPurchaseNotFound() {
        when(purchaseRepository.findById(999)).thenReturn(Optional.empty());

        assertThrows(InstanceNotFoundException.class, () -> {
            purchaseService.deleteProductToPurchase(999, 1);
        });
    }

    @Test
    void deleteProductToPurchase_shouldThrowExceptionWhenProductNotFound() {

        ProductInShopModel productInShopModel = new ProductInShopModel();
        productInShopModel.setProductInShopId(1);

        PurchaseLineModel purchaseLineModel = new PurchaseLineModel();
        purchaseLineModel.setProductInShop(productInShopModel);

        List<PurchaseLineModel> purchaseLineModelList = new ArrayList<>();
        purchaseLineModelList.add(purchaseLineModel);

        PurchaseModel purchaseWithoutProduct = new PurchaseModel();
        purchaseWithoutProduct.setPurchaseId(1);
        purchaseWithoutProduct.setShopping(true);
        purchaseWithoutProduct.setPurchaseLineModels(purchaseLineModelList);

        when(purchaseRepository.findById(1)).thenReturn(Optional.of(purchaseWithoutProduct));

        assertThrows(InstanceNotFoundException.class, () -> {
            purchaseService.deleteProductToPurchase(1, 999);
        });
    }
}