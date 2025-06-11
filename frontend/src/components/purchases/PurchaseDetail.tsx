import { useState, useEffect } from "react";
import type { PurchaseDto } from "../../types/Purchase";
import { useNavigate } from "react-router-dom";
import { getPurchaseById } from "../../services/PurchaseService";
import { useParams } from "react-router-dom";

export const PurchaseDetail = () => {
    const [purchase, setPurchase] = useState<PurchaseDto | null>(null);
    const [error, setError] = useState<string | null>(null);
    const navigate = useNavigate();
    const { purchaseId } = useParams();
    const isUpdatePage = [`/purchases/${purchaseId}/delete`, `/purchases/${purchaseId}/finish`].some(path => location.pathname.includes(path));

    useEffect(() => {
            fetchPurchase();
    }, [purchaseId]);

    const fetchPurchase = async () => {
            try {
                if(!purchaseId) {
                    setError('Purchase ID is required');
                    return;
                }
        
                const purchaseIdNumber = parseInt(purchaseId);
                if(isNaN(purchaseIdNumber)){
                    setError('Invalid purchase ID');
                    return;
                }
        
                const response = await getPurchaseById(purchaseIdNumber);
                setPurchase(response);
            } catch (error) {
                setError('Failed to fetch purchase details');
                console.error('Error fetching purchase:', error);
            }
        };

    

    if (error) {
        return <div>Error: {error}</div>;
    }

    if (!purchase) {
        return <div>Loading...</div>;
    }

    return (
        <div className="max-w-2xl mx-auto px-4 sm:px-6 lg:px-8">
            <div className="sm:flex sm:items-center">
                <div className="sm:flex-auto">
                    <h1 className="text-xl font-semibold text-gray-900">Purchase Information</h1>
                </div>
            </div>

            <div className="mt-8">
                <div className="bg-white shadow rounded-lg">
                    <div className="p-6">
                        <div className="grid grid-cols-1 gap-6">
                            <div>
                                <h2 className="text-lg font-medium text-gray-900">Purchase Details</h2>
                                <div className="mt-2">
                                    <p className="text-sm text-gray-500">Purchase ID: {purchase.purchaseId}</p>
                                    <p className="text-sm text-gray-500">Total Price: {purchase.totalPrice.toFixed(2)} €</p>
                                    <p className="text-sm text-gray-500">Shopping Status: {purchase.shopping ? "Shopping" : "Not Shopping"}</p>
                                </div>
                            </div>

                            <div>
                                <h2 className="text-lg font-medium text-gray-900">Customer Information</h2>
                                <div className="mt-2">
                                    <p className="text-sm text-gray-500">Customer ID: {purchase.customer.customerId}</p>
                                    <p className="text-sm text-gray-500">Name: {purchase.customer.name}</p>
                                    <p className="text-sm text-gray-500">Phone: {purchase.customer.phone}</p>
                                    <p className="text-sm text-gray-500">Email: {purchase.customer.email}</p>
                                </div>
                            </div>

                            <div>
                                <h2 className="text-lg font-medium text-gray-900">Products in Purchase</h2>
                                <div className="mt-2">
                                    <ul className="list-disc list-inside">
                                        {purchase.products.map((product, index) => (
                                            <li key={index} className="text-sm text-gray-500">
                                                <div className="grid grid-cols-4 gap-4">
                                                    <div>Product {index + 1}</div>
                                                    <div>Product ID: {product.productId}</div>
                                                    <div>Shop ID: {product.shopId}</div>
                                                    <div>Price: {product.price.toFixed(2)} €</div>
                                                </div>
                                            </li>
                                        ))}
                                    </ul>
                                </div>
                            </div>

                            <div className="mt-4">
                                {!isUpdatePage && (
                                <div>
                                <button
                                    onClick={() => navigate(`/purchases/${purchaseId}/addProduct`)}
                                    className="text-indigo-600 hover:text-indigo-900 font-medium"
                                >
                                    Add Product to Purchase
                                </button>
                                <button className="text-indigo-600 hover:text-indigo-900 font-medium" onClick={() => navigate(`/purchases/${purchaseId}/deleteProduct`)}>Delete product from purchase</button>
                                </div>
                                )}
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
};