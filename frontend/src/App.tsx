import { BrowserRouter, Routes, Route } from 'react-router-dom'
import './App.css'
import { Shops } from './components/shops/shops'
import { Products } from './components/products/products'
import { Home } from './components/Home'

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/shops" element={<Shops />} />
        <Route path="/products" element={<Products />} />
        <Route path="*" element={<div>404 Not Found</div>} />
      </Routes>
    </BrowserRouter>
  )
}

export default App
