import { useEffect, useState } from "react";
import { getProducts } from "../../services/product-service";
import { ProductList } from "./productList";
import type { ProductWithShopsDto } from "../../types/shops";

export const Products = () =>{
    const [products,setProducts] =  useState<ProductWithShopsDto[]>([]);
    const [error,setError] = useState<string | null>(null);


    useEffect(() =>{
        getProducts()
            .then((response) => {
                    setProducts(response);
            })
            .catch((error) => {
                setError(error.message);
            });
    }, []);

    if (error) return <div>Error loading products: {error}</div>;
    
    return <ProductList products={products} />;
}