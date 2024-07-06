import React from "react";
import RegisterUser from "./Register/register";
import Login from "./login/login";
import Header from "./Header/Header"
import Home from "./Home"
import AddTasks from "./Tasks/NewTask";
import { BrowserRouter,Navigate,Route,Routes } from "react-router-dom";
import Auth, { useAuth } from "./auth";

function AuthenticatedRoutes({children}){
    const authContext=useAuth()
    if(authContext.isAuthentic){
        return children
    }
    return <Navigate to="/"></Navigate>
}

const Main= () =>{
    return(
        <div>
            <Auth>
                <BrowserRouter>
                <Header></Header>
                    <Routes>
                    <Route path='/' element={<RegisterUser />}/>
                    <Route path='/auth/register' element={<RegisterUser />} />
                    <Route path='/auth/login' element={<Login/>}/>
                    <Route path='/home/:username' element={
                        <AuthenticatedRoutes>
                            <Home></Home>
                        </AuthenticatedRoutes>
                    }/>
                    <Route path='/home/addTask/:username' element={
                        <AuthenticatedRoutes>
                            <AddTasks></AddTasks>
                        </AuthenticatedRoutes>
                    }/>
                    </Routes>
                </BrowserRouter>
            </Auth>
        </div>
    );
}

export default Main;