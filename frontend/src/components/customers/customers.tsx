import { useEffect, useState } from "react";
import { getCustomers, getCustomerById, createCustomer, type CustomerFilters } from "../../services/CustomerService";
import type { CreateCustomerDto, CustomerDto, CustomerPutDto } from "../../types/Customer";
import CustomerList from "./CustomerList";
import { useNavigate, useParams } from "react-router-dom";
import CustomerDetail from "./CustomerDetail";
import CreateCustomer from "./CreateCustomer";
import CustomerUpdate from "./CustomerUpdate";


export const CustomersGetAll = () => {
    const [customers, setCustomers] = useState<CustomerDto[]>([]);
    const [currentPage, setCurrentPage] = useState(1);
    const [totalElements, setTotalElements] = useState(0);
    const [totalPages, setTotalPages] = useState(0);
    const [filters, setFilters] = useState<CustomerFilters>({});
    const itemsPerPage = 10;
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        const fetchCustomers = async () => {
            try {
                const response = await getCustomers({
                    page: currentPage - 1,
                    size: itemsPerPage,
                    filters
                });
                setCustomers(response.content);
                setTotalElements(response.totalElements);
                setTotalPages(response.totalPages);
                setLoading(false);
            } catch (error: any) {
                setError(error.message);
                setLoading(false);
            }
        };

        fetchCustomers();
    }, [currentPage, filters]);

    const handleFilterChange = (newFilters: CustomerFilters) => {
        setFilters(newFilters);
        setCurrentPage(1); 
    };

    if (loading) return <div>Loading customers...</div>;
    if (error) return <div>Error loading customers: {error}</div>;

    return (
        <CustomerList 
            customers={customers}
            currentPage={currentPage}
            totalPages={totalPages}
            totalElements={totalElements}
            onPageChange={setCurrentPage}
            onFilterChange={handleFilterChange}
            filters={filters}
        />
    );
}

export const CustomerById = () => {
    const [customers, setCustomer] = useState<CustomerDto | null>(null);
    const [error, setError] = useState<string | null>(null);
    const { customerId } = useParams();

    useEffect(() => {
        if (customerId) {
            getCustomerById(Number(customerId))
                .then((response) => {
                    setCustomer(response);
                })
                .catch((error) => {
                    setError(error.message);
                });
        }
    }, [customerId]);
    if (error) return <div>Error loading customer: {error}</div>;
    return <CustomerDetail customer={customers} />
}

export const CustomerPost = () => {
    const [error, setError] = useState<string | null>(null);
    const navigate = useNavigate();

    const handleCreateCustomer = async (newCustomer: CreateCustomerDto) => {
        try {
            const createdCustomer = await createCustomer(newCustomer);
            navigate(`/customers/${createdCustomer.customerId}`);
        } catch (error: any) {
            setError(error.message);
        }
    };

    if (error) return <div>Error creating customer: {error}</div>;

    return <CreateCustomer onSubmit={handleCreateCustomer} />;
}

export const CustomerPut = () => {
    const { customerId } = useParams();
    const [formData, setFormData] = useState<CustomerPutDto>({
        name: "",
        phone: 0,
        email: ""
    });
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        if (customerId) {
            getCustomerById(Number(customerId))
                .then((customer) => {
                    setFormData({
                        name: customer.name,
                        email: customer.email,
                        phone: customer.phone,
                    });
                })
                .catch((error) => {
                    setError(error.message);
                });
        }
    }, [customerId]);


    if (error) return <div>Error loading customer: {error}</div>;
    return <CustomerUpdate customer={formData}
    />;

}



