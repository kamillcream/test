<template>
  <div>
    <!-- Company Sidebar -->
    <aside v-if="userStore.userType === 'COMPANY'" class="company sidebar">
      <h5 class="font-weight-semi-bold">마이 페이지</h5>
      <ul class="nav nav-list flex-column mb-5">
        <li class="nav-item">
          <span class="nav-link">소속 공고 관리</span>
          <ul>
            <li class="nav-item">
              <router-link class="nav-link" to="/mypage/affiliationEdit"
                >소속 정보 수정</router-link
              >
            </li>
            <li class="nav-item">
              <router-link class="nav-link" to="/mypage/affiliatedMembers"
                >소속 인원 목록</router-link
              >
            </li>
            <li class="nav-item">
              <router-link
                class="nav-link"
                to="/mypage/affiliationApplicantList"
                >지원자 현황</router-link
              >
            </li>
          </ul>
        </li>
        <li class="nav-item">
          <span class="nav-link">프로젝트 관리</span>
          <ul>
            <li class="nav-item">
              <router-link class="nav-link" to="/mypage/affiliationProjectList"
                >프로젝트 공고 목록</router-link
              >
            </li>
            <li class="nav-item">
              <router-link class="nav-link" to="/mypage/appliedProjects"
                >지원 내역</router-link
              >
            </li>
            <li class="nav-item">
              <router-link class="nav-link" to="/mypage/projectScrap"
                >스크랩 내역</router-link
              >
            </li>
          </ul>
        </li>
        <li class="nav-item">
          <span class="nav-link">회원 정보 관리</span>
          <ul>
            <li class="nav-item">
              <router-link
                class="nav-link"
                to="/mypage/informationEdit"
                :class="{
                  'router-link-exact-active':
                    $route.path === '/mypage' ||
                    $route.path === '/mypage/informationEdit',
                }"
              >
                회원 정보 수정
              </router-link>
            </li>
            <li class="nav-item">
              <router-link class="nav-link" to="/mypage/withdraw"
                >회원 탈퇴</router-link
              >
            </li>
          </ul>
        </li>
      </ul>
    </aside>

    <!-- Personal Sidebar -->
    <aside v-else>
      <h5 class="font-weight-semi-bold">마이 페이지</h5>
      <ul class="nav nav-list flex-column mb-5">
        <li class="nav-item">
          <span class="nav-link">이력서 관리</span>
          <ul>
            <li class="nav-item">
              <router-link class="nav-link" to="/mypage/resumeForm"
                >이력서 등록</router-link
              >
            </li>
            <li class="nav-item">
              <router-link class="nav-link" to="/mypage/resumeList"
                >이력서 목록</router-link
              >
            </li>
          </ul>
        </li>
        <li class="nav-item">
          <span class="nav-link">소속 관리</span>
          <ul>
            <li class="nav-item">
              <router-link
                class="nav-link"
                to="/mypage/affiliatedJobApplications"
                >지원 내역</router-link
              >
            </li>
            <li class="nav-item">
              <router-link class="nav-link" to="/mypage/affiliatedScrap"
                >스크랩 내역</router-link
              >
            </li>
          </ul>
        </li>
        <li class="nav-item">
          <span class="nav-link">프로젝트 관리</span>
          <ul>
            <li class="nav-item">
              <router-link class="nav-link" to="/mypage/appliedProjects"
                >지원 내역</router-link
              >
            </li>
            <li class="nav-item">
              <router-link class="nav-link" to="/mypage/projectScrap"
                >스크랩 내역</router-link
              >
            </li>
          </ul>
        </li>
        <li class="nav-item">
          <span class="nav-link">회원 정보 관리</span>
          <ul>
            <li class="nav-item">
              <router-link
                class="nav-link"
                to="/mypage/informationEdit"
                :class="{
                  'router-link-exact-active':
                    $route.path === '/mypage' ||
                    $route.path === '/mypage/informationEdit',
                }"
              >
                회원 정보 수정
              </router-link>
            </li>
            <li class="nav-item">
              <router-link class="nav-link" to="/mypage/withdraw"
                >회원 탈퇴</router-link
              >
            </li>
          </ul>
        </li>
      </ul>
    </aside>
  </div>
</template>

<script setup>
import { useUserStore } from '@/fo/stores/userStore'

const userStore = useUserStore()
</script>

<style scoped>
.router-link-exact-active {
  font-weight: bold;
}

span.nav-link {
  color: black;
  font-weight: bold;
}
</style>
