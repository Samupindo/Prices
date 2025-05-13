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
import com.develop.prices.specification.PurchaseSpecification;
import com.develop.prices.to.PageResponseTo;
import com.develop.prices.to.PostPurchaseTo;
import com.develop.prices.to.ProductInShopTo;
import com.develop.prices.to.PurchaseTo;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class PurchaseServiceImpl implements PurchaseService {


    private final PurchaseModelMapper purchaseModelMapper;

    private final ProductInShopModelMapper productInShopModelMapper;

    private final PurchaseRepository purchaseRepository;

    private final ProductInShopRepository productInShopRepository;

    private final CustomerRepository customerRepository;


    public PurchaseServiceImpl(PurchaseModelMapper purchaseModelMapper,ProductInShopModelMapper productInShopModelMapper,PurchaseRepository purchaseRepository , ProductInShopRepository productInShopRepository, CustomerRepository customerRepository) {
        this.purchaseModelMapper = purchaseModelMapper;
        this.productInShopModelMapper = productInShopModelMapper;
        this.purchaseRepository = purchaseRepository;
        this.productInShopRepository = productInShopRepository;
        this.customerRepository = customerRepository;
    }


    @Override
    public PageResponseTo<PurchaseTo> findAllWithFilters(Integer customerId, List<Integer> productInShop, BigDecimal totalPriceMax, BigDecimal totalPriceMin, Boolean shopping, Pageable pageable) {

        Specification<PurchaseModel> spec = Specification.where(null);

        if (customerId != null) {
            spec = spec.and(PurchaseSpecification.hasCustomer(customerId));
        }

        if (productInShop != null && !productInShop.isEmpty()) {
            spec = spec.and(PurchaseSpecification.hasProductInShop(productInShop));

        }
        if (totalPriceMax != null) {
            spec = spec.and(PurchaseSpecification.hasPriceMax(totalPriceMax));
        }

        if (totalPriceMin != null) {
            spec = spec.and(PurchaseSpecification.hasPriceMin(totalPriceMin));
        }

        if (shopping != null) {
            spec = spec.and(PurchaseSpecification.hasShoppingStatus(shopping));
        }

        Page<PurchaseModel> purchaseModels = purchaseRepository.findAll(spec,pageable);

        List<PurchaseTo> purchaseToList = purchaseModels.getContent()
                .stream()
                .map(purchaseModel -> {
                    PurchaseTo purchaseTo = purchaseModelMapper.toPurchaseTo(purchaseModel);
                    List<ProductInShopTo> productInShopTos = purchaseModel.getPurchaseLineModels()
                            .stream().map(p -> productInShopModelMapper.toProductInShopTo(p.getProductInShop()))
                            .collect(Collectors.toList());
                    purchaseTo.setProducts(productInShopTos);
                    return purchaseTo;
                })
                .collect(Collectors.toList());


        return new PageResponseTo<>(
                purchaseToList,
                purchaseModels.getTotalElements(),
                purchaseModels.getTotalPages()
        );
    }

    @Override
    public PurchaseTo findPurchaseById(Integer purchaseId) {
        Optional<PurchaseModel> optionalPurchaseModel = purchaseRepository.findById(purchaseId);

        if (optionalPurchaseModel.isEmpty()) {
            throw new InstanceNotFoundException();
        }

        PurchaseModel purchaseModel = optionalPurchaseModel.get();

        PurchaseTo purchaseTo = purchaseModelMapper.toPurchaseTo(purchaseModel);

        List<ProductInShopTo> productInShopTos = purchaseModel.getPurchaseLineModels().stream()
                .map(p -> productInShopModelMapper.toPurchaseTo(p.getProductInShop()))
                .collect(Collectors.toList());
        purchaseTo.setProducts(productInShopTos);

        return purchaseTo;
    }

    @Override
    public PurchaseTo savePurchase(PostPurchaseTo postPurchaseTo) {
        CustomerModel customerModel = customerRepository.findById(postPurchaseTo.getCustomerId()).orElse(null);

        if (customerModel == null) {
            throw new InstanceNotFoundException();
        }

        PurchaseModel purchaseModel = new PurchaseModel();

        purchaseModel.setCustomer(customerModel);
        purchaseModel.setTotalPrice(BigDecimal.ZERO);
        purchaseModel.setShopping(true);

        PurchaseModel savedPurchaseModel = purchaseRepository.save(purchaseModel);

        PurchaseTo purchaseTo = purchaseModelMapper.toPurchaseTo(savedPurchaseModel);
        purchaseTo.setProducts(List.of());

        return purchaseTo;
    }

    @Override
    public PurchaseTo savePurchaseAndPurchaseLine(Integer purchaseId, Integer productInShopId) {
        Optional<PurchaseModel> optionalPurchaseModel = purchaseRepository.findById(purchaseId);
        Optional<ProductInShopModel> optionalProductInShopModel = productInShopRepository.findById(productInShopId);

        if (optionalPurchaseModel.isEmpty() || optionalProductInShopModel.isEmpty()) {
            throw new InstanceNotFoundException();
        }

        PurchaseModel purchaseModel = optionalPurchaseModel.get();

        if (!purchaseModel.isShopping()) {
            throw new BadRequestException();
        }

        ProductInShopModel productInShopModel = optionalProductInShopModel.get();

        PurchaseLineModel purchaseLineModel = new PurchaseLineModel();
        purchaseLineModel.setPurchase(purchaseModel);
        purchaseLineModel.setProductInShop(productInShopModel);
        purchaseModel.getPurchaseLineModels().add(purchaseLineModel);

        purchaseModel.setTotalPrice(purchaseModel.getTotalPrice());

        PurchaseModel purchaseModelDB = purchaseRepository.save(purchaseModel);

        PurchaseTo purchaseTo = purchaseModelMapper.toPurchaseTo(purchaseModel);

        List<ProductInShopTo> productInShopTos = purchaseModelDB.getPurchaseLineModels().stream()
                .map(p -> productInShopModelMapper.toProductInShopTo(p.getProductInShop()))
                .collect(Collectors.toList());
        purchaseTo.setProducts(productInShopTos);
        return purchaseTo;
    }

    @Override
    public PurchaseTo updatePurchaseStatusToFinishes(Integer purchaseId) {
        Optional<PurchaseModel> optionalPurchaseModel = purchaseRepository.findById(purchaseId);
        if (optionalPurchaseModel.isEmpty()) {
            throw new InstanceNotFoundException();
        }
        PurchaseModel purchaseModel = optionalPurchaseModel.get();
        if (!purchaseModel.isShopping()) {
            throw new BadRequestException();
        }

        purchaseModel.setShopping(false);
        purchaseRepository.save(purchaseModel);
        PurchaseTo purchaseTo = purchaseModelMapper.toPurchaseTo(purchaseModel);

        List<ProductInShopTo> productInShopTos = purchaseModel.getPurchaseLineModels().stream()
                .map(p -> productInShopModelMapper.toProductInShopTo(p.getProductInShop()))
                .collect(Collectors.toList());
        purchaseTo.setProducts(productInShopTos);

        return purchaseTo;
    }

    @Override
    public void deletePurchase(Integer purchaseId) {
        PurchaseModel purchaseModel = purchaseRepository.findById(purchaseId).orElse(null);
        if (purchaseModel == null) {
            throw new InstanceNotFoundException();
        }

        purchaseRepository.delete(purchaseModel);
    }

    @Override
    public void deleteProductToPurchase(Integer purchaseId, Integer productInShopId) {
        Optional<PurchaseModel> optionalPurchaseModel = purchaseRepository.findById(purchaseId);

        if (optionalPurchaseModel.isEmpty()) {
            throw new InstanceNotFoundException();
        }

        PurchaseModel purchaseModel = optionalPurchaseModel.get();

        List<PurchaseLineModel> purchaseLineModels = purchaseModel.getPurchaseLineModels();
        if (purchaseLineModels.isEmpty()) {
            throw new InstanceNotFoundException();
        }

        if (!purchaseModel.isShopping()) {
            throw new BadRequestException();
        }

        boolean productoExiste = purchaseModel.getPurchaseLineModels().stream()
                .anyMatch(purchaseLine ->
                        purchaseLine.getProductInShop().getProductInShopId().equals(productInShopId)
                );

        if (!productoExiste) {
            throw new InstanceNotFoundException();
        }

        purchaseModel.getPurchaseLineModels().removeIf(purchaseLine ->
                purchaseLine.getProductInShop().getProductInShopId().equals(productInShopId)
        );

        purchaseRepository.save(purchaseModel);
    }
}
