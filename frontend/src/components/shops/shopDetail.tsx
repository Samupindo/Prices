import { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { getShopById } from "../../services/shops-service";
import type { ShopDto } from "../../types/shops";

interface ShopDetailProps {
    shop: ShopDto | null;
}

export const ShopDetail = ({ shop }: ShopDetailProps) => {
    const navigate = useNavigate();

    if (!shop) return <div>Shop not found</div>;

    return (

        <div className="p-4">
            <div className="flex justify-between items-center mb-6">
                <h2 className="text-xl font-semibold">Shop Details</h2>
                <button
                    onClick={() => navigate('/shops')}
                    className="bg-gray-100 text-gray-600 px-4 py-2 rounded-md hover:bg-gray-200 transition-colors duration-150"
                >
                    Back to Shops
                </button>
            </div>

            <div className="bg-white shadow-md rounded-lg overflow-hidden">
                <div className="p-6">
                    <div className="grid grid-cols-2 gap-4">
                        <div className="space-y-2">
                            <p className="text-sm font-medium text-gray-500">Country</p>
                            <p className="text-lg text-gray-900">{shop.country}</p>
                        </div>
                        <div className="space-y-2">
                            <p className="text-sm font-medium text-gray-500">City</p>
                            <p className="text-lg text-gray-900">{shop.city}</p>
                        </div>
                        <div className="col-span-2 space-y-2">
                            <p className="text-sm font-medium text-gray-500">Address</p>
                            <p className="text-lg text-gray-900">{shop.address}</p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
};
