import { useNavigate } from "react-router-dom";
import type { CustomerDto } from "../../types/Customer";


interface CustomerDetailProps {
    customer: CustomerDto | null;
}

const CustomerDetail = ({ customer }: CustomerDetailProps) => {
    const navigate = useNavigate();
    if (!customer) {
        return <div className="text-red-500">Customer not found</div>;
    }

    return (
        <div className="container mx-auto p-8">
            <h2 className="text-2xl text-center font-bold mb-8 text-gray-900 pb-2 border-b-2 border-gray-200">
                Customer Detail
            </h2>
            <div className="flex flex-row space-x-10  items-center mb-6">
                <div className="bg-white shadow-md rounded-lg p-6">
                    <p className="text-gray-700 mb-4"><strong>ID:</strong> {customer.customerId}</p>
                    <p className="text-gray-700 mb-4"><strong>Name:</strong> {customer.name}</p>
                    <p className="text-gray-700 mb-4"><strong>Phone:</strong> {customer.phone}</p>
                    <p className="text-gray-700 mb-4"><strong>Email:</strong> {customer.email}</p>
                    <div className="flex gap-7 justify-center items-center ">
                        <button
                            onClick={() => navigate(`/customers/${customer.customerId}/edit`)}
                            className="bg-blue-500 text-center text-black px-4 py-2 rounded hover:bg-blue-600 transition-colors duration-200">
                            Edit
                        </button>
                    </div>
                </div>
            </div>
        </div>
    );
};
export default CustomerDetail;