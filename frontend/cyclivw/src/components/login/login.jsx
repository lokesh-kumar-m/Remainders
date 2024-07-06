
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { useAuth } from "../auth";
import './login.css'

const Login = () => {
    const authContext = useAuth()
    const [name, setName] = useState("")
    const [password, setPassword] = useState("")
    const [ErrorMessage, setMessage] = useState('')
    const navigateTo = useNavigate()
    const delay = ms => new Promise(res => setTimeout(res, ms));

    async function handleLogin() {
        
        if (await authContext.islogin(name, password)) {
            setMessage("Login Successful. Redirecting..")
            await delay(1000)
            navigateTo(`/home/${name}`)
        } else {
            console.log()
            setMessage("Please check username and password")
        }
    }
    function signupPage() {
        navigateTo(`/auth/register`)
    }
    return (
        <div className="login-card">
            <h2>Login</h2>
            <div>
                <div className="input-group">
                    <label>Username</label>
                    <input type="text" id="username" name="username" value={name} onChange={(e) => setName(e.target.value)} />
                </div>
                <div className="input-group">
                    <label >Password</label>
                    <input type="password" id="password" name="password" value={password} onChange={(e) => setPassword(e.target.value)} />
                </div>
                {ErrorMessage}
                <div className="button-group">
                    <button type="button" onClick={handleLogin}>Login</button>
                    <button type="button" onClick={signupPage}>Sign Up</button>
                </div>
            </div>
        </div>
    );
}

export default Login