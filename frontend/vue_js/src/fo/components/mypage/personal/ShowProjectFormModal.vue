<template>
  <div class="modal-layer">
    <div class="modal-content">
      <div class="modal-header">
        <h4 class="modal-title">프로젝트 이력 추가하기</h4>
        <button class="close-btn" @click="closeModal">×</button>
      </div>

      <div class="modal-body">
        <!-- 프로젝트 내용 -->
        <div class="section-block">
          <div class="section-title">프로젝트 내용</div>
          <div class="form-row">
            <div class="form-group">
              <label class="modal-label">프로젝트명</label>
              <input
                v-model="form.name"
                type="text"
                class="form-control"
                placeholder="프로젝트명 (예: 금융 시스템 구축)"
              />
            </div>
            <div class="form-group">
              <label class="modal-label">참여기간</label>
              <input
                v-model="form.period"
                type="text"
                class="form-control"
                placeholder="참여기간 (예: 2023.01 ~ 2023.12)"
              />
            </div>
          </div>
          <div class="form-row">
            <div class="form-group">
              <label class="modal-label">고객사</label>
              <input
                v-model="form.client"
                type="text"
                class="form-control"
                placeholder="고객사 (예: OO은행)"
              />
            </div>
            <div class="form-group">
              <label class="modal-label">업무단</label>
              <input
                v-model="form.workUnit"
                type="text"
                class="form-control"
                placeholder="업무단 (예: 대외계/내부)"
              />
            </div>
            <div class="form-group">
              <label class="modal-label">역할</label>
              <input
                v-model="form.role"
                type="text"
                class="form-control"
                placeholder="역할 (예: 개발)"
              />
            </div>
          </div>
        </div>

        <!-- 개발환경 -->
        <div class="section-block">
          <div class="section-title">개발환경</div>
          <div class="form-row">
            <div class="form-group">
              <label class="modal-label">기종</label>
              <input
                :value="selectedDeviceText"
                type="text"
                class="form-control"
                placeholder="기종 (예: PC)"
                readonly
                @click="openSkillModal"
              />
            </div>
            <div class="form-group">
              <label class="modal-label">OS</label>
              <input
                :value="selectedOsText"
                type="text"
                class="form-control"
                placeholder="OS (예: Linux)"
                readonly
                @click="openSkillModal"
              />
            </div>
            <div class="form-group">
              <label class="modal-label">DBMS</label>
              <input
                :value="selectedDbmsText"
                type="text"
                class="form-control"
                placeholder="DBMS (예: MySQL)"
                readonly
                @click="openSkillModal"
              />
            </div>
          </div>
          <div class="form-row">
            <div class="form-group">
              <label class="modal-label">언어</label>
              <input
                :value="selectedLanguagesText"
                type="text"
                class="form-control"
                placeholder="언어 (쉼표로 구분, 예: Java, Python)"
                readonly
                @click="openSkillModal"
              />
            </div>
            <div class="form-group">
              <label class="modal-label">TOOL</label>
              <input
                :value="selectedToolsText"
                type="text"
                class="form-control"
                placeholder="TOOL (쉼표로 구분, 예: Eclipse, VSCode)"
                readonly
                @click="openSkillModal"
              />
            </div>
            <div class="form-group">
              <label class="modal-label">FW</label>
              <input
                :value="selectedFrameworksText"
                type="text"
                class="form-control"
                placeholder="FW (쉼표로 구분, 예: Spring Boot, Vue.js)"
                readonly
                @click="openSkillModal"
              />
            </div>
          </div>
          <div class="form-row">
            <div class="form-group">
              <label class="modal-label">기타</label>
              <input
                v-model="form.etc"
                type="text"
                class="form-control"
                placeholder="기타 (쉼표로 구분, 예: git, Docker)"
              />
            </div>
          </div>
        </div>
      </div>

      <div class="modal-footer">
        <button class="btn btn-primary" @click="submit">저장하기</button>
        <button class="btn btn-light" @click="closeModal">닫기</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, defineProps, onMounted, computed } from 'vue'
import { useModalStore } from '@/fo/stores/modalStore'
import SkillSelectModal from '@/fo/components/project/SkillSelectModal.vue'

import { api } from '@/axios.js'

const props = defineProps({
  onComplete: Function,
})

const modalStore = useModalStore()

const form = ref({
  name: '',
  period: '',
  client: '',
  workUnit: '',
  role: '',
  device: '',
  os: '',
  dbms: '',
  languages: '',
  tools: '',
  frameworks: '',
  etc: '',
})
const skills = ref([])
const selectedSkills = ref([])

// 각 타입별 computed
const selectedDeviceText = computed(() =>
  selectedSkills.value.filter(s => s.type === 'device').map(s => s.name).join(', ')
)
const selectedOsText = computed(() =>
  selectedSkills.value.filter(s => s.type === 'os').map(s => s.name).join(', ')
)
const selectedDbmsText = computed(() =>
  selectedSkills.value.filter(s => s.type === 'dbms').map(s => s.name).join(', ')
)
const selectedLanguagesText = computed(() =>
  selectedSkills.value.filter(s => s.type === 'language').map(s => s.name).join(', ')
)
const selectedToolsText = computed(() =>
  selectedSkills.value.filter(s => s.type === 'tool').map(s => s.name).join(', ')
)
const selectedFrameworksText = computed(() =>
  selectedSkills.value.filter(s => s.type === 'framework').map(s => s.name).join(', ')
)

