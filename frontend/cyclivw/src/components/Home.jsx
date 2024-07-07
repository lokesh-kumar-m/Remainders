import { useEffect, useState } from "react";
import { deleteTask,UpdateTask,getSortedTask } from "./API/taskApiService";
import { useAuth } from "./auth";
import { useNavigate } from "react-router-dom";
import Button from '@mui/material/Button';
import ButtonGroup from '@mui/material/ButtonGroup';

const Home = () => {
    const [userList, setUserList] = useState([])
    const [isEditing,SetEditing]=useState(0)
    const [updatedState,setUpdatedState]=useState('')
    const authContext = useAuth()
    const navigateTo=useNavigate()

    useEffect(() => {
        fetchsortedData("All")
    }, [])

    function fetchsortedData(sorter){
        getSortedTask(authContext.username,sorter).then(
            (response)=>{setUserList(response.data)}
        ).catch(
            (error)=>console.log(error)
        )
    }
    function handleDelete(id){
        deleteTask(id).then(
            ()=>{fetchsortedData("All")}
        ).catch(
            (error)=>{console.log(error)}
        )
    }
    function handleUpdate(id){
        SetEditing(0)
        UpdateTask(id,updatedState).then(
            (response)=>{fetchsortedData("ALL")}
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
                                    <td> 
                                        
                                    <Button size="small" onClick={()=>{handleDelete(task.id)}} variant="outlined" color="error">Delete</Button>    
                                    </td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                ) : (
                    <p>No tasks are available, please add some.</p>
                )}
                <hr />
                <Button  onClick={navigateTask} variant="contained" color="success">
                Add Task
      </Button>
                <br />
                <br />
                <ButtonGroup variant="contained" aria-label="Basic button group">
                    <Button onClick={()=>fetchsortedData("completed")}>completed</Button>
                    <Button onClick={()=>fetchsortedData("pending")}>pending</Button>
                    <Button onClick={()=>fetchsortedData("All")}>all Tasks</Button>
                </ButtonGroup>
            </div>

        </div>
    )
}

export default Home;