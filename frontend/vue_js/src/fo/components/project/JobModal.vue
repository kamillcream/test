<template>
  <Teleport to="body">
    <div class="modal-backdrop">
      <div class="modal-wrapper">
        <h5 class="modal-title">모집 직군 선택</h5>

        <div class="grid-button-group">
          <button
            v-for="job in jobOptions"
            :key="job"
            :class="['job-button', { selected: isSelected(job) }]"
            @click="toggleJob(job)"
          >
            {{ job }}
          </button>
        </div>

        <div class="modal-footer">
          <button class="btn btn-cancel" @click="closeModal">닫기</button>
          <button class="btn btn-confirm" @click="confirmSelection">
            선택 완료
          </button>
        </div>
      </div>
    </div>
  </Teleport>
</template>

<script setup>
import { ref, defineEmits, defineProps } from 'vue'
import { useModalStore } from '../../stores/modalStore.js'

const emit = defineEmits(['confirm'])
const props = defineProps({
  onConfirm: Function,
  jobs: {
    type: Array,
    default: () => [],
  },
})

const modalStore = useModalStore()

const jobOptions = [
  '개발PM',
  '게임개발',
  '데이터분석가',
  '데이터엔지니어',
  '백엔드/서버개발',
  '보안컨설팅',
  '앱개발',
  '웹개발',
  '웹마스터',
  '퍼블리셔',
  '프론트엔드',
  'BI 엔지니어',
  '시스템엔지니어',
  'SI개발',
  'SQA',
  '전문분야',
  '검색엔진',
  '네트워크',
  '딥러닝',
  '머신러닝',
]

const selectedJobs = ref([...props.jobs])

const toggleJob = (job) => {
  const i = selectedJobs.value.indexOf(job)
  i === -1 ? selectedJobs.value.push(job) : selectedJobs.value.splice(i, 1)
}

const isSelected = (job) => selectedJobs.value.includes(job)

const confirmSelection = () => {
  emit('confirm', selectedJobs.value)
  modalStore.closeModal()
}

const closeModal = () => {
  modalStore.closeModal()
}
</script>

<style scoped>
.modal-backdrop {
  position: fixed;
  inset: 0;
  background: rgba(0, 0, 0, 0.4);
  z-index: 9999;
  display: flex;
  align-items: center;
  justify-content: center;
}

.modal-wrapper {
  background: #fff;
  padding: 32px;
  border-radius: 12px;
  width: 600px;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.15);
}

.modal-title {
  font-size: 18px;
  font-weight: 600;
  margin-bottom: 24px;
}

.grid-button-group {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 12px;
  margin-bottom: 24px;
}

.job-button {
  padding: 12px 10px;
  border-radius: 8px;
  border: 1px solid #dee2e6;
  background: white;
  font-weight: 500;
  transition: 0.2s;
  cursor: pointer;
}

.job-button.selected {
  background-color: #0d6efd;
  color: white;
  border-color: #0d6efd;
}

.modal-footer {
  display: flex;
  justify-content: flex-end;
  gap: 12px;
}

.btn {
  padding: 8px 16px;
  border-radius: 8px;
  font-weight: 500;
  cursor: pointer;
  border: none;
}

.btn-cancel {
  background: #f1f3f5;
  color: #333;
}

.btn-confirm {
  background: #0d6efd;
  color: white;
}
</style>
