import type { ShopDto } from "../../types/shops";

interface ShopListProps {
    shops: ShopDto[];
}

export const ShopList = ({ shops }: ShopListProps) => {
    return (
        <div className="p-4"> 
            <h2 className="text-xl font-semibold mb-3">Shops</h2>

            <div className="shadow-md rounded-lg overflow-hidden bg-white">

                <div className="grid grid-cols-3 w-full bg-gray-100">
                    <div className="px-4 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider border-b border-gray-200">Country</div>
                    <div className="px-4 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider border-b border-gray-200">City</div>
                    <div className="px-4 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider border-b border-gray-200">Address</div>
                </div>

               {Array.isArray(shops) && shops.length > 0 ? (
                    shops.map((shop, index) => (
                        <div key={shop.shopId} className={`grid grid-cols-3 w-full ${index % 2 === 0 ? 'bg-white' : 'bg-gray-50'}`}>
                            <div className="px-4 py-3 whitespace-nowrap text-sm text-gray-700 border-b border-gray-200">{shop.country}</div>
                            <div className="px-4 py-3 whitespace-nowrap text-sm text-gray-700 border-b border-gray-200">{shop.city}</div>
                            <div className="px-4 py-3 text-sm text-gray-700 border-b border-gray-200">{shop.address}</div> 
                        </div>
                    ))
                ) : (
                    <div className="grid grid-cols-1 w-full"> 
                        <div className="col-span-3 px-4 py-3 text-center text-sm text-gray-500 border-b border-gray-200">
                            No shops available
                        </div>
                    </div>
                )}
            </div>
        </div>
    );
};