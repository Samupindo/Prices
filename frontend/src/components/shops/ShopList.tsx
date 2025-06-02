import { useNavigate, useParams } from "react-router-dom";
import type { ShopDto } from "../../types/shops";
import type { ShopFilter } from "@/services/ShopsService";
import {
    Pagination,
    PaginationContent,
    PaginationItem,
    PaginationLink,
    PaginationNext,
    PaginationPrevious,
} from "@/components/ui/pagination"
import { useState } from "react";

interface ShopListProps {
    shops: ShopDto[];
    onFilterChange: (filter: ShopFilter) => void;
    currentPage: number;
    totalPages: number;
    filters: ShopFilter;
    onPageChange: (page: number) => void;
}

export const ShopList = ({ shops, onFilterChange, currentPage, totalPages, filters, onPageChange }: ShopListProps) => {
    const navigate = useNavigate();
    const [localFilters, setLocalFilters] = useState<ShopFilter>(filters);
    type ShopFilterKey = 'country' | 'city' | 'address'; 

    const handleFilterChange = (filter: ShopFilter) => {
        onFilterChange(filter);
    }

    const handleSearch = (event: React.ChangeEvent<HTMLInputElement>, field: ShopFilterKey) => {
        const emptyFilter = {country: '', city: '', address: ''};
        const newFilter = {...localFilters, [field]: event.target.value};
        setLocalFilters(newFilter);
        handleFilterChange(newFilter);

        if (event.target.value === '') {
            handleFilterChange(emptyFilter);
        }
    }

    return (
        <div className="p-4">
            <h2 className="text-center text-xl font-semibold mb-3 bg-gray-200 rounded-xl py-3">Shops</h2>
            <div className="flex justify-center items-center gap-4 bg-gray-100 p-2 px-4 rounded-xl mb-4 mt-2">
                <label htmlFor="country">Country:</label>
                <input
                    type="text"
                    className="border border-gray-300 rounded-md px-2 py-1 bg-white"
                    value={localFilters.country}
                    onChange={(event) => handleSearch(event, 'country')}
                />
                <label htmlFor="city">City:</label>
                <input
                    type="text"
                    className="border border-gray-300 rounded-md px-2 py-1 bg-white"
                    value={localFilters.city}
                    onChange={(event) => handleSearch(event, 'city')}
                />
                <label htmlFor="address">Address:</label>
                <input
                    type="text"
                    className="border border-gray-300 rounded-md px-2 py-1 bg-white"
                    value={localFilters.address}
                    onChange={(event) => handleSearch(event, 'address')}
                />
            </div>

            <div className="shadow-md rounded-lg overflow-hidden bg-white">
                <table className="min-w-full divide-y divide-gray-200">
                    <thead className="bg-gray-50">
                        <tr>
                            <th
                                scope="col"
                                className="px-6 py-3 text-center text-xs font-medium text-gray-500 uppercase tracking-wider">
                                Shop ID
                            </th>
                            <th
                                scope="col"
                                className="px-6 py-3 text-center text-xs font-medium text-gray-500 uppercase tracking-wider">
                                Country
                            </th>
                            <th
                                scope="col"
                                className="px-6 py-3 text-center text-xs font-medium text-gray-500 uppercase tracking-wider">
                                City
                            </th>
                            <th
                                scope="col"
                                className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                                Address
                            </th>
                            <th
                                scope="col"
                                className="px-6 py-3 text-center text-xs font-medium text-gray-500 uppercase tracking-wider">
                                Actions
                            </th>
                        </tr>
                    </thead>
                    <tbody className="bg-white divide-y divide-gray-200">
                        {shops.map((shop, index) => (
                            <tr key={shop.shopId} className={index % 2 === 0 ? 'bg-white' : 'bg-gray-50'}>
                                <td className="px-6 py-4 whitespace-nowrap text-sm font-medium text-gray-900 text-center">
                                    {shop.shopId}
                                </td>
                                    <td className="px-6 py-4 whitespace-nowrap text-sm font-medium text-gray-900 text-center">
                                    {shop.country}
                                </td>
                                <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500 text-center">
                                    {shop.city}
                                </td>
                                <td className="px-6 py-4 text-sm text-gray-500 text-left">
                                    {shop.address}
                                </td>
                                <td className="flex gap-1 px-6 py-4 whitespace-nowrap text-center text-sm font-medium">
                                    <button
                                        onClick={() => navigate(`/shops/${shop.shopId}`)}
                                        className="text-indigo-600 hover:text-indigo-900 hover:underline focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500 rounded-md">
                                        View
                                    </button>
                                    <button
                                        onClick={() => navigate(`/shops/${shop.shopId}/edit`)}
                                        className="text-green-500 hover:text-indigo-900 hover:underline focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500 rounded-md">
                                        Edit
                                    </button>
                                    <button
                                        onClick={() => navigate(`/shops/${shop.shopId}/delete`)}
                                        className="text-red-600 hover:text-indigo-900 hover:underline focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500 rounded-md">
                                        Delete
                                    </button>
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>
            <button
                onClick={() => navigate('/shops/create')}
                className="flex w-full md:w-auto bg-indigo-600 text-black px-6 py-3 rounded-lg hover:bg-indigo-700 transition-colors duration-150 text-base font-semibold focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500 mt-4">
                Add New Shop
            </button>
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
    );
};
