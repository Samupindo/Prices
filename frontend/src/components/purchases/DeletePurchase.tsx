import { useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { deletePurchase } from "../../services/PurchaseService";
import { PurchaseDetail } from "./PurchaseDetail";

export const DeletePurchase = ()=>{

    const {id} = useParams();
    const navigate = useNavigate();
    const [error, setError] = useState<string | null>(null);
    const [isLoading, setIsLoading] = useState(false);

    const handleDelete = async () => {
        if (!id) {
            setError('Purchase ID is required');
            return;
        }
        try {
            setError(null);
            setIsLoading(true);
            const purchaseId = parseInt(id);
            if (isNaN(purchaseId)) {
                setError('Invalid purchase ID');
                setIsLoading(false);
                return;
            }
            await deletePurchase(purchaseId);
            navigate('/purchases');
        } catch (error) {
            setError('Failed to delete purchase');
            console.error('Error deleting purchase:', error);
        }
    };

    return (
            <div className="max-w-3xl mx-auto px-4 sm:px-6 lg:px-8">
                <button
                    onClick={() => navigate('/purchases')}
                    className="mr-150 bg-blue-500 hover:bg-blue-700 text-black font-bold py-2 px-4 rounded-md mb-6" 
                >
                    Back
                </button>
                <div className="mb-8">
                    <PurchaseDetail />
                </div>
    
                <div className="bg-white shadow rounded-lg">
                    <div className="p-6">
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
                                    className="inline-flex justify-center py-2 px-4 border border-gray-300 shadow-sm text-sm font-medium rounded-md text-gray-700 bg-white hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500"
                                >
                                    Cancel
                                </button>
                                <button
                                    onClick={handleDelete}
                                    disabled={isLoading}
                                    className={`inline-flex justify-center py-2 px-4 border border-transparent shadow-sm text-sm font-medium rounded-md focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-red-500 ${isLoading ? 'bg-red-400 cursor-not-allowed' : 'bg-red-600 hover:bg-red-700'}`}
                                >
                                    {isLoading ? 'Deleting...' : 'Delete'}
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        );
}