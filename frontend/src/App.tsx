import { BrowserRouter, Routes, Route } from 'react-router-dom'
import './App.css'
import { AllShops } from './components/shops/shops'
import { ShopById } from './components/shops/shops'

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<div>Home</div>} />
        <Route path="/shops" element={<AllShops />} />
        <Route path="/shops/:shopId" element={<ShopById />} />
      </Routes>
    </BrowserRouter>
  )
}

export default App
