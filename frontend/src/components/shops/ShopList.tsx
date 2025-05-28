import { useNavigate } from "react-router-dom";
import type { ShopDto } from "../../types/shops";

interface ShopListProps {
    shops: ShopDto[];
}

export const ShopList = ({ shops }: ShopListProps) => {
    const navigate = useNavigate();

    return (
        <div className="p-4">
            <h2 className="text-xl font-semibold mb-3">Shops</h2>

            <div className="shadow-md rounded-lg overflow-hidden bg-white">
                <table className="min-w-full divide-y divide-gray-200">
                    <thead className="bg-gray-50">
                        <tr>
                            <th
                                scope="col"
                                className="px-6 py-3 text-center text-xs font-medium text-gray-500 uppercase tracking-wider">
                                Country
                            </th>
                            <th
                                scope="col"
                                className="px-6 py-3 text-center text-xs font-medium text-gray-500 uppercase tracking-wider">
                                City
                            </th>
                            <th
                                scope="col"
                                className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                                Address
                            </th>
                            <th
                                scope="col"
                                className="px-6 py-3 text-center text-xs font-medium text-gray-500 uppercase tracking-wider">
                                Actions
                            </th>
                        </tr>
                    </thead>
                    <tbody className="bg-white divide-y divide-gray-200">
                        {shops.map((shop, index) => (
                            <tr key={shop.shopId} className={index % 2 === 0 ? 'bg-white' : 'bg-gray-50'}>
                                <td className="px-6 py-4 whitespace-nowrap text-sm font-medium text-gray-900 text-center">
                                    {shop.country}
                                </td>
                                <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500 text-center">
                                    {shop.city}
                                </td>
                                <td className="px-6 py-4 text-sm text-gray-500 text-left">
                                    {shop.address}
                                </td>
                                <td className="px-6 py-4 whitespace-nowrap text-center text-sm font-medium">
                                    <button
                                        onClick={() => navigate(`/shops/${shop.shopId}`)}
                                        className="text-indigo-600 hover:text-indigo-900 hover:underline focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500 rounded-md">
                                        View
                                    </button>
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>
        </div>
    );
};
