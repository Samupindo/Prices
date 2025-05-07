package com.develop.prices.mapper;

import com.develop.prices.dto.ProductDTO;
import com.develop.prices.dto.ProductNameDTO;
import com.develop.prices.dto.ShopInfoDTO;
import com.develop.prices.entity.ProductInShopModel;
import com.develop.prices.entity.ProductModel;
import com.develop.prices.to.ProductNameTo;
import com.develop.prices.to.ProductTo;
import com.develop.prices.to.ShopInfoTo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;


@Mapper(componentModel = "spring")
public interface ProductModelMapper {

    ProductTo toProductTo (ProductModel productModel);

    List<ProductTo> toProductTo(List<ProductModel> productModels);

    @Mapping(target = "productId", ignore = true)
    @Mapping(target = "prices", ignore = true)
    ProductModel toProductModel(ProductTo productTo);

    @Mapping(target = "productId", ignore = true)
    @Mapping(target = "prices", ignore = true)
    ProductModel toProductModel(ProductNameTo productNameTo);

    @Mapping(target = "productId", ignore = true)
    @Mapping(target = "prices", ignore = true)
    ProductModel toProductModelUpdate(ProductNameTo productNameTo);

    ShopInfoTo toShopInfoTo(ProductInShopModel productInShopModel);

    List<ShopInfoTo> toShopInfoTos(List<ProductInShopModel> productInShopModels);

    default ProductModel map(Integer productId){
        if(productId == null){
            return null;
        }
        ProductModel productModel = new ProductModel();
        productModel.setProductId(productId);
        return productModel;
    }

}
