import { useNavigate } from "react-router-dom";
import type { ShopAddDto } from "../../types/Shops";
import { createShop } from "../../services/ShopsService";
import { useState } from "react";

interface CreateShopProps {
    shopAddDto: ShopAddDto
}

export const CreateShop = ({ shopAddDto }: CreateShopProps) => {
    const navigate = useNavigate();
    const [formData, setFormData] = useState<ShopAddDto>(shopAddDto);
    const [error, setError] = useState<string | null>(null);

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value } = e.target;
        setFormData(prev => ({
            ...prev,
            [name]: value
        }));
    };

    const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        try {
            const createdShop = await createShop(formData);
            navigate(`/shops/${createdShop.shopId}`);
        } catch (error) {
            setError(error instanceof Error ? error.message : "An error occurred");
        }
    };

    return (
        <div className="max-w-md mx-auto p-6">
            <h2 className="text-2xl font-bold mb-4">Create New Shop</h2>
            {error && <div className="text-red-500 mb-4">{error}</div>}
            <form onSubmit={handleSubmit} className="space-y-4">
                <div>
                    <input
                        type="text"
                        name="country"
                        value={formData.country}
                        onChange={handleChange}
                        placeholder="Country"
                        className="w-full p-2 border rounded focus:ring-2 focus:ring-blue-500"
                        required
                    />
                </div>
                <div>
                    <input
                        type="text"
                        name="city"
                        value={formData.city}
                        onChange={handleChange}
                        placeholder="City"
                        className="w-full p-2 border rounded focus:ring-2 focus:ring-blue-500"
                        required
                    />
                </div>
                <div>
                    <input
                        type="text"
                        name="address"
                        value={formData.address}
                        onChange={handleChange}
                        placeholder="Address"
                        className="w-full p-2 border rounded focus:ring-2 focus:ring-blue-500"
                        required
                    />
                </div>
                <div className="grid grid-cols-2 gap-4">
                    <button
                        type="button"
                        onClick={() => navigate('/shops')}
                        className="w-full bg-gray-500 text-black py-2 px-4 rounded hover:bg-gray-600 transition-colors">
                        Cancel
                    </button>
                    <button
                        type="submit"
                        className="w-full bg-blue-500 text-black py-2 px-4 rounded hover:bg-blue-600 transition-colors">
                        Create Shop
                    </button>
                </div>
            </form>
        </div>
    );
}
