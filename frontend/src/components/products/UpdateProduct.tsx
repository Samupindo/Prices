import { useState, useEffect } from "react";
import { useParams, useNavigate } from "react-router-dom";
import type { ProductWithShopsDto } from "../../types/products";
import { getProductById, updateProduct } from "../../services/product-service";
import { ProductDetail } from "./ProductDetail";

export const UpdateProduct = () => {
    const { id } = useParams();
    const navigate = useNavigate();
    const [product, setProduct] = useState<ProductWithShopsDto | null>(null);
    const [error, setError] = useState<string | null>(null);
    const [name, setName] = useState<string>("");
    const [isLoading, setIsLoading] = useState(true);

    const fetchProduct = async () => {
        try {
            if (!id) {
                setError('Product ID is required');
                return;
            }

            const productId = parseInt(id);
            if (isNaN(productId)) {
                setError('Invalid product ID');
                return;
            }

            setError(null);
            const response = await getProductById(productId);
            setProduct(response);
            setName(response.name); // Inicializar el campo del formulario con el nombre actual
        } catch (error) {
            setError('Failed to fetch product');
            console.error('Error fetching product:', error);
        } finally {
            setIsLoading(false);
        }
    };

    useEffect(() => {
        fetchProduct();
    }, [id]);

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        
        if (!name.trim()) {
            setError('El nombre del producto es requerido');
            return;
        }

        try {
            setError(null);
            if (product) {
                const updatedProduct = await updateProduct(product.productId, { name });
                setProduct(updatedProduct);
                navigate('/products');
            }
        } catch (error) {
            setError('Failed to update product');
            console.error('Error updating product:', error);
        }
    };

    if (error) {
        return <div className="text-red-500">Error: {error}</div>;
    }

    if (isLoading) {
        return <div>Loading...</div>;
    }

    return (
        <div className="max-w-3xl mx-auto px-4 sm:px-6 lg:px-8">
            <button
                onClick={() => navigate('/products')}
                className=" mr-150 bg-blue-500 hover:bg-blue-700 text-black font-bold py-2 px-4 rounded-md mb-6" 
            >
                Back
            </button>
            <div className="mb-8">
                <ProductDetail />
            </div>

            {/* Formulario para actualizar */}
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
                                className="inline-flex justify-center py-2 px-4 border border-transparent shadow-sm text-sm font-medium rounded-md text-black bg-indigo-600 hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500"
                            >
                                Actualizar Producto
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    );
};