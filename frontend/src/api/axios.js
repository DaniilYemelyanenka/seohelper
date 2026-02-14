import axios from 'axios';
import  {isGlobalLoading} from '../store/loading';

const api = axios.create({
    baseURL: "http://localhost:8080/api/v1"
});

api.interceptors.request.use(
    config=> {
        isGlobalLoading.value = true
        return config
    },
    error => {
        isGlobalLoading.value = false
        return Promise.reject(error)
    }
);

api.interceptors.response.use(
  response => {
    isGlobalLoading.value = false
    return response
  },
  error => {
    isGlobalLoading.value = false
    return Promise.reject(error)
  }
)

export default api;