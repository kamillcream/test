<template>
  <transition name="fade">
    <div
      v-if="visible"
      :class="[
        'alert',
        type === 'danger'
          ? 'alert-danger'
          : type === 'success'
          ? 'alert-info'
          : 'alert-info',
        'alert-dismissible',
      ]"
      :style="alertStyle"
      role="alert"
    >
      <button
        type="button"
        class="btn-close"
        @click="hide"
        aria-label="Close"
      />
      <strong>
        <i
          :class="{
            'fa-solid fa-check': type === 'success',
            'fas fa-exclamation-triangle': type === 'danger',
          }"
        ></i>
        {{ type === 'danger' ? '실패!' : '성공!' }}
      </strong>
      {{ message }}
    </div>
  </transition>
</template>

<script setup>
import { computed } from 'vue'
import { useAlertStore } from '@/fo/stores/alertStore'
import { storeToRefs } from 'pinia'

const alertStore = useAlertStore()
const { visible, message, type, scrollTop } = storeToRefs(alertStore)
const { hide } = alertStore

// 알림 창 위치를 계산하는 함수
const alertStyle = computed(() => {
  return {
    position: 'fixed',
    bottom: `${scrollTop.value + 10}px`, // 스크롤 위치 + 약간의 여백
    left: '50%',
    transform: 'translateX(-50%)',
    zIndex: 9999,
    maxWidth: '400px',
    width: '100%',
    pointerEvents: 'none', // 알림 창이 다른 요소 위에 나타나지 않도록
  }
})
</script>

<style scoped>
.alert {
  pointer-events: none; /* 알림 창이 다른 요소 위에 나타나지 않도록 */
}

.alert * {
  pointer-events: all; /* 알림 내의 버튼 클릭은 가능하도록 */
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
