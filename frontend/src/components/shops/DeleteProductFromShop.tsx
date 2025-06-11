import { useState, useEffect } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { deleteProductFromShop, getShopById } from "../../services/ShopsService";
import { getProductById } from "../../services/ProductsService";
import type { ShopDto } from "../../types/Shops";
import type { ProductWithShopsDto } from "../../types/Products";

export const DeleteProductFromShop = () => {
    const { shopId, productId } = useParams();
    const navigate = useNavigate();
    const [isDeleting, setIsDeleting] = useState(false);
    const [error, setError] = useState<string | null>(null);
    const [shop, setShop] = useState<ShopDto | null>(null);
    const [product, setProduct] = useState<ProductWithShopsDto | null>(null);

    useEffect(() => {
        const fetchData = async () => {
            try {
                if (!shopId || !productId) return;
                
                const [shopData, productData] = await Promise.all([
                    getShopById(shopId),
                    getProductById(parseInt(productId))
                ]);
                
                setShop(shopData);
                setProduct(productData);
            } catch (error: any) {
                setError(error.message || "Failed to load shop and product details");
            }
        };
        
        fetchData();
    }, [shopId, productId]);

    if (!shopId || !productId) {
        return <div className="text-red-500">Shop ID and Product ID are required</div>;
    }

    const handleDelete = async () => {
        try {
            setIsDeleting(true);
            await deleteProductFromShop(shopId, productId);
            navigate(`/products/${productId}`, { replace: true });
        } catch (error) {
            setError("An error occurred while removing the product from this shop");
        } finally {
            setIsDeleting(false);
        }
    };

    const handleCancel = () => {
        navigate(`/products/${productId}`, { replace: true });
    };

    if (!shop || !product) {
        return <div className="text-gray-500">Loading...</div>;
    }

    return (
        <div className="max-w-2xl mx-auto px-4 sm:px-6 lg:px-8">
            <div className="sm:flex sm:items-center">
                <div className="sm:flex-auto">
                    <h1 className="text-xl font-semibold text-gray-900">Remove Product from Shop</h1>
                </div>
            </div>
            <div className="mt-8">
                <div className="bg-white shadow rounded-lg p-6">
                    {error && (
                        <div className="mb-4 p-4 bg-red-100 text-red-700 rounded">
                            {error}
                        </div>
                    )}
                    <div>
                        <div className="mb-6">
                            <h2 className="text-lg font-medium text-gray-900">Confirmation</h2>
                            <p className="mt-2 text-sm text-gray-500">
                                You are about to remove the following product from this shop:
                            </p>
                            <div className="mt-4 bg-gray-50 p-4 rounded">
                                <p className="font-medium">Product: {product.name}</p>
                                <p className="text-sm text-gray-600">From: {`${shop.address}, ${shop.city}, ${shop.country}`}</p>
                            </div>
                        </div>
                        <p className="text-gray-700 mb-4">
                            Are you sure you want to remove this product from the shop? This action cannot be undone.
                        </p>
                        <div className="flex justify-end space-x-4">
                            <button
                                type="button"
                                onClick={handleDelete}
                                className="bg-red-600 text-red-500 px-4 py-2 rounded hover:bg-red-700 disabled:opacity-50"
                                disabled={isDeleting}
                            >
                                {isDeleting ? "Removing..." : "Remove Product"}
                            </button>
                            <button
                                type="button"
                                onClick={handleCancel}
                                className="bg-gray-200 text-gray-700 px-4 py-2 rounded hover:bg-gray-300 disabled:opacity-50"
                                disabled={isDeleting}
                            >
                                Cancel
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}; 