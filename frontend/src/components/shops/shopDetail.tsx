import { useNavigate } from "react-router-dom";
import type { ShopDto } from "../../types/Shops";

interface ShopDetailProps {
    shop: ShopDto | null;
}

export const ShopDetail = ({ shop }: ShopDetailProps) => {
    const navigate = useNavigate();

    if (!shop) {
        return (
            <div className="p-8 text-center text-gray-500 text-lg">
                Shop not found.
            </div>
        );
    }

    return (
        <div className="p-6 md:p-8 max-w-6xl mx-auto">
            <div className="flex flex-col sm:flex-row justify-center items-center mb-8 bg-gray-200 rounded-xl py-3">
                <p className="text-4xl font-semibold text-gray-800 mb-4 sm:mb-0">
                    Shop Details
                </p>
            </div>

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

            <div className="mt-6 flex flex-col md:flex-row justify-center items-center space-y-4 md:space-y-0 md:space-x-4">

                <div className="flex flex-col sm:flex-row w-full md:w-auto space-y-3 sm:space-y-0 sm:space-x-4">
                    <button
                        onClick={() => navigate(`/shops/${shop.shopId}/addProduct`)}
                        className="w-full sm:w-auto bg-indigo-600 text-black px-6 py-3 rounded-lg hover:bg-indigo-700 transition-colors duration-150 text-base font-semibold focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500">
                        Add Product
                    </button>
                    <button
                        onClick={() => navigate(`/shops/${shop.shopId}/edit`)}
                        className="w-full sm:w-auto bg-indigo-600 text-black px-6 py-3 rounded-lg hover:bg-indigo-700 transition-colors duration-150 text-base font-semibold focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500">
                        Edit Shop
                    </button>
                    <button
                        onClick={() => navigate(`/shops/${shop.shopId}/delete`)}
                        className="w-full sm:w-auto bg-red-600 text-black px-6 py-3 rounded-lg hover:bg-red-700 transition-colors duration-150 text-base font-semibold focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-red-500">
                        Delete Shop
                    </button>
                </div>
            </div>
        </div>
    );
};