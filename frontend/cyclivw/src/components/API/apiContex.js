import axios from "axios";

export const apiContext=axios.create(
    {
        baseURL:'http://localhost:8080'
    }
)