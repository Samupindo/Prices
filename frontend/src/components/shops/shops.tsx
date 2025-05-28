import { useEffect, useState } from "react";
import { getShops } from "../../services/shops-service";
import type { ShopDto } from "../../types/shops";
import { ShopList } from "./shopsList";

export const Shops = () => {
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