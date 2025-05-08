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
import { useAlertStore } from '@/fo/stores/alertStore'
import { storeToRefs } from 'pinia'

const alertStore = useAlertStore()
const { visible, message, type } = storeToRefs(alertStore)
const { hide } = alertStore
</script>

<style scoped>
.alert {
  position: fixed;
  top: 10%;
  left: 50%;
  transform: translate(-50%, -50%);
  z-index: 9999;
  max-width: 400px;
  width: 100%;
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
