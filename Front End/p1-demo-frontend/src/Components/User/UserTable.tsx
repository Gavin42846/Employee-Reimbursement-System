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
            //console.log(response.data)

            //store the user data in our users state object
            setUsers(response.data)
        } catch(error){
            console.error("Error fetching users:", error);
            alert("something went wrong trying to fetch users")
        }
    }

    //DELETE user function
    const deleteUser = async (userId: number) => {
        if (!window.confirm("Are you sure you want to delete this user? This will also remove all their reimbursements!")) {
            return;
        }

        try {
            await axios.delete(`http://localhost:8080/users/${userId}`, {
                withCredentials: true
            });

            alert(`User ${userId} has been deleted.`);
            
            //Remove deleted user from UI without refreshing the page
            setUsers(users.filter(user => user.userId !== userId));

        } catch (error) {
            console.error("Error deleting user:", error);
            alert("Failed to delete user.");
        }
    };

    return(
        
        <Container className="d-flex flex-column align-items-center mt-3">
            
            <div className="d-flex justify-content-end w-50">
                <Button variant="dark" className="mb-2" onClick={()=>navigate("/")}>Login</Button>
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
                                {/* <Button variant="outline-success" >Promote</Button> */}
                                
                                <Button variant="outline-danger" onClick={() => deleteUser(user.userId)}>Fire</Button> 
                            </td>
                        </tr>
                    ))}
                </tbody>
            </Table>
            
        </Container>
        
    )

}