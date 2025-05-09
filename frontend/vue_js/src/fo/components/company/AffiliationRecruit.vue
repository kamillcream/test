<template>
  <div>
    <div class="modal-header">
      <h4
        class="modal-title"
        id="schoolSearchModalLabel"
        style="font-weight: bold"
      >
        소속 신청하기
      </h4>
      <button
        type="button"
        class="btn-close"
        data-bs-dismiss="modal"
        @click="closeModal"
        aria-hidden="true"
      ></button>
    </div>
    <div class="modal-body" style="background-color: #f5f5f5">
      <!-- 회색 배경 -->

      <!-- 회사명 -->
      <div class="mb-3">
        <label
          for="companyName"
          class="form-label text-primary"
          style="font-weight: bold"
          >회사명</label
        >
        <div class="text-dark" id="companyName">
          {{ afltnInfo.company_nm }}
        </div>
      </div>

      <!-- 대표자명 -->
      <div class="mb-3">
        <label
          for="ceoName"
          class="form-label text-primary"
          style="font-weight: bold"
          >대표자명</label
        >
        <div class="text-dark" id="ceoName">{{ afltnInfo.user_nm }}</div>
      </div>

      <!-- 개업년수 -->
      <div class="mb-3">
        <label
          for="yearsInBusiness"
          class="form-label text-primary"
          style="font-weight: bold"
          >개업년수</label
        >
        <!-- [수정] 오픈일자부터 개업일수 계산 -->
        <div class="text-dark" id="yearsInBusiness">
          {{ afltnInfo.company_open_dt }}
        </div>
      </div>

      <!-- 회사위치 -->
      <div class="mb-3">
        <label
          for="companyLocation"
          class="form-label text-primary"
          style="font-weight: bold"
          >회사위치</label
        >
        <div class="text-dark" id="companyLocation">
          {{ afltnInfo.company_adress }}
        </div>
      </div>

      <!-- 간단한 설명 -->
      <div class="mb-3">
        <label
          for="companyDescription"
          class="form-label text-primary"
          style="font-weight: bold"
          >회사 설명</label
        >
        <div class="text-dark" id="companyDescription">
          {{ afltnInfo.company_greeting_txt }}
        </div>
      </div>

      <!-- 관련 태그 -->
      <div class="mb-3">
        <label
          for="companyDescription"
          class="form-label text-primary"
          style="font-weight: bold"
          >관련 태그</label
        >
        <div class="d-flex flex-wrap gap-2 mb-3">
          <span
            v-for="tag in afltnInfo.tags"
            :key="tag"
            class="btn btn-rounded btn-3d btn-light"
            >{{ tag }}</span
          >
        </div>
      </div>

      <!-- 이력서 선택 -->
      <div class="mb-3">
        <label
          for="resume"
          class="form-label text-primary"
          style="font-weight: bold"
          >소속 신청할 이력서</label
        >

        <!-- [추가] 선택한 이력서로 이력서 이름 변경 -->
        <div class="text-dark" id="resume">
          선택한 이력서:
          <a href="#" class="text-primary">이력서를 선택하세요.</a>
        </div>
      </div>

      <!-- 간단한 자기소개 -->
      <div class="mb-3">
        <label
          for="selfIntroduction"
          class="form-label text-primary"
          style="font-weight: bold"
          >간단한 자기소개</label
        >
        <textarea
          class="form-control border-0 bg-white"
          id="selfIntroduction"
          rows="4"
          placeholder="자기소개를 입력해주세요."
        ></textarea>
      </div>
    </div>
    <div class="modal-footer">
      <button type="button" class="btn btn-primary" @click="clickRecruit">
        소속 신청하기
      </button>
      <button
        type="button"
        class="btn btn-light"
        data-bs-dismiss="modal"
        @click="closeModal"
      >
        닫기
      </button>
    </div>
  </div>
</template>
<script setup>
import { useModalStore } from '@/fo/stores/modalStore'
import { computed, defineProps } from 'vue'

const props = defineProps({ afltnInfo: Array })
const afltnInfo = computed(() => props.afltnInfo)

const modalStore = useModalStore()

const closeModal = () => {
  modalStore.closeModal()
}

// 소속 신청하기 버튼 클릭 이벤트
const clickRecruit = () => {}
</script>
<style></style>
