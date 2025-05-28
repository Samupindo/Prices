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

                <div className="grid grid-cols-1 md:grid-cols-2 gap-8">
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
                </div>
            </div>
        </div>
    )
}