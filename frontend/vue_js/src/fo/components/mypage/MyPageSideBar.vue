<template>
  <div>
    <!-- Company Sidebar -->
    <aside v-if="authStore.isCompany" class="company sidebar">
      <h5 class="font-weight-semi-bold">마이 페이지</h5>
      <ul class="nav nav-list flex-column mb-5">
        <li class="nav-item">
          <router-link class="nav-link" to="#">소속 공고 관리</router-link>
          <ul>
            <li class="nav-item">
              <router-link class="nav-link" to="/affiliation/update"
                >소속 정보 수정</router-link
              >
            </li>
            <li class="nav-item">
              <router-link class="nav-link" to="/affiliation/members"
                >소속 인원 목록</router-link
              >
            </li>
            <li class="nav-item">
              <router-link class="nav-link" to="/affiliation/applicants"
                >지원자 현황</router-link
              >
            </li>
          </ul>
        </li>
        <li class="nav-item">
          <router-link class="nav-link" to="#">프로젝트 관리</router-link>
          <ul>
            <li class="nav-item">
              <router-link class="nav-link active" to="/project/list"
                >프로젝트 공고 목록</router-link
              >
            </li>
            <li class="nav-item">
              <router-link class="nav-link" to="/project/applications"
                >지원 내역</router-link
              >
            </li>
            <li class="nav-item">
              <router-link class="nav-link" to="/project/scrap"
                >스크랩 내역</router-link
              >
            </li>
          </ul>
        </li>
        <li class="nav-item">
          <router-link class="nav-link" to="#">회원 정보 관리</router-link>
          <ul>
            <li class="nav-item">
              <router-link class="nav-link" to="/member/update"
                >회원 정보 수정</router-link
              >
            </li>
            <li class="nav-item">
              <router-link class="nav-link" to="/member/withdrawal"
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
          <router-link class="nav-link" to="#">이력서 관리</router-link>
          <ul>
            <li class="nav-item">
              <router-link class="nav-link active" to="/resume/register"
                >이력서 등록</router-link
              >
            </li>
            <li class="nav-item">
              <router-link class="nav-link" to="/resume/list"
                >이력서 목록</router-link
              >
            </li>
          </ul>
        </li>
        <li class="nav-item">
          <router-link class="nav-link" to="#">소속 관리</router-link>
          <ul>
            <li class="nav-item">
              <router-link class="nav-link" to="/affiliation/applications"
                >지원 내역</router-link
              >
            </li>
            <li class="nav-item">
              <router-link class="nav-link" to="/affiliation/scrap"
                >스크랩 내역</router-link
              >
            </li>
          </ul>
        </li>
        <li class="nav-item">
          <router-link class="nav-link" to="#">프로젝트 관리</router-link>
          <ul>
            <li class="nav-item">
              <router-link class="nav-link" to="/project/applications"
                >지원 내역</router-link
              >
            </li>
            <li class="nav-item">
              <router-link class="nav-link" to="/project/scrap"
                >스크랩 내역</router-link
              >
            </li>
          </ul>
        </li>
        <li class="nav-item">
          <router-link class="nav-link" to="#">회원 정보 관리</router-link>
          <ul>
            <li class="nav-item">
              <router-link class="nav-link" to="/member/update"
                >회원 정보 수정</router-link
              >
            </li>
            <li class="nav-item">
              <router-link class="nav-link" to="/member/withdrawal"
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
import { useAuthStore } from '@/fo/stores/authStore'

const authStore = useAuthStore()

//추후 로그인 기능 완성 이후 수정필요
</script>
