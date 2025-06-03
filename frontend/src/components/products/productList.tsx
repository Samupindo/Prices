import { useState } from "react";
import type { ProductWithShopsDto } from "../../types/shops";
import { Link, useNavigate } from "react-router-dom";

interface ProductListProps {
    products: ProductWithShopsDto[];
    totalPages: number;
    currentPage: number;
    onPageChange: (page: number) => void;
}

export const ProductList = ({ products, totalPages, currentPage, onPageChange }: ProductListProps) => {
    const cellPadding = "px-6 py-4";
    const navigate = useNavigate();

    // Estado para los filtros
    const [filters, setFilters] = useState<{
        name?: string;
        priceMin?: number;
        priceMax?: number;
    }>({
        name: '',
        priceMin: undefined,
        priceMax: undefined,
    });

    // Filtrar productos según los filtros aplicados
    const filteredProducts = products.filter(product => {
        // Si no hay filtros, mostramos todos los productos
        if (!filters.name && !filters.priceMin && !filters.priceMax) {
            return true;
        }

        const productName = product.name?.toLowerCase() || "";
        const matchesName = !filters?.name || productName.includes(filters.name?.toLowerCase());

        // Verificar si el producto tiene al menos un shop que cumpla con los filtros de precio
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
                                ID
                            </th>
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
                                                        {product.productId}
                                                    </td>
                                                )}
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
                                                        >
                                                            <Link to={`/products/${product.productId}`}>View</Link>
                                                        </button> 
                                                        <button
                                                            className="text-indigo-600 hover:text-indigo-900 font-medium"
                                                        >
                                                            <Link to={`/update-products/${product.productId}`}>Edit</Link>
                                                        </button>
                                                        <button
                                                            className="text-indigo-600 hover:text-indigo-900 font-medium"
                                                        >
                                                            <Link to={`/delete-products/${product.productId}`}>Delete</Link>
                                                        </button>
                                                       

                                                    </td>
                                                )}
                                            </tr>
                                        ));
                                    } else {
                                        return (
                                            <tr key={product.productId} className={rowBgClass}>
                                                <td
                                                    className={`${cellPadding} whitespace-nowrap text-sm text-gray-900 font-medium align-middle text-center`}
                                                >
                                                    {product.productId}
                                                </td>
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
                                                    >
                                                        <Link to={`/update-products/${product.productId}`}>Edit</Link>
                                                    </button>
                                                    <button
                                                        className="text-indigo-600 hover:text-indigo-900 font-medium"
                                                    >
                                                        <Link to={`/delete-products/${product.productId}`}>Delete</Link>
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
            <div className="mt-4 flex justify-center items-center space-x-2">
                <button
                    onClick={() => onPageChange(currentPage - 1)}
                    disabled={currentPage === 0}
                    className="px-4 py-2 bg-gray-100 rounded-md hover:bg-gray-200 disabled:opacity-50"
                >
                    Previous
                </button>

                {Array.from({ length: totalPages }, (_, i) => i).map((page) => (
                    <button
                        key={page}
                        onClick={() => onPageChange(page)}
                        className={`px-4 py-2 rounded-md ${currentPage === page
                                ? 'bg-blue-500 text-white'
                                : 'bg-gray-100 hover:bg-gray-200'
                            }`}
                    >
                        {page + 1} {/* Mostramos el número de página +1 para que sea más amigable */}
                    </button>
                ))}

                <button
                    onClick={() => onPageChange(currentPage + 1)}
                    disabled={currentPage === totalPages - 1}
                    className="px-4 py-2 bg-gray-100 rounded-md hover:bg-gray-200 disabled:opacity-50"
                >
                    Next
                </button>
            </div>

            <div className="flex flex-col space-y-4">
                <button className="inline-flex justify-center py-2 px-4 border border-transparent shadow-sm text-sm font-medium rounded-md text-white bg-indigo-600 hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500 transition-colors duration-200">
                    <Link to="/products-create" className="text-white">Añadir producto</Link>
                </button>

            </div>
        </div>
    );
};
