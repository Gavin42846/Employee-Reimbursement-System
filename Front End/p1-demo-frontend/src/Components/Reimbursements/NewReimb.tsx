import axios from "axios"
import { useEffect, useState } from "react"
import { Button, Container, Form } from "react-bootstrap"
import { useNavigate } from "react-router-dom"

export const NewReimb:React.FC = () => {


    //we can use the useNavigate hook to navigate between components programatically
        //(no more manual URL changing)
        const navigate = useNavigate()

        // State to store form input values
    const [reimbData, setReimbData] = useState({
        amount: "",
        description: "",
        userId: null // Will be populated with logged-in userId
    });

    
    // Fetch userId on component load
    useEffect(() => {
        fetchUserId();
    }, []);

    const fetchUserId = async () => {
        try {
            const response = await axios.get("http://localhost:8080/auth/me", { withCredentials: true });
            setReimbData((prevState) => ({ ...prevState, userId: response.data.userId }));
        } catch (error) {
            console.error("Error fetching user ID:", error);
            alert("Failed to get logged-in user.");
        }
    };

    // Update form fields
    const handleInputChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value } = event.target;
        setReimbData((prevState) => ({ ...prevState, [name]: value }));
    };


    // Handle form submission
    const submitReimbursement = async () => {
        if (!reimbData.userId) {
            alert("User is not logged in. Please log in before submitting a request.");
            return;
        }
        
        const amountValue = Number(reimbData.amount);


        if(isNaN(amountValue) || amountValue <= 0) {
            alert("Reimbursement amount must be greater than 0.")
            return;
        }


        try {
            const response = await axios.post("http://localhost:8080/reimbursements", {
                description: reimbData.description,
                amount: Number(reimbData.amount), 
                status: "pending", 
                userId: reimbData.userId
            }, { withCredentials: true });

            console.log("Reimbursement submitted:", response.data);
            alert("Reimbursement request submitted successfully!");
            navigate("/reimbursements/my-reimbursements");
        } catch (error) {
            console.error("Error submitting reimbursement:", error);
            alert("Failed to submit reimbursement.");
        }
    };

    return(

        <Container>
        <div className="d-flex justify-content-end w-50">
            <Button variant="dark" className="mb-2" onClick={() => navigate("/reimbursements/my-reimbursements")}>
                My Reimbursements
            </Button>
        </div>
        <div>
            <h1>Reimbursement Application</h1>
            <div>
                <Form.Control
                    type="number"
                    placeholder="Enter amount for your request here"
                    name="amount"
                    value={reimbData.amount}
                    onChange={handleInputChange}
                    min="1"
                />
            </div>
            <div>
                <Form.Control
                    type="text"
                    placeholder="What is this request for?"
                    name="description"
                    value={reimbData.description}
                    onChange={handleInputChange}
                />
            </div>
            <div>
                <Button onClick={submitReimbursement}>Submit Reimbursement</Button>
            </div>
        </div>
    </Container>
  )

}