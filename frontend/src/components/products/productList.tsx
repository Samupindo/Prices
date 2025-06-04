import { useState } from "react";
import type { ProductWithShopsDto } from "../../types/Products";
import { Link, useNavigate } from "react-router-dom";
import { PaginationDefault } from "../PaginationDefault";

interface ProductListProps {
    products: ProductWithShopsDto[];
    totalPages: number;
    currentPage: number;
    onPageChange: (page: number) => void;
}

export const ProductList = ({ products, totalPages, currentPage, onPageChange }: ProductListProps) => {
    const cellPadding = "px-6 py-4";
    const navigate = useNavigate();

    const [filters, setFilters] = useState<{
        name?: string;
        priceMin?: number;
        priceMax?: number;
    }>({
        name: '',
        priceMin: undefined,
        priceMax: undefined,
    });

    const filteredProducts = products.filter(product => {
        if (!filters.name && !filters.priceMin && !filters.priceMax) {
            return true;
        }

        const productName = product.name?.toLowerCase() || "";
        const matchesName = !filters?.name || productName.includes(filters.name?.toLowerCase());

        const hasMatchingShop = product.shop?.some(shop => {
            const price = shop.price;
            const matchesPrice = !filters?.priceMin || price >= filters.priceMin;
            const matchesMaxPrice = !filters?.priceMax || price <= filters.priceMax;
            return matchesPrice && matchesMaxPrice;
        });

        return matchesName && (hasMatchingShop || !product.shop);
    });

    return (
        <div className="p-4">


            <div className="shadow-md sm:rounded-lg overflow-x-auto">
                <table className="min-w-full divide-y divide-gray-200">
                    <thead className="bg-gray-100">
                        <tr>
                            <th
                                scope="col"
                                className={`${cellPadding} text-left text-xs font-medium text-gray-500 uppercase tracking-wider`}
                            >
                                Product
                            </th>
                            <th
                                scope="col"
                                className={`${cellPadding} text-left text-xs font-medium text-gray-500 uppercase tracking-wider`}
                            >
                                Shop
                            </th>
                            <th
                                scope="col"
                                className={`${cellPadding} text-left text-xs font-medium text-gray-500 uppercase tracking-wider`}
                            >
                                Price
                            </th>
                            <th
                                scope="col"
                                className={`${cellPadding} text-center text-xs font-medium text-gray-500 uppercase tracking-wider`} // Centrado para la columna de acciones
                            >
                                Actions
                            </th>
                        </tr>
                    </thead>
                    <tbody className="bg-white divide-y divide-gray-200">
                        {Array.isArray(filteredProducts) && filteredProducts.length > 0 ? (
                            filteredProducts
                                .sort((a, b) => Number(a.productId) - Number(b.productId))
                                .map((product, productIndex) => {
                                    const rowBgClass = productIndex % 2 === 0 ? 'bg-white' : 'bg-gray-50';
                                    const hasShops = product.shop && Array.isArray(product.shop) && product.shop.length > 0;
                                    const shopCount = hasShops ? product.shop.length : 1;

                                    if (hasShops) {
                                        return product.shop.map((shop, shopIndex) => (
                                            <tr
                                                key={`${product.productId}-${shop.productInShopId}`}
                                                className={rowBgClass}
                                            >
                                                {shopIndex === 0 && (

                                                    <td
                                                        rowSpan={shopCount}
                                                        className={`${cellPadding} whitespace-nowrap text-sm text-gray-900 font-medium align-middle text-center`}
                                                    >
                                                        {product.name}
                                                    </td>
                                                )}
                                                <td className={`${cellPadding} whitespace-nowrap text-sm text-gray-700`}>
                                                    Shop {shop.shopId}
                                                </td>
                                                <td className={`${cellPadding} whitespace-nowrap text-sm text-gray-700`}>
                                                    {shop.price} €
                                                </td>
                                                {shopIndex === 0 && (
                                                    <td
                                                        rowSpan={shopCount}
                                                        className={`${cellPadding} whitespace-nowrap text-sm align-middle text-center`}
                                                    >
                                                         <button
                                                            className="text-indigo-600 hover:text-indigo-900 font-medium"
                                                            onClick={() => navigate(`/products/${product.productId}`)}
                                                        >
                                                            View
                                                        </button> 
                                                        <button
                                                            className="text-indigo-600 hover:text-indigo-900 font-medium"
                                                            onClick={() => navigate(`/products/${product.productId}/edit`)}
                                                        >
                                                            Edit
                                                        </button>
                                                        <button
                                                            className="text-indigo-600 hover:text-indigo-900 font-medium"
                                                            onClick={() => navigate(`/products/${product.productId}/delete`)}
                                                        >
                                                            Delete
                                                        </button>
                                                       

                                                    </td>
                                                )}
                                            </tr>
                                        ));
                                    } else {
                                        return (
                                            <tr key={product.productId} className={rowBgClass}>
                                                <td className={`${cellPadding} whitespace-nowrap text-sm text-gray-900 font-medium align-middle text-center`}>
                                                    {product.name}
                                                </td>
                                                <td className={`${cellPadding} whitespace-nowrap text-sm text-gray-500 text-center`}>
                                                    -
                                                </td>
                                                <td className={`${cellPadding} whitespace-nowrap text-sm text-gray-500 text-center`}>
                                                    -
                                                </td>
                                                <td className={`${cellPadding} whitespace-nowrap text-sm align-middle text-center`}>
                                                    <button
                                                        className="text-indigo-600 hover:text-indigo-900 font-medium"
                                                        onClick={() => navigate(`/products/${product.productId}/edit`)}
                                                    >
                                                        Edit
                                                    </button>
                                                    <button
                                                        className="text-indigo-600 hover:text-indigo-900 font-medium"
                                                        onClick={() => navigate(`/products/${product.productId}/delete`)}
                                                    >
                                                        Delete
                                                    </button>
                                                </td>
                                            </tr>
                                        );
                                    }
                                })
                        ) : (
                            <tr>
                                <td
                                    colSpan={4}
                                    className={`${cellPadding} text-center text-sm text-gray-500`}
                                >
                                    No products available
                                </td>
                            </tr>
                        )}
                    </tbody>
                </table>
            </div>

            {/* Paginación */}
            <PaginationDefault
                currentPage={currentPage}
                totalPages={totalPages}
                onPageChange={onPageChange}
            />

            <div className="flex flex-col space-y-4">
                

            </div>
        </div>
    );
};
