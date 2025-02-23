
import './App.css'
import { BrowserRouter, Route, Routes } from 'react-router-dom'
import { Login } from './Components/LoginRegister/Login'
import 'bootstrap/dist/css/bootstrap.css'
import { Register } from './Components/LoginRegister/Register'
import { UserTable } from './Components/User/UserTable'
import { ViewMyReimb } from './Components/Reimbursements/ViewMyReimb'
import { ViewAllReimb } from './Components/Reimbursements/ViewAllReimb'
import { NewReimb } from './Components/Reimbursements/NewReimb'
//^THIS IS A REQUIRED MANUAL IMPORT FOR BOOTSTRAP TO WORK!!!

function App() {

  return (
    <>
      
      <BrowserRouter>
        <Routes>
          {/* empty string or / for path makes the component render at startup */}
          <Route path="" element={<Login/>}/>
          <Route path="register" element={<Register/>}/>
          <Route path="users" element={<UserTable/>}/>
          <Route path="/reimbursements/my-reimbursements" element={<ViewMyReimb/>}/>
          <Route path="reimbursements/view" element={<ViewAllReimb/>}/>
          <Route path="/create-reimbursement" element={<NewReimb/>}/>
        </Routes>
      </BrowserRouter>

    </>
  )
}

export default App