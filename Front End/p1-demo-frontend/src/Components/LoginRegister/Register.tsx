import axios from "axios"
import { useState } from "react"
import { Button, Container, Form } from "react-bootstrap"
import { useNavigate } from "react-router-dom"

export const Register:React.FC = () => {


    //we can use the useNavigate hook to navigate between components programatically
        //(no more manual URL changing)
        const navigate = useNavigate()
  
        const register = async () => {

            if (!regisCreds.username || !regisCreds.firstname || !regisCreds.lastname || !regisCreds.password) {
                alert("All fields are required. Please fill in all fields.");
                return;
            }


            try {
                //Send user-provided input instead of hardcoded values
                const response = await axios.post("http://localhost:8080/auth/register", regisCreds);
        
                alert("User created!");
                console.log(response.data); // Check response from backend
        
                navigate("/");
        
            } catch (error) {
                console.error("Registration failed:", error);
                alert("Registration failed. Please try again.");
            }
        }   

    //Defining a state object to store the user's login credentials
        const [regisCreds, setRegisCreds] = useState({
            username:"",
            firstname:"",
            lastname:"",
            password:""
        }) //could have made interface for this but we didnt
    
        //Function to store user inputs
        const storeValues = (event:React.ChangeEvent<HTMLInputElement>) => {
            //im going to store the name and value of the inputs for ease of use below
    
            const name = event.target.name
            const value = event.target.value
    
            //"Take whatever input was changed, and set the matching state field to the value of that input"
            //[name] can be either username or password. this ugly code lends flexibility
            //this syntax is less useful with only 2 fields but wayyy more useful if there are like, 50
            setRegisCreds((regisCreds) => ({...regisCreds, [name]:value}))
        }

    return(

        <Container>
        <div>
            <h1>New here? Create an Account for free!</h1>
            <div>
                <Form.Control
                    type="text"
                    placeholder="username"
                    name="username"
                    value={regisCreds.username}
                    onChange={storeValues}
                />
            </div>
            <div>
                <Form.Control
                    type="text"
                    placeholder="first name"
                    name="firstname"
                    value={regisCreds.firstname}
                    onChange={storeValues}
                />
            </div>
            <div>
                <Form.Control
                    type="text"
                    placeholder="last name"
                    name="lastname"
                    value={regisCreds.lastname}
                    onChange={storeValues}
                />
            </div>
              
            <div>
                <Form.Control
                    type="password"
                    placeholder="password"
                    name="password"
                    value={regisCreds.password}
                    onChange={storeValues}
                />
            </div>

                <Button onClick={register} disabled={!regisCreds.username || !regisCreds.firstname || !regisCreds.lastname || !regisCreds.password}>
                    Create Account!
                </Button>
            </div>
      </Container>
  )

}