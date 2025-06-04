import { useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { deletePurchase } from "../../services/PurchaseService";
import { PurchaseDetail } from "./PurchaseDetail";

export const DeletePurchase = () => {
    const { purchaseId } = useParams(); 
    const navigate = useNavigate();
    const [error, setError] = useState<string | null>(null);
    const [isLoading, setIsLoading] = useState(false);

    const handleDelete = async () => {
        try {
            setError(null);
            setIsLoading(true);
            if (!purchaseId) {
                return;
            }
            const id = parseInt(purchaseId);
            if (isNaN(id)) {
                return;
            }
            await deletePurchase(id);
            navigate('/purchases');
        } catch (error) {
            console.error('Error deleting purchase:', error);
            setIsLoading(false);
        }
    };

    return (
        <div className="max-w-3xl mx-auto px-4 sm:px-6 lg:px-8">
            <div className="mb-8">
                <PurchaseDetail />
            </div>
            
            <div className="bg-white shadow rounded-lg">
                <div className="p-6">
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
                    
                    <div className="text-center">
                        <h3 className="text-lg font-medium text-gray-900 mb-4">
                            Are you sure you want to remove this purchase?
                        </h3>
                        <p className="text-sm text-gray-500 mb-6">
                            This action cannot be undone
                        </p>
                        <div className="flex justify-center gap-4">
                            <button
                                onClick={() => navigate('/purchases')}
                                className="px-4 py-2 border border-gray-300 rounded-md text-sm font-medium text-gray-700 bg-white hover:bg-gray-50"
                            >
                                Cancel
                            </button>
                            <button
                                onClick={handleDelete}
                                disabled={isLoading}
                                className="px-4 py-2 border border-transparent rounded-md text-sm font-medium text-white bg-red-600 hover:bg-red-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-red-500"
                            >
                                {isLoading ? 'Deleting...' : 'Delete'}
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
};