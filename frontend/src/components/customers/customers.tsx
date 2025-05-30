import { use, useEffect, useState } from "react";
import { getCustomers, getCustomerById, createCustomer, updateCustomer } from "./services/customerService";
import type { CreateCustomerDto, CustomerDto, CustomerPutDto } from "./types/Customer";
import CustomerList from "./CustomerList";
import { useNavigate, useParams } from "react-router-dom";
import CustomerDetail from "./CustomerDetail";
import CreateCustomer from "./CreateCustomer";
import CustomerUpdate from "./CustomerUpdate";


export const CustomersGetAll = () => {
    const [customers, setCustomers] = useState<CustomerDto[]>([]);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        getCustomers()
            .then((response) => {
                setCustomers(response);
            })
            .catch((error) => {
                setError(error.message);
            });
    }, []);

    if (error) return <div>Error loading customers: {error}</div>;

    return <CustomerList customers={customers} />
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



