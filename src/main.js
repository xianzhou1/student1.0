import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import axios from 'axios'
import './assets/iconf/iconfont.css'

const app = createApp(App)
app.provide("$axios", axios)
app.use(router)

axios.defaults.baseURL = 'http://localhost:8080'
axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded'
axios.defaults.headers.post['Access-Control-Allow-Origin'] = '*'
app.config.globalProperties.$axios = axios
app.mount("#app")