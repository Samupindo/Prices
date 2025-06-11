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
        
        let processedValue: number | undefined = undefined;
        if (value) {
            processedValue = parseFloat(value);
        }

        setFilters(prev => ({
            ...prev,
            [name]: processedValue,
        }));
        
        // Aplicar los filtros inmediatamente después de cambiar el valor
        onApplyFilters({
            ...filters,
            [name]: processedValue,
            priceMin: name === 'priceMin' ? processedValue : filters.priceMin,
            priceMax: name === 'priceMax' ? processedValue : filters.priceMax
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
        <div className="flex justify-center items-center gap-4 bg-gray-100 p-2 px-4 rounded-xl mb-4">
            <div className="flex items-center gap-2">
                <label htmlFor="name" className="text-xs font-medium text-gray-600">
                    Product Name:
                </label>
                <input
                    type="text"
                    id="name"
                    name="name"
                    value={filters.name || ''}
                    onChange={handleInputChange}
                    className="border border-gray-300 rounded-md px-2 py-1 bg-white"
                    placeholder="Search by product name"
                />
            </div>
            <div className="flex items-center gap-2">
                <label htmlFor="priceMin" className="text-xs font-medium text-gray-600">
                    Min Price:
                </label>
                <input
                    type="number"
                    id="priceMin"
                    name="priceMin"
                    value={filters.priceMin || ''}
                    onChange={handleInputChange}
                    className="border border-gray-300 rounded-md px-2 py-1 bg-white"
                    placeholder="Min price"
                />
            </div>
            <div className="flex items-center gap-2">
                <label htmlFor="priceMax" className="text-xs font-medium text-gray-600">
                    Max Price:
                </label>
                <input
                    type="number"
                    id="priceMax"
                    name="priceMax"
                    value={filters.priceMax || ''}
                    onChange={handleInputChange}
                    className="border border-gray-300 rounded-md px-2 py-1 bg-white"
                    placeholder="Max price"
                />
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