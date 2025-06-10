import { useEffect, useRef, useState } from "react";
import { getProducts } from "../../services/ProductsService";
import { ProductList } from "./ProductList";
import { ProductsFilters } from "./ProductsFilters";
import type { ProductWithShopsDto } from "../../types/Products";
import { useNavigate } from "react-router-dom";

export const Products = () => {
    const [products, setProducts] = useState<ProductWithShopsDto[]>([]);
    const [totalPages, setTotalPages] = useState(0);
    const [currentPage, setCurrentPage] = useState(0);
    const [error, setError] = useState<string | null>(null);
    const navigate = useNavigate();
    const [findId, setFindId] = useState<string | null>(null);
    const initialized = useRef(false)

    const fetchProducts = async (filters?: {
        name?: string;
        priceMin?: number;
        priceMax?: number;
    }) => {
        try {
            setError(null);
            const response = await getProducts({
                page: currentPage,
                size: 10,
                filters: filters || {}
            });
            setProducts(response.content);
            setTotalPages(response.totalPages);
        } catch (error) {
            setError('Failed to fetch products');
            console.error('Error fetching products:', error);
        }
    };

    const handlePageChange = (page: number) => {
        const backendPage = page - 1;
        if (backendPage >= 0 && backendPage < totalPages) {
            setCurrentPage(backendPage);
        }
    };

    useEffect(() => {
        if (!initialized.current) {
            initialized.current = true
            fetchProducts();
        }
    }, [currentPage]);

    if (error) return <div>Error loading products: {error}</div>;

    return (
        <div className="p-4">
            <button
                onClick={() => navigate('/')}
                className="mr-150 bg-blue-500 hover:bg-blue-700 text-black font-bold py-2 px-4 rounded-md mb-6"
            >
                Home
            </button>
            <h2 className="text-center text-xl font-semibold mb-3 bg-gray-200 rounded-xl py-3">Products</h2>
            <div className="flex justify-start mb-4">
                <button
                    onClick={() => navigate('/products/create')}
                    className="flex w-full md:w-auto bg-indigo-600 text-black px-6 py-3 rounded-lg hover:bg-indigo-700 transition-colors duration-150 text-base font-semibold focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500"
                >
                    Añadir producto
                </button>
            </div>
            <ProductsFilters onApplyFilters={fetchProducts} />
            <ProductList
                products={products}
                totalPages={totalPages}
                currentPage={currentPage + 1}
                onPageChange={handlePageChange}
            />
        </div>
    );
};