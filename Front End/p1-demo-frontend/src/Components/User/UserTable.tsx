import axios from "axios"
import { useEffect, useState } from "react"
import { Button, Container, Table } from "react-bootstrap"
import { User } from "../../Interfaces/User"
import { useNavigate } from "react-router-dom"

export const UserTable:React.FC = () => {

    const navigate = useNavigate()
    //state object to store the user array from the backend
    const[users, setUsers] = useState<User[]>([])

    //useEffect - we'll call a GET request for all suers when the component loads
    useEffect(()=> {
        //TODO: make sure the user is a manager. redirect them to login
        getAllUsers()
    }, []) // use square brackets to make it run once on load

    //function to get all users from backend (HTTP req)
    const getAllUsers = async() => {
        try{
            const response = await axios.get("http://localhost:8080/users", {
                withCredentials: true
            })
            console.log(response.data)

            //store the user data in our users state object
            setUsers(response.data)
        } catch{
            alert("something went wrong trying to fetch users")
        }
        
    }

    //function that does a fake update or delete(wanna show how to extract data from a map)
    const updateUser = (user:User) => {
        alert("User " + user.userId + " has been fake updated or deleted")
        //this is where you would do patch request
        //cache the list of users and update that so we dont make repeat DB calls
    }

    return(
        
        <Container className="d-flex flex-column align-items-center mt-3">
            
            <div className="d-flex justify-content-end w-50">
                <Button variant="dark" className="mb-2" onClick={()=>navigate("/reimbursements/view")}>Reimbursements</Button>
            </div>
            <h3>Users: </h3>
            
            <Table className="table-dark table-hover table-striped w-50">
                <thead>
                    <tr>
                        <th>User Id</th>
                        <th>Username</th>
                        <th>First</th>
                        <th>Last</th>
                        <th>Role</th>
                        <th>Options</th>
                    </tr>
                </thead>
                <tbody className="table-secondary">
                    {users.map((user:User)=> (
                        <tr key={user.userId}>
                            <td>{user.userId}</td>
                            <td>{user.username}</td>
                            <td>{user.firstname}</td>
                            <td>{user.lastname}</td>
                            <td>{user.role}</td>
                            <td>
                                <Button variant="outline-success" onClick={() => updateUser(user)}>Promote</Button>
                                <Button variant="outline-danger" onClick={() => updateUser(user)}>Fire</Button> 
                            </td>
                        </tr>
                    ))}
                    {/*  why paraenthesis to open arrow function? because it implicitly returns instead of explicitly*/}
                    
                    
                </tbody>
            </Table>
            
        </Container>
        
    )

}