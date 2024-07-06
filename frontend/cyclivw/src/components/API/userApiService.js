import { apiContext } from "./apiContex"

export const AddNewUser=(USER)=>apiContext.post(`/auth/register`,USER)

export const AuthenticateUser=(INFO)=>apiContext.post(`/auth/login`,INFO)

export const RemoveTask=(id)=>apiContext.delete(`tasks/remove/${id}`)
