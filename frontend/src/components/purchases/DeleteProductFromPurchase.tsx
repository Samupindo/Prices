import { useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { deleteProductFromPurchase,getPurchaseById } from "../../services/PurchaseService";
import axiosInstance from "../../config/api-customer";

export const DeleteProductFromPurchase = () => {
    const [purchaseId, setPurchaseId] = useState<number>(0);
    const [productInShopId, setProductInShopId] = useState<number>(0);
    const [error, setError] = useState<string | null>(null);
    const navigate = useNavigate();
    
    const validateIds = async (purchaseId: number, productInShopId: number) => {
        try {
            const response = await getPurchaseById(purchaseId);
            const purchase = response;

            if (!purchase.shopping) {
                throw new Error(`Purchase with ID ${purchaseId} is already finished.`);
            }

            // const productInShopResponse = await axiosInstance.get(`/productInShop/${productInShopId}`);
            // const productInShop = productInShopResponse.data;

            // // Verificar que el productInShop tiene precio
            // if (!productInShop.price) {
            //     throw new Error(`Product in shop with ID ${productInShopId} is not available for purchase.`);
            // }

            return true;
        } catch (error: any) {
            const errorMessage = error.response?.data?.message ||
                error.message ||
                `Invalid IDs. Please verify that purchase ID ${purchaseId} and product in shop ID ${productInShopId} exist.`;
            throw new Error(errorMessage);
        }
    }
    const handleSubmit = async (event: React.FormEvent) => {
        event.preventDefault();

        if (!purchaseId || !productInShopId) {
            setError('Both IDs are required');
            return;
        }

        try {
            setError(null);
            await validateIds(purchaseId, productInShopId);
            try {
                await deleteProductFromPurchase(purchaseId, productInShopId);
                navigate('/purchases');
            } catch (error: any) {
                const errorMessage = error.response?.data?.message ||
                    error.message ||
                    `Failed to delete product in shop with ID ${productInShopId} from purchase with ID ${purchaseId}. ` +
                    `Please verify that the product is available in the shop.`;
                setError(errorMessage);
            }
        } catch (error: any) {
            setError(error.message);
            console.error('Error details:', error);
        }
    }

    const handleOnChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value } = e.target;
        if (name === 'purchaseId') {
            setPurchaseId(parseInt(value));
        } else if (name === 'productInShopId') {
            setProductInShopId(parseInt(value));
        }
    }

    if (error) {
        return (
            <div className="mt-8">
                <div className="rounded-md bg-red-50 p-4">
                    <div className="flex">
                        <div className="ml-3">
                            <h3 className="text-sm font-medium text-red-800">Error</h3>
                            <div className="mt-2 text-sm text-red-700">
                                <p>{error}</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        );
    }

    return (
        <div className="max-w-md w-full space-y-8 bg-white p-8 rounded-lg shadow-lg border border-gray-200">
            <div>
                <h1 className="text-xl font-semibold text-gray-900 mb-4">Delete Product from Purchase</h1>
                <form onSubmit={handleSubmit} className="space-y-4">
                    <div>
                        <label htmlFor="purchaseId" className="block text-sm font-medium text-gray-700">
                            Purchase ID
                        </label>
                        <input
                            type="number"
                            id="purchaseId"
                            name="purchaseId"
                            value={purchaseId}
                            onChange={handleOnChange}
                            className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm"
                            required
                        />
                    </div>
                    <div>
                        <label htmlFor="productInShopId" className="block text-sm font-medium text-gray-700">
                            Product in Shop ID
                        </label>
                        <input
                            type="number"
                            id="productInShopId"
                            name="productInShopId"
                            value={productInShopId}
                            onChange={handleOnChange}
                            className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm"
                            required
                        />
                    </div>
                    <button
                        type="submit"
                        className="inline-flex justify-center py-2 px-4 border border-transparent shadow-sm text-sm font-medium rounded-md text-black bg-indigo-600 hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500 w-full"
                    >
                        Delete Product
                    </button>
                </form>
            </div>
        </div>
    );
};