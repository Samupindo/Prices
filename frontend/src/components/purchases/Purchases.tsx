import { useEffect, useState } from "react";
import type { PurchaseDto } from "../../types/purchase";
import { useNavigate } from "react-router-dom";
import { getPurchases } from "../../services/PurchaseService";
import { PurchasesFilters } from "./PurchasesFilters";
import { PurchasesList } from "./PurchasesList";



export const Purchases= () => {
    const [purchases,setPurchases] = useState<PurchaseDto[]>([]);
    const [totalPages,setTotalPages] = useState(1);
    const [error,setError] = useState<string | null>(null);
    const navigate = useNavigate();
    const fetchPurchases = async (filters?: {
        customerId?: number;
        productInShop?: number[];
        totalPriceMax?: number;
        totalPriceMin?: number;
        shopping?: boolean;
        page?: number;
        size?: number;
    }) => {
        try {
            setError(null);
            const response = await getPurchases(filters);
            setPurchases(response.content);
            setTotalPages(response.totalPages);
        } catch (error) {
            setError('Failed to fetch purchases');
            console.error('Error fetching purchases:', error);
        }
    };

    useEffect(() => {
        fetchPurchases();
    }, []);


    if (error) return <div>Error loading products: {error}</div>;
    return  ( 
        <div>
            <div className="sm:flex sm:items-center">
                <div className="sm:flex-auto">
                    <h1 className="text-xl font-semibold text-gray-900">Purchases</h1>
                    <p className="mt-2 text-sm text-gray-700">
                        A list of all purchases in your store
                    </p>
                </div>
            </div>
            <button
                onClick={() => navigate('/')}
                className="mr-150 bg-blue-500 hover:bg-blue-700 text-black font-bold py-2 px-4 rounded-md mb-6" 
            >
                Home
            </button>
            <PurchasesFilters onApplyFilters={fetchPurchases}/>
            <PurchasesList purchases={purchases} totalPages={totalPages}/>
        </div>
    );
}