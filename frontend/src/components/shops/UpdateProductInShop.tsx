import { updateProductInShop } from "@/services/ShopsService";
import type { ProductInShopPatchDto, ProductWithShopsDto } from "@/types/Shops";
import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { Combobox } from "@/components/Combobox";
import { getProducts } from "@/services/ProductsService";

interface UpdateProductInShopProps {
    productInShopPatch: ProductInShopPatchDto;
}

const productOptions = (products: ProductWithShopsDto[]) => {
    return products.map((product) => ({
        value: product.productId,
        label: product.name
    }));
};

export const UpdateProductInShop = ({ productInShopPatch }: UpdateProductInShopProps) => {
    const { shopId } = useParams();
    const [error, setError] = useState<string | null>(null);
    const [price, setPrice] = useState<string>('');
    const [products, setProducts] = useState<ProductWithShopsDto[]>([]);
    const [selectedProduct, setSelectedProduct] = useState<number | null>(null);
    const navigate = useNavigate();

    useEffect(() => {
        getProducts({ page: 0, size: 100 }).then(response => {
            const productsInShop = response.content.filter(product => 
                product.shop.some(shop => shop.shopId === Number(shopId))
            );
            setProducts(productsInShop);
        }).catch(error => {
            setError(error.message);
        });
    }, [shopId]);

    const handleProductChange = (value: number | null) => {
        setSelectedProduct(value);
    };

    if (error) return <div className="text-red-500">Error updating product in shop: {error}</div>;

    const handlePriceChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        setPrice(event.target.value);
    };

    const handleSubmit = (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        if (shopId && selectedProduct) {
            updateProductInShop(shopId, selectedProduct.toString(), { price: Number(price) }).then(() => {
                navigate(`/products/${selectedProduct}`);
            }).catch(error => {
                setError(error.message);
            });
        }
    };

    return (
        <div className="p-4 w-full">
            <div className="w-full">
                <h2 className="text-xl text-center bg-gray-200 rounded-xl py-3 font-bold mb-3 text-gray-900 pb-2 border-b-2 border-gray-200 mb-8">
                    Update Product Price
                </h2>
            </div>
            <div className="flex flex-row gap-4">
                <Combobox
                    options={productOptions(products)}
                    value={selectedProduct}
                    onChange={handleProductChange}
                    placeholder="Select a product..."
                    searchPlaceholder="Search products..."
                    emptyMessage="No product option found."
                    className="w-[200px] h-[45px]"
                />
                <form className=" w-full max-w-sm items-center flex flex-row gap-2" onSubmit={handleSubmit}>
                    <input type="decimal" step="any" placeholder="Price" value={price} onChange={handlePriceChange} className="border-2 border-gray-300 w-full p-2 rounded-md" />
                    <button type="submit" className="border-2 border-gray-300 h-[45px]">
                        Submit
                    </button>
                </form>
            </div>
        </div>
    );
}


