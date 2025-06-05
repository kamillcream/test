<template>
  <div>
    <div class="modal-header">
      <h4 class="modal-title text-bold" id="schoolSearchModalLabel">
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
    <div class="modal-body bg-f5">
      <!-- 회색 배경 -->

      <!-- 회사명 -->
      <div class="mb-3">
        <label for="companyName" class="form-label text-primary text-bold"
          >회사명</label
        >
        <div class="text-dark" id="companyName">
          {{ afltnInfo.companyNm }}
        </div>
      </div>

      <!-- 대표자명 -->
      <div class="mb-3">
        <label for="ceoName" class="form-label text-primary text-bold"
          >대표자명</label
        >
        <div class="text-dark" id="ceoName">{{ afltnInfo.ceoNm }}</div>
      </div>

      <!-- 개업년수 -->
      <div class="mb-3">
        <label for="yearsInBusiness" class="form-label text-primary text-bold"
          >개업년수</label
        >
        <!-- [수정] 오픈일자부터 개업일수 계산 -->
        <div class="text-dark" id="yearsInBusiness">
          {{ afltnInfo.openYear }}년차
        </div>
      </div>

      <!-- 회사위치 -->
      <div class="mb-3">
        <label for="companyLocation" class="form-label text-primary text-bold"
          >회사위치</label
        >
        <div class="text-dark" id="companyLocation">
          {{ afltnInfo.address }}
        </div>
      </div>

      <!-- 간단한 설명 -->
      <div class="mb-3">
        <label
          for="companyDescription"
          class="form-label text-primary text-bold"
          >회사 설명</label
        >
        <div class="text-dark" id="companyDescription">
          {{ afltnInfo.greeting }}
        </div>
      </div>

      <!-- 관련 태그 -->
      <div class="mb-3">
        <label
          for="companyDescription"
          class="form-label text-primary text-bold"
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
        <label for="resume" class="form-label text-primary text-bold"
          >소속 신청할 이력서</label
        >

        <!-- [추가] 선택한 이력서로 이력서 이름 변경 -->
        <div class="text-dark" id="resume">
          선택한 이력서:
          <a href="#" class="text-primary" @click="openResumeModal"
            >이력서를 선택하세요.</a
          >
        </div>
      </div>

      <!-- 간단한 자기소개 -->
      <div class="mb-3">
        <label for="selfIntroduction" class="form-label text-primary text-bold"
          >간단한 자기소개</label
        >
        <textarea
          class="form-control border-0 bg-white"
          id="selfIntroduction"
          rows="4"
          placeholder="자기소개를 입력해주세요."
          v-model="affiliationStore.greeting"
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
import { api } from '@/axios'
import { useAffiliationStore } from '@/fo/stores/AffiliationStore'
import { useAlertStore } from '@/fo/stores/alertStore'
import { useModalStore } from '@/fo/stores/modalStore'
import { computed, defineProps } from 'vue'
import ResumeSelectModal from '../mypage/common/ResumeSelectModal.vue'
import CommonConfirmModal from '../common/CommonConfirmModal.vue'

const props = defineProps({ afltnInfo: Array })
const afltnInfo = computed(() => props.afltnInfo)
console.log(afltnInfo)

const modalStore = useModalStore()
const alertStore = useAlertStore()
const affiliationStore = useAffiliationStore()

const closeModal = () => {
  affiliationStore.resetGreeting()
  modalStore.closeModal()
}

// 소속 신청하기 버튼 클릭 이벤트
const clickRecruit = async () => {
  if (affiliationStore.viewerSq == null) {
    alertStore.show('로그인 후 이용해주세요.', 'danger')
  }
  modalStore.openModal(CommonConfirmModal, {
    title: '소속 신청',
    message: `${afltnInfo.value.companyNm} 소속 신청하시겠습니까?`,
    onConfirm: async () => {
      // [추가] 이력서 미선택시 리턴
      try {
        const res = await api.$post(`/affiliation/apply`, {
          companySq: afltnInfo.value.sq,
          resumeSq: 1,
          companyApplicationGreetingTxt: affiliationStore.greeting,
        })
        if (res.status == 'CREATED') {
          alertStore.show(res.message, 'success')
          closeModal()
        }
      } catch (error) {
        alertStore.show('소속 신청에 실패했습니다.', 'danger')
      }
    },
  })
}

// 이력서 선택
const openResumeModal = () => {
  modalStore.openModal(ResumeSelectModal)
}
</script>
<style>
.text-bold {
  font-weight: bold;
}
.bg-f5 {
  background: #f5f5f5;
}
</style>
