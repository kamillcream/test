<template>
  <div>
    <div class="modal-header">
      <h5 class="modal-title">기업인증</h5>
      <button
        type="button"
        class="btn-close"
        @click="closeModal"
        aria-hidden="true"
      ></button>
    </div>
    <div class="modal-body">
      <!-- 기업명, 대표자명 -->
      <div class="row mb-3">
        <div class="col">
          <input
            type="text"
            class="form-control"
            placeholder="기업명을 입력하세요"
            v-model="form.companyName"
          />
        </div>
        <div class="col">
          <input
            type="text"
            class="form-control"
            placeholder="대표자명을 입력하세요"
            v-model="form.ceoName"
          />
        </div>
      </div>

      <!-- 개업일자 -->
      <div class="mb-3">
        <input
          type="date"
          class="form-control"
          name="openDate"
          v-model="form.openDate"
        />
      </div>

      <!-- 사업자등록번호 + 인증 버튼 -->
      <div class="input-group mb-3">
        <input
          type="text"
          class="form-control"
          placeholder="사업자 등록번호를 입력하세요"
          v-model="form.bizNumber"
        />
        <button class="btn btn-primary" type="button">인증하기</button>
      </div>

      <!-- 약관 동의 -->
      <div class="form-check mb-4">
        <input
          type="checkbox"
          v-model="form.terms"
          id="agreeTerms"
          class="form-check-input"
        />
        <label class="form-check-label me-1" for="agreeTerms">
          약관에 동의합니다.
        </label>
        <a class="font-primary" @click="openTermsModal"> 이용약관</a>
      </div>

      <!-- 인증 완료 버튼 -->
      <div class="d-grid">
        <button type="button" class="btn btn-primary" @click="handleConfirm">
          기업 인증 완료
        </button>
      </div>
    </div>
    <div class="modal-footer">
      <button class="btn btn-light" @click="closeModal">닫기</button>
    </div>
  </div>
</template>

<script setup>
import { reactive, defineProps, nextTick } from 'vue'
import { useModalStore } from '@/fo/stores/modalStore'
import { useAlertStore } from '@/fo/stores/alertStore'
import { companyAgreementText } from '@/assets/terms'
import TermsAgreementModal from './TermsAgreementModal.vue'

const props = defineProps({
  onConfirm: { type: Function, required: true }, // 부모로 넘기는 콜백
})

const alertStore = useAlertStore()
const modalStore = useModalStore()

// 입력값을 하나의 reactive 객체로 관리
const form = reactive({
  companyName: '',
  ceoName: '',
  openDate: '',
  bizNumber: '',
  terms: false,
})

function closeModal() {
  modalStore.closeModal()
}

function openTermsModal() {
  modalStore.openModal(TermsAgreementModal, {
    title: '기업정보 수집 및 이용 동의서',
    body: companyAgreementText,
    onConfirm: async () => {
      alertStore.show('약관 동의 처리되었습니다.', 'success')
      modalStore.closeModal()
      await nextTick()
      form.terms = true
    },
  })
}

function handleConfirm() {
  // 유효성 검사 등 가능
  if (!form.terms) {
    alertStore.show('약관에 동의해주세요.', 'warning')
    return
  }

  // 부모 컴포넌트로 데이터 전달
  // 구조분해하여 값만 넘길 수도 있음
  const payload = { ...form }
  // emit처럼 사용하는 방식
  // defineProps로 받은 onConfirm 실행
  props.onConfirm(payload)

  closeModal()
}
</script>
