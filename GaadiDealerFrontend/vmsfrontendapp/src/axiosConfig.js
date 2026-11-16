import axios from "axios";

const instance=axios.create(
    {
        baseURL:import.meta.env.VITE_API_BASE_URL||"http://localhost:8080",
        headers:{
            "Content-Type":"application/json"
        },
        withCredentials:true,
    }
);
//M2:For every request attaching the token as bearer token
// instance.interceptors.request.use((config)=>{
//     const savedToken=localStorage.getItem('token')
//     if(savedToken)
//         config.headers.Authorization=`Bearer ${savedToken}`

//     return config
// })
export default instance;
