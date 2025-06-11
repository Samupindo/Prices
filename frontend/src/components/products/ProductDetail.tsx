import { useState, useEffect } from "react"
import type { ProductWithShopsDto } from "../../types/Products";
import { getProductById } from "../../services/ProductsService";
import { useParams } from "react-router-dom";
import { useNavigate } from "react-router-dom";
import type { ShopDto } from "@/types/Shops";
import { Combobox } from "../Combobox";
import { getShops } from "@/services/ShopsService";

const shopOptions = (shops: ShopDto[]) => {
    return shops.map(shop => ({
        value: shop.shopId,
        label: shop.shopId.toString()
    }));
}

export const ProductDetail = () => {
    const [product, setProduct] = useState<ProductWithShopsDto | null>(null);
    const [error, setError] = useState<string | null>(null);
    const { productId } = useParams();
    const navigate = useNavigate();
    const [shops, setShops] = useState<ShopDto[]>([]);
    const [selectedShop, setSelectedShop] = useState<number | null>(null);
    const isUpdatePage = [`/products/${productId}/delete`, `/products/${productId}/edit`].some(path => location.pathname.includes(path));
    const fetchProduct = async () => {
        try {
            if (!productId) {
                setError('Product ID is required');
                return;
            }
            const productIdNumber = parseInt(productId);
            if (isNaN(productIdNumber)) {
                setError('Invalid product ID');
                return;
            }

            setError(null);
            const response = await getProductById(productIdNumber);
            setProduct(response);
        } catch (error) {
            setError('Failed to fetch product');
            console.error('Error fetching product:', error);
        }
    };
    const fetchShops = async () => {
        const response = await getShops({ page: 0, size: 100 }).then(response => {
            setShops(response.content);
        }).catch(error => {
            setError('Failed to fetch shops');
            console.error('Error fetching shops:', error);
        });
        return response;
    };

    useEffect(() => {
        fetchProduct();
        fetchShops();
    }, [productId]);
    if (error) {
        return <div>Error: {error}</div>;
    }
    if (!product) {
        return <div>Loading...</div>;
    }

    const handleShopChange = (value: number | null) => {
        setSelectedShop(value);
    };

    const showEditButton = product !== null && !isUpdatePage;
    return (
        <div className="max-w-2xl mx-auto px-4 sm:px-6 lg:px-8">
            <div className="sm:flex sm:items-center">
                <div className="sm:flex-auto">
                    <h1 className="text-xl font-semibold text-gray-900">Product Information</h1>
                </div>
            </div>
            <div className="mt-8">
                <div className="bg-white shadow rounded-lg">
                    <div className="p-6">
                        <div className="grid grid-cols-1 gap-6">
                            <div>
                                <h2 className="text-lg font-medium text-gray-900">{product.name}</h2>
                                <div className="mt-2">
                                    <p className="text-sm text-gray-500">Product ID: {product.productId}</p>
                                </div>
                            </div>
                            <div>
                                <h2 className="text-lg font-medium text-gray-900">Shop Prices</h2>
                                <div className="mt-2">
                                    <ul className="list-disc list-inside">
                                        {product.shop.map((shop, index) => (
                                            <li key={index} className="text-sm text-gray-500 flex items-center justify-between mb-2">
                                                <span>Shop {shop.shopId}: ${shop.price}</span>
                                                <div className="flex flex-row gap-2">
                                                    <button
                                                        onClick={() => navigate(`/shops/${shop.shopId}/editProduct`)}
                                                        className="text-green-600 text-sm"
                                                    >
                                                        Edit
                                                    </button>
                                                        <button
                                                            onClick={() => navigate(`/shops/${shop.shopId}/products/${product.productId}/delete`)}
                                                        className="text-red-600 hover:text-red-800 text-sm"
                                                    >
                                                        Delete
                                                    </button>
                                                </div>
                                            </li>
                                        ))}
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            {showEditButton && (
                <div className="mt-4 flex flex-row gap-8 justify-center items-center">
                    <button
                        type="button"
                        className="rounded-md border border-transparent bg-indigo-600 px-4 py-2 text-sm font-medium text-black shadow-sm hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:ring-offset-2"
                        onClick={() => navigate(`/products/${productId}/edit`)}
                    >
                        Edit Product
                    </button>
                </div>
            )}
        </div>
    );
};