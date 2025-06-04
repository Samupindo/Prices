import { useState } from "react";

interface PurchasesFiltersProps {
    onApplyFilters: (filters: {
        customerId?: number;
        totalPriceMin?: number;
        totalPriceMax?: number;
        shopping?: boolean;
    }) => void;
}

export const PurchasesFilters = ({ onApplyFilters }: PurchasesFiltersProps) => {
    const [filters, setFilters] = useState({
        customerId: undefined,
        totalPriceMin: undefined,
        totalPriceMax: undefined,
        shopping: undefined,
    });

    const handleInputChange = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
        const { name, value } = e.target;
        
        // Actualizar el filtro correspondiente
        setFilters(prev => ({
            ...prev,
            [name]: value === '' ? undefined : value,
        }));

        // Aplicar los filtros inmediatamente
        onApplyFilters({
            ...filters,
            [name]: value === '' ? undefined : value,
            customerId: name === 'customerId' ? (value ? parseInt(value) : undefined) : filters.customerId,
            totalPriceMin: name === 'totalPriceMin' ? (value ? parseFloat(value) : undefined) : filters.totalPriceMin,
            totalPriceMax: name === 'totalPriceMax' ? (value ? parseFloat(value) : undefined) : filters.totalPriceMax,
            shopping: name === 'shopping' ? (value === 'true') : filters.shopping
        });
    };

    const handleResetFilters = () => {
        setFilters({
            customerId: undefined,
            totalPriceMin: undefined,
            totalPriceMax: undefined,
            shopping: undefined,
        });
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
                        className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm"
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
                        className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm"
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
                        className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm"
                    />
                </div>
                <div>
                    <label htmlFor="shopping" className="block text-xs font-medium text-gray-600">
                        Shopping
                    </label>
                    <select
                        id="shopping"
                        name="shopping"
                        value={filters.shopping ?? ''}
                        onChange={handleInputChange}
                        className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm"
                    >
                        <option value="">All</option>
                        <option value="true">Shopping</option>
                        <option value="false">Not Shopping</option>
                    </select>
                </div>
            </div>
            <div className="mt-4 flex justify-end space-x-4">
                <button
                    onClick={handleResetFilters}
                    className="px-4 py-2 border border-gray-300 rounded-md text-sm font-medium text-gray-700 bg-white hover:bg-gray-50"
                >
                    Reset
                </button>
            </div>
        </div>
    );
};