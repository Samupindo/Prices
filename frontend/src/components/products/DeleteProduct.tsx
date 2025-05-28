import { useState, useEffect } from "react";
import { useParams, useNavigate } from "react-router-dom";
import type { ProductWithShopsDto } from "../../types/products";
import { getProductById, deleteProduct } from "../../services/product-service";
import { ProductDetail } from "./ProductDetail";

export const DeleteProduct = () => {
    const { id } = useParams();
    const navigate = useNavigate();
    const [product, setProduct] = useState<ProductWithShopsDto | null>(null);
    const [error, setError] = useState<string | null>(null);

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
        } catch (error) {
            setError('Failed to fetch product');
            console.error('Error fetching product:', error);
        }
    };

    useEffect(() => {
        fetchProduct();
    }, [id]);

    const handleDelete = async () => {
        if (!product) return;

        try {
            setError(null);
            await deleteProduct(product.productId);
            navigate('/products');
        } catch (error) {
            setError('Failed to delete product');
            console.error('Error deleting product:', error);
        }
    };

    if (error) {
        return <div className="text-red-500">Error: {error}</div>;
    }



    return (
        <div className="max-w-3xl mx-auto px-4 sm:px-6 lg:px-8">
            <div className="sm:flex sm:items-center mb-8">
                <div className="sm:flex-auto">
                    <h1 className="text-xl font-semibold text-gray-900">Eliminar Producto</h1>
                </div>
            </div>

            {/* Sección de detalles del producto */}
            <div className="mb-8">
                <h2 className="text-lg font-medium text-gray-900 mb-4">Producto a Eliminar</h2>
                <ProductDetail />
            </div>

            {/* Formulario de confirmación */}
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
                                className="inline-flex justify-center py-2 px-4 border border-transparent shadow-sm text-sm font-medium rounded-md text-red-700 bg-red-600 hover:bg-red-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-red-700"
                            >
                                Eliminar
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
};