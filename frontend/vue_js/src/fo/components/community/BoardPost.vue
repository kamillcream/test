<template>
  <div>
    <div class="post-header">
      <h1 class="font-weight-semi-bold mb-0 position-relative">
        <div v-if="isQna" class="d-flex flex-wrap gap-2 mb-2">
          <span
            v-if="boardInfo.board_adopt_status_cd == 0"
            class="badge bg-danger badge-xs"
            style="font-size: large"
            >미해결</span
          >
          <span
            v-if="boardInfo.board_adopt_status_cd == 1"
            class="badge bg-secondary badge-xs"
            style="font-size: large"
            >자체해결</span
          >
          <span
            v-if="boardInfo.board_adopt_status_cd == 2"
            class="badge bg-warning badge-xs"
            style="font-size: large"
            >진행중</span
          >
          <span
            v-if="boardInfo.board_adopt_status_cd == 3"
            class="badge bg-success badge-xs"
            style="font-size: large"
            >채택완료</span
          >
        </div>
        <!-- 제목 + 오른쪽 아이콘들 -->
        <div class="d-flex justify-content-between align-items-start">
          <a href="blog-post.html">{{ boardInfo.board_ttl }}</a>
          <!-- 아이콘 버튼 -->
          <span class="post-icons d-flex">
            <!-- 조회수 버튼 추가 -->
            <a
              href="#"
              class="btn btn-light btn-rounded text-grey d-flex align-items-center me-2"
              style="font-size: 1rem"
            >
              <i class="fa-solid fa-eye" style="font-size: 1.2rem"></i>
              <span class="me-2 ms-2 text-grey">조회수</span>
              <span>{{ boardInfo.view_cnt }}</span>
            </a>
            <!-- 추천 버튼 -->
            <a
              href="#"
              class="btn btn-light btn-rounded text-grey d-flex align-items-center me-2"
              style="font-size: 1rem"
            >
              <i class="fa-regular fa-thumbs-up" style="font-size: 1.2rem"></i>
              <span class="me-2 ms-2 text-grey">추천</span>
              <span>{{ boardInfo.recommend_cnt }}</span>
            </a>
            <!-- 신고 버튼 -->
            <!-- [추가] 본인 인증 로직 -->
            <a
              v-if="boardInfo.user_sq != 1"
              href="#"
              class="btn btn-light btn-rounded text-grey d-flex align-items-center me-2"
              style="font-size: 1rem"
            >
              <i class="fa-solid fa-land-mine-on" style="font-size: 1.2rem"></i>
              <span class="me-2 ms-2 text-grey">신고</span>
            </a>
          </span>
        </div>
      </h1>
      <div class="post-meta">
        <span
          ><i class="far fa-user"></i> By
          <a href="#">{{ boardInfo.user_nm }}</a>
        </span>
        <span
          ><i class="far fa-calendar-alt"></i>
          <a href="#">{{ boardInfo.created_at }}</a></span
        >
      </div>
    </div>
    <!-- 게시글 본문 -->
    <p class="post-description mt-5 mb-5 text-5">
      {{ boardInfo.board_description_edt }}
    </p>
    <!-- 첨부파일 섹션 -->
    <div
      v-if="boardInfo.attachments.length > 0"
      class="post-attachments mt-3 mb-4"
    >
      <h5 class="font-weight-bold text-grey">첨부파일</h5>
      <ul class="list-unstyled">
        <li v-for="attachment in boardInfo.attachments" :key="attachment">
          <a :href="`files/${attachment}`" download>{{ attachment }}</a>
        </li>
      </ul>
    </div>
    <!-- "태그" 제목 추가 -->
    <div v-if="boardInfo.normal_tags.length > 0" class="post-tags mt-4">
      <h5 class="font-weight-bold text-grey">태그</h5>
      <!-- 태그 제목 추가 -->

      <a
        v-for="skill_tag in boardInfo.skill_tags"
        :key="skill_tag"
        href="#"
        class="btn btn-rounded btn-primary"
        >{{ skill_tag }}</a
      >
      <a
        v-for="normal_tag in boardInfo.normal_tags"
        :key="normal_tag"
        href="#"
        class="btn btn-rounded btn-light"
        >{{ normal_tag }}</a
      >
    </div>
    <!-- [추가] 본인 인증 로직 -->
    <div v-if="boardInfo.user_sq == 1" class="post-admin mt-4 text-end">
      <a v-if="isQna" href="#" class="btn btn-primary">답변 작성</a>
      <a v-if="isQna" href="#" class="btn btn-primary">자체 해결</a>
      <a href="#" class="btn btn-primary">수정</a>
      <a href="#" class="btn btn-primary">삭제</a>
    </div>
  </div>
</template>
<script setup>
import { computed, defineProps } from 'vue'

const props = defineProps({ boardInfo: Array, isQna: Boolean })

const boardInfo = computed(() => props.boardInfo)
const isQna = computed(() => props.isQna)
</script>
<style></style>
