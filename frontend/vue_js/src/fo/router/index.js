import { createRouter, createWebHistory } from 'vue-router'

import MainPage from '../views/MainPage.vue'
import TestPage from '../views/TestPage.vue'
import ProjectPostPage from '../views/project/ProjectPostPage.vue'
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
    path: '/project',
    component: ProjectPostPage,
    name: 'ProjectPost',
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes,
})

export default router
