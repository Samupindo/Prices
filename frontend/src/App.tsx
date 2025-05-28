import { BrowserRouter, Routes, Route } from 'react-router-dom'
import './App.css'
import { Shops } from './components/shops/shops'
import { Products } from './components/products/Product'
import { Home } from './components/Home'
import { CreateProduct } from './components/products/CreateProduct'
import { ProductDetail } from './components/products/ProductDetail'
import { UpdateProduct } from './components/products/UpdateProduct'
import { DeleteProduct } from './components/products/DeleteProduct'

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/shops" element={<Shops />} />
        <Route path="/products" element={<Products />} />
        <Route path="*" element={<div>404 Not Found</div>} />
        <Route path="/products-create" element={<CreateProduct />} />
        <Route path="/products/:id" element={<ProductDetail />} />
        <Route path="/update-products/:id" element={<UpdateProduct />} />
        <Route path="/delete-products/:id" element={<DeleteProduct />} />


      </Routes>
    </BrowserRouter>
  )
}

export default App
