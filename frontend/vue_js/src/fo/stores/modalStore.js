// src/stores/modalStore.js
import { defineStore } from 'pinia'
import { ref } from 'vue'

export const useModalStore = defineStore('modal', () => {
  const modalComponent = ref(null) // 어떤 모달 컴포넌트를 띄울지
  const modalProps = ref({}) // 모달에 전달할 props
  const isOpen = ref(false) // 모달 열림 여부

  function openModal(component, props = {}) {
    modalComponent.value = component
    modalProps.value = props
    isOpen.value = true
  }

  function closeModal() {
    isOpen.value = false
    modalComponent.value = null
    modalProps.value = {}
  }

  return {
    modalComponent,
    modalProps,
    isOpen,
    openModal,
    closeModal,
  }
})
