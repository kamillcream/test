<template>
  <div>
    <!-- Backdrop -->
    <transition name="fade">
      <div
        v-show="isOpen"
        class="modal-backdrop fade"
        :class="{ show: isOpen }"
        @click="closeModal"
      ></div>
    </transition>

    <!-- Modal -->
    <transition name="fade">
      <div
        v-show="isOpen"
        class="modal fade"
        :class="{ show: isOpen }"
        tabindex="-1"
        style="display: block"
      >
        <div :class="['modal-dialog', modalProps.size || '']">
          <div class="modal-content">
            <component :is="modalComponent" v-bind="modalProps" />
          </div>
        </div>
      </div>
    </transition>
  </div>
</template>

<script setup>
import { useModalStore } from '@/fo/stores/modalStore'
import { onMounted, onBeforeUnmount, watch } from 'vue'
import { storeToRefs } from 'pinia'

const modalStore = useModalStore()
const { isOpen, modalComponent, modalProps } = storeToRefs(modalStore)
const { closeModal } = modalStore

const handleEscape = (event) => {
  if (event.key === 'Escape') {
    closeModal()
  }
}

onMounted(() => {
  window.addEventListener('keydown', handleEscape)
})

onBeforeUnmount(() => {
  window.removeEventListener('keydown', handleEscape)
})

let prevScrollY = 0 // 초기값 설정

watch(isOpen, (newVal) => {
  if (newVal) {
    // 모달이 열릴 때 현재 스크롤 위치를 저장
    prevScrollY = window.scrollY
    document.body.style.overflow = 'hidden'
  } else {
    // 모달이 닫힐 때 저장된 스크롤 위치로 복원
    document.body.style.overflow = ''
    window.scrollTo(0, prevScrollY) // 이전 위치로 복원
  }
})
</script>

<style scoped>
.modal {
  z-index: 1050;
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  overflow: auto;
  outline: 0;
}

.modal-backdrop {
  z-index: 1040;
  position: fixed;
  inset: 0;
  background-color: rgba(0, 0, 0, 0.5);
}

.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.3s ease;
}
.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}
</style>
