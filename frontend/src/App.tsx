import './App.css'
import { BrowserRouter, Route, Routes } from 'react-router-dom'
import { CustomerPost, CustomersGetAll } from './components/customers/customers'
import { CustomerById } from './components/customers/customers'

function App() {
  return(
    <BrowserRouter>
        <Routes>
          <Route path="/" element={<h2>Home</h2>} />
          <Route path="/customers" element={<CustomersGetAll />} />
          <Route path="/customers/:customerId" element={<CustomerById />} />
          <Route path="/customers-createCustomers" element={<CustomerPost/>} />
        </Routes>
    </BrowserRouter>
  )
  
}

export default App
