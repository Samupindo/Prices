import React from 'react';
import logo from './logo.svg';
import './App.css';
import CustomerList from './components/customers/customerList';

function App() {
  return (
    <div className="App">
      <h1>Customer Management</h1>
      <CustomerList />
    </div>
  );
}

export default App;
