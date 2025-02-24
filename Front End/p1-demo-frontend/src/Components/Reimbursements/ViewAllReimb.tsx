import { Button, Container, Table } from "react-bootstrap"
import { User } from "../../Interfaces/User"
import { useEffect, useState } from "react"
import axios from "axios"
import { ReimbursementWithUser } from "../../Interfaces/ReinbWithUser"
import { Reimbursement } from "../../Interfaces/Reimbursement"
import { useNavigate } from "react-router-dom"



export const ViewAllReimb:React.FC = () => {

        const [users, setUsers] = useState<User[]>([]);
        const [reimbursements, setReimbursements] = useState<Reimbursement[]>([]);
        const [combinedData, setCombinedData] = useState<ReimbursementWithUser[]>([]);
        const [filteredData, setFilteredData] = useState<ReimbursementWithUser[]>([]);
        const [loading, setLoading] = useState(true);
        const [showPending, setShowPending] = useState(false); 
        const navigate = useNavigate()
    
        useEffect(() => {
            fetchUsersAndReimbursements();
        }, []);
    
        const fetchUsersAndReimbursements = async () => {
            try {
                // Fetch users
                const usersResponse = await axios.get("http://localhost:8080/users", {
                    withCredentials: true
                });
    
                // Fetch reimbursements
                const reimbursementsResponse = await axios.get("http://localhost:8080/reimbursements/view", {
                    withCredentials: true
                });
    
                // Store raw data
                setUsers(usersResponse.data);
                setReimbursements(reimbursementsResponse.data);

    
                // Merge users with reimbursements
                const mergedData = reimbursementsResponse.data.map((reimb: Reimbursement) => {
                    const userId = reimb.user ? Number(reimb.user.userId) : null;

                    const user = usersResponse.data.find((u: User) => Number(u.userId) === userId);
                    return {
                        reimbId: reimb.reimbId,
                        userId: userId || "Unknown",
                        username: user ? user.username : "Unknown User",
                        description: reimb.description,
                        amount: Number(reimb.amount),
                        status: reimb.status
                    };
                });
    
                // Store the merged data
                setCombinedData(mergedData);
                setFilteredData(mergedData);
            } catch (error) {
                console.error("Error fetching data:", error);
                navigate("/");
            } finally {
                setLoading(false);
            }
        };

        //Function to handle approve/deny actions
    const updateStatus = async (reimbId: number, newStatus: string) => {
        try {
            const response = await axios.patch(`http://localhost:8080/reimbursements/${reimbId}/status/${newStatus}`, null, {
                withCredentials: true
            });

            console.log(`Reimbursement ${reimbId} updated to ${newStatus}`, response.data);
            alert(`Reimbursement ${reimbId} marked as ${newStatus}`);

            // Refresh the data after updating the status
            fetchUsersAndReimbursements();
        } catch (error) {
            console.error("Error updating reimbursement status:", error);
            alert("Failed to update reimbursement status.");
        }
    };

    const filterPendingReimbursements = () => {
        if (showPending) {
            // Show all reimbursements when toggled back
            setFilteredData(combinedData);
            setShowPending(false);
        } else {
            // Filter only "PENDING" reimbursements
            setFilteredData(combinedData.filter(reimb => reimb.status.toUpperCase() === "PENDING"));
            setShowPending(true);
        }
    };
    
    


    return(
        <Container className="d-flex flex-column align-items-center mt-3">
                <Button variant="dark" className="mb-2" onClick={filterPendingReimbursements}>
                    {showPending ? "Show All Reimbursements" : "Show Pending Only"}
                </Button>
                    <div className="d-flex justify-content-end w-50">
                        <Button variant="dark" className="mb-2" onClick={()=>navigate("/users")}>Users</Button>
                    </div>
                    
                    <h3>Reimbursements: </h3>
        
                    <Table className="table-dark table-hover table-striped w-50">
                        <thead>
                            <tr>
                                <th>User Id</th>
                                <th>Username</th>
                                <th>Description</th>
                                <th>Amount</th>
                                <th>Status</th>
                                <th>Options</th>
                            </tr>
                        </thead>
                        <tbody className="table-secondary">
                        {filteredData.map((item) => (
                                <tr key={item.reimbId}>
                                    <td>{item.userId}</td>
                                    <td>{item.username}</td>
                                    <td>{item.description}</td>
                                    <td>${item.amount}</td>
                                    <td>{item.status}</td>
                                    <td>
                                        <Button variant="outline-success" onClick={() => updateStatus(item.reimbId, "approved")}>Approve</Button>
                                        <Button variant="outline-danger" onClick={() => updateStatus(item.reimbId, "declined")}>Deny</Button> 
                                    </td>
                                </tr>
                        ))}
                            {/*  why paraenthesis to open arrow function? because it implicitly returns instead of explicitly*/} 
                        </tbody>
                    </Table>
                </Container>
        )    
}