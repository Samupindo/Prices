import type { PurchaseDto } from "../../types/Purchase";
import { useNavigate } from "react-router-dom";
import { PaginationDefault } from "../PaginationDefault";

interface PurchaseListProps {
    purchases: PurchaseDto[];
    totalPages: number;
    currentPage: number;
    onPageChange: (page: number) => void;
}

export const PurchasesList = ({ purchases, totalPages, currentPage, onPageChange }: PurchaseListProps) => {
    const navigate = useNavigate();

    return (
        <div className="p-4">
            <div className="shadow-md sm:rounded-lg overflow-x-auto mb-4">
                <table className="min-w-full divide-y divide-gray-200">
                    <thead className="bg-gray-100">
                        <tr>
                            <th scope="col" className="text-left text-xs font-medium text-gray-500 uppercase tracking-wider px-6 py-4">
                                Customer ID
                            </th>
                            <th scope="col" className="text-left text-xs font-medium text-gray-500 uppercase tracking-wider px-6 py-4">
                                Total Price
                            </th>
                            <th scope="col" className="text-left text-xs font-medium text-gray-500 uppercase tracking-wider px-6 py-4">
                                Shopping Status
                            </th>
                            <th scope="col" className="text-left text-xs font-medium text-gray-500 uppercase tracking-wider px-6 py-4">
                                Actions
                            </th>
                        </tr>
                    </thead>
                    <tbody className="bg-white divide-y divide-gray-200">
                        {purchases.map((purchase) => (
                            <tr key={purchase.purchaseId}>
                                <td className="px-6 py-4">{purchase.customer.customerId}</td>
                                <td className="px-6 py-4">{purchase.totalPrice.toFixed(2)}</td>
                                <td className="px-6 py-4">{purchase.shopping ? "Shopping" : "Not Shopping"}</td>
                                <td className="px-6 py-4">
                                    <div className="flex justify-center gap-1">
                                        <button
                                            onClick={(e) => {
                                                e.stopPropagation();
                                                navigate(`/purchases/${purchase.purchaseId}/delete`);
                                            }}
                                            className="text-red-600 hover:text-indigo-900 hover:underline focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500 rounded-md"
                                        >
                                            Delete
                                        </button>
                                        {purchase.shopping ? (
                                            <>
                                                <button
                                                    onClick={(e) => {
                                                        e.stopPropagation();
                                                        navigate(`/purchases/${purchase.purchaseId}/finish`);
                                                    }}
                                                    className="text-green-500 hover:text-indigo-900 hover:underline focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500 rounded-md"
                                                >
                                                    Finish
                                                </button>
                                                <button
                                                    onClick={(e) => {
                                                        e.stopPropagation();
                                                        navigate(`/purchases/${purchase.purchaseId}/addProduct`);
                                                    }}
                                                    className="text-blue-500 hover:text-indigo-900 hover:underline focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500 rounded-md"
                                                >
                                                    Add Products
                                                </button>
                                                <button
                                                    onClick={(e) => {
                                                        e.stopPropagation();
                                                        navigate(`/purchases/${purchase.purchaseId}/deleteProduct`);
                                                    }}
                                                    className="text-red-600 hover:text-indigo-900 hover:underline focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500 rounded-md"
                                                >
                                                    Delete Product
                                                </button>
                                            </>
                                        ) : (
                                            <>
                                                <button
                                                    className="text-gray-400 hover:text-gray-400 cursor-not-allowed font-medium"
                                                    disabled
                                                >
                                                    Finish
                                                </button>
                                                <button
                                                    className="text-gray-400 hover:text-gray-400 cursor-not-allowed font-medium"
                                                    disabled
                                                >
                                                    Add Products
                                                </button>
                                                <button
                                                    className="text-gray-400 hover:text-gray-400 cursor-not-allowed font-medium"
                                                    disabled
                                                >
                                                    Delete Product
                                                </button>
                                            </>
                                        )}
                                    </div>
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>

            {/* Paginación */}
            <PaginationDefault
                currentPage={currentPage}
                totalPages={totalPages}
                onPageChange={onPageChange}
            />

        </div>
    );
};