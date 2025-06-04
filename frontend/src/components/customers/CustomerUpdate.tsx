import { useNavigate, useParams } from "react-router-dom";
import type { CustomerPutDto } from "../../types/Customer";
import { useState } from "react";
import { updateCustomer } from "../../services/CustomerService";


interface CustomerUpdateProps {
    customer: CustomerPutDto;
}

const CustomerUpdate = ({ customer }: CustomerUpdateProps) => {
    const navigate = useNavigate();
    const { customerId } = useParams();
    const [formData, setFormData] = useState<CustomerPutDto>(customer);
    const [error, setError] = useState<string | null>(null);

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value } = e.target;
        setFormData(prev => ({
            ...prev,
            [name]: value
        }));
    };

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        if (!customerId) return;
        try {
            await updateCustomer(Number(customerId), formData);
            navigate(`/customers/${customerId}`);
        } catch (error: any) {
            setError(error.message);
        }
    };

    return (
        <div className="container shadow-md rounded-lg mx-auto px-8  ">
            <h2 className="text-2xl text-center p-6 font-bold text-gray-900  border-b-2 border-gray-200">
                Update Customer
            </h2>
            {error && <div className="text-red-500 mb-4">{error}</div>}
            <div className="bg-white p-6">
                <form onSubmit={handleSubmit} className="max-w-md space-y-5">
                    <div>
                        <label className="block text-gray-700 text-sm font-bold mb-2">Name</label>
                        <input
                            type="text"
                            name="name"
                            value={formData.name}
                            onChange={handleChange}
                            className="w-60 px-3 py-2 border rounded-md focus:outline-none focus:ring-1 focus:ring-blue-500"
                            required
                        />
                    </div>
                    <div>
                        <label className="block text-gray-700 text-sm font-bold mb-2">Email</label>
                        <input
                            type="email"
                            name="email"
                            value={formData.email}
                            onChange={handleChange}
                            className="w-full px-3 py-2 border rounded-md focus:outline-none focus:ring-1 focus:ring-blue-500"
                            required
                        />
                    </div>
                    <div>
                        <label className="block text-gray-700 text-sm font-bold mb-2">Phone</label>
                        <input
                            type="number"
                            name="phone"
                            value={formData.phone}
                            onChange={handleChange}
                            className="w-full px-3 py-2 border rounded-md focus:outline-none focus:ring-1 focus:ring-blue-500"
                        />
                    </div>
                    <div className="flex justify-center">
                        <button
                            type="submit"
                            className="mt-4 bg-blue-500 text-black px-4 py-2 rounded hover:bg-blue-600 transition-colors duration-200">
                            Update Customer
                        </button>
                    </div>
                </form>
            </div>
        </div>
    );
}
export default CustomerUpdate;