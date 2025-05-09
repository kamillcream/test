import { createRouter, createWebHistory } from 'vue-router'

import MainPage from '../views/MainPage.vue'
import TestPage from '../views/TestPage.vue'
import BoardList from '../views/community/BoardList.vue'
import BoardDetail from '../views/community/BoardDetail.vue'
import QnaDetail from '../views/community/QnaDetail.vue'
import LoginPage from '../views/user/LoginPage.vue'
import FindAccountPage from '../views/user/FindAccountPage.vue'
import FindIdResultPage from '../views/user/FindIdResultPage.vue'
import ResetPasswordPage from '../views/user/ResetPasswordPage.vue'
import PersonalRegisterPage from '../views/user/PersonalRegisterPage.vue'
import CompanyRegisterPage from '../views/user/CompanyRegisterPage.vue'

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
    path: '/board',
    component: BoardList,
    name: 'BoardList',
  },

  {
    path: '/board/:board_sq',
    component: BoardDetail,
    name: 'BoardDetail',
    props: true,
  },

  {
    path: '/qna/:board_sq',
    component: QnaDetail,
    name: 'QnaDetail',
    props: true,
  },
  {
    path: '/board',
    component: BoardList,
    name: 'BoardList',
  },

  {
    path: '/board/:board_sq',
    component: BoardDetail,
    name: 'BoardDetail',
    props: true,
  },

  {
    path: '/qna/:board_sq',
    component: QnaDetail,
    name: 'QnaDetail',
    props: true,
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

  {
    path: '/personalRegister',
    component: PersonalRegisterPage,
    name: 'PersonalRegister',
  },

  {
    path: '/companyRegister',
    component: CompanyRegisterPage,
    name: 'CompanyRegiste',
  },
]

const router = createRouter({
  history: createWebHistory(),
  routes,
})

export default router
