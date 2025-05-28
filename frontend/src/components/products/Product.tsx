import { useEffect, useState } from "react";
import { createProduct, getProducts } from "../../services/product-service";
import { ProductList } from "./ProductList";
import { ProductsFilters } from "./ProductsFilters";
import type { ProductWithShopsDto, PageResponseDto, ProductDto } from "../../types/products";

export const Products = () => {
    const [products, setProducts] = useState<ProductWithShopsDto[]>([]);
    const [totalPages, setTotalPages] = useState(1);
    const [error, setError] = useState<string | null>(null);

    const fetchProducts = async (filters?: {
        name?: string;
        priceMin?: number;
        priceMax?: number;
        page?: number;
        size?: number;
    }) => {
        try {
            setError(null);
            const response = await getProducts(filters);
            setProducts(response.content);
            setTotalPages(response.totalPages);
        } catch (error) {
            setError('Failed to fetch products');
            console.error('Error fetching products:', error);
        }
    };

    useEffect(() => {
        fetchProducts();
    }, []);



    if (error) return <div>Error loading products: {error}</div>;
    
    return (
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
            <div className="sm:flex sm:items-center">
                <div className="sm:flex-auto">
                    <h1 className="text-xl font-semibold text-gray-900">Products</h1>
                    <p className="mt-2 text-sm text-gray-700">
                        A list of all the products in your store
                    </p>
                </div>
            </div>


            <ProductsFilters onApplyFilters={fetchProducts} />

            <ProductList 
                products={products} 
                totalPages={totalPages}
            />
        </div>
    );
};