import { createRouter, createWebHistory } from 'vue-router'

import MainPage from '../views/MainPage.vue'
import TestPage from '../views/TestPage.vue'
import UserProjectSpecPage from '../views/project/UserProjectSpecPage.vue'
import CompanyProjectSpecPage from '../views/project/CompanyProjectSpecPage.vue'
import ProjectPostPage from '../views/project/ProjectPostPage.vue'
import BoardList from '../views/community/BoardList.vue'
import BoardDetail from '../views/community/BoardDetail.vue'
import QnaDetail from '../views/community/QnaDetail.vue'
import LoginPage from '../views/login&signup/LoginPage.vue'
import FindAccountPage from '../views/login&signup/FindAccountPage.vue'
import FindIdResultPage from '../views/login&signup/FindIdResultPage.vue'
import ResetPasswordPage from '../views/login&signup/ResetPasswordPage.vue'
import SignUpPage from '../views/login&signup/SignUpPage.vue'
import AffiliationList from '../views/company/AffiliationList.vue'
import QnaList from '../views/community/QnaList.vue'
import MyPageLayout from '../views/mypage/MyPageLayout.vue'
import InformationEditPage from '../views/mypage/common/InformationEditPage.vue'
import AffiliationEditPage from '../views/mypage/company/AffiliationEditPage.vue'
import WithdrawPage from '../views/mypage/common/WithdrawPage.vue'
import AffiliatedJobApplicationsPage from '../views/mypage/personal/AffiliatedJobApplicationsPage.vue'
import AffiliatedScrapPage from '../views/mypage/personal/AffiliatedScrapPage.vue'
import ResumeListPage from '../views/mypage/personal/ResumeListPage.vue'
import AffiliatedMembersPage from '../views/mypage/company/AffiliatedMembersPage.vue'

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
    path: '/signUp',
    component: SignUpPage,
    name: 'SignUp',
  },

  {
    path: '/myPage',
    component: MyPageLayout,
    children: [
      // 기본 화면 설정
      {
        path: '', // 여기! 기본 자식 경로
        name: 'MyPageDefault',
        component: InformationEditPage,
      },
      //common
      {
        path: 'informationEdit',
        name: 'InformationEdit',
        component: InformationEditPage,
      },
      {
        path: 'withdraw',
        name: 'Withdraw',
        component: WithdrawPage,
      },

      //personal
      {
        path: 'affiliatedJobApplications',
        name: 'AffiliatedJobApplications',
        component: AffiliatedJobApplicationsPage,
      },
      {
        path: 'affiliatedScrap',
        name: 'AffiliatedScrap',
        component: AffiliatedScrapPage,
      },
      {
        path: 'resumeList',
        name: 'ResumeList',
        component: ResumeListPage,
      },

      //company
      {
        path: 'affiliationEdit',
        name: 'AffiliationEdit',
        component: AffiliationEditPage,
      },
      {
        path: 'affiliatedMembers',
        name: 'AffiliatedMembers',
        component: AffiliatedMembersPage,
      },
    ],
  },
]
const router = createRouter({
  history: createWebHistory(),
  routes,
})

export default router
