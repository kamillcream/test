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

watch(isOpen, (newVal) => {
  if (newVal) {
    document.body.style.overflow = 'hidden'
  } else {
    document.body.style.overflow = ''
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
