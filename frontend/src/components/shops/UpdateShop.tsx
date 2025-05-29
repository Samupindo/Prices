import type { ShopPutDto } from "../../types/shops";
import { useNavigate, useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import { getShopById, updateShop } from "../../services/ShopsService";


interface UpdateShopProps {
    shopPutDto: ShopPutDto;
}

export const UpdateShop = ({ shopPutDto }: UpdateShopProps) => {
    const navigate = useNavigate();
    const { shopId } = useParams();
    const [formData, setFormData] = useState<ShopPutDto>(shopPutDto);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        if (shopId) {
            getShopById(shopId)
                .then((shop) => {
                    setFormData({
                        country: shop.country,
                        city: shop.city,
                        address: shop.address
                    });
                })
                .catch((error) => {
                    setError(error.message);
                });
        }
    }, [shopId]);

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value } = e.target;
        setFormData(prev => ({
            ...prev,
            [name]: value
        }));
    };

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        if (!shopId) return;

        try {
            await updateShop(shopId, formData);
            navigate('/shops/' + shopId);
        } catch (error) {
            setError(error instanceof Error ? error.message : "An error occurred");
        }
    };
 
    return (
        <div className="p-6 md:p-8 max-w-6xl mx-auto">
            <div className="flex flex-col sm:flex-row justify-center items-center mb-8 bg-gray-200 rounded-xl py-3">
                <p className="text-4xl font-semibold text-gray-800 mb-4 sm:mb-0">
                    Shop Edit
                </p>
            </div>

            {error && (
                <div className="mb-4 p-4 bg-red-100 border border-red-400 text-red-700 rounded">
                    {error}
                </div>
            )}

            <form onSubmit={handleSubmit}>
                <div className="bg-white shadow-xl rounded-xl overflow-hidden border-gray-200">
                    <div className="p-6 sm:p-10 md:p-12">
                        <div className="grid grid-cols-1 md:grid-cols-3 gap-x-8 gap-y-8">
                            <div className="space-y-1">
                                <p className="text-sm sm:text-base font-semibold text-gray-500 uppercase tracking-wide">Country</p>
                                <input 
                                    type="text" 
                                    name="country" 
                                    className="w-full p-2 border rounded text-lg sm:text-xl md:text-2xl text-gray-900" 
                                    value={formData.country}
                                    onChange={handleChange}
                                    required 
                                />
                            </div>

                            <div className="space-y-1">
                                <p className="text-sm sm:text-base font-semibold text-gray-500 uppercase tracking-wide">City</p>
                                <input 
                                    type="text" 
                                    name="city" 
                                    className="w-full p-2 border rounded text-lg sm:text-xl md:text-2xl text-gray-900" 
                                    value={formData.city}
                                    onChange={handleChange}
                                    required 
                                />
                            </div>

                            <div className="md:col-span-3 space-y-1 mt-4 md:mt-0">
                                <p className="text-sm sm:text-base font-semibold text-gray-500 uppercase tracking-wide">Address</p>
                                <input 
                                    type="text" 
                                    name="address" 
                                    className="w-full p-2 border rounded text-lg sm:text-xl md:text-2xl text-gray-900" 
                                    value={formData.address}
                                    onChange={handleChange}
                                    required 
                                />
                            </div>
                        </div>
                    </div>
                </div>
                
                <div className="mt-10 flex flex-col md:flex-row justify-center items-center space-y-4 md:space-y-0 md:space-x-4">
                    <button
                        type="button"
                        onClick={() => navigate('/shops/' + shopId)}
                        className="w-full md:w-auto bg-gray-200 text-gray-700 px-6 py-3 rounded-lg hover:bg-gray-300 transition-colors duration-150 text-base font-medium focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-gray-400">
                        Cancel
                    </button>

                    <button
                        type="submit"
                        onClick={handleSubmit}
                        className="w-full md:w-auto bg-indigo-600 text-black px-6 py-3 rounded-lg hover:bg-indigo-700 transition-colors duration-150 text-base font-semibold focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500">
                        Save Changes
                    </button>
                </div>
            </form>
        </div>
    );
}