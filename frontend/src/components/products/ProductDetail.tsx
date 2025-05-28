import { useState, useEffect } from "react"
import type { ProductWithShopsDto } from "../../types/products";
import { getProductById } from "../../services/product-service";
import { useParams } from "react-router-dom";

export const ProductDetail = () => {    
    const [product, setProduct] = useState<ProductWithShopsDto | null>(null);
    const [error, setError] = useState<string | null>(null);
    const { id } = useParams();

    const fetchProduct = async () => {
        try {
            if (!id) {
                setError('Product ID is required');
                return;
            }

            const productId = parseInt(id);
            if (isNaN(productId)) {
                setError('Invalid product ID');
                return;
            }

            setError(null);
            const response = await getProductById(productId);
            setProduct(response);
        } catch (error) {
            setError('Failed to fetch product');
            console.error('Error fetching product:', error);
        }
    };

    useEffect(() => {
        fetchProduct();
    }, [id]);

    if (error) {
        return <div>Error: {error}</div>;
    }

    if (!product) {
        return <div>Loading...</div>;
    }

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
                                            <li key={index} className="text-sm text-gray-500">
                                                 Shop {shop.shopId}: ${shop.price}
                                            </li>
                                        ))}
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
};