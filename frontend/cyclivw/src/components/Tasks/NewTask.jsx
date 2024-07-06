import { useState } from "react"
import { addNewTask } from "../API/taskApiService"
import { useAuth } from "../auth"
import { useNavigate } from "react-router-dom"

const AddTasks=()=>{
    const [description,setDescription]=useState('')
    const [dueDate,setDueDate]=useState(new Date())
    const apiContext=useAuth()
    const navigateTo=useNavigate()
    const handleDateChange = (e) => {
        const selectedDate = e.target.value;
        const today = new Date().toISOString().split('T')[0];
        if (selectedDate >= today) {
          setDueDate(selectedDate);
        } else {
          alert('Due date cannot be in the past');
        }
      };

    function handleAdd(){
        const DATA={
            "name":apiContext.username,
            "description":description,
            "due":dueDate,
            "status":"pending"
        }
        addNewTask(DATA).then(
            (response)=>{navigateTo(`/home/${apiContext.username}`)}
        ).catch(
            (error)=>{console.log(error)}
        )

    }  
    return(
        <div>
            <h2>Add Task</h2>
            <hr />
            <div className="input-group">
                <label>description</label>
                <input type="text" value={description} onChange={(e) => setDescription(e.target.value)} />
            </div>
            <div className="input-group">
                <label >Due Date</label>
                <input type="date" 
                value={dueDate} 
                min={new Date().toISOString().split('T')[0]} 
                onChange={handleDateChange} />
            </div>
            <button onClick={handleAdd}>Add</button>
        </div>
    )
}
export default AddTasks