import { useEffect, useState } from "react";
import { getShops, getShopById } from "../../services/ShopsService";
import type { ShopDto } from "../../types/shops";
import { ShopList } from "./ShopList";
import { ShopDetail } from "./ShopDetail";
import { useParams } from "react-router-dom";

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
    const [shop, setShop] = useState<ShopDto | null>(null);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        if (shopId) {
            getShopById(shopId)
                .then((response) => {
                    setShop(response);
                })
                .catch((error) => {
                    setError(error.message);
                });
        }
    }, [shopId]);

    if (error) return <div>Error loading shop: {error}</div>;

    return <ShopDetail shop={shop} />;
}


