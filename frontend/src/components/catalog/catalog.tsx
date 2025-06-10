import { ProductWithShopsDto } from "@/types/Shops";
import { PaginationDefault } from "../PaginationDefault";

interface CatalogProps {
    products: ProductWithShopsDto[];
    totalPages: number;
    currentPage: number;
    onPageChange: (page: number) => void;
}

export const Catalog = ({ products, totalPages, currentPage, onPageChange }: CatalogProps) => {
    return <div>


        <PaginationDefault 
         currentPage={currentPage}
         totalPages={totalPages}
         onPageChange={onPageChange}
         />
    </div>;
}
