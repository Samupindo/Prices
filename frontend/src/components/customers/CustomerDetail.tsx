import type { CustomerDto } from "./types/customer";


interface CustomerDetailProps {
    customer: CustomerDto | null;
}

const CustomerDetail = ({ customer }: CustomerDetailProps) => {
    if (!customer) {
        return <div className="text-red-500">Customer not found</div>;
    }

    return (
        <div className="container mx-auto p-8">
            <h2 className="text-2xl font-bold mb-8 text-gray-900 pb-2 border-b-2 border-gray-200">
                Customer Detail
            </h2>
            <div className="bg-white shadow-md rounded-lg p-6">
                <p className="text-gray-700 mb-4"><strong>ID:</strong> {customer.customerId}</p>
                <p className="text-gray-700 mb-4"><strong>Name:</strong> {customer.name}</p>
                <p className="text-gray-700 mb-4"><strong>Phone:</strong> {customer.phone}</p>
                <p className="text-gray-700 mb-4"><strong>Email:</strong> {customer.email}</p>
            </div>
        </div>
    );
};
export default CustomerDetail;