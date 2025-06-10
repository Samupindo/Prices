import { useParams } from "react-router-dom";
import { useState } from "react";
import { finishPurchase } from "../../services/PurchaseService";
import { useNavigate } from "react-router-dom";
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
        <div className="container mx-auto p-8">
            <div className="max-w-4xl mx-auto mb-6 mt-10">
                <div className="relative transform overflow-hidden rounded-lg bg-white text-left shadow-xl transition-all sm:my-8 sm:w-full sm:max-w-lg">
                    <div className="bg-white px-4 pt-5 pb-4 sm:p-6 sm:pb-4">
                        <div className="sm:flex sm:items-start">
                            <div className="mx-auto flex size-12 shrink-0 items-center justify-center rounded-full bg-red-100 sm:mx-0 sm:size-10">
                                <svg className="size-6 text-red-600" fill="none" viewBox="0 0 24 24" strokeWidth="1.5" stroke="currentColor" aria-hidden="true" data-slot="icon">
                                    <path strokeLinecap="round" strokeLinejoin="round" d="M12 9v3.75m-9.303 3.376c-.866 1.5.217 3.374 1.948 3.374h14.71c1.73 0 2.813-1.874 1.948-3.374L13.949 3.378c-.866-1.5-3.032-1.5-3.898 0L2.697 16.126ZM12 15.75h.007v.008H12v-.008Z" />
                                </svg>
                            </div>
                            <div className="mt-3 text-center sm:mt-0 sm:ml-4 sm:text-left">
                                <h3 className="text-base font-semibold text-gray-900" id="modal-title">Finish Purchase</h3>
                                <div className="mt-2">
                                    <p className="text-sm text-gray-500 text-center">Are you sure you want to finish this purchase? This action cannot be undone.</p>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div className="bg-gray-50 px-4 py-3 sm:flex sm:flex-row-reverse sm:px-6">
                        <div className="flex justify-center w-full sm:justify-end">
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
                            <button
                                onClick={() => navigate('/purchases')}
                                className="mt-3 inline-flex w-full justify-center rounded-md bg-white px-3 py-2 text-sm font-semibold text-gray-900 shadow-xs ring-1 ring-gray-300 ring-inset hover:bg-gray-50 sm:mt-0 sm:w-auto"
                            >
                                Cancel
                            </button>
                            <button
                                onClick={handleFinish}
                                disabled={isLoading}
                                className={`inline-flex w-full justify-center rounded-md bg-red-600 px-3 py-2 text-sm font-semibold text-red-500 shadow-xs bg-red-800 hover:bg-red-500 ring-1 ring-red-300 ${isLoading ? 'cursor-not-allowed' : ''} sm:ml-3 sm:w-auto`}
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