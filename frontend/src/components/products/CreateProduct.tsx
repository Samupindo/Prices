import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { createProduct, getProducts } from "../../services/ProductsService";
export const CreateProduct = () => {
    const [error, setError] = useState<string | null>(null);
    const [name,setName] = useState<string>('');
    const navigate = useNavigate();
    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setName(e.target.value);
    };
    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        if (!name.trim()) {
            setError('El nombre del producto es requerido');
            return;
        }
        try {
            setError(null);
            const response = await createProduct({ name: name });
            console.log('Response from createProduct:', response);
            await getProducts({ page: 0, size: 10 });
            navigate('/products');
        } catch (error: any) {
            const errorMessage = error.response?.data?.message || error.message || 'Failed to create product';
            setError(errorMessage);
            console.error('Error details:', error);
        }
    };
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
                {error && (
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
                )}
                <div>
                    <h1 className="text-xl font-semibold text-gray-900 mb-4">Crear Producto</h1>
                    <form onSubmit={handleSubmit} className="space-y-4">
                        <div>
                            <label htmlFor="name" className="block text-sm font-medium text-gray-700">
                                Nombre del producto
                            </label>
                            <input
                                type="text"
                                id="name"
                                name="name"
                                value={name}
                                onChange={handleChange}
                                className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm"
                                required
                            />
                        </div>
                        <button
                            type="submit"
                            className="inline-flex justify-center py-2 px-4 border border-transparent shadow-sm text-sm font-medium rounded-md text-black bg-indigo-600 hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500 w-full"
                        >
                            Crear Producto
                        </button>
                    </form>
                </div>
            </div>
    );
};