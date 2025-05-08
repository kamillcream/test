import { createRouter, createWebHistory } from 'vue-router'

import MainPage from '../views/MainPage.vue'
import TestPage from '../views/TestPage.vue'

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
]

const router = createRouter({
  history: createWebHistory(),
  routes,
})

export default router
