import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { updateProduct, getProductById } from "../../services/ProductsService";
import { ProductDetail } from "./ProductDetail";
import { useParams } from "react-router-dom";

export const UpdateProduct = () => {
    const { productId } = useParams();
    const navigate = useNavigate();
    const [name, setName] = useState<string>("");
    const [error, setError] = useState<string | null>(null);
    const [isLoading, setIsLoading] = useState(false);
    const [product, setProduct] = useState<any>(null);
    const isUpdatePage = location.pathname.includes('/update-products');

    const fetchProduct = async () => {
        if (!productId) {
            setError('Product ID is required');
            return;
        }

        const productIdNumber = parseInt(productId);
        if (isNaN(productIdNumber)) {
            setError('Invalid product ID');
            return;
        }

        try {
            const response = await getProductById(productIdNumber);
            setProduct(response);
            setName(response?.name || '');
        } catch (error) {
            setError('Failed to fetch product');
            console.error('Error fetching product:', error);
        }
    };

    useEffect(() => {
        fetchProduct();
    }, [productId]);

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        setError(null);
        setIsLoading(true);

        if (!name.trim()) {
            setError('El nombre del producto es requerido');
            setIsLoading(false);
            return;
        }

        if (!productId) {
            setError('Product ID is required');
            setIsLoading(false);
            return;
        }

        try {
            const productIdNumber = parseInt(productId);
            if (isNaN(productIdNumber)) {
                setError('Invalid product ID');
                setIsLoading(false);
                return;
            }

            await updateProduct(productIdNumber, { name });
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

    if (!product) {
        return <div>Loading...</div>;
    }

    return (
        <div className="container mx-auto p-8 ">
            <div className="max-w-4xl mx-auto mb-6 mt-10">
         
                <div className="relative transform overflow-hidden rounded-lg  bg-white text-left shadow-xl transition-all sm:my-8 sm:w-full sm:max-w-lg">
                    <div className="bg-white px-4 pt-5 pb-4 sm:p-6 sm:pb-4 sm:flex sm:items-center">
                        <div className="sm:flex sm:items-center">
                            <div className="mx-auto flex size-12 shrink-0 items-center justify-center rounded-full bg-indigo-100 sm:mx-0 sm:size-10">
                                <svg className="size-6 text-indigo-600" fill="none" viewBox="0 0 24 24" strokeWidth="1.5" stroke="currentColor" aria-hidden="true" data-slot="icon">
                                    <path strokeLinecap="round" strokeLinejoin="round" d="M16.862 4.487l1.687-1.688a1.875 1.875 0 112.652 2.652L10.582 16.07a4.5 4.5 0 01-1.897 1.13L6 18l.8-2.685a4.5 4.5 0 011.13-1.897l8.932-8.931zm0 0L19.5 7.125M18 14v4.75A2.25 2.25 0 0115.75 21H5.25A2.25 2.25 0 013 18.75V8.25A2.25 2.25 0 015.25 6H10" />
                                </svg>
                            </div>
                            <div className="mt-3 text-center sm:mt-0 sm:ml-4 sm:text-left">
                                <h3 className="text-base font-semibold text-gray-900" id="modal-title">Update Product</h3>
                                <div className="mt-2">
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

                                        <div className="flex justify-center w-full sm:justify-end">
                                            <button
                                                type="submit"
                                                disabled={isLoading}
                                                className={`inline-flex justify-center py-2 px-4 border border-transparent shadow-sm text-sm font-medium rounded-md focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500 ${isLoading ? 'bg-indigo-400 cursor-not-allowed' : 'bg-indigo-600 hover:bg-indigo-700'}`}
                                            >
                                                {isLoading ? 'Actualizando...' : 'Actualizar'}
                                            </button>
                                            <button
                                                type="button"
                                                onClick={() => navigate("/products")}
                                                className="mt-3 inline-flex justify-center py-2 px-4 border border-gray-300 rounded-md shadow-sm text-sm font-medium text-gray-700 bg-white hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500 sm:mt-0"
                                            >
                                                Cancelar
                                            </button>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
};