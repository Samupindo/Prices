import { BrowserRouter, Routes, Route } from 'react-router-dom'
import './App.css'
import { AllShops, ShopPut } from './components/shops/Shops'
import { ShopById } from './components/shops/Shops'
import { ShopPost } from './components/shops/Shops'
import { ShopDelete } from './components/shops/Shops'

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<div>Home</div>} />
        <Route path="/shops" element={<AllShops />} />
        <Route path="/shops/:shopId" element={<ShopById />} />
        <Route path="/shops/create" element={<ShopPost />} />
        <Route path="/shops/:shopId/edit" element={<ShopPut />} />
        <Route path="/shops/:shopId/delete" element={<ShopDelete />} />
      </Routes>
    </BrowserRouter>
  )
}

export default App
