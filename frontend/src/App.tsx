import './App.css'
import { BrowserRouter, Route, Routes } from 'react-router-dom'
import { CustomerPost, CustomerPut, CustomersGetAll } from './components/customers/customers'
import { CustomerById } from './components/customers/customers'
import { CustomerDelete } from './components/customers/CustomerDelete'


function App() {
  return(
    <BrowserRouter>
        <Routes>
          <Route path="/" element={<h2>Home</h2>} />
          <Route path="/customers" element={<CustomersGetAll />} />
          <Route path="/customers/:customerId" element={<CustomerById />} />
          <Route path="/customers-createCustomers" element={<CustomerPost/>} />
          <Route path="/customers/:customerId/update" element={<CustomerPut />} />
          <Route path="/customers/delete/:customerId" element={<CustomerDelete />} />
        </Routes>
    </BrowserRouter>
  )
  
}

export default App
