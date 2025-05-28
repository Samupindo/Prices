import { BrowserRouter, Routes, Route } from 'react-router-dom'
import './App.css'
import { Shops } from './components/shops/shops'

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<div>Home</div>} />
        <Route path="/shops" element={<Shops />} />
      </Routes>
    </BrowserRouter>
  )
}

export default App
