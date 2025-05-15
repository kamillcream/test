<template>
  <div class="modal-content">
    <div class="modal-header">
      <h4 class="modal-title" id="smallModalLabel">이력서 등록</h4>
      <button
        type="button"
        class="btn-close"
        @click="closeModal"
        aria-hidden="true"
      >
        ×
      </button>
    </div>
    <div class="modal-body">
      <p>등록하시겠습니까 ?</p>
    </div>
    <div class="modal-footer">
      <button type="submit" class="btn btn-primary" @click="confirm">
        확인
      </button>
      <button type="button" class="btn btn-light" @click="closeModal">
        취소
      </button>
    </div>
  </div>
</template>

<script setup>
import { useModalStore } from '@/fo/stores/modalStore'
import { defineEmits } from 'vue'

// ✅ 나중에 API 호출 함수 연결될 emit
const emit = defineEmits(['confirm'])

const modalStore = useModalStore()

const confirm = () => {
  emit('confirm') // TODO: 여기에 API 연동 함수가 연결될 예정
  modalStore.closeModal()
}

const closeModal = () => {
  modalStore.closeModal()
}
</script>

<style scoped>
.modal-content {
  border-radius: 8px;
}
</style>
