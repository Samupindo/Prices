import axios from "axios";
import type { AxiosInstance } from "axios";

const axiosInstance: AxiosInstance = axios.create({
  baseURL: "http://localhost:8080",
  timeout: 5000, 
  headers: {
    "Content-Type": "application/json",
    "Accept": "application/json",
  },
});

export default axiosInstance;