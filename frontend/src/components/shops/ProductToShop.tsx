import { getProductById } from "@/services/ProductsService";
import { addProductToShop, getShopById } from "@/services/ShopsService";
import type { ProductDto } from "@/types/Products";
import type { ShopDto, AddProductShopDto } from "@/types/Shops";
import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";

interface ProductToShopProps {
    addProductShopDto: AddProductShopDto;
}

export const ProductToShop = ({ addProductShopDto }: ProductToShopProps) => {
    const navigate = useNavigate();
    const [productId, setProductId] = useState<number>(0);
    const [shop, setShop] = useState<ShopDto | null>(null);
    const { shopId } = useParams();
    const [error, setError] = useState<string | null>(null);
    const [price, setPrice] = useState<string>('');

    useEffect(() => {
        if (shopId) {
            getShopById(shopId)
                .then((shop) => {
                    setShop(shop);
                })
                .catch((error) => {
                    setError(error.message);
                });
        }
    }, [shopId]);

    const validateIds = async (shopId: string, productId: number) => {
        try {
            if (!shopId) {
                setError('Shop ID is required');
            }

            const response = await getShopById(shopId);
            const shop = response;

            if (!shop) {
                setError(`Shop with ID ${shopId} not found.`);
            }

            return true;
        } catch (error) {
            setError(`Invalid IDs. Please verify that shop ID ${shopId} and product ID ${productId} exist.`);
        }
    }

    const handleSubmit = async (event: React.FormEvent) => {
        event.preventDefault();

        if (!shopId || !productId) {
            setError('Shop ID and Product ID are required');
            return;
        }

        try {
            await validateIds(shopId, productId);

            addProductShopDto.price = Number(price);
            await addProductToShop(shopId, productId.toString(), addProductShopDto);
            navigate(`/products/${productId}`);

        } catch (error) {
            setError(error instanceof Error ? error.message : "An error occurred");
        }
    }

    if (error) return <div>{error}</div>;

    if (!shop) return <div>Error loading shop</div>;

    return (
        <div>
            <div className="grid grid-cols-1 bg-white text-center shadow-xl rounded-xl overflow-hidden border-gray-200 p-6 sm:p-10 md:p-12">
                <div>
                    <div className="space-y-1">
                        <p className="text-sm sm:text-base font-semibold text-gray-500 uppercase tracking-wide">Shop ID</p>
                        <p className="text-lg sm:text-xl md:text-2xl text-gray-700 break-words">{shop.shopId}</p>
                    </div>

                    <div className="space-y-1 mt-5">
                        <p className="text-sm sm:text-base font-semibold text-gray-500 uppercase tracking-wide">Country</p>
                        <p className="text-lg sm:text-xl md:text-2xl text-gray-900">{shop.country}</p>
                    </div>

                    <div className="space-y-1 mt-5">
                        <p className="text-sm sm:text-base font-semibold text-gray-500 uppercase tracking-wide">City</p>
                        <p className="text-lg sm:text-xl md:text-2xl text-gray-900">{shop.city}</p>
                    </div>

                    <div className="md:col-span-3 space-y-1 mt-5">
                        <p className="text-sm sm:text-base font-semibold text-gray-500 uppercase tracking-wide">Address</p>
                        <p className="text-lg sm:text-xl md:text-2xl text-gray-900">{shop.address}</p>
                    </div>
                </div>
            </div>
            <div>
                <form className="mt-2 flex gap-2" onSubmit={handleSubmit}>
                    <input placeholder="Product ID" className="bg-white border border-gray-300 rounded-lg px-4 py-2" type="number" value={productId} onChange={(e) => setProductId(parseInt(e.target.value))} />
                    <input placeholder="Price" className="bg-white border border-gray-300 rounded-lg px-4 py-2" type="number" value={price} onChange={(e) => setPrice(e.target.value)} />
                    <button className="bg-indigo-600 text-black px-6 py-3 rounded-lg hover:bg-indigo-700 transition-colors duration-150 text-base font-semibold focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500" type="submit">Add Product</button>
                </form>
            </div>
        </div>
    )
}
