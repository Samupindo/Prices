import { getProductById, getProducts } from "@/services/ProductsService";
import { addProductToShop, getShopById } from "@/services/ShopsService";
import type { ShopDto, AddProductShopDto } from "@/types/Shops";
import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { Combobox } from "@/components/Combobox";
import { ShopDetail } from "./ShopDetail";
import type { ProductDto } from "@/types/Products";


interface ProductToShopProps {
    addProductShopDto: AddProductShopDto;
}

const productOptions = (products: ProductDto[]) => {
    return products.map((product) => ({
        value: product.productId,
        label: product.name
    }));
};

export const ProductToShop = ({ addProductShopDto }: ProductToShopProps) => {
    const navigate = useNavigate();
    const [productId, setProductId] = useState<number>(0);
    const [shop, setShop] = useState<ShopDto | null>(null);
    const { shopId } = useParams();
    const [error, setError] = useState<string | null>(null);
    const [price, setPrice] = useState<string>('');
    const [selectedProduct, setSelectedProduct] = useState<number | null>(null);
    const [products, setProducts] = useState<ProductDto[]>([]);

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
        getProducts({ page: 0, size: 1000 }).then(response => {
            setProducts(response.content);
        }).catch(error => {
            setError(error.message);
        });
    }, [shopId]);

    const handleProductChange = (value: number | null) => {
        setSelectedProduct(value);
        setProductId(value || 0);
    };


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
            <ShopDetail shop={shop} />
            <div className="flex flex-row gap-4 mt-4">
                <Combobox
                    options={productOptions(products)}
                    value={selectedProduct}
                    onChange={handleProductChange}
                    placeholder="Select a product..."
                    searchPlaceholder="Search products..."
                    emptyMessage="No product option found."
                    className="w-[200px] h-[45px]"
                />
                <form className=" w-full max-w-sm items-center flex flex-row gap-2" onSubmit={(handleSubmit)}>
                    <input type="number" placeholder="Price" value={price} onChange={(e) => setPrice(e.target.value)} className="border-2 border-gray-300 w-full p-2 rounded-md" />
                    <button type="submit" >
                        Submit
                    </button>
                </form>
            </div>
        </div>
    )
}
