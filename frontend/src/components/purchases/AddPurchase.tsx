import { useState } from "react"
import { useNavigate } from "react-router-dom"
import { createPurchase } from "../../services/PurchaseService";

export const AddPurchase = () => {
    const [customerId, setCustomerId] = useState('');
    const [error, setError] = useState<string | null>(null);
    const navigate = useNavigate();

    const handleOnChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setCustomerId(e.target.value);
    }

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        
        if (!customerId.trim()) {
            setError('El ID del cliente es requerido');
            return;
        }

        try {
            setError(null);
            const response = await createPurchase({ customerId: Number(customerId) });
            console.log('Purchase added successfully:', response);
            navigate('/purchases');
        } catch (error: any) {
            const errorMessage = error.response?.data?.message || error.message || 'Failed to create purchase';
            setError(errorMessage);
            console.error('Error details:', error);
        }
    }

    if (error) {
        return (
            <div className="mt-8">
                <div className="rounded-md bg-red-50 p-4">
                    <div className="flex">
                        <div className="ml-3">
                            <h3 className="text-sm font-medium text-red-800">Error</h3>
                            <div className="mt-2 text-sm text-red-700">
                                <p>{error}</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        );
    }

    return (
        <div className="max-w-md w-full space-y-8 bg-white p-8 rounded-lg shadow-lg border border-gray-200">
            <div>
                <h1 className="text-xl font-semibold text-gray-900 mb-4">Crear Compra</h1>
                <form onSubmit={handleSubmit} className="space-y-4">
                    <div>
                        <label htmlFor="customerId" className="block text-sm font-medium text-gray-700">
                            ID del Cliente
                        </label>
                        <input
                            type="number"
                            id="customerId"
                            name="customerId"
                            value={customerId}
                            onChange={handleOnChange}
                            className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm"
                            required
                        />
                    </div>
                    <button
                        type="submit"
                        className="inline-flex justify-center py-2 px-4 border border-transparent shadow-sm text-sm font-medium rounded-md text-black bg-indigo-600 hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500 w-full"
                    >
                        Crear Compra
                    </button>
                </form>
            </div>
        </div>
    )
}