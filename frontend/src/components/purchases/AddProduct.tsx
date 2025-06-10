import { useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { addProductToPurchase, getPurchaseById } from "../../services/PurchaseService";
import { PurchaseDetail } from "./PurchaseDetail";

export const AddProduct = () => {
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
            await addProductToPurchase(purchaseIdNumber, productInShopId);
            navigate(`/purchases/${purchaseId}`);
        } catch (error: any) {
            setError(error.message);
            console.error('Error adding product:', error);
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
        <div className="container mx-auto p-8">
            <div className="max-w-4xl mx-auto mb-6 mt-10">
                <div className="relative transform overflow-hidden rounded-lg bg-white text-left shadow-xl transition-all sm:my-8 sm:w-full sm:max-w-lg">
                    <div className="bg-white px-4 pt-5 pb-4 sm:p-6 sm:pb-4">
                        <div className="sm:flex sm:items-start">
                            <div className="mx-auto flex size-12 shrink-0 items-center justify-center rounded-full bg-indigo-100 sm:mx-0 sm:size-10">
                                <svg className="size-6 text-indigo-600" fill="none" viewBox="0 0 24 24" strokeWidth="1.5" stroke="currentColor" aria-hidden="true" data-slot="icon">
                                    <path strokeLinecap="round" strokeLinejoin="round" d="M16.862 4.487l1.687-1.688a1.875 1.875 0 112.652 2.652L10.582 16.07a4.5 4.5 0 01-1.897 1.13L6 18l.8-2.685a4.5 4.5 0 011.13-1.897l8.932-8.931zm0 0L19.5 7.125M18 14v4.75A2.25 2.25 0 0115.75 21H5.25A2.25 2.25 0 013 18.75V8.25A2.25 2.25 0 015.25 6H10" />
                                </svg>
                            </div>
                            <div className="mt-3 text-center sm:mt-0 sm:ml-4 sm:text-left">
                                <h3 className="text-base font-semibold text-gray-900" id="modal-title">Add Product to Purchase</h3>
                                <div className="mt-2">
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
                                    <form onSubmit={handleSubmit} className="space-y-6">
                                        <div>
                                            <label htmlFor="productInShopId" className="block text-sm font-medium text-gray-700">
                                                Product in Shop ID
                                            </label>
                                            <div className="mt-1">
                                                <input
                                                    type="number"
                                                    id="productInShopId"
                                                    name="productInShopId"
                                                    value={productInShopId}
                                                    onChange={handleOnChange}
                                                    className="shadow-sm focus:ring-indigo-500 focus:border-indigo-500 block w-full sm:text-sm border-gray-300 rounded-md"
                                                    required
                                                    min="1"
                                                />
                                            </div>
                                        </div>

                                        <div className="flex justify-center w-full sm:justify-end">
                                            <button
                                                type="button"
                                                onClick={() => navigate(`/purchases/${purchaseId}`)}
                                                className="mt-3 inline-flex w-full justify-center rounded-md bg-white px-3 py-2 text-sm font-semibold text-gray-900 shadow-xs ring-1 ring-gray-300 ring-inset hover:bg-gray-50 sm:mt-0 sm:w-auto"
                                            >
                                                Cancel
                                            </button>
                                            <button
                                                type="submit"
                                                disabled={isLoading || !productInShopId}
                                                className={`inline-flex w-full justify-center rounded-md bg-indigo-600 px-3 py-2 text-sm font-semibold text-indigo-500 shadow-xs bg-indigo-800 hover:bg-indigo-500 ring-1 ring-indigo-300 ${isLoading || !productInShopId ? 'cursor-not-allowed' : ''} sm:ml-3 sm:w-auto`}
                                            >
                                                {isLoading ? 'Adding...' : 'Add Product'}
                                            </button>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
};