import { useEffect, useState } from 'react';
import type { CustomerDto } from './types/Customer';
import { useNavigate } from 'react-router-dom';
import {
    Pagination,
    PaginationContent,
    PaginationItem,
    PaginationLink,
    PaginationNext,
    PaginationPrevious,
} from "@/components/ui/pagination"
import type { CustomerFilters } from './services/customerService';

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

    type CustomerFiltersKeys ='name' | 'email' | 'phone';

    const handleSearch = (e: React.ChangeEvent<HTMLInputElement>, field: CustomerFiltersKeys) => {
        const newFilters = { ...localFilters, [field]: e.target.value };
        const emptyFilters = { name: '', email: '', phone: '' };

        setLocalFilters(newFilters);
        onFilterChange(localFilters);

        if(e.target.value === '') {
            onFilterChange(emptyFilters);
        }
        
    };

    return (
        <div className="container mx-auto p-8">
            <h2 className="text-2xl font-bold mb-8 text-gray-900 pb-2 border-b-2 border-gray-200">
                Customer List
            </h2>
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
                            type="text"
                            value={localFilters.phone || ''}
                            onChange={(e) => handleSearch(e, 'phone')}
                            className="rounded-md border border-gray-300 px-3 py-2 focus:ring-2 focus:ring-blue-500"
                            placeholder="Search by phone..."
                        />
                    </div>
                </div>
            </div>
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
            <div className="flex items-center gap-6">

                <Pagination>
                    <PaginationContent>
                        <PaginationItem>
                            <PaginationPrevious
                                onClick={() => onPageChange(Math.max(1, currentPage - 1))}
                                className={`cursor-pointer ${currentPage === 1 ? 'pointer-events-none opacity-50' : ''}`}
                            />
                        </PaginationItem>

                        {Array.from({ length: totalPages }, (_, i) => i + 1).map((page) => (
                            <PaginationItem key={page}>
                                <PaginationLink
                                    onClick={() => onPageChange(page)}
                                    isActive={currentPage === page}
                                    className="cursor-pointer"
                                >
                                    {page}
                                </PaginationLink>
                            </PaginationItem>
                        ))}

                        <PaginationItem>
                            <PaginationNext
                                onClick={() => onPageChange(Math.min(totalPages, currentPage + 1))}
                                className={`cursor-pointer ${currentPage === totalPages ? 'pointer-events-none opacity-50' : ''}`}
                            />
                        </PaginationItem>
                    </PaginationContent>
                </Pagination>
            </div>
        </div>
    );
};

export default CustomerList;