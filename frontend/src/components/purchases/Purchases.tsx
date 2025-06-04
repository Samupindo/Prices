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
        <div>
            <div className="sm:flex sm:items-center">
                <div className="sm:flex-auto">
                    <h1 className="text-xl font-semibold text-gray-900">Purchases</h1>
                    <p className="mt-2 text-sm text-gray-700">
                        A list of all purchases
                    </p>
                </div>
            </div>
            <button
                onClick={() => navigate('/')}
                className="inline-flex justify-center py-2 px-4 border border-transparent shadow-sm text-sm font-medium rounded-md text-blue bg-indigo-600 hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500 transition-colors duration-200"
            >
                Home
            </button>
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