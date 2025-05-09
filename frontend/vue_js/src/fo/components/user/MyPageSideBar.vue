<template>
  <div>
    <!-- Company Sidebar -->
    <aside v-if="authStore.isCompany" class="company sidebar">
      <h5 class="font-weight-semi-bold">마이 페이지</h5>
      <ul class="nav nav-list flex-column mb-5">
        <li class="nav-item">
          <a class="nav-link" href="#">소속 공고 관리</a>
          <ul>
            <li class="nav-item">
              <a class="nav-link" href="#">소속 정보 수정</a>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="#">소속 인원 목록</a>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="#">지원자 현황</a>
            </li>
          </ul>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="#">프로젝트 관리</a>
          <ul>
            <li class="nav-item">
              <a class="nav-link active" href="#">프로젝트 공고 목록</a>
            </li>
            <li class="nav-item"><a class="nav-link" href="#">지원 내역</a></li>
            <li class="nav-item">
              <a class="nav-link" href="#">스크랩 내역</a>
            </li>
          </ul>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="#">회원 정보 관리</a>
          <ul>
            <li class="nav-item">
              <a class="nav-link" href="#">회원 정보 수정</a>
            </li>
            <li class="nav-item"><a class="nav-link" href="#">회원 탈퇴</a></li>
          </ul>
        </li>
      </ul>
    </aside>

    <!-- Personal Sidebar -->
    <!-- <aside v-else-if="authStore.isPersonal" class="personal sidebar"> -->
    <aside v-else>
      <h5 class="font-weight-semi-bold">마이 페이지</h5>
      <ul class="nav nav-list flex-column mb-5">
        <li class="nav-item">
          <a class="nav-link" href="#">이력서 관리</a>
          <ul>
            <li class="nav-item">
              <a class="nav-link active" href="#">이력서 등록</a>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="#">이력서 목록</a>
            </li>
          </ul>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="#">소속 관리</a>
          <ul>
            <li class="nav-item"><a class="nav-link" href="#">지원 내역</a></li>
            <li class="nav-item">
              <a class="nav-link" href="#">스크랩 내역</a>
            </li>
          </ul>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="#">프로젝트 관리</a>
          <ul>
            <li class="nav-item"><a class="nav-link" href="#">지원 내역</a></li>
            <li class="nav-item">
              <a class="nav-link" href="#">스크랩 내역</a>
            </li>
          </ul>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="#">회원 정보 관리</a>
          <ul>
            <li class="nav-item">
              <a class="nav-link" href="#">회원 정보 수정</a>
            </li>
            <li class="nav-item"><a class="nav-link" href="#">회원 탈퇴</a></li>
          </ul>
        </li>
      </ul>
    </aside>
  </div>
</template>

<script setup>
import { useAuthStore } from '@/fo/stores/authStore'

const authStore = useAuthStore()

//추후 로그인 정보 받게되면 수정 예정
</script>
