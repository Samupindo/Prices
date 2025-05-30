import { useState } from "react";

interface PurchasesFiltersProps {
    onApplyFilters: (filters: {
        customerId?: number;
        productInShop?: number[];
        totalPriceMax?: number;
        totalPriceMin?: number;
        shopping?: boolean;
        page?: number;
        size?: number;
    }) => void;
}

export const PurchasesFilters = ({ onApplyFilters }: PurchasesFiltersProps) => {
    const [filters, setFilters] = useState({
        customerId: undefined,
        totalPriceMax: undefined,
        totalPriceMin: undefined,
        shopping: undefined,
        page: undefined,
        size: undefined,
    });

    const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value } = e.target;
        setFilters(prev => ({
            ...prev,
            [name]: value === '' ? undefined : value,
        }));
    }

    const handleApplyFilters = () => {
        onApplyFilters({
            ...filters,
            totalPriceMin: filters.totalPriceMin ? parseFloat(filters.totalPriceMin) : undefined,
            totalPriceMax: filters.totalPriceMax ? parseFloat(filters.totalPriceMax) : undefined,
        })
    }


    const handleResetFilters = () => {
        setFilters({
            customerId: undefined,
            totalPriceMax: undefined,
            totalPriceMin: undefined,
            shopping: undefined,
            page: undefined,
            size: undefined,
        })
        onApplyFilters({});
    };

    return (
        <div className="bg-white shadow-sm rounded-lg p-4 mb-4">
            <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
                <div>
                    <label htmlFor="customerId" className="block text-xs font-medium text-gray-600">
                        Customer ID
                    </label>
                    <input
                        type="number"
                        id="customerId"
                        name="customerId"
                        value={filters.customerId || ''}
                        onChange={handleInputChange}
                        className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 focus:ring-1 focus:ring-offset-0 sm:text-xs"
                        placeholder="Filter by customer ID"
                    />
                </div>

                <div>
                    <label htmlFor="totalPriceMin" className="block text-xs font-medium text-gray-600">
                        Min Total Price
                    </label>
                    <input
                        type="number"
                        id="totalPriceMin"
                        name="totalPriceMin"
                        value={filters.totalPriceMin || ''}
                        onChange={handleInputChange}
                        className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:ring-indigo-500 focus:ring-1 focus:ring-offset-0 sm:text-xs"
                        placeholder="Min total price"
                    />
                </div>

                <div>
                    <label htmlFor="totalPriceMax" className="block text-xs font-medium text-gray-600">
                        Max Total Price
                    </label>
                    <input
                        type="number"
                        id="totalPriceMax"
                        name="totalPriceMax"
                        value={filters.totalPriceMax || ''}
                        onChange={handleInputChange}
                        className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:ring-indigo-500 focus:ring-1 focus:ring-offset-0 sm:text-xs"
                        placeholder="Max total price"
                    />
                </div>

                <div>
                    <label htmlFor="shopping" className="block text-xs font-medium text-gray-600">
                        Shopping Status
                    </label>
                    <input
                        type="text"
                        id="shopping"
                        name="shopping"
                        value={filters.shopping || ''}
                        onChange={handleInputChange}
                        className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:ring-indigo-500 focus:ring-1 focus:ring-offset-0 sm:text-xs"
                        placeholder="Filter by shopping status"
                    />
                </div>
            </div>

            <div className="mt-4 flex justify-end space-x-2">
                <button
                    onClick={handleResetFilters}
                    className="px-3 py-1.5 bg-indigo-600 border border-transparent rounded-md text-xs font-medium text-gray hover:bg-indigo-700 focus:outline-none focus:ring-1 focus:ring-offset-0 focus:ring-indigo-500 transition-all duration-150"
                >
                    Reset
                </button>
                <button
                    onClick={handleApplyFilters}
                    className="px-3 py-1.5 bg-indigo-600 border border-transparent rounded-md text-xs font-medium text-gray hover:bg-indigo-700 focus:outline-none focus:ring-1 focus:ring-offset-0 focus:ring-indigo-500 transition-all duration-150"
                >
                    Apply Filters
                </button>
            </div>
        </div>
    );
}