import { useEffect, useState } from "react";
import { getShops, getShopById, createShop } from "../../services/ShopsService";
import type { ShopDto, ShopAddDto, ShopPutDto } from "../../types/shops";
import { ShopList } from "./ShopList";
import { ShopDetail } from "./ShopDetail";
import { useParams } from "react-router-dom";
import { CreateShop } from "./CreateShop";
import { UpdateShop } from "./UpdateShop";

export const AllShops = () => {
    const [shops, setShops] = useState<ShopDto[]>([]);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        getShops()
            .then((response) => {
                    setShops(response);
            })
            .catch((error) => {
                setError(error.message);
            });
    }, []);

    if (error) return <div>Error loading shops: {error}</div>;
    
    return <ShopList shops={shops} />;
}

export const ShopById = () => {
    const { shopId } = useParams();
    const [shopDto, setShopDto] = useState<ShopDto | null>(null);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        if (shopId) {
            getShopById(shopId)
                .then((response) => {
                    setShopDto(response);
                })
                .catch((error) => {
                    setError(error.message);
                });
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

export const ShopPut = () => {
    const [formData] = useState<ShopPutDto>({
        country: '',
        city: '',
        address: ''
    });

    return <UpdateShop shopPutDto={formData} />;
}
    
