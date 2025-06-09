import { useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { deleteProduct } from "../../services/ProductsService";
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
            navigate('/products', { replace: true });
        } catch (error) {
            setError('Failed to delete product');
            console.error('Error deleting product:', error);
        } finally {
            setIsLoading(false);
        }
    };

    if (error) {
        return <div>Error loading product: {error}</div>;
    }

    return (
        <div className="container mx-auto p-8">
            <div className="max-w-4xl mx-auto mb-6 mt-10">
                <div className="bg-white shadow-md rounded-lg p-6">
                    <ProductDetail />
                </div>
                <div className="relative transform overflow-hidden rounded-lg bg-white text-left shadow-xl transition-all sm:my-8 sm:w-full sm:max-w-lg">
                    <div className="bg-white px-4 pt-5 pb-4 sm:p-6 sm:pb-4">
                        <div className="sm:flex sm:items-start">
                            <div className="mx-auto flex size-12 shrink-0 items-center justify-center rounded-full bg-red-100 sm:mx-0 sm:size-10">
                                <svg className="size-6 text-red-600" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" aria-hidden="true" data-slot="icon">
                                    <path stroke-linecap="round" stroke-linejoin="round" d="M12 9v3.75m-9.303 3.376c-.866 1.5.217 3.374 1.948 3.374h14.71c1.73 0 2.813-1.874 1.948-3.374L13.949 3.378c-.866-1.5-3.032-1.5-3.898 0L2.697 16.126ZM12 15.75h.007v.008H12v-.008Z" />
                                </svg>
                            </div>
                            <div className="mt-3 text-center sm:mt-0 sm:ml-4 sm:text-left">
                                <h3 className="text-base font-semibold text-gray-900" id="modal-title">Delete Product</h3>
                                <div className="mt-2">
                                    <p className="text-sm text-gray-500">Are you sure you want to delete this product? </p>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div className="bg-gray-50 px-4 py-3 sm:flex sm:flex-row-reverse sm:px-6">
                        <div className="flex justify-center w-full sm:justify-end">

                            <button
                                onClick={() => navigate("/products", { replace: true })}
                                className="mt-3 inline-flex w-full justify-center rounded-md bg-white px-3 py-2 text-sm font-semibold text-gray-900 shadow-xs ring-1 ring-gray-300 ring-inset hover:bg-gray-50 sm:mt-0 sm:w-auto"
                            >
                                Cancel
                            </button>
                            <button
                                onClick={handleDelete}
                                disabled={isLoading}
                                className={`inline-flex w-full justify-between ml-10 rounded-md bg-red-600 px-3 py-2 text-sm font-semibold text-red-500 shadow-xs bg-red-800 hover:bg-red-500 ring-1 ring-red-300 ${isLoading ? 'cursor-not-allowed' : ''} sm:ml-3 sm:w-auto`}
                            >
                                {isLoading ? 'Deleting...' : 'Delete'}
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
};