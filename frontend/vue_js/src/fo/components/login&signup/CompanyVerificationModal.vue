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
            v-model="companyPorfileStore.companyData.companyName"
          />
        </div>
        <div class="col">
          <input
            type="text"
            class="form-control"
            placeholder="대표자명을 입력하세요"
            v-model="companyPorfileStore.companyData.ceoName"
          />
        </div>
      </div>

      <!-- 개업일자 -->
      <div class="mb-3">
        <input
          type="date"
          class="form-control"
          name="openDate"
          v-model="companyPorfileStore.companyData.openDate"
        />
      </div>

      <!-- 사업자등록번호 + 인증 버튼 -->
      <div class="input-group mb-3">
        <input
          type="text"
          class="form-control"
          placeholder="사업자 등록번호를 입력하세요"
          v-model="companyPorfileStore.companyData.bizNumber"
        />
        <button class="btn btn-primary" type="button">인증하기</button>
      </div>

      <!-- 약관 동의 -->
      <div class="form-check mb-4">
        <input
          type="checkbox"
          v-model="companyPorfileStore.termsAgreed"
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
import { defineProps } from 'vue'
import { useModalStore } from '@/fo/stores/modalStore'
import { useAlertStore } from '@/fo/stores/alertStore'
import { companyAgreementText } from '@/assets/terms'
import TermsAgreementModal from './TermsAgreementModal.vue'
import { useCompanyProfileStore } from '@/fo/stores/companyProfileStore'

defineProps({
  onConfirm: { type: Function, required: true }, // 부모로 넘기는 콜백
})

const companyPorfileStore = useCompanyProfileStore()
const modalStore = useModalStore()
const alertStore = useAlertStore()

const termsText = companyAgreementText

function closeModal() {
  modalStore.closeModal()
}

function openTermsModal() {
  modalStore.openModal(TermsAgreementModal, {
    title: '기업정보 수집 및 이용 동의서',
    body: termsText,
    onConfirm: () => {
      alertStore.show('약관 동의 처리되었습니다.', 'success')
      companyPorfileStore.termsAgreed = true
      modalStore.closeModal() // 약관 모달 닫기
    },
  })
}

function handleConfirm() {
  if (!companyPorfileStore.termsAgreed) {
    alertStore.show('약관에 동의해주세요.', 'warning')
    return
  }

  // 인증 완료 시 스토어에 상태 저장
  const payload = { ...companyPorfileStore.companyData }
  companyPorfileStore.setProfile(payload)

  // 필요 시 부모로 전달
  // props.onConfirm(payload)

  closeModal()
}
</script>
