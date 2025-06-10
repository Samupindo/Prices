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
        <div className="container mx-auto p-8">
            <div className="max-w-4xl mx-auto mb-6 mt-10">
                <div className="bg-white shadow-md rounded-lg p-6">
                    <PurchaseDetail />
                </div>
                <div className="relative transform overflow-hidden rounded-lg bg-white text-left shadow-xl transition-all sm:my-8 sm:w-full sm:max-w-lg">
                    <div className="bg-white px-4 pt-5 pb-4 sm:p-6 sm:pb-4">
                        <div className="sm:flex sm:items-start">
                            <div className="mx-auto flex size-12 shrink-0 items-center justify-center rounded-full bg-red-100 sm:mx-0 sm:size-10">
                                <svg className="size-6 text-red-600" fill="none" viewBox="0 0 24 24" strokeWidth="1.5" stroke="currentColor" aria-hidden="true" data-slot="icon">
                                    <path strokeLinecap="round" strokeLinejoin="round" d="M12 9v3.75m-9.303 3.376c-.866 1.5.217 3.374 1.948 3.374h14.71c1.73 0 2.813-1.874 1.948-3.374L13.949 3.378c-.866-1.5-3.032-1.5-3.898 0L2.697 16.126ZM12 15.75h.007v.008H12v-.008Z" />
                                </svg>
                            </div>
                            <div className="mt-3 text-center sm:mt-0 sm:ml-4 sm:text-left">
                                <h3 className="text-base font-semibold text-gray-900" id="modal-title">Delete Product from Purchase</h3>
                                <div className="mt-2">
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
                                                    className="shadow-sm focus:ring-red-500 focus:border-red-500 block w-full sm:text-sm border-gray-300 rounded-md"
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
                                                className={`inline-flex w-full justify-center rounded-md bg-red-600 px-3 py-2 text-sm font-semibold text-red-500 shadow-xs bg-red-800 hover:bg-red-500 ring-1 ring-red-300 ${isLoading || !productInShopId ? 'cursor-not-allowed' : ''} sm:ml-3 sm:w-auto`}
                                            >
                                                {isLoading ? 'Deleting...' : 'Delete Product'}
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