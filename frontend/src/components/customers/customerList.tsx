import type { CustomerDto } from './types/customer';
import { useNavigate } from 'react-router-dom';


interface CustomerListProps {
    customers: CustomerDto[];
}

const CustomerList = ({ customers }: CustomerListProps) => {

    const navigate = useNavigate();
    return (
        <div className="container mx-auto p-8">
            <h2 className="text-2xl font-bold mb-8 text-gray-900 pb-2 border-b-2 border-gray-200">
                Customer List
            </h2>
            
            <div className="relative overflow-x-auto shadow-md sm:rounded-lg">
                <table className="min-w-full divide-y divide-gray-200">
                    <thead className="bg-black text-white sticky top-0">
                        <tr>
                            <th className="px-6 py- text-left text-xs font-medium uppercase tracking-wider w-1/6">ID</th>
                            <th className="px-14 py-3 text-left text-xs font-medium uppercase tracking-wider w-2/6">Name</th>
                            <th className="px-14 py-3 text-left text-xs font-medium uppercase tracking-wider w-1/6">Phone</th>
                            <th className="px-20 py-3 text-left text-xs font-medium uppercase tracking-wider w-2/6">Email</th>
                            <th className="px-20 py-3 text-left text-xs font-medium uppercase tracking-wider w-2/6">Actions</th>
                        </tr>
                    </thead>
                    <tbody className="bg-white divide-y divide-gray-200 overflow-y-auto max-h-[50vh]">
                        {customers.map((customer: CustomerDto) => (
                            <tr key={customer.customerId} 
                                className="hover:bg-gray-100 transition-colors duration-200">
                                <td className="px-6 py-4 whitespace-nowrap w-1/6">{customer.customerId}</td>
                                <td className="px-6 py-4 whitespace-nowrap w-2/6">{customer.name}</td>
                                <td className="px-6 py-4 whitespace-nowrap w-1/6">{customer.phone}</td>
                                <td className="px-6 py-4 whitespace-nowrap w-2/6">{customer.email}</td>
                                <td className="px-6 py-4 whitespace-nowrap w-2/6">
                                    <button className="bg-blue-600 hover:bg-blue-800" onClick={() => navigate(`/customers/${customer.customerId}`)}>
                                        View
                                        </button>
                                    <button className="bg-yellow-600 hover:bg-yellow-800 ml-2" onClick={() => navigate(`/customers/${customer.customerId}/update`)}
                                        >
                                            Edit
                                    </button>
                                    <button className="bg-red-600 hover:bg-red-800 ml-2">
                                        <a href={`/customers/delete/${customer.customerId}`}>Delete</a>
                                    </button>
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </table> 
            </div>
            <button
                    onClick={() => navigate("/customers-createCustomers")}
                    className="mt-4 bg-blue-500 text-black flex justify-left px-4 py-2 rounded hover:bg-blue-600 transition-colors duration-200">
                    Add Customer
                </button>
        </div>
    );
};

export default CustomerList;