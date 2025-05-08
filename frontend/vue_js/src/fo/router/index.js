import { createRouter, createWebHistory } from 'vue-router'

import MainPage from '../views/MainPage.vue'
import TestPage from '../views/TestPage.vue'
import LoginPage from '../components/user/LoginPage.vue'
import FindAccountPage from '../components/user/FindAccountPage.vue'
import FindIdResultPage from '../components/user/FindIdResultPage.vue'
import ResetPasswordPage from '../components/user/ResetPasswordPage.vue'

const routes = [
  {
    path: '/',
    component: MainPage,
    name: 'Main',
  },

  {
    path: '/test',
    component: TestPage,
    name: 'TestConfirm',
  },

  {
    path: '/login',
    component: LoginPage,
    name: 'Login',
  },

  {
    path: '/findAccount',
    component: FindAccountPage,
    name: 'FindAccount',
  },

  {
    path: '/findIdResult',
    component: FindIdResultPage,
    name: 'FindIdResult',
  },

  {
    path: '/resetPassword',
    component: ResetPasswordPage,
    name: 'ResetPassword',
  },
]

const router = createRouter({
  history: createWebHistory(),
  routes,
})

export default router
