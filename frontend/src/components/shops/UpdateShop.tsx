import type { ShopPutDto } from "../../types/shops";
import { useNavigate, useParams } from "react-router-dom";
import { useState } from "react";
import { updateShop } from "../../services/ShopsService";


interface UpdateShopProps {
    shopPutDto: ShopPutDto;
}

export const UpdateShop = ({ shopPutDto }: UpdateShopProps) => {
    const navigate = useNavigate();
    const { shopId } = useParams();
    const [formData, setFormData] = useState<ShopPutDto>(shopPutDto);
    const [error, setError] = useState<string | null>(null);

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value } = e.target;
        setFormData(formData => ({
            ...formData,
            [name]: value
        }));
    };

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        if (!shopId) return;

        try {
            await updateShop(shopId, formData);
            navigate(`/shops/${shopId}`);
        } catch (error) {
            setError(error instanceof Error ? error.message : "An error occurred");
        }
    };

    return (
        <div className="p-6 md:p-8 mx-auto w-full">
            <h2 className="text-2xl font-bold mb-4">
                Shop Edit
            </h2>
            {error && (
                <div className="mb-4 p-4 bg-red-100 border border-red-400 text-red-700 rounded">
                    {error}
                </div>
            )}

            <form onSubmit={handleSubmit} className="space-y-4">
                <div>
                    <input
                        type="text"
                        name="country"
                        value={formData.country}
                        onChange={handleChange}
                        placeholder={shopPutDto.country}
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
                        placeholder={shopPutDto.city}
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
                        placeholder={shopPutDto.address}
                        className="w-full p-2 border rounded focus:ring-2 focus:ring-blue-500"
                        required
                    />
                </div>

                <div className="grid grid-cols-2 gap-4">
                    <button
                        type="button"
                        onClick={() => navigate(`/shops/${shopId}`)}
                        className="w-full md:w-auto bg-gray-200 text-black px-6 py-3 rounded-lg hover:bg-gray-300 transition-colors duration-150 text-base font-medium focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-gray-400">
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