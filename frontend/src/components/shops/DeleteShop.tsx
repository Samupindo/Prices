import { useEffect, useState } from "react";
import { deleteShop } from "../../services/ShopsService";
import { useParams, useNavigate } from "react-router-dom";

export const DeleteShop = () => {
    const { shopId } = useParams();
    const navigate = useNavigate();
    const [isDeleting, setIsDeleting] = useState(false);
    const [shouldDelete, setShouldDelete] = useState(false);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        if (shouldDelete && shopId) {
            const performDelete = async () => {
                try {
                    setIsDeleting(true);
                    await deleteShop(shopId);
                    navigate('/shops');
                } catch (error) {
                    setError(error instanceof Error ? error.message : "An error occurred");
                    setIsDeleting(false);
                    setShouldDelete(false);
                }
            };
            performDelete();
        }
    }, [shouldDelete, shopId, navigate]);

    if (!shopId) return <div>Shop ID not found</div>;

    const handleDelete = () => {
        setShouldDelete(true);
    };

    const handleCancel = () => {
        navigate('/shops');
    };

    return (
        <div className="fixed inset-0 bg-gray-600 bg-opacity-50 flex items-center justify-center p-4">
            <div className="bg-white rounded-lg p-6 max-w-sm w-full">
                <h2 className="text-xl font-bold mb-4">Delete Shop</h2>
                <p className="text-gray-600 mb-6">
                    Are you sure you want to delete this shop? This action cannot be undone.
                </p>

                {error && (
                    <div className="mb-4 p-3 bg-red-100 border border-red-400 text-red-700 rounded">
                        {error}
                    </div>
                )}

                <div className="flex justify-center space-x-3">
                    <button
                        type="button"
                        onClick={handleCancel}
                        disabled={isDeleting}
                        className="px-4 py-2 bg-gray-200 text-gray-800 rounded hover:bg-gray-300 focus:outline-none focus:ring-2 focus:ring-gray-400"
                    >
                        Cancel
                    </button>
                    <button
                        type="button"
                        onClick={handleDelete}
                        disabled={isDeleting}
                        className="px-4 py-2 bg-red-600 text-red-600 rounded hover:bg-red-700 focus:outline-none focus:ring-2 focus:ring-red-500"
                    >
                        {isDeleting ? 'Deleting...' : 'Delete'}
                    </button>
                </div>
            </div>
        </div>
    );
};