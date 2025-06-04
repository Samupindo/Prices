import { useEffect, useState } from "react";
import { deleteShop, getShopById } from "../../services/ShopsService";
import { useParams, useNavigate } from "react-router-dom";
import type { ShopDto } from "../../types/Shops";

export const DeleteShop = () => {
    const { shopId } = useParams();
    const navigate = useNavigate();
    const [isDeleting, setIsDeleting] = useState(false);
    const [shouldDelete, setShouldDelete] = useState(false);
    const [error, setError] = useState<string | null>(null);
    const [shop, setShop] = useState<ShopDto | null>(null);

    useEffect(() => {
        if (shopId) {
            getShopById(shopId)
                .then((response) => {
                    setShop(response);
                })
                .catch((error) => {
                    setError(error.message);
                });
        }
    }, [shopId]);

    if (!shopId) return <div>Shop ID not found</div>;

    const handleDelete = () => {
        if (shop) {
            const performDelete = async () => {
                try {
                    setIsDeleting(true);
                    await deleteShop(shopId);
                    navigate('/shops', { replace: true });
                } catch (error) {
                    setError(error instanceof Error ? error.message : "An error occurred");
                    setIsDeleting(false);
                    setShouldDelete(false);
                }
            };
            performDelete();
        }
    };

    const handleCancel = () => {
        navigate('/shops', { replace: true });
    };

    if (!shop) return <div>Shop not found</div>;

    return (
        <div className="fixed inset-0 bg-gray-600 bg-opacity-50 flex items-center justify-center p-4">
            <div className="bg-white rounded-lg p-6 max-w-sm w-full">
                <h2 className="text-xl font-bold mb-4">Delete Shop</h2>

                <div className="grid grid-cols-1 bg-white text-center shadow-xl rounded-xl overflow-hidden border-gray-200 p-6 sm:p-10 md:p-12">
                    <div>
                        <div className="space-y-1">
                            <p className="text-sm sm:text-base font-semibold text-gray-500 uppercase tracking-wide">Shop ID</p>
                            <p className="text-lg sm:text-xl md:text-2xl text-gray-700 break-words">{shop.shopId}</p>
                        </div>

                        <div className="space-y-1 mt-5">
                            <p className="text-sm sm:text-base font-semibold text-gray-500 uppercase tracking-wide">Country</p>
                            <p className="text-lg sm:text-xl md:text-2xl text-gray-900">{shop.country}</p>
                        </div>

                        <div className="space-y-1 mt-5">
                            <p className="text-sm sm:text-base font-semibold text-gray-500 uppercase tracking-wide">City</p>
                            <p className="text-lg sm:text-xl md:text-2xl text-gray-900">{shop.city}</p>
                        </div>

                        <div className="md:col-span-3 space-y-1 mt-5">
                            <p className="text-sm sm:text-base font-semibold text-gray-500 uppercase tracking-wide">Address</p>
                            <p className="text-lg sm:text-xl md:text-2xl text-gray-900">{shop.address}</p>
                        </div>
                    </div>
                </div>
                
                <p className="text-gray-600 mb-6 mt-4">
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