function parseArray(str) {
  if (!str) return []
  if (Array.isArray(str)) return str // 이미 배열이면 그대로 반환
  return str
    .split(/[,\n;]/)
    .map((s) => s.trim())
    .filter(Boolean)
}

onMounted(async () => {
  await loadDefaultFormData() // 신규 등록용
})

const updateSelectedSkills = () => {
  const tools = parseArray(form.value.tools).map((name) => ({
    name,
    type: 'tool',
  }))
  const frameworks = parseArray(form.value.frameworks).map((name) => ({
    name,
    type: 'framework',
  }))
  selectedSkills.value = [...tools, ...frameworks]
}

const loadDefaultFormData = async () => {
  try {
    const response = await api.$get('/projects/forms')
    skills.value = response.output.skills
  } catch (e) {
    console.error('프로젝트 정보 불러오기 실패 (신규)', e)
  }
}

const openSkillModal = () => {
  updateSelectedSkills()
  modalStore.openModal(SkillSelectModal, {
    skills: skills.value,
    onConfirm: handleSkillConfirm,
  })
}
const handleSkillConfirm = (skills) => {
  selectedSkills.value = skills
  //  각 타입별로 form에 값 반영
  form.value.device = selectedDeviceText.value
  form.value.os = selectedOsText.value
  form.value.dbms = selectedDbmsText.value
  form.value.languages = selectedLanguagesText.value
  form.value.tools = selectedToolsText.value
  form.value.frameworks = selectedFrameworksText.value

  // 콘솔 로그 추가
  console.log('--- [handleSkillConfirm] ---')
  console.log('selectedSkills:', JSON.parse(JSON.stringify(selectedSkills.value)))
  console.log('form.value.device:', form.value.device)
  console.log('form.value.os:', form.value.os)
  console.log('form.value.dbms:', form.value.dbms)
  console.log('form.value.languages:', form.value.languages)
  console.log('form.value.tools:', form.value.tools)
  console.log('form.value.frameworks:', form.value.frameworks)
}

const submit = () => {
  const project = {
    device: form.value.device,
    os: form.value.os,
    dbms: form.value.dbms,
    languages: form.value.languages.split(',').map(name => ({ name: name.trim() })),
    tools: form.value.tools.split(',').map(name => name.trim()),
    frameworks: form.value.frameworks.split(',').map(name => ({ name: name.trim() })),
    etc: form.value.etc.split(',').map(name => ({ name: name.trim() })),
    name: form.value.name,
    period: form.value.period,
    client: form.value.client,
    workUnit: form.value.workUnit,
    role: form.value.role,
  }
  props.onComplete(project)
  closeModal()
}

const closeModal = () => {
  modalStore.closeModal()
}
</script>

<style scoped>
.modal-layer {
  position: fixed;
  top: 0;
  left: 0;
  z-index: 9999;
  width: 100vw;
  height: 100vh;
  background: rgba(0, 0, 0, 0.4);
  display: flex;
  align-items: center;
  justify-content: center;
}
.modal-content {
  position: relative;
  width: 750px;
  max-width: 95vw;
  background: #fff;
  padding: 28px 20px 16px 20px;
  overflow-x: hidden;
  overflow-y: auto;
  box-sizing: border-box;
  border-radius: 8px;
}
.section-block {
  border: 1px solid #e0e0e0;
  border-radius: 10px;
  padding: 18px 16px 10px 16px;
  margin-bottom: 20px;
  background: #fafbfc;
}
.modal-header {
  height: 40px;
  display: flex;
  align-items: flex-start;
  justify-content: space-between;
  padding-bottom: 4px;
  padding-top: 0;
}
.modal-title {
  color: #222;
  font-size: 1.2rem;
  font-weight: bold;
  margin-top: 0;
  margin-bottom: 0;
}
.close-btn {
  background: transparent;
  border: none;
  font-size: 1.8rem;
  cursor: pointer;
  display: block;
  line-height: 1;
  padding: 0 8px;
  color: #888;
  position: relative;
  top: -6px;
}
.modal-label {
  display: none;
}
.section-title {
  font-weight: bold;
  font-size: 1.05rem;
  margin-bottom: 8px;
  color: #333;
}
.modal-body .form-group {
  margin-bottom: 0;
}
.form-row {
  display: flex;
  gap: 16px;
  margin-bottom: 16px;
}
.form-row .form-group {
  flex: 1 1 0;
}
.form-control {
  width: 100%;
  padding: 8px;
  box-sizing: border-box;
}
.modal-footer {
  display: flex;
  justify-content: flex-end;
  gap: 8px;
  margin-top: 10px;
}
</style>