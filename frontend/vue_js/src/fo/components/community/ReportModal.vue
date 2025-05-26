<template>
  <div>
    <div class="modal-header">
      <h4 class="modal-title" id="defaultModalLabel">신고 하기</h4>
      <button
        type="button"
        class="btn-close"
        data-bs-dismiss="modal"
        aria-hidden="true"
        @click="closeModal"
      ></button>
    </div>

    <div class="modal-body">
      <p class="mb-3">신고 사유를 선택해주세요:</p>
      <form>
        <div
          class="form-check mb-2"
          v-for="(reasonLabel, idx) in [
            '욕설/비방',
            '스팸/홍보',
            '개인정보 노출',
            '기타',
          ]"
          :key="idx"
        >
          <input
            class="form-check-input"
            type="checkbox"
            :checked="selectedReason === reasonLabel"
            @change="handleReasonChange(reasonLabel)"
            :id="'reason' + idx"
          />
          <label class="form-check-label" :for="'reason' + idx">{{
            reasonLabel
          }}</label>
        </div>

        <!-- 직접입력칸 -->
        <div class="mt-3 text-area" id="customReasonContainer">
          <label for="customReason" class="form-label">기타 사유 입력</label>
          <textarea
            class="form-control"
            id="customReason"
            rows="3"
            placeholder="자세한 신고 사유를 입력해주세요."
            v-model="customReason"
            :disabled="selectedReason !== '기타'"
          ></textarea>
        </div>
      </form>
    </div>

    <div class="modal-footer">
      <button type="button" class="btn btn-primary" @click="reportRegister">
        신고 제출
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
import CommonConfirmModal from '../common/CommonConfirmModal.vue'
import { api } from '@/axios'
import { useAlertStore } from '@/fo/stores/alertStore'
import { computed, defineProps, ref } from 'vue'

const modalStore = useModalStore()
const alertStore = useAlertStore()

const props = defineProps({
  reportTypeCd: { type: Number, default: 0 },
  sq: { type: Number, default: 0 },
})

const selectedReason = ref('')
const customReason = ref('')

const reason = computed(() => {
  if (selectedReason.value === '기타') {
    return customReason.value.trim()
  }
  return selectedReason.value
})

const closeModal = () => {
  modalStore.closeModal()
}

const handleReasonChange = (reasonLabel) => {
  // 이미 선택된 걸 다시 클릭하면 해제
  if (selectedReason.value === reasonLabel) {
    selectedReason.value = ''
    customReason.value = '' // 기타 입력값도 초기화
  } else {
    selectedReason.value = reasonLabel
  }
}

const reportRegister = () => {
  if (!reason.value) {
    alertStore.show(
      `신고 사유를 ${selectedReason.value === '기타' ? '입력' : '선택'}해주세요`,
      'danger',
    )
    return
  }
  modalStore.openModal(CommonConfirmModal, {
    title: '신고 등록',
    message: '신고하시겠습니까?',
    onConfirm: async () => {
      const res = await api.$post(`/report`, {
        userSq: 2,
        sq: props.sq,
        reportReasonTxt: reason.value,
        reportTypeCd: props.reportTypeCd,
      })
      if (res.status == 'CREATED') {
        alertStore.show(res.message, 'success')
        window.location.reload()
      } else {
        alertStore.show('신고 등록에 실패하였습니다.', 'danger')
      }
      modalStore.closeModal()
    },
  })
}
</script>
<style>
.text-area {
  display: block;
}
</style>
