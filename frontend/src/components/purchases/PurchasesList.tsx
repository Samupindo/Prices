import { useState } from "react";
import type { PurchaseDto } from "../../types/purchase";
import { Link, useNavigate } from "react-router-dom"

interface PurchaseListProps{
    purchases : PurchaseDto[];
    totalPages : number;
}

export const PurchasesList = ({purchases,totalPages}:PurchaseListProps) => {
    const cellPadding = "px-6 py-4";
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
                        </tr>
                    </thead>
                    <tbody className="bg-white divide-y divide-gray-200">
                        {purchases.map((purchase) => (
                            <tr key={purchase.purchaseId}>
                                <td className={cellPadding}>{purchase.purchaseId}</td>
                                <td className={cellPadding}>{purchase.customer.customerId}</td>
                                <td className={cellPadding}>{purchase.totalPrice.toFixed(2)}</td>
                                <td className={cellPadding}>{purchase.shopping ? "Shopping" : "Not Shopping"}</td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>
            
        </div>
    );
};