import { useState } from "react";
import type { CreateCustomerDto } from "./types/customer";


interface CustomerPostProps {
    onSubmit: (customer: CreateCustomerDto) => void;
}

const CustomerForm = ({ onSubmit }: CustomerPostProps) => {
    const [newCustomer, setNewCustomer] = useState<CreateCustomerDto>({
        name: "",
        phone: 0,
        email: ""
    });

    const handleSubmit = (event: React.FormEvent) => {
        event.preventDefault();
        onSubmit(newCustomer);
    };

    const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value } = event.target;
        setNewCustomer({
            ...newCustomer,
            [name]: name === "phone" ? Number(value) : value
        });
    };
    return (
        <div className="container mx-auto p-8">
            <h2 className="text-2xl font-bold mb-8 text-gray-900 pb-2 border-b-2 border-gray-200">
                Create Customer
            </h2>
            <form onSubmit={handleSubmit} className="max-w-md space-y-4">
                <div>
                    <label className="block text-gray-700 text-sm font-bold mb-2">
                        Name
                    </label>
                    <input
                        type="text"
                        name="name"
                        value={newCustomer.name}
                        onChange={handleChange}
                        className="w-full px-3 py-2 border rounded-md focus:outline-none focus:ring-1 focus:ring-blue-500"
                        required
                    />
                </div>
                <div>
                    <label className="block text-gray-700 text-sm font-bold mb-2">
                        Email
                    </label>
                    <input
                        type="email"
                        name="email"
                        value={newCustomer.email}
                        onChange={handleChange}
                        className="w-full px-3 py-2 border rounded-md focus:outline-none focus:ring-1 focus:ring-blue-500"
                        required
                    />
                </div>
                <div>
                    <label className="block text-gray-700 text-sm font-bold mb-2">
                        Phone
                    </label>
                    <input
                        type="number"
                        name="phone"
                        value={newCustomer.phone}
                        onChange={handleChange}
                        className="w-full px-3 py-2 border rounded-md focus:outline-none focus:ring-1 focus:ring-blue-500"
                        required
                    />
                </div>
                <button
                    type="submit"
                    className="bg-blue-500 text-black px-4 py-2 rounded hover:bg-blue-600 transition-colors duration-200"
                >
                    Create Customer
                </button>
            </form>
        </div>
    );

}
export default CustomerForm;
