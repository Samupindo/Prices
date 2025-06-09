import { updateProductInShop } from "@/services/ShopsService";
import type { ProductInShopPatchDto } from "@/types/Shops";
import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { Combobox } from "@/components/Combobox";
import type { ProductDto } from "@/types/Products";
import { getProducts } from "@/services/ProductsService";
import { Button } from "../ui/button";

interface UpdateProductInShopProps {
    productInShopPatch: ProductInShopPatchDto;
}

const productOptions = (products: ProductDto[]) => {
    return products.map((product) => ({
        value: product.name,
        label: product.name
    }));
};

export const UpdateProductInShop = ({ productInShopPatch }: UpdateProductInShopProps) => {
    const { shopId } = useParams();
    const [error, setError] = useState<string | null>(null);
    const [price, setPrice] = useState<string>('');
    const [products, setProducts] = useState<ProductDto[]>([]);
    const [selectedProduct, setSelectedProduct] = useState<string | null>(null);
    const navigate = useNavigate();

    useEffect(() => {
        getProducts({ page: 0, size: 100 }).then(response => {
            setProducts(response.content);
        }).catch(error => {
            setError(error.message);
        });
    }, []);

    const handleProductChange = (value: string | null) => {
        setSelectedProduct(value);
    };

    if (error) return <div className="text-red-500">Error updating product in shop: {error}</div>;

    const handlePriceChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        setPrice(event.target.value);
    };

    const handleSubmit = (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        if (shopId && products.productId) {
        updateProductInShop(shopId, productInShopPatch.productId, { price: Number(price) }).then(() => {
            navigate(`/products/${productInShopPatch.productId}`);
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
                    <input type="number" placeholder="Price" value={price} onChange={handlePriceChange} className="border-2 border-gray-300 w-full p-2 rounded-md" />
                    <Button type="submit" variant="outline">
                        Submit
                    </Button>
                </form>
            </div>
        </div>
    );
}


