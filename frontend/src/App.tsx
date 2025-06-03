import { BrowserRouter, Routes, Route } from 'react-router-dom'
import './App.css'
import { Products } from './components/products/Product'
import { Home } from './components/Home'
import { CreateProduct } from './components/products/CreateProduct'
import { ProductDetail } from './components/products/ProductDetail'
import { UpdateProduct } from './components/products/UpdateProduct'
import { DeleteProduct } from './components/products/DeleteProduct'
import { CustomerById, CustomerPost, CustomerPut, CustomersGetAll } from './components/customers/customers';
import { CustomerDelete } from './components/customers/CustomerDelete'
import { AllShops, ShopPut } from './components/shops/shops'
import { ShopById } from './components/shops/shops'
import { ShopPost } from './components/shops/shops'
import { ShopDelete } from './components/shops/shops'
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
        <Route path="*" element={<div>404 Not Found</div>} />


        <Route path="/shops" element={<AllShops />} />
        <Route path="/shops/:shopId" element={<ShopById />} />
        <Route path="/shops/create" element={<ShopPost />} />
        <Route path="/shops/:shopId/edit" element={<ShopPut />} />
        <Route path="/shops/:shopId/delete" element={<ShopDelete />} />

        <Route path="/products" element={<Products />} />
        <Route path="/products/:productId" element={<ProductDetail />} />
        <Route path="/products/create" element={<CreateProduct />} />
        <Route path="/products/:productId/edit" element={<UpdateProduct />} />
        <Route path="/products/:productId/delete" element={<DeleteProduct />} />

        <Route path="/customers" element={<CustomersGetAll />} />
        <Route path="/customers/:customerId" element={<CustomerById />} />
        <Route path="/customers/create" element={<CustomerPost />} />
        <Route path="/customers/:customerId/edit" element={<CustomerPut />} />
        <Route path="/customers/:customerId/delete" element={<CustomerDelete />} />

        <Route path="/purchases" element={<Purchases />} />
        <Route path='/purchases/:purchaseId' element={<PurchaseDetail />} />
        <Route path="/purchases/create" element={<AddPurchase />} />
        <Route path="/purchases/:purchaseId/delete" element={<DeletePurchase />} />
        <Route path="/purchases/:purchaseId/finish" element={<FinishPurchase />} />
        <Route path="/purchases/:purchaseId/addProduct" element={<AddProduct />} />
        <Route path="/purchases/:purchaseId/deleteProduct" element={<DeleteProductFromPurchase />} />
      </Routes>
    </BrowserRouter>
  )
}

export default App