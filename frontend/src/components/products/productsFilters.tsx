import { useState } from 'react';
interface ProductsFiltersProps {
    onApplyFilters: (filters: {
        name?: string;
        priceMin?: number;
        priceMax?: number;
    }) => void;
}

export const ProductsFilters = ({ onApplyFilters }: ProductsFiltersProps) => {
    const [filters, setFilters] = useState({
        name: '',
        priceMin: undefined,
        priceMax: undefined,
    });

    const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value } = e.target;
        setFilters(prev => ({
            ...prev,
            [name]: value === '' ? undefined : value,
        }));
        
        // Aplicar los filtros inmediatamente después de cambiar el valor
        onApplyFilters({
            ...filters,
            [name]: value === '' ? undefined : value,
            priceMin: name === 'priceMin' ? (value ? parseFloat(value) : undefined) : filters.priceMin,
            priceMax: name === 'priceMax' ? (value ? parseFloat(value) : undefined) : filters.priceMax
        });
    };

    const handleResetFilters = () => {
        setFilters({
            name: '',
            priceMin: undefined,
            priceMax: undefined,
        });
        onApplyFilters({});
    };
    return (
        <div className="bg-white shadow-sm rounded-lg p-4 mb-4">
            <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
                <div>
                    <label htmlFor="name" className="block text-xs font-medium text-gray-600">
                        Product Name
                    </label>
                    <input
                        type="text"
                        id="name"
                        name="name"
                        value={filters.name || ''}
                        onChange={handleInputChange}
                        className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 focus:ring-1 focus:ring-offset-0 sm:text-xs"
                        placeholder="Search by product name"
                    />
                </div>

                <div>
                    <label htmlFor="priceMin" className="block text-xs font-medium text-gray-600">
                        Min Price
                    </label>
                    <input
                        type="number"
                        id="priceMin"
                        name="priceMin"
                        value={filters.priceMin || ''}
                        onChange={handleInputChange}
                        className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 focus:ring-1 focus:ring-offset-0 sm:text-xs"
                        placeholder="Min price"
                    />
                </div>

                <div>
                    <label htmlFor="priceMax" className="block text-xs font-medium text-gray-600">
                        Max Price
                    </label>
                    <input
                        type="number"
                        id="priceMax"
                        name="priceMax"
                        value={filters.priceMax || ''}
                        onChange={handleInputChange}
                        className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 focus:ring-1 focus:ring-offset-0 sm:text-xs"
                        placeholder="Max price"
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
            </div>
        </div>
    );
};