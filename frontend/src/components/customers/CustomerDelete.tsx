import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import type { CustomerDto } from "./types/customer";
import { deleteCustomer, getCustomerById } from "./services/customerService";
import CustomerDetail from "./CustomerDetail";


export const CustomerDelete = () => {
    const { customerId } = useParams();
    const navigate = useNavigate();
    const [customer, setCustomer] = useState<CustomerDto>();
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        if (customerId) {
            getCustomerById(Number(customerId))
                .then((response) => {
                    setCustomer(response);
                })
                .catch((error) => {
                    setError(error.message);
                });
        }
    }, [customerId]);

    const handleDeleteCustomer = async () => {
        if (!customerId) return;
        try {
            await deleteCustomer(Number(customerId));
            navigate('/customers');
        } catch (error: any) {
            setError(error.message);
        }
    };

    if (error) return <div>Error loading customer: {error}</div>;
    if (!customer) return <div>Loading...</div>;

    return (
        <div className="container mx-auto p-8">
            <CustomerDetail customer={customer} />
            <div className="mt-10 bg-white shadow-md rounded-lg p-6">
                <p className="text-gray-700 mb-4">Are you sure you want to delete this customer?</p>
                <button
                    onClick={() => navigate('/customers')}
                    className="bg-red-500 text-black px-4 py-2 rounded hover:bg-red-600 transition-colors duration-200"
                >
                    Cancel
                </button>
                <button
                    onClick={handleDeleteCustomer}
                    className="bg-gray-500 text-black px-4 py-2 rounded hover:bg-gray-600 transition-colors duration-200"
                >
                    Delete
                </button>
            </div>

        </div>
    );
};