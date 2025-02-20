import { useEffect, useRef } from "react";
import { Button, Container, Form } from "react-bootstrap"
import { useNavigate } from "react-router-dom"

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

    return(
        /*Bootstrap gives us this Container element that does some default padding and centering*/
        <Container> 

            <h1>Welcome</h1>
                <h3>Please Log In:</h3>
                
                <div>
                    <Form.Control
                        type="text"
                        placeholder="username"
                        name="username"
                        ref={usernameRef} //attaches ref
                    />
                </div>

                <div>
                    <Form.Control
                        type="password"
                        placeholder="password"
                        name="password"
                    />
                </div>
                

            <Button className="btn-success m-1">Login</Button>
            <Button className="btn-dark" onClick={()=>navigate("/register")}>Register</Button>
        </Container>
    )


}