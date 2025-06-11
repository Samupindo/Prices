import { Link } from "react-router-dom"

export const Home = () => {
    return (
        <div className="min-h-screen bg-gradient-to-br from-gray-50 to-gray-100 py-12 px-4 sm:px-6 lg:px-8">
            <div className="max-w-7xl mx-auto">
                <h1 className="text-4xl font-bold text-gray-900 text-center mb-12">
                    Welcome to Mic-Prices
                </h1>
                <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-8">
                    <Link
                        to="/products"
                        className="bg-white p-6 rounded-xl shadow-lg hover:shadow-xl hover:scale-[1.03] transition-all duration-300 transform flex flex-col items-center text-center border-2 border-indigo-500 hover:border-indigo-600"
                    >
                        <div className="bg-indigo-100 p-4 rounded-full mb-6">
                            <svg
                                className="w-10 h-10 text-indigo-600"
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
                        <h2 className="text-2xl font-bold text-indigo-900 mb-3">
                            Products
                        </h2>
                        <p className="text-gray-600">
                            Manage your product catalog
                        </p>
                    </Link>

                    <Link
                        to="/shops"
                        className="bg-white p-6 rounded-xl shadow-lg hover:shadow-xl hover:scale-[1.03] transition-all duration-300 transform flex flex-col items-center text-center border-2 border-purple-500 hover:border-purple-600"
                    >
                        <div className="bg-purple-100 p-4 rounded-full mb-6">
                            <svg
                                className="w-10 h-10 text-purple-600"
                                fill="none"
                                stroke="currentColor"
                                viewBox="0 0 24 24"
                            >
                                <path
                                    strokeLinecap="round"
                                    strokeLinejoin="round"
                                    strokeWidth={2}
                                    d="M3 3h2l.4 2M7 13h10l4-8H5.4M7 13L5.4 5M7 13l-2.293 2.293c-.63.63-.184 1.707.707 1.707H17m0 0a2 2 0 100 4 2 2 0 000-4zm-8 2a2 2 0 11-4 0 2 2 0 014 0z"
                                />
                            </svg>
                        </div>
                        <h2 className="text-2xl font-bold text-purple-900 mb-3">
                            Shops
                        </h2>
                        <p className="text-gray-600">
                            Manage your shop information
                        </p>
                    </Link>

                    <Link
                        to="/customers"
                        className="bg-white p-6 rounded-xl shadow-lg hover:shadow-xl hover:scale-[1.03] transition-all duration-300 transform flex flex-col items-center text-center border-2 border-green-500 hover:border-green-600"
                    >
                        <div className="bg-green-100 p-4 rounded-full mb-6">
                            <svg
                                className="w-10 h-10 text-green-600"
                                fill="none"
                                stroke="currentColor"
                                viewBox="0 0 24 24"
                            >
                                <path
                                    strokeLinecap="round"
                                    strokeLinejoin="round"
                                    strokeWidth={2}
                                    d="M17 20h5v-2a3 3 0 00-5.356-1.857M17 20H7m10 0v-2c0-.656-.126-1.283-.356-1.857M7 20H2v-2a3 3 0 015.356-1.857M7 20v-2c0-.656.126-1.283.356-1.857m0 0a5.002 5.002 0 019.288 0M15 7a3 3 0 11-6 0 3 3 0 016 0zm6 3a2 2 0 11-4 0 2 2 0 014 0zM7 10a2 2 0 11-4 0 2 2 0 014 0z"
                                />
                            </svg>
                        </div>
                        <h2 className="text-2xl font-bold text-green-900 mb-3">
                            Customers
                        </h2>
                        <p className="text-gray-600">
                            Manage your customer information
                        </p>
                    </Link>

                    <Link
                        to="/purchases"
                        className="bg-white p-6 rounded-xl shadow-lg hover:shadow-xl hover:scale-[1.03] transition-all duration-300 transform flex flex-col items-center text-center border-2 border-pink-500 hover:border-pink-600"
                    >
                        <div className="bg-pink-100 p-4 rounded-full mb-6">
                            <svg
                                className="w-10 h-10 text-pink-600"
                                fill="none"
                                stroke="currentColor"
                                viewBox="0 0 24 24"
                            >
                                <path
                                    strokeLinecap="round"
                                    strokeLinejoin="round"
                                    strokeWidth={2}
                                    d="M12 8c-1.657 0-3 .895-3 2s1.343 2 3 2 3 .895 3 2-1.343 2-3 2m0-8c1.11 0 2.08.402 2.599 1M12 8V7m0 1v8m0 0v1m0-1c-1.11 0-2.08-.402-2.599-1M21 12a9 9 0 11-18 0 9 9 0 0118 0z"
                                />
                            </svg>
                        </div>
                        <h2 className="text-2xl font-bold text-pink-900 mb-3">
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