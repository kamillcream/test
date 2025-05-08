<template>
  <div>
    <div class="modal-header">
      <h5 class="modal-title">{{ title }}</h5>
    </div>
    <div class="modal-body">
      <p>{{ message }}</p>
    </div>
    <div class="modal-footer">
      <button class="btn btn-primary" @click="onConfirm">확인</button>
      <button class="btn btn-light" @click="closeModal">취소</button>
    </div>
  </div>
</template>

<script setup>
import { useModalStore } from '@/fo/stores/modalStore'
import { defineProps } from 'vue'

defineProps({
  title: { type: String, default: '확인' },
  message: { type: String, default: '이 작업을 계속하시겠습니까?' },
  onConfirm: { type: Function, required: true },
})

const modalStore = useModalStore()

function closeModal() {
  modalStore.closeModal()
}
</script>
