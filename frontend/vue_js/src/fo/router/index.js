import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '../stores/userStore'

import MainPage from '../views/MainPage.vue'
import TestPage from '../views/TestPage.vue'
import UserProjectSpecPage from '../views/project/UserProjectSpecPage.vue'
import CompanyProjectSpecPage from '../views/project/CompanyProjectSpecPage.vue'
import ProjectPostPage from '../views/mypage/common/ProjectPostPage.vue'

import BoardListPage from '../views/community/BoardListPage.vue'
import BoardDetailPage from '../views/community/BoardDetailPage.vue'
import QnaDetailPage from '../views/community/QnaDetailPage.vue'
import BoardResisterPage from '../views/community/BoardResisterPage.vue'
import QnaResisterPage from '../views/community/QnaResisterPage.vue'

import LoginPage from '../views/login&signup/LoginPage.vue'
import FindAccountPage from '../views/login&signup/FindAccountPage.vue'
import FindIdResultPage from '../views/login&signup/FindIdResultPage.vue'
import ResetPasswordPage from '../views/login&signup/ResetPasswordPage.vue'
import SignUpPage from '../views/login&signup/SignUpPage.vue'

import AffiliationListPage from '../views/company/AffiliationListPage.vue'
import QnaListPage from '../views/community/QnaListPage.vue'
import MyPageLayout from '../views/mypage/MyPageLayout.vue'
import InformationEditPage from '../views/mypage/common/InformationEditPage.vue'
import ResumeListPage from '../views/mypage/personal/ResumeListPage.vue'
import AffiliatedMembersPage from '../views/mypage/company/AffiliatedMembersPage.vue'
import AffiliationApplicantListPage from '../views/mypage/company/AffiliationApplicantListPage.vue'
import ProjectListPage from '../views/mypage/common/ProjectListPage.vue'
import ResumeFormPage from '../views/mypage/personal/ResumeFormPage.vue'
import WithdrawPage from '../views/mypage/common/WithdrawPage.vue'
import AffiliatedJobApplicationsPage from '../views/mypage/personal/AffiliatedJobApplicationsPage.vue'
import AffiliatedScrapPage from '../views/mypage/personal/AffiliatedScrapPage.vue'
import AffiliationEditPage from '../views/mypage/company/AffiliationEditPage.vue'

import AppliedProjectsPage from '../views/mypage/personal/AppliedProjectsPage.vue'
import ProjectScrapPage from '../views/mypage/personal/ProjectScrapPage.vue'

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
    path: '/project/spec/user/:project_sq',
    component: UserProjectSpecPage,
    name: 'UserProjectSpec',
  },

  {
    path: '/project/spec/company/:project_sq',
    component: CompanyProjectSpecPage,
    name: 'CompanyProjectSpec',
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
    name: 'AffiliationListPage',
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
      {
        path: 'resumeForm',
        name: 'ResumeForm',
        component: ResumeFormPage,
      },
      {
        path: 'appliedProjects',
        name: 'appliedProjects',
        component: AppliedProjectsPage,
      },
      {
        path: 'projectScrap',
        name: 'projectScrap',
        component: ProjectScrapPage,
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
      {
        path: 'affiliationApplicantList',
        name: 'AffiliationApplicantList',
        component: AffiliationApplicantListPage,
      },
      {
        path: 'projectListPage',
        name: 'ProjectListPage',
        component: ProjectListPage,
      },

      {
        path: 'projectPostPage',
        name: 'ProjectPostPage',
        component: ProjectPostPage,
      },
    ],
  },
]
const router = createRouter({
  history: createWebHistory(),
  routes,
})

router.beforeEach((to, from, next) => {
  const userStore = useUserStore()

  const publicPages = [
    'Login',
    'SignUp',
    'FindAccount',
    'FindIdResult',
    'ResetPassword',
  ]
  const authRequiredPages = [
    'MyPageDefault',
    'InformationEdit',
    'Withdraw' /* ... 로그인 필요 페이지들 */,
  ]

  // 로그인 중인데 로그인/회원가입 페이지 접근 시 메인으로 리다이렉트
  if (userStore.isLoggedIn && publicPages.includes(to.name)) {
    return next({ name: 'Main' })
  }

  // 로그인 안 된 상태에서 로그인 필요 페이지 접근 시 로그인 페이지로 리다이렉트
  if (!userStore.isLoggedIn && authRequiredPages.includes(to.name)) {
    return next({ name: 'Login' })
  }

  next()
})

export default router
