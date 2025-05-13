<template>
  <div>
    <div class="modal-header">
      <h5 class="modal-title">{{ title }}</h5>
      <button
        type="button"
        class="btn-close"
        @click="closeModal"
        aria-hidden="true"
      ></button>
    </div>
    <div class="modal-body" style="max-height: 500px; overflow-y: auto">
      <!-- 동적 바인딩을 위한 body -->
      <div v-html="body"></div>
    </div>
    <div class="modal-footer">
      <button class="btn btn-primary" @click="onConfirm">동의</button>
      <button class="btn btn-light" @click="closeModal">닫기</button>
    </div>
  </div>
</template>

<script setup>
import { useModalStore } from '@/fo/stores/modalStore'
import { defineProps } from 'vue'

defineProps({
  title: { type: String, default: '확인' },
  body: { type: String, required: true }, // body를 props로 받음
  onConfirm: { type: Function, required: true },
})

const modalStore = useModalStore()

function closeModal() {
  modalStore.closeModal()
}
</script>
