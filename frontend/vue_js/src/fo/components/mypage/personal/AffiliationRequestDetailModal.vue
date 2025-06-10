<template>
  <div class="modal-content">
    <div class="modal-header">
      <h4 class="modal-title" style="font-weight: bold">소속 신청 내역</h4>
      <button
        type="button"
        class="btn-close"
        @click="closeModal"
        aria-hidden="true"
      ></button>
    </div>
    <div class="modal-body" style="background-color: #f5f5f5">
      <!-- 회사명 -->
      <div class="mb-3">
        <label
          for="companyName"
          class="form-label text-primary"
          style="font-weight: bold"
          >회사명</label
        >
        <div class="text-dark" id="companyName">
          {{ companyInfo.companyNm }}
        </div>
      </div>

      <!-- 대표자명  -->
      <div class="mb-3">
        <label
          for="ceoName"
          class="form-label text-primary"
          style="font-weight: bold"
          >대표자명</label
        >
        <div class="text-dark" id="ceoName">{{ companyInfo.ceoNm }}</div>
      </div>

      <!-- 개업년수 -->
      <div class="mb-3">
        <label
          for="yearsInBusiness"
          class="form-label text-primary"
          style="font-weight: bold"
          >개업년수</label
        >
        <div class="text-dark" id="yearsInBusiness">
          {{ companyInfo.openYear }}년
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
          {{ companyInfo.address }}
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
          {{ companyInfo.greeting }}
        </div>
      </div>

      <!-- 관련 태그 -->
      <div class="mb-3">
        <label
          for="companyTags"
          class="form-label text-primary"
          style="font-weight: bold"
          >관련 태그</label
        >
        <div class="d-flex flex-wrap gap-2 mb-3">
          <span
            v-for="tag in companyInfo.tags"
            :key="tag"
            class="btn btn-rounded btn-3d btn-light"
          >
            {{ tag }}
          </span>
        </div>
      </div>

      <!-- 이력서 선택 -->
      <div class="mb-3">
        <label
          for="resume"
          class="form-label text-primary"
          style="font-weight: bold"
          >소속 신청한 이력서</label
        >
        <div class="text-dark" id="resume">
          선택한 이력서:
          <a
            href="#"
            class="text-primary"
            @click="openResumeModal(apply.resumeSq)"
            >{{ props.apply.resumeTtl }}</a
          >
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
        <div class="text-dark" id="introduce">
          {{ props.apply.greeting }}
        </div>
      </div>
    </div>
    <div class="modal-footer">
      <!-- <button type="button" class="btn btn-primary" @click="handleSubmit">
        신청 취소
      </button> -->
      <button type="button" class="btn btn-light" @click="closeModal">
        닫기
      </button>
    </div>
  </div>
</template>

<script setup>
import { ref, defineProps, onMounted } from 'vue'
import { useModalStore } from '@/fo/stores/modalStore'
import { api } from '@/axios'
import { useAlertStore } from '@/fo/stores/alertStore'
import ResumeDetailModal from '../common/ResumeDetailModal.vue'

const modalStore = useModalStore()
const alertStore = useAlertStore()

const props = defineProps({
  apply: {
    type: Object,
    required: true,
  },
})

const companyInfo = ref({
  name: '회사명',
  ceoName: '홍길동', // 실제 있으면 넣고, 없으면 더미
  yearsInBusiness: 10, // 실제 있으면 props에서 가져오기
  location: '서울시 강남구', // 실제 있으면 props에서 가져오기
  description:
    'EST Soft는 기술 혁신과 뛰어난 팀워크로 다양한 산업에 최적화된 솔루션을 제공합니다.',
  tags: ['서울', '신입', '학력무관', 'JAVA'], // 태그 있으면 넘기고, 없으면 기본
})

const getCompanyInfo = async () => {
  try {
    const res = await api.$get(`/mypage/applications/${props.apply.companySq}`)
    companyInfo.value = res.output
    console.log(res)
  } catch (error) {
    alertStore.show('소속 정보를 불러올 수 없습니다.', 'danger')
  }
}

// 이력서 모달창 열기
const openResumeModal = (sq) => {
  modalStore.openModal(ResumeDetailModal, {
    size: 'modal-lg',
    sq,
  })
}

// 모달 닫기
const closeModal = () => {
  modalStore.closeModal()
}

// 소속 신청 제출
// const handleSubmit = () => {
//   // TODO: 소속 신청 로직 구현
//   console.log('소속 신청 제출')
//   closeModal()
// }

onMounted(() => {
  getCompanyInfo()
})
</script>

<style scoped>
.modal-content {
  border-radius: 8px;
}

.btn-rounded {
  border-radius: 20px;
}

.btn-3d {
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.gap-2 {
  gap: 0.5rem;
}
</style>
