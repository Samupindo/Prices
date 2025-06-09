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
        <div className="flex justify-center items-center gap-4 bg-gray-100 p-2 px-4 rounded-xl mb-4">
            <div className="flex items-center gap-2">
                <label htmlFor="customerId" className="text-xs font-medium text-gray-600">
                    Customer ID:
                </label>
                <input
                    type="number"
                    id="customerId"
                    name="customerId"
                    value={filters.customerId || ''}
                    onChange={handleInputChange}
                    className="border border-gray-300 rounded-md px-2 py-1 bg-white"
                />
            </div>
            <div className="flex items-center gap-2">
                <label htmlFor="totalPriceMin" className="text-xs font-medium text-gray-600">
                    Min Total Price:
                </label>
                <input
                    type="number"
                    id="totalPriceMin"
                    name="totalPriceMin"
                    value={filters.totalPriceMin || ''}
                    onChange={handleInputChange}
                    className="border border-gray-300 rounded-md px-2 py-1 bg-white"
                />
            </div>
            <div className="flex items-center gap-2">
                <label htmlFor="totalPriceMax" className="text-xs font-medium text-gray-600">
                    Max Total Price:
                </label>
                <input
                    type="number"
                    id="totalPriceMax"
                    name="totalPriceMax"
                    value={filters.totalPriceMax || ''}
                    onChange={handleInputChange}
                    className="border border-gray-300 rounded-md px-2 py-1 bg-white"
                />
            </div>
            <div className="flex items-center gap-2">
                <label htmlFor="shopping" className="text-xs font-medium text-gray-600">
                    Shopping:
                </label>
                <select
                    id="shopping"
                    name="shopping"
                    value={filters.shopping ?? ''}
                    onChange={handleInputChange}
                    className="border border-gray-300 rounded-md px-2 py-1 bg-white"
                >
                    <option value="">All</option>
                    <option value="true">Yes</option>
                    <option value="false">No</option>
                </select>
            </div>
            <button
                onClick={handleResetFilters}
                className="bg-indigo-600 text-black px-6 py-2 rounded-lg hover:bg-indigo-700 transition-colors duration-150 text-base font-semibold focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500"
            >
                Reset
            </button>
        </div>
    );
};