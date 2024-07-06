import { useEffect, useState } from "react";
import { getmyTask,deleteTask,UpdateTask } from "./API/taskApiService";
import { useAuth } from "./auth";
import { useNavigate } from "react-router-dom";

const Home = () => {
    const [userList, setUserList] = useState([])
    const [isEditing,SetEditing]=useState(0)
    const [updatedState,setUpdatedState]=useState('')
    const authContext = useAuth()
    const navigateTo=useNavigate()

    useEffect(() => {
        fetchData()
    }, [])

    function fetchData(){
        getmyTask(authContext.username).then(
            (response) => { setUserList(response.data) }
        ).catch(
            (error) => { console.log(error) }
        )
    }
    function handleDelete(id){
        deleteTask(id).then(
            ()=>{fetchData()}
        ).catch(
            (error)=>{console.log(error)}
        )
    }
    function handleUpdate(id){
        SetEditing(0)
        UpdateTask(id,updatedState).then(
            (response)=>{fetchData()}
        ).catch(
            (error)=>{console.log(error)}
        )
    }
    function navigateTask(){
        navigateTo(`/home/addTask/${authContext.username}`)
    }

    return (
        <div>
            <div>
                <h1>TODO App</h1>
                {userList.length > 0 ? (
                    <table>
                        <thead>
                            <tr>
                                <th>Description</th>
                                <th>Due</th>
                                <th>Status</th>
                                <th></th>
                            </tr>
                        </thead>
                        <tbody>
                            {userList.map((task,id) => (
                                <tr key={id}>
                                    <td>{task.description}</td>
                                    <td>{task.dueDate}</td>
                                    <td className="">{task.status}</td>
                                    {isEditing==task.id?
                                        <div>
                                            <input type="text" value={updatedState} onChange={(e)=>{setUpdatedState(e.target.value)}}/>
                                        </div>
                                    :""}
                                    <td> {isEditing==task.id?<button onClick={()=>handleUpdate(task.id)}>modify</button>:
                                    <button onClick={()=>{SetEditing(task.id)}}>Update</button>}</td>
                                    <td> <button onClick={()=>{handleDelete(task.id)}}>Delete</button></td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                ) : (
                    <p>No tasks are available, please add some.</p>
                )}
                <hr />
                <button onClick={navigateTask}>Add Task</button>
            </div>

        </div>
    )
}

export default Home;