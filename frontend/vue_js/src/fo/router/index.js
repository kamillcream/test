import { createRouter, createWebHistory } from 'vue-router'

import MainPage from '../views/MainPage.vue'
import TestPage from '../views/TestPage.vue'
import UserProjectSpecPage from '../views/project/UserProjectSpecPage.vue'
import CompanyProjectSpecPage from '../views/project/CompanyProjectSpecPage.vue'
import ProjectPostPage from '../views/project/ProjectPostPage.vue'
import BoardList from '../views/community/BoardList.vue'
import BoardDetail from '../views/community/BoardDetail.vue'
import QnaDetail from '../views/community/QnaDetail.vue'
import LoginPage from '../views/user/LoginPage.vue'
import FindAccountPage from '../views/user/FindAccountPage.vue'
import FindIdResultPage from '../views/user/FindIdResultPage.vue'
import ResetPasswordPage from '../views/user/ResetPasswordPage.vue'
import PersonalRegisterPage from '../views/user/PersonalRegisterPage.vue'
import CompanyRegisterPage from '../views/user/CompanyRegisterPage.vue'
import AffiliationList from '../views/company/AffiliationList.vue'
import QnaList from '../views/community/QnaList.vue'
import BoardResister from '../views/community/BoardResister.vue'
import QnaResister from '../views/community/QnaResister.vue'

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
    path: '/project/spec/user',
    component: UserProjectSpecPage,
    name: 'UserProjectSpec',
  },

  {
    path: '/project/spec/company',
    component: CompanyProjectSpecPage,
    name: 'CompanyProjectSpec',
  },

  {
    path: '/project',
    component: ProjectPostPage,
    name: 'ProjectPost',
  },

  {
    path: '/board',
    component: BoardList,
    name: 'BoardList',
  },

  {
    path: '/board/register',
    component: BoardResister,
    name: 'BoardResister',
  },

  {
    path: '/board/:board_sq',
    component: BoardDetail,
    name: 'BoardDetail',
    props: true,
  },

  {
    path: '/qna',
    component: QnaList,
    name: 'QnaList',
  },

  {
    path: '/qna/register',
    component: QnaResister,
    name: 'QnaResister',
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
    path: '/affiliation',
    component: AffiliationList,
    name: 'affiliationList',
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
