import type { ProductWithShopsDto } from "../../types/shops";
import { Link } from "react-router-dom"

interface ProductListProps {
    products: ProductWithShopsDto[];
    totalPages: number;
}

export const ProductList = ({ products, totalPages }: ProductListProps) => {
    const cellPadding = "px-6 py-4";

    const handleEditClick = (productId: string | number) => {
        console.log("Edit product with ID:", productId);
    };

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
                        {Array.isArray(products) && products.length > 0 ? (
                            products.map((product, productIndex) => {
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
                                                        onClick={() => handleEditClick(product.productId)}
                                                        className="text-indigo-600 hover:text-indigo-900 font-medium"
                                                    >
                                                        Edit
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
                                                    onClick={() => handleEditClick(product.productId)}
                                                    className="text-indigo-600 hover:text-indigo-900 font-medium"
                                                >
                                                    Edit
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
                <div className="py-4">
                    <nav aria-label="Page navigation">
                        <ul className="inline-flex -space-x-px">
                            {[...Array(totalPages).keys()].map((page) => (
                                <li key={page}>
                                    <button
                                        className="py-2 px-3 leading-tight text-gray-500 bg-white rounded-l-lg border border-gray-300 hover:bg-gray-100 hover:text-gray-700"
                                    >
                                        {page + 1}
                                    </button>
                                </li>
                            ))}
                        </ul>
                    </nav>
                </div>
                <button><Link to="/products-create">Añadir un producto</Link></button>

            </div>

        </div>
    );
};