import { apiContext } from "./apiContex";

export const getmyTask=(user)=>apiContext.get(`/tasks/${user}`)
export const addNewTask=(TASK)=>apiContext.post(`/tasks/addTask`,TASK)
export const deleteTask=(id)=>apiContext.delete(`tasks/remove/${id}`)
export const UpdateTask=(id,status)=>apiContext.put(`tasks/update/${id}/${status}`)