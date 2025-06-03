import { useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import {  deleteProduct } from "../../services/ProductsService";
import { ProductDetail } from "./ProductDetail";
export const DeleteProduct = () => {
    const { productId } = useParams();
    const navigate = useNavigate();
    const [error, setError] = useState<string | null>(null);
    const [isLoading, setIsLoading] = useState(false);
    const handleDelete = async () => {
        if (!productId) {
            setError('Product ID is required');
            return;
        }
        try {
            setError(null);
            setIsLoading(true);
            const productIdNumber = parseInt(productId);
            if (isNaN(productIdNumber)) {
                setError('Invalid product ID');
                setIsLoading(false);
                return;
            }
            await deleteProduct(productIdNumber);
            navigate('/products');
        } catch (error) {
            setError('Failed to delete product');
            console.error('Error deleting product:', error);
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
                    <div className="text-center">
                        <h3 className="text-lg font-medium text-gray-900 mb-4">
                            ¿Estás seguro de que quieres eliminar este producto?
                        </h3>
                        <p className="text-sm text-gray-500 mb-6">
                            Esta acción no se puede deshacer.
                        </p>
                        <div className="flex justify-center gap-4">
                            <button
                                onClick={() => navigate('/products')}
                                className="inline-flex justify-center py-2 px-4 border border-gray-300 shadow-sm text-sm font-medium rounded-md text-gray-700 bg-white hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500"
                            >
                                Cancelar
                            </button>
                            <button
                                onClick={handleDelete}
                                disabled={isLoading}
                                className={`inline-flex justify-center py-2 px-4 border border-transparent shadow-sm text-sm font-medium rounded-md focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-red-500 ${isLoading ? 'bg-red-400 cursor-not-allowed' : 'bg-red-600 hover:bg-red-700'}`}
                            >
                                {isLoading ? 'Eliminando...' : 'Eliminar'}
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
};