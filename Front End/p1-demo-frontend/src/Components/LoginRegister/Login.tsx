import axios from "axios";
import { useEffect, useRef, useState } from "react";
import { Button, Container, Form } from "react-bootstrap"
import { useNavigate } from "react-router-dom"
import { store } from "../GlobalData/store";

export const Login:React.FC = () => {

    //we can use the useNavigate hook to navigate between components programatically
        //(no more manual URL changing)
    const navigate = useNavigate()

    //Using the useRef and useEffect hooks to focus our username input box on component load
    const usernameRef = useRef<HTMLInputElement>(null);

    useEffect(() => {
        //if the current value of the ref is truthy...
        if (usernameRef.current) {
            usernameRef.current.focus(); //focus it so the user can type right away
        }


    }, []); //remember [] means this happens on component load

    //Defining a state object to store the user's login credentials
    const [loginCreds, setLoginCreds] = useState({
        username:"",
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
        setLoginCreds((loginCreds) => ({...loginCreds, [name]:value}))
    }


    //Function to make the actual login request
    //navigates to /users if a manager loggen in, and /games if a user logged in
    const login = async () => {
        //TODO: make sure username/pw are present before proceeding

        try{

            const response = await axios.post("http://localhost:8080/auth/login", loginCreds, {withCredentials:true})
            //with credentials lets us interact with session on the backend
            //every req that depends on the user being loggeed in, being an admin, etc, needs this
            
            //if catch doesnt run login was successful, save the data to our global store then switch components
            store.loggedInUser = response.data // this is our logged in user data from the backend
            
            

            //users will get sent to users comp if they are an "admin" or the games comp if they are a "user"

            if(store.loggedInUser.role === "admin") {
                //greet user with stored data
                alert(store.loggedInUser.username + " has logged in! Welcome")
                navigate("/users")
            } else {
                //greet user with stored data
                alert(store.loggedInUser.username + " has logged in! Welcome")
                navigate("/reimbursements/my-reimbursements")
            }
        } catch{
            alert("Login unsuccessful")
        }
    }

    return(
        /*Bootstrap gives us this Container element that does some default padding and centering*/
        <Container> 

            <h1>Employee Reimbursement System</h1>
                <h3>Please Log In:</h3>
                
                <div>
                    <Form.Control
                        type="text"
                        placeholder="username"
                        name="username"
                        ref={usernameRef} //attaches ref
                        onChange={storeValues}
                    />
                </div>

                <div>
                    <Form.Control
                        type="password"
                        placeholder="password"
                        name="password"
                        onChange={storeValues}
                    />
                </div>
                

            <Button className="btn-success m-1" onClick={login}>Login</Button>
            <Button className="btn-dark" onClick={()=>navigate("/register")}>Register</Button>
        </Container>
    )


}