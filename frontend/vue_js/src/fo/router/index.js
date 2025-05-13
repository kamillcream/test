import { createRouter, createWebHistory } from 'vue-router'

import MainPage from '../views/MainPage.vue'
import TestPage from '../views/TestPage.vue'
import UserProjectSpecPage from '../views/project/UserProjectSpecPage.vue'
import CompanyProjectSpecPage from '../views/project/CompanyProjectSpecPage.vue'
import ProjectPostPage from '../views/project/ProjectPostPage.vue'
import BoardListPage from '../views/community/BoardListPage.vue'
import BoardDetailPage from '../views/community/BoardDetailPage.vue'
import QnaDetailPage from '../views/community/QnaDetailPage.vue'
import LoginPage from '../views/user/LoginPage.vue'
import FindAccountPage from '../views/user/FindAccountPage.vue'
import FindIdResultPage from '../views/user/FindIdResultPage.vue'
import ResetPasswordPage from '../views/user/ResetPasswordPage.vue'
import PersonalRegisterPage from '../views/user/PersonalRegisterPage.vue'
import CompanyRegisterPage from '../views/user/CompanyRegisterPage.vue'
import AffiliationListPage from '../views/company/AffiliationListPage.vue'
import QnaListPage from '../views/community/QnaListPage.vue'
import BoardResisterPage from '../views/community/BoardResisterPage.vue'
import QnaResisterPage from '../views/community/QnaResisterPage.vue'

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
    component: BoardListPage,
    name: 'BoardListPage',
  },

  {
    path: '/board/register',
    component: BoardResisterPage,
    name: 'BoardResisterPage',
  },

  {
    path: '/board/:board_sq',
    component: BoardDetailPage,
    name: 'BoardDetailPage',
    props: true,
  },

  {
    path: '/qna',
    component: QnaListPage,
    name: 'QnaListPage',
  },

  {
    path: '/qna/register',
    component: QnaResisterPage,
    name: 'QnaResisterPage',
  },

  {
    path: '/qna/:board_sq',
    component: QnaDetailPage,
    name: 'QnaDetailPage',
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
    component: AffiliationListPage,
    name: 'affiliationListPage',
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
