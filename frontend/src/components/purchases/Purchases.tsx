import { useEffect, useState } from "react";
import type { PurchaseDto } from "../../types/Purchase";
import { useNavigate } from "react-router-dom";
import { getPurchases } from "../../services/PurchaseService";
import { PurchasesFilters } from "./PurchasesFilters";
import { PurchasesList } from "./PurchasesList";

export const Purchases = () => {
    const [purchases, setPurchases] = useState<PurchaseDto[]>([]);
    const [totalPages, setTotalPages] = useState(0);
    const [currentPage, setCurrentPage] = useState(1);
    const [error, setError] = useState<string | null>(null);
    const navigate = useNavigate();

    const handlePageChange = (page: number) => {
        setCurrentPage(page);
    };

    const handleApplyFilters = async (filters?: {
        customerId?: number;
        totalPriceMin?: number;
        totalPriceMax?: number;
        shopping?: boolean;
    }) => {
        try {
            setError(null);
            const response = await getPurchases({
                page: currentPage - 1, // Convertir a 0-based
                size: 10,
                filters: filters
            });
            setPurchases(response.content);
            setTotalPages(response.totalPages);
        } catch (error) {
            setError('Failed to fetch purchases');
            console.error('Error fetching purchases:', error);
        }
    };

    useEffect(() => {
        handleApplyFilters();
    }, [currentPage]);

    if (error) return <div>Error loading purchases: {error}</div>;

    return (
        <div className="p-4">
            <button
                onClick={() => navigate('/')}
                className="mr-150 bg-blue-500 shadow-md rounded-md hover:bg-blue-700 text-black font-bold py-2 px-4 rounded-md mb-6"
            >
                Home
            </button>
            <h2 className="text-center text-xl font-semibold mb-3 bg-gray-200 rounded-xl py-3">Purchases</h2>
            <div className="flex justify-start mb-4">
                <button
                    onClick={() => navigate('/purchases/create')}
                    className="flex w-full md:w-auto bg-indigo-600 text-black px-6 py-3 rounded-lg hover:bg-indigo-700 transition-colors duration-150 text-base font-semibold focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500"
                >
                    Add Purchase
                </button>
            </div>
            <PurchasesFilters onApplyFilters={handleApplyFilters} />
            <PurchasesList
                purchases={purchases}
                totalPages={totalPages}
                currentPage={currentPage}
                onPageChange={handlePageChange}
            />
        </div>
    );
};