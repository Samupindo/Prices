import { useState } from "react";
import type { PurchaseDto } from "../../types/Purchase";
import { Link, useNavigate } from "react-router-dom";
import { PaginationDefault } from "../PaginationDefault";

interface PurchaseListProps {
    purchases: PurchaseDto[];
    totalPages: number;
    currentPage: number;
    onPageChange: (page: number) => void;
}

export const PurchasesList = ({ purchases, totalPages, currentPage, onPageChange }: PurchaseListProps) => {
    const cellPadding = "px-6 py-4";
    const navigate = useNavigate();
    const [findId, setFindId] = useState<string | null>(null);

    return (
        <div className="p-4">
            <div className="shadow-md sm:rounded-lg overflow-x-auto mb-4">
                <div className="flex-1">
                    <div className="flex space-x-4">
                        
                        <input
                            name="purchaseId"
                            type="number"
                            placeholder="Find purchase by id"
                            onChange={(e) => setFindId(e.target.value)}
                            className="flex-1 rounded-md border border-gray-300 bg-white py-2 px-3 shadow-sm placeholder-gray-400 focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm"
                        />
                        <button
                            onClick={() => navigate(`/purchases/${findId}`)}
                            className="inline-flex items-center rounded-md border border-transparent bg-indigo-600 px-4 py-2 text-sm font-medium text-black shadow-sm hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500 focus:ring-offset-2 transition-colors duration-200"
                        >
                            Buscar
                        </button>
                        <button
                        className="text-indigo-600 hover:text-indigo-900 font-medium"
                        onClick={() => navigate('/purchases/create')}
                    >
                        Add Purchase
                    </button>
                    </div>
                </div>
                <table className="min-w-full divide-y divide-gray-200">
                    <thead className="bg-gray-100">
                        <tr>
                            <th scope="col" className={`${cellPadding} text-left text-xs font-medium text-gray-500 uppercase tracking-wider`}>
                                ID
                            </th>
                            <th scope="col" className={`${cellPadding} text-left text-xs font-medium text-gray-500 uppercase tracking-wider`}>
                                Customer ID
                            </th>
                            <th scope="col" className={`${cellPadding} text-left text-xs font-medium text-gray-500 uppercase tracking-wider`}>
                                Total Price
                            </th>
                            <th scope="col" className={`${cellPadding} text-left text-xs font-medium text-gray-500 uppercase tracking-wider`}>
                                Shopping Status
                            </th>
                            <th scope="col" className={`${cellPadding} text-left text-xs font-medium text-gray-500 uppercase tracking-wider`}>
                                Actions
                            </th>
                        </tr>
                    </thead>
                    <tbody className="bg-white divide-y divide-gray-200">
                        {purchases.map((purchase) => (
                            <tr key={purchase.purchaseId}>
                                <td className={cellPadding}><Link to={`/purchases/${purchase.purchaseId}`}>{purchase.purchaseId}</Link></td>
                                <td className={cellPadding}>{purchase.customer.customerId}</td>
                                <td className={cellPadding}>{purchase.totalPrice.toFixed(2)}</td>
                                <td className={cellPadding}>{purchase.shopping ? "Shopping" : "Not Shopping"}</td>
                                <td className={cellPadding}>
                                    <button className="text-indigo-600 hover:text-indigo-900 font-medium">
                                        <Link to={`/purchases/${purchase.purchaseId}/delete`}>
                                            Delete
                                        </Link>
                                    </button>
                                    {purchase.shopping ? (
                                        <button className="text-indigo-600 hover:text-indigo-900 font-medium">
                                            <Link to={`/purchases/${purchase.purchaseId}/finish`}>Finish</Link>
                                        </button>
                                    ) : (
                                        <button className="text-gray-400 cursor-not-allowed font-medium" disabled>
                                            Finish
                                        </button>
                                    )}
                                    {purchase.shopping ? (
                                        <>
                                            <button className="text-indigo-600 hover:text-indigo-900 font-medium">
                                                <Link to={`/purchases/${purchase.purchaseId}/addProduct`}>Add Products</Link>
                                            </button>
                                            <button className="text-indigo-600 hover:text-indigo-900 font-medium">
                                                <Link to={`/purchases/${purchase.purchaseId}/deleteProduct`}>Delete Product</Link>
                                            </button>
                                        </>
                                    ) : (
                                        <>
                                            <button className="text-gray-400 cursor-not-allowed font-medium" disabled>
                                                Add Products
                                            </button>
                                            <button className="text-gray-400 cursor-not-allowed font-medium" disabled>
                                                Delete Product
                                            </button>
                                        </>
                                    )}
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