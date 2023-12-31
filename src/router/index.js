import { createRouter, createWebHashHistory } from 'vue-router'
import root from '../pages/rootComponents.vue'
import login from '../pages/loginComponent.vue'
import allShow from '../components/allShow'


const routes = [
  {
    path: '/',
    redirect: '/login'
  },
  {
    path: '/root/:userName/:id',
    name: 'root',
    component: root
  },
  {
    path: '/login',
    component: login
  },

]

const router = createRouter({
  history: createWebHashHistory(),
  routes,
  mode: 'histroy'
})

export default router
