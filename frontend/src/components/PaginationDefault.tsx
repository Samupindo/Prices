import {
    Pagination,
    PaginationContent,
    PaginationItem,
    PaginationLink,
    PaginationNext,
    PaginationPrevious,
} from "@/components/ui/pagination"



interface PaginationProps {
    currentPage: number;
    totalPages: number;
    onPageChange: (page: number) => void;
}

export const PaginationDefault: React.FC<PaginationProps> = ({
    currentPage,
    totalPages,
    onPageChange
}) => {
    if (totalPages <= 1) return null;

    return <Pagination>
    <PaginationContent>
        <PaginationItem>
            <PaginationPrevious
                onClick={() => onPageChange(Math.max(1, currentPage - 1))}
                className={`cursor-pointer ${currentPage === 1 ? 'pointer-events-none opacity-50' : ''}`}
            />
        </PaginationItem>

        {Array.from({ length: totalPages }, (_, i) => i + 1).map((page) => (
            <PaginationItem key={page}>
                <PaginationLink
                    onClick={() => onPageChange(page)}
                    isActive={currentPage === page}
                    className="cursor-pointer"
                >
                    {page}
                </PaginationLink>
            </PaginationItem>
        ))}

        <PaginationItem>
            <PaginationNext
                onClick={() => onPageChange(Math.min(totalPages, currentPage + 1))}
                className={`cursor-pointer ${currentPage === totalPages ? 'pointer-events-none opacity-50' : ''}`}
            />
        </PaginationItem>
    </PaginationContent>
</Pagination>
    



};