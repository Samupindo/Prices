import { useState } from "react";
import type { PurchaseDto } from "../../types/purchase";
import { Link, useNavigate } from "react-router-dom"

interface PurchaseListProps {
    purchases: PurchaseDto[];
    totalPages: number;
}

export const PurchasesList = ({ purchases, totalPages }: PurchaseListProps) => {
    const cellPadding = "px-6 py-4";
    const navigate = useNavigate();
    const purchaseIdBtn = purchases.map((purchase) => (purchase.purchaseId));
    return (
        <div className="p-4">
            <div className="shadow-md sm:rounded-lg overflow-x-auto">
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
                                    <button className="text-indigo-600 hover:text-indigo-900 font-medium"><Link to={`/finish-purchases/${purchase.purchaseId}`}>Finish</Link></button>
                                    <button className="text-indigo-600 hover:text-indigo-900 font-medium"><Link to={`/delete-purchases/${purchase.purchaseId}`}>Delete</Link></button>
                                </td>
                            </tr>
                            
                        ))}
                    </tbody>
                </table>
            </div>
            <button className="text-indigo-600 hover:text-indigo-900 font-medium" onClick={() => navigate('/create-purchase')}>Add Purchase</button>          
            <button className="text-indigo-600 hover:text-indigo-900 font-medium"><Link to={`/add-purchaseLine`}>Add Product to Purchase</Link></button>

        </div>
    );
};