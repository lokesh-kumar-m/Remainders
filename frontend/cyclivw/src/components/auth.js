import { createContext, useContext, useState } from "react";
import { apiContext } from "./API/apiContex";
import { AuthenticateUser } from "./API/userApiService";


export const AuthContext=createContext()
export const useAuth=()=>useContext(AuthContext)

const Auth=({children})=>{
    const [isAuthentic,setAuthentic]=useState(false)
    const [username,setName]=useState('')
    const [authToken,setToken]=useState('')

    async function islogin(username,password){
        try{
            const response=await AuthenticateUser({"userName":username,"password":password})
            if(response.status==200){
            const jwtToken='Bearer '+response.data
            setToken(jwtToken)
            setName(username)
            setAuthentic(true)
            apiContext.interceptors.request.use(
                (config)=>{
                    config.headers.Authorization=jwtToken
                    return config
                }
            )
            return true;
            }
            else{
                setAuthentic(false)
                return false
            }

        }catch(error){
            console.log(error)
        }
    }

    function islogout(){
        setToken('')
        setName('')
        setAuthentic(false)
        return true
    }

    return(
        <AuthContext.Provider value={ {username,isAuthentic,authToken,islogin,islogout} }>
            {children}
        </AuthContext.Provider>
    );
}

export default Auth