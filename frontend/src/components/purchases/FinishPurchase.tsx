import { useParams } from "react-router-dom";
import { useState } from "react";
import { finishPurchase } from "../../services/PurchaseService";
import { useNavigate } from "react-router-dom";
import { PurchaseDetail } from "./PurchaseDetail";
export const FinishPurchase = () => {
    const navigate = useNavigate();
    const { purchaseId } = useParams();
    const [error, setError] = useState<string | null>(null);
    const [isLoading, setIsLoading] = useState(false);




    const handleFinish = async () => {
        if (!purchaseId) {
            setError('Purchase ID is required');
            return;
        }
        try {
            setError(null);
            setIsLoading(true);
            const purchaseIdNumber = parseInt(purchaseId);
            if (isNaN(purchaseIdNumber)) {
                setError('Invalid purchase ID');
                setIsLoading(false);
                return;
            }
            await finishPurchase(purchaseIdNumber);
            navigate('/purchases');
        } catch (error) {
            setError('Failed to finish purchase');
            console.error('Error finishing purchase:', error);
        }
    };
    return (
                <div className="max-w-3xl mx-auto px-4 sm:px-6 lg:px-8">
                    <div className="mb-8">
                        <PurchaseDetail />
                    </div>
        
                    <div className="bg-white shadow rounded-lg">
                        <div className="p-6">
                            <div className="text-center">
                                <h3 className="text-lg font-medium text-gray-900 mb-4">
                                    Are you sure you want finish this purchase?
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
                                        onClick={handleFinish}
                                        disabled={isLoading}
                                        className={`inline-flex justify-center py-2 px-4 border border-transparent shadow-sm text-sm font-medium rounded-md focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-red-500 ${isLoading ? 'bg-red-400 cursor-not-allowed' : 'bg-red-600 hover:bg-red-700'}`}
                                    >
                                        {isLoading ? 'Finishing...' : 'Finish'}
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            );
};