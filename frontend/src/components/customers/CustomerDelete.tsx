import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import type { CustomerDto } from "./types/customer";
import { deleteCustomer, getCustomerById } from "./services/customerService";


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
            <h2 className="text-2xl font-bold mb-8 text-gray-900 pb-2 border-b-2 border-gray-200">
                Customer
            </h2>
            <div className=" max-w-4xl mx-auto  mb-6 mt-10">
                <div className="bg-white shadow-md rounded-lg p-6">
                    <p className="text-gray-700 mb-4"><strong>ID:</strong> {customer.customerId}</p>
                    <p className="text-gray-700 mb-4"><strong>Name:</strong> {customer.name}</p>
                    <p className="text-gray-700 mb-4"><strong>Phone:</strong> {customer.phone}</p>
                    <p className="text-gray-700 mb-4"><strong>Email:</strong> {customer.email}</p>
                </div>
                <div className="relative transform overflow-hidden rounded-lg bg-white text-left shadow-xl transition-all sm:my-8 sm:w-full sm:max-w-lg">
                    <div className="bg-white px-4 pt-5 pb-4 sm:p-6 sm:pb-4">
                        <div className="sm:flex sm:items-start">
                            <div className="mx-auto flex size-12 shrink-0 items-center justify-center rounded-full bg-red-100 sm:mx-0 sm:size-10">
                                <svg className="size-6 text-red-600" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" aria-hidden="true" data-slot="icon">
                                    <path stroke-linecap="round" stroke-linejoin="round" d="M12 9v3.75m-9.303 3.376c-.866 1.5.217 3.374 1.948 3.374h14.71c1.73 0 2.813-1.874 1.948-3.374L13.949 3.378c-.866-1.5-3.032-1.5-3.898 0L2.697 16.126ZM12 15.75h.007v.008H12v-.008Z" />
                                </svg>
                            </div>
                            <div className="mt-3 text-center sm:mt-0 sm:ml-4 sm:text-left">
                                <h3 className="text-base font-semibold text-gray-900" id="modal-title">Delete Customer</h3>
                                <div className="mt-2">
                                    <p className="text-sm text-gray-500">Are you sure you want to delete this customer? </p>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div className="bg-gray-50 px-4 py-3 sm:flex sm:flex-row-reverse sm:px-6">
                        <button
                            onClick={handleDeleteCustomer}
                            className="inline-flex w-full justify-center rounded-md bg-red-600 px-3 py-2 text-sm font-semibold text-black shadow-xs bg-red-800 hover:bg-red-500 ring-1 ring-red-300  sm:ml-3 sm:w-auto">Deactivate</button>
                        <button
                            onClick={() => navigate("/customers")}
                            className="mt-3 inline-flex w-full justify-center rounded-md bg-white px-3 py-2 text-sm font-semibold text-gray-900 shadow-xs ring-1 ring-gray-300 ring-inset hover:bg-gray-50 sm:mt-0 sm:w-auto">Cancel</button>
                    </div>
                </div>
            </div>
        </div>
    );
};