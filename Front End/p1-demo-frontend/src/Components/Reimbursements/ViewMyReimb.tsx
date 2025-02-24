import { Button, Container, Table } from "react-bootstrap";
import { useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";
import axios from "axios";
//import { Reimbursement } from "../../Interfaces/Reimbursement";

export interface Reimbursement1 {
    reimbId: number; 
    description: string;
    amount: number;
    status: string;
    user?: {  
        userId: number;
        username: string;
    };
}


export const ViewMyReimb: React.FC = () => {
    const navigate = useNavigate();
    const [reimbursements, setReimbursements] = useState<Reimbursement1[]>([]);
    const [loading, setLoading] = useState(true);
    const [filteredReimbursements, setFilteredReimbursements] = useState<Reimbursement1[]>([]);
    const [showPending, setShowPending] = useState(false); //Tracks if we're showing pending only


    useEffect(() => {
        fetchMyReimbursements();
    }, []);

    const fetchMyReimbursements = async () => {
        try {
            const response = await axios.get("http://localhost:8080/reimbursements/my-reimbursements", {
                withCredentials: true
            });
    
            console.log("My Reimbursements:", response.data);

    

            const formattedData = response.data.map((reimb: Reimbursement1) => ({
                ...reimb,
                userId: reimb.user ? reimb.user.userId : null 
            }));
    
            setReimbursements(formattedData);
            setFilteredReimbursements(response.data);
        } catch (error) {
            console.error("Error fetching reimbursements:", error);
            navigate("/");
        } finally {
            setLoading(false);
        }
    };
    const deleteReimbursement = async (reimbId: number) => {
        if (!window.confirm("Are you sure you want to delete this reimbursement?")) {
            return;
        }
    
        try {
            await axios.delete(`http://localhost:8080/reimbursements/${reimbId}`, {
                withCredentials: true
            });
    
            alert(`Reimbursement ${reimbId} has been deleted.`);
    
            //Remove the deleted reimbursement from both lists
            const updatedReimbursements = reimbursements.filter(reimb => reimb.reimbId !== reimbId);
            setReimbursements(updatedReimbursements);
            setFilteredReimbursements(updatedReimbursements.filter(reimb => showPending ? reimb.status.toUpperCase() === "PENDING" : true));
        } catch (error) {
            console.error("Error deleting reimbursement:", error);
            alert("Failed to delete reimbursement.");
        }
    };
    

    const filterPendingReimbursements = () => {
        if (showPending) {
            // If we are already showing only pending, show all reimbursements again
            setFilteredReimbursements(reimbursements);
            setShowPending(false);
        } else {
            // Otherwise, filter only pending ones
            setFilteredReimbursements(reimbursements.filter(reimb => reimb.status.toUpperCase() === "PENDING"));
            setShowPending(true);
        }
    };
    
    const logout = async () => {
        try {
            await axios.post("http://localhost:8080/auth/logout", null, {
                withCredentials: true,
            });
    
            alert("You have been logged out.");
            navigate("/"); //Redirect to login/home page
        } catch (error) {
            console.error("Error logging out:", error);
            alert("Failed to log out.");
        }
    };

    



    return (
        <Container className="d-flex flex-column align-items-center mt-3">
            <div className="d-flex justify-content-end w-50">
                <Button variant="dark" className="mb-2" onClick={logout}>Logout</Button>
                <Button variant="dark" className="mb-2" onClick={filterPendingReimbursements}>
                    {showPending ? "Show All Reimbursements" : "Show Pending Only"}
                </Button>
                <Button variant="dark" className="mb-2" onClick={() => navigate("/create-reimbursement")}>
                    Submit Reimbursement Request
                </Button>
            </div>

            <h3>My Reimbursements</h3>

            {loading ? (
                <p>Loading reimbursements...</p> // Prevents empty render
            ) : (
                <Table className="table-dark table-hover table-striped w-50">
                    <thead>
                        <tr>
                            <th>Description</th>
                            <th>Amount</th>
                            <th>Status</th>
                            <th>Options</th>
                        </tr>
                    </thead>
                    <tbody className="table-secondary">
                        {reimbursements.length > 0 ? (
                            filteredReimbursements.map((reimb) => (
                                <tr key={reimb.reimbId}>
                                    <td>{reimb.description}</td>
                                    <td>${reimb.amount.toFixed(2)}</td> {/* Formats amount */}
                                    <td>{reimb.status}</td>
                                    <td>
                                        <Button variant="outline-danger" onClick={() => deleteReimbursement(reimb.reimbId)}>Delete</Button>
                                    </td>
                                </tr>
                            ))
                        ) : (
                            <tr>
                                <td colSpan={4} className="text-center">
                                    No reimbursements found.
                                </td>
                            </tr>
                        )}
                    </tbody>
                </Table>
            )}
        </Container>
    );
};