<template>
  <div
    class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50"
  >
    <div class="bg-white rounded-xl shadow-lg p-6 w-full max-w-md">
      <h2 class="text-xl font-semibold mb-4">근무 형태 선택</h2>
      <div class="grid grid-cols-2 gap-3 mb-6">
        <button
          v-for="type in workTypes"
          :key="type"
          @click="toggleSelection(type)"
          :class="[
            'py-2 px-4 rounded border text-center font-medium',
            selected.includes(type)
              ? 'bg-blue-500 text-white'
              : 'bg-white text-gray-700 border-gray-300',
          ]"
        >
          {{ type }}
        </button>
      </div>
      <div class="flex justify-end gap-2">
        <button @click="close" class="border px-3 py-1 rounded text-sm">
          닫기
        </button>
        <button
          @click="confirm"
          class="bg-black text-white px-3 py-1 rounded text-sm"
        >
          선택 완료
        </button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, defineEmits } from 'vue'
import { useModalStore } from '@/stores/modalStore'

const emit = defineEmits(['confirm'])
const modalStore = useModalStore()

const workTypes = ['프리랜서', '인턴', '정규직', '계약직']
const selected = ref([])

const toggleSelection = (type) => {
  const index = selected.value.indexOf(type)
  if (index === -1) selected.value.push(type)
  else selected.value.splice(index, 1)
}

const confirm = () => {
  emit('confirm', selected.value)
  modalStore.closeModal()
}

const close = () => {
  modalStore.closeModal()
}
</script>
