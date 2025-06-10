import { useState } from 'react';
import type { CustomerDto } from '../../types/Customer';
import { useNavigate } from 'react-router-dom';
import type { CustomerFilters } from '../../services/CustomerService';
import { PaginationDefault } from '../PaginationDefault';

interface CustomerListProps {
    customers: CustomerDto[];
    currentPage: number;
    totalPages: number;
    totalElements: number;
    onPageChange: (page: number) => void;
    onFilterChange: (filters: CustomerFilters) => void;
    filters: CustomerFilters;
}
const CustomerList = ({
    customers,
    currentPage,
    totalPages,
    onPageChange,
    onFilterChange,
    filters
}: CustomerListProps) => {
    const navigate = useNavigate();
    const [localFilters, setLocalFilters] = useState(filters);

    type CustomerFiltersKeys = 'name' | 'email' | 'phone';

    const handleSearch = (e: React.ChangeEvent<HTMLInputElement>, field: CustomerFiltersKeys) => {
        const newFilters = { ...localFilters, [field]: e.target.value };
        const emptyFilters = { name: '', email: '', phone: 0 };

        setLocalFilters(newFilters);
        onFilterChange(newFilters);

        if (e.target.value === '') {
            onFilterChange(emptyFilters);
        }

    }

    return (
        <div className="container mx-auto p-8">
            <button
                onClick={() => navigate('/')}
                className="mr-150 bg-blue-500 shadow-md rounded-md text-blue-400 hover:bg-blue-700 text-black font-bold py-2 px-4 rounded-md mb-6"
            >
                Home
            </button>
            <h2 className="text-xl text-center bg-gray-200 rounded-xl py-3 font-bold mb-3 text-gray-900 pb-2 border-b-2 border-gray-200">
                Customer List
            </h2>
            
            <div className="flex justify-between items-center">
                <button
                    onClick={() => navigate("/customers/create")}
                    className="flex w-full md:w-auto shadow-md rounded-md border-gray-300 bg-indigo-600 text-black px-6 py-3 rounded-lg hover:bg-indigo-700 transition-colors duration-150 text-base font-semibold focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500">
                    Add Customer
                </button>
            </div>

            <div className="mb-6 bg-white p-4 rounded-lg shadow-md">
                <div className="grid grid-cols-1 md:grid-cols-3 gap-4 mb-4">
                    <div className="flex flex-col">
                        <label className="text-sm font-medium text-gray-700 mb-1">Name</label>
                        <input
                            type="text"
                            value={localFilters.name || ''}
                            onChange={(e) => handleSearch(e, 'name')}
                            className="rounded-md border border-gray-300 px-3 py-2 focus:ring-2 focus:ring-blue-500"
                            placeholder="Search by name..."
                        />
                    </div>
                    <div className="flex flex-col">
                        <label className="text-sm font-medium text-gray-700 mb-1">Email</label>
                        <input
                            type="text"
                            value={localFilters.email || ''}
                            onChange={(e) => handleSearch(e, 'email')}
                            className="rounded-md border border-gray-300 px-3 py-2 focus:ring-2 focus:ring-blue-500"
                            placeholder="Search by email..."
                        />
                    </div>
                    <div className="flex flex-col">
                        <label className="text-sm font-medium text-gray-700 mb-1">Phone</label>
                        <input
                            type="number"
                            value={localFilters.phone || ''}
                            onChange={(e) => handleSearch(e, 'phone')}
                            className="rounded-md border border-gray-300 px-3 py-2 focus:ring-2 focus:ring-blue-500"
                            placeholder="Search by phone..."
                        />
                    </div>
                </div>
            </div>

            <div className="relative overflow-x-auto shadow-md mt-10 sm:rounded-lg">
                <table className="min-w-full divide-y divide-gray-200">
                    <thead className="bg-black text-white sticky top-0">
                        <tr>
                            <th className="px-14 py-3 text-left text-xs font-medium uppercase tracking-wider w-2/6">Name</th>
                            <th className="px-20 py-3 text-left text-xs font-medium uppercase tracking-wider w-2/6">Email</th>
                            <th className="px-14 py-3 text-left text-xs font-medium uppercase tracking-wider w-1/6">Phone</th>
                            <th className="px-20 py-3 text-left text-xs font-medium uppercase tracking-wider w-2/6">Actions</th>
                        </tr>
                    </thead>
                    <tbody className="bg-white divide-y divide-gray-200 overflow-y-auto max-h-[50vh]">
                        {customers.map((customer: CustomerDto) => (
                            <tr onClick={() => navigate(`/customers/${customer.customerId}`)} key={customer.customerId}
                                className="hover:bg-teal-100 transition-colors duration-200 cursor-pointer">
                                <td className="px-6 py-4 whitespace-nowrap w-2/6">{customer.name}</td>
                                <td className="px-6 py-4 whitespace-nowrap w-2/6">{customer.email}</td>
                                <td className="px-6 py-4 whitespace-nowrap w-1/6">{customer.phone}</td>
                                <td className="px-6 py-4 whitespace-nowrap w-2/6">
                                    <button className="bg-yellow-600 shadow-md rounded-md hover:bg-yellow-800 ml-2 text-green-600" onClick={(e) => { e.stopPropagation(); navigate(`/customers/${customer.customerId}/edit`) }}
                                    >
                                        Edit
                                    </button>
                                    <button onClick={(e) => { e.stopPropagation(); navigate(`/customers/${customer.customerId}/delete`) }}
                                        className="bg-red-600 shadow-md rounded-md text-red-500 hover:bg-red-800 ml-2">
                                        Delete
                                    </button>
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>
            <div className="flex items-center gap-6 mt-10">

                <PaginationDefault
                    currentPage={currentPage}
                    totalPages={totalPages}
                    onPageChange={onPageChange}
                />
            </div>
        </div>
    );
};

export default CustomerList;