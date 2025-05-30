import { useState,  } from "react";
import {  useNavigate } from "react-router-dom";
import {  updateProduct } from "../../services/Product-service";
import { ProductDetail } from "./ProductDetail";
import { useParams } from "react-router-dom";

export const UpdateProduct = () => {
    const { id } = useParams();
    const navigate = useNavigate();
    const [name, setName] = useState<string>("");
    const [error, setError] = useState<string | null>(null);
    const [isLoading, setIsLoading] = useState(false);
    const isUpdatePage = location.pathname.includes('/update-products');

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        setError(null);
        setIsLoading(true);

        if (!name.trim()) {
            setError('El nombre del producto es requerido');
            setIsLoading(false);
            return;
        }

        if (!id) {
            setError('Product ID is required');
            setIsLoading(false);
            return;
        }

        try {
            const productId = parseInt(id);
            if (isNaN(productId)) {
                setError('Invalid product ID');
                setIsLoading(false);
                return;
            }

            await updateProduct(productId, { name });
            navigate('/products');
        } catch (error) {
            setError('Failed to update product');
            console.error('Error updating product:', error);
        } finally {
            setIsLoading(false);
        }
    };

    if (error) {
        return (
            <div className="rounded-md bg-red-50 p-4 mb-4">
                <div className="flex">
                    <div className="ml-3">
                        <h3 className="text-sm font-medium text-red-800">Error</h3>
                        <div className="mt-2 text-sm text-red-700">
                            <p>{error}</p>
                        </div>
                    </div>
                </div>
            </div>
        );
    }

    return (
        <div className="max-w-3xl mx-auto px-4 sm:px-6 lg:px-8">
            <button
                onClick={() => navigate('/products')}
                className="mr-150 bg-blue-500 hover:bg-blue-700 text-black font-bold py-2 px-4 rounded-md mb-6" 
            >
                Back
            </button>
            <div className="mb-8">
                <ProductDetail />
            </div>

            <div className="bg-white shadow rounded-lg">
                <div className="p-6">
                    <form onSubmit={handleSubmit} className="space-y-6">
                        <div>
                            <label htmlFor="name" className="block text-sm font-medium text-gray-700">
                                Nuevo Nombre
                            </label>
                            <div className="mt-1">
                                <input
                                    type="text"
                                    id="name"
                                    name="name"
                                    value={name}
                                    onChange={(e) => setName(e.target.value)}
                                    className="shadow-sm focus:ring-indigo-500 focus:border-indigo-500 block w-full sm:text-sm border-gray-300 rounded-md"
                                    required
                                />
                            </div>
                        </div>

                        <div>
                            <button
                                type="submit"
                                disabled={isLoading}
                                className={`inline-flex justify-center py-2 px-4 border border-transparent shadow-sm text-sm font-medium rounded-md focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500 ${isLoading ? 'bg-indigo-400 cursor-not-allowed' : 'bg-indigo-600 hover:bg-indigo-700'}`}
                            >
                                {isLoading ? 'Actualizando...' : 'Actualizar'}
                            </button>
                        </div>
                    </form>
                    
                </div>
                
            </div>
        </div>
    );
};