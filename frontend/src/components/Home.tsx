import { Link } from "react-router-dom"

export const Home = () => {
    return (
        <div className="min-h-screen bg-gray-50">
            <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-16">
                <div className="text-center">
                    <h1 className="text-4xl font-bold text-gray-900 mb-8">
                        Welcome to Micro Prices
                    </h1>
                    <p className="text-xl text-gray-600 mb-12">
                        Manage your products and shops efficiently
                    </p>
                </div>

                <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-8">
                    <Link 
                        to="/products" 
                        className="bg-white p-6 rounded-lg shadow-md hover:shadow-lg transition-all duration-300 flex flex-col items-center text-center"
                    >
                        <div className="bg-indigo-100 p-3 rounded-full mb-4">
                            <svg 
                                className="w-8 h-8 text-indigo-600" 
                                fill="none" 
                                stroke="currentColor" 
                                viewBox="0 0 24 24"
                            >
                                <path 
                                    strokeLinecap="round" 
                                    strokeLinejoin="round" 
                                    strokeWidth={2} 
                                    d="M12 4v16m8-8H4"
                                />
                            </svg>
                        </div>
                        <h2 className="text-2xl font-semibold text-gray-900 mb-2">
                            Products
                        </h2>
                        <p className="text-gray-600">
                            View and manage your product catalog
                        </p>
                    </Link>

                    <Link 
                        to="/shops" 
                        className="bg-white p-6 rounded-lg shadow-md hover:shadow-lg transition-all duration-300 flex flex-col items-center text-center"
                    >
                        <div className="bg-green-100 p-3 rounded-full mb-4">
                            <svg 
                                className="w-8 h-8 text-green-600" 
                                fill="none" 
                                stroke="currentColor" 
                                viewBox="0 0 24 24"
                            >
                                <path 
                                    strokeLinecap="round" 
                                    strokeLinejoin="round" 
                                    strokeWidth={2} 
                                    d="M17.657 16.657L13.414 20.9a1.998 1.998 0 01-2.827 0l-4.244-4.243a8 8 0 1111.314 0z"
                                />
                            </svg>
                        </div>
                        <h2 className="text-2xl font-semibold text-gray-900 mb-2">
                            Shops
                        </h2>
                        <p className="text-gray-600">
                            Manage your retail locations
                        </p>
                    </Link>

                    <Link 
                        to="/customers" 
                        className="bg-white p-6 rounded-lg shadow-md hover:shadow-lg transition-all duration-300 flex flex-col items-center text-center"
                    >
                        <div className="bg-blue-100 p-3 rounded-full mb-4">
                            <svg 
                                className="w-8 h-8 text-blue-600" 
                                fill="none" 
                                stroke="currentColor" 
                                viewBox="0 0 24 24"
                            >
                                <path 
                                    strokeLinecap="round" 
                                    strokeLinejoin="round" 
                                    strokeWidth={2} 
                                    d="M15 7a2 2 0 012 2m4 0a6 6 0 01-7.743 5.743L11 17H9v2H7v2H4a1 1 0 01-1-1v-2.586a1 1 0 01.293-.707l5.964-5.964A6 6 0 1121 9z"
                                />
                            </svg>
                        </div>
                        <h2 className="text-2xl font-semibold text-gray-900 mb-2">
                            Customers
                        </h2>
                        <p className="text-gray-600">
                            Manage your customer information and preferences
                        </p>
                    </Link>

                    <Link 
                        to="/purchases" 
                        className="bg-white p-6 rounded-lg shadow-md hover:shadow-lg transition-all duration-300 flex flex-col items-center text-center"
                    >
                        <div className="bg-purple-100 p-3 rounded-full mb-4">
                            <svg 
                                className="w-8 h-8 text-purple-600" 
                                fill="none" 
                                stroke="currentColor" 
                                viewBox="0 0 24 24"
                            >
                                <path 
                                    strokeLinecap="round" 
                                    strokeLinejoin="round" 
                                    strokeWidth={2} 
                                    d="M3 10h18M7 15h1m4 0h1m-7 4h12a3 3 0 11-1.732 2.866L7.066 22H3a1 1 0 110-2h4l5.172-5.172a1.5 1.5 0 012.122 0l5.171 5.171a1 1 0 010 1.414z"
                                />
                            </svg>
                        </div>
                        <h2 className="text-2xl font-semibold text-gray-900 mb-2">
                            Purchases
                        </h2>
                        <p className="text-gray-600">
                            Manage and track customer purchases
                        </p>
                    </Link>
                </div>
            </div>
        </div>
    )
}