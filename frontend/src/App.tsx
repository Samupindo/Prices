import { BrowserRouter, Routes, Route } from 'react-router-dom'
import './App.css'
import { Shops } from './components/shops/shops'
import { Products } from './components/products/products'

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<div>Home</div>} />
        <Route path="/shops" element={<Shops />} />
        <Route path="/products" element={<Products />} />
      </Routes>
    </BrowserRouter>
  )
}

export default App
