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
                v-model="form.device"
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
                v-model="form.os"
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
                v-model="form.dbms"
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
                v-model="form.languages"
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
                v-model="form.tools"
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
                v-model="form.frameworks"
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
import { ref, defineProps, onMounted } from 'vue'
import { useModalStore } from '@/fo/stores/modalStore'
import SkillSelectModal from '@/fo/components/project/SkillSelectModal.vue'
import axios from 'axios'

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
  // device: '',
  // os: '',
  // dbms: '',
  // languages: '',
  // tools: '',
  // frameworks: '',
  skills: [],
  etc: '',
})

const selectedSkills = ref([])
const skillList = ref([])
// const languageList = ref([])
// const frameworkList = ref([])
// const toolList = ref([])

function parseArray(str) {
  if (!str) return []
  return str
    .split(/[,\n;]/)
    .map((s) => s.trim())
    .filter(Boolean)
}

// const updateSelectedSkills = () => {
//   const languages = parseArray(form.value.languages).map((name) => ({
//     name,
//     type: 'language',
//   }))
//   const tools = parseArray(form.value.tools).map((name) => ({
//     name,
//     type: 'tool',
//   }))
//   const frameworks = parseArray(form.value.frameworks).map((name) => ({
//     name,
//     type: 'framework',
//   }))
//   selectedSkills.value = [...languages, ...tools, ...frameworks]
// }

const openSkillModal = () => {
  modalStore.openModal(SkillSelectModal, {
    skills: skillList.value,
    onComplete: handleSkillConfirm,
  })
}

const handleSkillConfirm = (selected) => {
  form.value.skills = selected
}

// const handleSkillConfirm = (selected) => {
//   form.value.languages = selected
//     .filter((s) => s.type === 'language')
//     .map((s) => s.name)
//     .join(', ')
//   form.value.tools = selected
//     .filter((s) => s.type === 'tool')
//     .map((s) => s.name)
//     .join(', ')
//   form.value.frameworks = selected
//     .filter((s) => s.type === 'framework')
//     .map((s) => s.name)
//     .join(', ')
// }

const submit = () => {
  const project = {
    name: form.value.name,
    period: form.value.period,
    client: form.value.client,
    workUnit: form.value.workUnit,
    role: form.value.role,
    skills: selectedSkills.value,
    // device: form.value.device,
    // os: form.value.os,
    // dbms: form.value.dbms,
    // languages: parseArray(form.value.languages).map((name) => ({ name })),
    // tools: parseArray(form.value.tools),
    // frameworks: parseArray(form.value.frameworks).map((name) => ({ name })),
    etc: parseArray(form.value.etc).map((name) => ({ name })),
  }
  props.onComplete(project)
  closeModal()
}

const closeModal = () => {
  modalStore.closeModal()
}

onMounted(async () => {
  try {
    const res = await axios.get(
      'http://localhost:8080/api/mypage/resume/skills',
      console.log('기술 목록:', res.data),
    )
    skillList.value = res.data
    // languageList.value = res.data.filter((skill) => skill.type === 'language')
    // frameworkList.value = res.data.filter((skill) => skill.type === 'framework')
    // toolList.value = res.data.filter((skill) => skill.type === 'tool')
  } catch (e) {
    console.error('[ 기술 목록 조회 실패]', e)
  }
})
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
