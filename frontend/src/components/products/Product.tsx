import { useEffect, useState } from "react";
import { getProducts } from "../../services/ProductsService";
import { ProductList } from "./ProductList";
import { ProductsFilters } from "./ProductsFilters";
import type { ProductWithShopsDto } from "../../types/products";
import { useNavigate } from "react-router-dom";

export const Products = () => {
    const [products, setProducts] = useState<ProductWithShopsDto[]>([]);
    const [totalPages, setTotalPages] = useState(0);
    const [currentPage, setCurrentPage] = useState(1); 
    const [error, setError] = useState<string | null>(null);
    const navigate = useNavigate();
    const [findId, setFindId] = useState<string | null>(null);

    const fetchProducts = async (filters?: {
        name?: string;
        priceMin?: number;
        priceMax?: number;
        page?: number;
        size?: number;
    }) => {
        try {
            setError(null);
            const response = await getProducts({
                ...filters,
                page: currentPage,
                size: 10
            });
            setProducts(response.content);
            setTotalPages(response.totalPages);
        } catch (error) {
            setError('Failed to fetch products');
            console.error('Error fetching products:', error);
        }
    };

    const handlePageChange = (page: number) => {
        setCurrentPage(page);
        fetchProducts();
    };

    useEffect(() => {
        fetchProducts();
    }, [currentPage]);

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
            <button
                onClick={() => navigate('/')}
                className="mr-150 bg-blue-500 hover:bg-blue-700 text-black font-bold py-2 px-4 rounded-md mb-6"
            >
                Home
            </button>

            <ProductsFilters onApplyFilters={fetchProducts} />
            <div className="flex space-x-4">
                <input
                    name="productId"
                    type="number"
                    placeholder="Find product by id"
                    onChange={(e) => setFindId(e.target.value)}
                    className="flex-1 rounded-md border border-gray-300 bg-white py-2 px-3 shadow-sm placeholder-gray-400 focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm"
                />
                <button
                    onClick={() => navigate(`/update-products/${findId}`)}
                    className="inline-flex items-center rounded-md border border-transparent bg-indigo-600 px-4 py-2 text-sm font-medium text-black shadow-sm hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500 focus:ring-offset-2 transition-colors duration-200"
                >
                    Search
                </button>
            </div>
            <ProductList
                products={products}
                totalPages={totalPages}
                currentPage={currentPage}
                onPageChange={handlePageChange}
            />
        </div>
    );
};