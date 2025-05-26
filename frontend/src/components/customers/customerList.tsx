import React, { useEffect, useState } from "react";
import { CustomerDto } from "./types/Customer";
import { CustomerService } from "./services/customerService";
import { PageResponse } from "./types/Customer";

const CustomerList = () => {
    const [customerPage, setCustomerPage] = useState<PageResponse<CustomerDto>>({
        content: [],
        totalElements: 0,
        totalPages: 0
    });
    const [loading, setLoading] = useState<boolean>(true);
    const [error, setError] = useState<string | null>(null);

    const customerService = new CustomerService();

    useEffect(() => {
        const fetchCustomers = async () => {
            try {
                console.log('Fetching customers...');
                const response = await customerService.getCustomers();
                setCustomerPage(response);
                console.log('Customers loaded:', response);
            } catch (error: any) {
                console.error("Error details:", error);
                setError(`Error: ${error.message}`);
            } finally {
                setLoading(false);
            }
        };

        fetchCustomers();
    }, []);

    if (loading) {
        return <div>Loading Customers... Please wait.</div>;
    }

    if (error) {
        return (
            <div>
                <h2>Error Loading Customers</h2>
                <div style={{ color: 'red', margin: '10px 0' }}>{error}</div>
                <div>Please check your browser's console for more details.</div>
            </div>
        );
    }

    if (!customerPage.content || customerPage.content.length === 0) {
        return (
            <div>
                <h2>Customer List</h2>
                <p>No customers found in the database</p>
            </div>
        );
    }

    return (
        <div>
            <h2>Customer List</h2>
            <div>Total customers: {customerPage.totalElements}</div>
            <div>Page: {customerPage.totalPages}</div>
            <table border={1} cellPadding={10}>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Name</th>
                        <th>Email</th>
                        <th>Phone</th>
                    </tr>
                </thead>
                <tbody>
                    {customerPage.content.map((customer: CustomerDto) => (
                        <tr key={customer.customerId}>
                            <td>{customer.customerId}</td>
                            <td>{customer.name}</td>
                            <td>{customer.email}</td>
                            <td>{customer.phone}</td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
};

export default CustomerList;