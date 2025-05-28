import { useEffect, useState } from "react";
import { getCustomers, getCustomerById } from "./services/customerService";
import type { CustomerDto } from "./types/customer";
import CustomerList from "./CustomerList";
import { useParams } from "react-router-dom";
import CustomerDetail from "./customerDetail";


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
    
    return <CustomerList customers={customers}/>
}

export const CustomerById = () => {
    const [customers, setCustomer] = useState<CustomerDto | null>(null);
    const [error, setError] = useState<string | null>(null);
    const {customerId} = useParams();

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
    return <CustomerDetail customer={customers}/>
}