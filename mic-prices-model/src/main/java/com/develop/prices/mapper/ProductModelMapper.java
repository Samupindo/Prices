package com.develop.prices.mapper;

import com.develop.prices.dto.ProductDTO;
import com.develop.prices.dto.ProductNameDTO;
import com.develop.prices.dto.ShopInfoDTO;
import com.develop.prices.entity.ProductInShopModel;
import com.develop.prices.entity.ProductModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;


@Mapper(componentModel = "spring")
public interface ProductModelMapper {

    ProductDTO toProductDTO (ProductModel productModel);

    List<ProductDTO> toProductTo(List<ProductModel> productModels);

    @Mapping(target = "productId", ignore = true)
    @Mapping(target = "prices", ignore = true)
    ProductModel toProductModel(ProductDTO productDTO);

    @Mapping(target = "productId", ignore = true)
    @Mapping(target = "prices", ignore = true)
    ProductModel toProductModel(ProductNameDTO productNameDTO);

    @Mapping(target = "productId", ignore = true)
    @Mapping(target = "prices", ignore = true)
    ProductModel toProductModelUpdate(ProductNameDTO productNameDTO);

    ShopInfoDTO toShopInfoDTO(ProductInShopModel productInShopModel);

    List<ShopInfoDTO> toShopInfoDTO(List<ProductInShopModel> productInShopModels);

    default ProductModel map(Integer productId){
        if(productId == null){
            return null;
        }
        ProductModel productModel = new ProductModel();
        productModel.setProductId(productId);
        return productModel;
    }

}
