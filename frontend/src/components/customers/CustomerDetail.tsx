import { useNavigate } from "react-router-dom";
import type { CustomerDto } from "./types/customer";
import { useState } from "react";


interface CustomerDetailProps {
    customer: CustomerDto | null;
}

const CustomerDetail = ({ customer }: CustomerDetailProps) => {
    const navigate = useNavigate();
    const [isEditing, setIsEditing] = useState(false);
    if (!customer) {
        return <div className="text-red-500">Customer not found</div>;
    }

    return (
        <div className="container mx-auto p-8">
            <h2 className="text-2xl font-bold mb-8 text-gray-900 pb-2 border-b-2 border-gray-200">
                Customer Detail
            </h2>
            <div className="flex flex-row space-x-10  items-center mb-6">
                <div className="bg-white shadow-md rounded-lg p-6">
                    <p className="text-gray-700 mb-4"><strong>ID:</strong> {customer.customerId}</p>
                    <p className="text-gray-700 mb-4"><strong>Name:</strong> {customer.name}</p>
                    <p className="text-gray-700 mb-4"><strong>Phone:</strong> {customer.phone}</p>
                    <p className="text-gray-700 mb-4"><strong>Email:</strong> {customer.email}</p>
                    <button
                        onClick={() => navigate("/customers")}
                        className="ml- bg-gray-500 text-black mr-10 rounded hover:bg-gray-600 transition-colors duration-00">
                        Back to List
                    </button>
                    <button
                        onClick={() => setIsEditing(!isEditing)}
                        className="bg-blue-500 text-black px-4 py-2 rounded hover:bg-blue-600 transition-colors duration-200">
                        {isEditing ? "Cancel Edit" : "Edit"}
                    </button>
                    
                </div>
                {isEditing && (
                    <div className="mt-6 shadow-md rounded-lg p-6">
                        <form className="space-y-4">
                            <div>
                                <label className="block text-gray-700">Name</label>
                                <input
                                    type="text"
                                    className="w-full px-3 py-2 border rounded"
                                />
                            </div>
                            <div>
                                <label className="block text-gray-700">Phone</label>
                                <input
                                    type="text"
                                    className="w-full px-3 py-2 border rounded"
                                />
                            </div>
                            <div>
                                <label className="block text-gray-700">Email</label>
                                <input
                                    type="email"
                                    className="w-full px-3 py-2 border rounded"
                                />
                            </div>
                            <button
                                type="submit"
                                className="bg-green-500 text-black px-4 py-2 rounded hover:bg-green-600 transition-colors duration-200">
                                Save Changes
                            </button>
                        </form>
                    </div>
                )}
            </div>
        </div>
    );
};
export default CustomerDetail;