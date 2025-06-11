import { useEffect, useRef, useState } from "react";
import { getShops, getShopById, type ShopFilter } from "../../services/ShopsService";
import type { ShopDto, ShopAddDto, ShopPutDto, AddProductShopDto, ProductInShopPatchDto } from "../../types/Shops";
import { ShopList } from "./ShopList";
import { ShopDetail } from "./ShopDetail";
import { useParams } from "react-router-dom";
import { CreateShop } from "./CreateShop";
import { UpdateShop } from "./UpdateShop";
import { DeleteShop } from "./DeleteShop";
import { ProductToShop } from "./ProductToShop";
import { UpdateProductInShop } from "./UpdateProductInShop";
import { DeleteProductFromShop } from "./DeleteProductFromShop";

export const AllShops = () => {
    const [shops, setShops] = useState<ShopDto[]>([]);
    const [currentPage, setCurrentPage] = useState<number>(1);
    const [totalElements, setTotalElements] = useState<number>(0);
    const [totalPages, setTotalPages] = useState<number>(0);
    const [filters, setFilters] = useState<ShopFilter>({});
    const itemsPerPage = 10;
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        getShops({ page: currentPage - 1, size: itemsPerPage, filters })
            .then((response) => {
                setShops(response.content);
                setTotalElements(response.totalElements);
                setTotalPages(response.totalPages);
            })
            .catch((error) => {
                setError(error.message);
            });
    }, [currentPage, filters]);

    const handleFilterChange = (filter: ShopFilter) => {
        setFilters(filter);
        setCurrentPage(1);
    }

    if (error) return <div>Error loading shops: {error}</div>;

    return <ShopList 
        shops={shops}
        currentPage={currentPage}
        totalPages={totalPages}
        totalElements={totalElements}
        onFilterChange={handleFilterChange}
        onPageChange={setCurrentPage}
        filters={filters}
    />;
}

export const ShopById = () => {
    const { shopId } = useParams();
    const [shopDto, setShopDto] = useState<ShopDto | null>(null);
    const [error, setError] = useState<string | null>(null);
    const initialized = useRef(false)


    useEffect(() => {

        if (!initialized.current) {
            initialized.current = true
            if (shopId) {
                getShopById(shopId)
                    .then((response) => {
                        setShopDto(response);
                    })
                    .catch((error) => {
                        setError(error.message);
                    });
            }
        }
    }, [shopId]);

    if (error) return <div>Error loading shop: {error}</div>;

    return <ShopDetail shop={shopDto} />;
}

export const ShopPost = () => {
    const [formData] = useState<ShopAddDto>({
        country: '',
        city: '',
        address: ''
    });

    return <CreateShop shopAddDto={formData} />;
}

export const AddProductToShop = () => {
    const [formData] = useState<AddProductShopDto>({
        price: 0,
    });


    return <ProductToShop addProductShopDto={formData} />;
}

export const ShopPut = () => {
    const { shopId } = useParams();
    const [formData, setFormData] = useState<ShopPutDto>({
        country: '',
        city: '',
        address: ''
    });
    const [error, setError] = useState<string | null>(null);
    const initialized = useRef(false)

    useEffect(() => {
        if (!initialized.current) {
            initialized.current = true
            if (shopId) {
                getShopById(shopId)
                    .then((shop) => {
                        setFormData({
                            country: shop.country,
                            city: shop.city,
                            address: shop.address
                        });
                    })
                    .catch((error) => {
                        setError(error.message);
                    });
            }
        }
    }, [shopId]);
    if (error) return <div>Error loading shop: {error}</div>;

    return <UpdateShop shopPutDto={formData} />;
}

export const PatchProductInShop = () => {
    const [formData] = useState<ProductInShopPatchDto>({
        price: 0,
    });

    return <UpdateProductInShop productInShopPatch={formData} />;
}

export const ShopDelete = () => {
    return <DeleteShop />;
}

export const DeleteProductInShop = () => {
    return <DeleteProductFromShop />;
}
