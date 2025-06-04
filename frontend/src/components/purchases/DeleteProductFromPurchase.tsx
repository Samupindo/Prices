import { useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { deleteProductFromPurchase, getPurchaseById } from "../../services/PurchaseService";
import { PurchaseDetail } from "./PurchaseDetail";

export const DeleteProductFromPurchase = () => {
    const { purchaseId } = useParams();
    const [productInShopId, setProductInShopId] = useState<number>(0);
    const [error, setError] = useState<string | null>(null);
    const navigate = useNavigate();
    const [isLoading, setIsLoading] = useState(false);

    const validateIds = async (purchaseId: string, productInShopId: number) => {
        try {
            if (!purchaseId) {
                throw new Error('Purchase ID is required');
            }
            const purchaseIdNumber = parseInt(purchaseId);
            if (isNaN(purchaseIdNumber)) {
                throw new Error('Invalid purchase ID');
            }

            const response = await getPurchaseById(purchaseIdNumber);
            const purchase = response;

            if (!purchase.shopping) {
                throw new Error(`Purchase with ID ${purchaseId} is already finished.`);
            }

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
        setError(null);
        setIsLoading(true);

        try {
            if (!purchaseId) {
                throw new Error('Purchase ID is required');
            }
            const purchaseIdNumber = parseInt(purchaseId);
            if (isNaN(purchaseIdNumber)) {
                throw new Error('Invalid purchase ID');
            }

            await validateIds(purchaseId, productInShopId);
            await deleteProductFromPurchase(purchaseIdNumber, productInShopId);
            navigate(`/purchases/${purchaseId}`);
        } catch (error: any) {
            setError(error.message);
            console.error('Error deleting product:', error);
        } finally {
            setIsLoading(false);
        }
    };

    const handleOnChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value } = e.target;
        if (name === 'productInShopId') {
            setProductInShopId(parseInt(value));
        }
    };

    return (
        <div className="max-w-3xl mx-auto px-4 sm:px-6 lg:px-8">
            <div className="mb-8">
                <PurchaseDetail />
            </div>
            <div className="mb-8">
                <h1 className="text-xl font-semibold text-gray-900 mb-4">Delete Product from Purchase</h1>
                {error && (
                    <div className="mb-4 p-4 rounded-md bg-red-50 border-l-4 border-red-400">
                        <div className="flex">
                            <div className="flex-shrink-0">
                                <svg className="h-5 w-5 text-red-400" viewBox="0 0 20 20" fill="currentColor">
                                    <path fillRule="evenodd" d="M10 18a8 8 0 100-16 8 8 0 000 16zM8.707 7.293a1 1 0 00-1.414 1.414L8.586 10l-1.293 1.293a1 1 0 101.414 1.414L10 11.414l1.293 1.293a1 1 0 001.414-1.414L11.414 10l1.293-1.293a1 1 0 00-1.414-1.414L10 8.586 8.707 7.293z" clipRule="evenodd" />
                                </svg>
                            </div>
                            <div className="ml-3">
                                <h3 className="text-sm font-medium text-red-800">{error}</h3>
                            </div>
                        </div>
                    </div>
                )}

                <form onSubmit={handleSubmit} className="space-y-4">
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
                            min="1"
                        />
                    </div>

                    <div className="flex justify-end space-x-4">
                        <button
                            type="button"
                            onClick={() => navigate(`/purchases/${purchaseId}`)}
                            className="px-4 py-2 border border-gray-300 rounded-md text-sm font-medium text-gray-700 bg-white hover:bg-gray-50"
                        >
                            Cancel
                        </button>
                        <button
                            type="submit"
                            disabled={isLoading || !productInShopId}
                            className={`px-4 py-2 border border-transparent rounded-md text-sm font-medium ${isLoading || !productInShopId ? 'bg-red-400 cursor-not-allowed' : 'bg-red-600 hover:bg-red-700'
                                }`}
                        >
                            {isLoading ? 'Deleting...' : 'Delete Product'}
                        </button>
                    </div>
                </form>
            </div>
        </div>
    );
};