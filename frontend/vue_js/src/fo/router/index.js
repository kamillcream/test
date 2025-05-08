import { createRouter, createWebHistory } from 'vue-router'

import MainPage from '../views/MainPage.vue'
import TestPage from '../views/TestPage.vue'
import UserProjectSpecPage from '../views/project/UserProjectSpecPage.vue'
import CompanyProjectSpecPage from '../views/project/CompanyProjectSpecPage.vue'

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

]

const router = createRouter({
  history: createWebHistory(),
  routes,
})

export default router
