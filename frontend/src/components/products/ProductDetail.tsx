 import { useState, useEffect, useRef } from "react"
import type { ProductWithShopsDto } from "../../types/Products";
import { getProductById } from "../../services/ProductsService";
import { useParams } from "react-router-dom";
import {  useNavigate } from "react-router-dom";
export const ProductDetail = () => {    
    const [product, setProduct] = useState<ProductWithShopsDto | null>(null);
    const [error, setError] = useState<string | null>(null);
    const [isLoading, setIsLoading] = useState(false);
    const {productId} = useParams<{ productId: string }>();
    const navigate = useNavigate();
    const initialized = useRef(false)
    const isUpdatePage = [`/products/${productId}/delete`, `/products/${productId}/edit`].some(path => location.pathname.includes(path));
    const fetchProduct = async () => {
        if (isLoading || !productId) return;
        
        setIsLoading(true);
        try {
            const productIdNumber = parseInt(productId);
            if (isNaN(productIdNumber)) {
                setError('Invalid product ID');
                setIsLoading(false);
                return;
            }
            setError(null);
            const response = await getProductById(productIdNumber);
            setProduct(response);
        } catch (error) {
            setError('Failed to fetch product');
            console.error('Error fetching product:', error);
        } finally {
            setIsLoading(false);
        }
    };

    useEffect(() => {
        if (!initialized.current) {
            initialized.current = true
            if (productId) {
                fetchProduct();
            }
        }
    }, [productId]);
    if (isLoading) {
        return <div>Loading...</div>;
    }
    if (error) {
        return <div>Error: {error}</div>;
    }
    if (!product) {
        return <div>Loading...</div>;
    }
    // Mostrar el botón de editar solo si el producto existe y no estamos en una página de actualización/eliminación
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
            {showEditButton && (
                <div className="mt-4 sm:mt-0 sm:ml-16 sm:flex-none">
                    <button
                        type="button"
                        className="inline-flex items-center justify-center rounded-md border border-transparent bg-indigo-600 px-4 py-2 text-sm font-medium text-black shadow-sm hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:ring-offset-2 mr-11 mt-10"
                        onClick={() => navigate(`/products/${productId}/edit`)}
                    >
                        Editar
                    </button>
                </div>
            )}
        </div>
    );
};