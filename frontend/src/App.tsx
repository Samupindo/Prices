import { BrowserRouter, Routes, Route } from 'react-router-dom'
import './App.css'
import { Products } from './components/products/Product'
import { Home } from './components/Home'
import { CreateProduct } from './components/products/CreateProduct'
import { ProductDetail } from './components/products/ProductDetail'
import { UpdateProduct } from './components/products/UpdateProduct'
import { DeleteProduct } from './components/products/DeleteProduct'
import { CustomerById, CustomerPost, CustomerPut, CustomersGetAll } from './components/customers/Customers';
import { CustomerDelete } from './components/customers/CustomerDelete'
import { AllShops, ShopPut } from './components/shops/Shops'
import { ShopById } from './components/shops/Shops'
import { ShopPost } from './components/shops/Shops'
import { ShopDelete } from './components/shops/Shops'
import { Purchases } from './components/purchases/Purchases'
import { PurchaseDetail } from './components/purchases/PurchaseDetail'
import { AddPurchase } from './components/purchases/AddPurchase'
import { DeletePurchase } from './components/purchases/DeletePurchase'
import { FinishPurchase } from './components/purchases/FinishPurchase'
import { AddProduct } from './components/purchases/AddProduct'  
import { DeleteProductFromPurchase } from './components/purchases/DeleteProductFromPurchase'
function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/shops" element={<AllShops />} />
        <Route path="/shops/:shopId" element={<ShopById />} />
        <Route path="/shops/create" element={<ShopPost />} />
        <Route path="/shops/:shopId/edit" element={<ShopPut />} />
        <Route path="/shops/:shopId/delete" element={<ShopDelete />} />

        <Route path="/products" element={<Products />} />
        <Route path="*" element={<div>404 Not Found</div>} />
        <Route path="/products-create" element={<CreateProduct />} />
        <Route path="/products/:id" element={<ProductDetail />} />
        <Route path="/update-products/:id" element={<UpdateProduct />} />
        <Route path="/delete-products/:id" element={<DeleteProduct />} />


        <Route path="/customers" element={<CustomersGetAll />} />
        <Route path="/customers/:customerId" element={<CustomerById />} />
        <Route path="/customers-createCustomers" element={<CustomerPost />} />
        <Route path="/customers/:customerId/update" element={<CustomerPut />} />
        <Route path="/customers/delete/:customerId" element={<CustomerDelete />} />

        <Route path="/purchases" element={<Purchases />} />
        <Route path='/purchases/:id' element={<PurchaseDetail />} />
        <Route path="/create-purchase" element={<AddPurchase />} />
        <Route path="/delete-purchases/:id" element={<DeletePurchase />} />
        <Route path="/finish-purchases/:id" element={<FinishPurchase />} />
        <Route path="/add-purchaseLine" element={<AddProduct />} />
        <Route path="/delete-purchaseLine" element={<DeleteProductFromPurchase />} />
      </Routes>
    </BrowserRouter>
  )
}

export default App