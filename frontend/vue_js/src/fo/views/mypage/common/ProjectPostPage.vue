<template lang="">
  <div class="d-flex layout-wrapper mx-auto">
    <aside class="sidebar bg-light" style="width: 220px">
      <MyPageSideBar />
    </aside>
    <main class="content flex-grow-1 px-4">
      <div
        class="tab-pane tab-pane-navigation active show"
        id="projectRegisterForm"
        role="tabpanel"
      >
        <h4 class="mb-3">프로젝트 등록</h4>
        <div class="card bg-color-grey mb-4">
          <div class="card-body">
            <form
              class="contact-form form-style-2"
              action="php/project-register.php"
              method="POST"
              novalidate="novalidate"
            >
              <!-- 프로젝트 제목 -->
              <div class="row">
                <div class="form-group col">
                  <label
                    class="form-label mb-1 text-2"
                    style="font-weight: bold"
                    >프로젝트 제목</label
                  >
                  <input
                    type="text"
                    class="form-control text-3 h-auto py-2"
                    name="title"
                    placeholder="예: 쇼핑몰 관리자 시스템 구축"
                    v-model="projectTitle"
                    required=""
                  />
                </div>
              </div>

              <!-- 프로젝트 장소 -->
              <div class="row">
                <div class="form-group col-lg-6">
                  <label
                    class="form-label mb-1 text-2"
                    style="font-weight: bold"
                    >시</label
                  >
                  <select
                    class="form-select form-control h-auto"
                    name="city"
                    v-model="selectedCity"
                    required=""
                  >
                    <option value="">선택</option>
                    <option value="seoul">서울</option>
                    <option value="busan">부산</option>
                    <!-- 추가 -->
                  </select>
                </div>
                <div class="form-group col-lg-6">
                  <label
                    class="form-label mb-1 text-2"
                    style="font-weight: bold"
                    >구</label
                  >
                  <select
                    class="form-select form-control h-auto"
                    name="district"
                    v-model="selectedDistrict"
                    required=""
                  >
                    <option value="">선택</option>
                    <option value="gangnam">강남구</option>
                    <option value="jongno">종로구</option>
                    <!-- 동적으로 바뀌도록 JS 연동 가능 -->
                  </select>
                </div>
              </div>
              <!-- 개발자 등급 / 학력 -->
              <div class="row">
                <div class="form-group col-lg-6">
                  <label
                    class="form-label mb-1 text-2"
                    style="font-weight: bold"
                    >개발자 등급(경력)</label
                  >
                  <select
                    class="form-select form-control h-auto"
                    name="career"
                    v-model="selectedDevGrade"
                    required=""
                  >
                    <option value="">선택</option>
                    <option value="1">초급 (신입~5년)</option>
                    <option value="2">중급 (5년~10년)</option>
                    <option value="10">고급 (10년 이상)</option>
                  </select>
                </div>
                <div class="form-group col-lg-6">
                  <label
                    class="form-label mb-1 text-2"
                    style="font-weight: bold"
                    >학력</label
                  >
                  <select
                    class="form-select form-control h-auto"
                    name="education"
                    v-model="selectedEducation"
                    required=""
                  >
                    <option value="">선택</option>
                    <option value="none">학력무관</option>
                    <option value="highschool">고등학교 졸업</option>
                    <option value="college">전문대 졸업</option>
                    <option value="university">대학교 졸업</option>
                    <option value="master">석사</option>
                    <option value="phd">박사</option>
                  </select>
                </div>
              </div>
              <!-- 프로젝트 기간 -->
              <div class="row">
                <div class="form-group col">
                  <label
                    class="form-label mb-1 text-2"
                    style="font-weight: bold"
                    >프로젝트 기간</label
                  >
                  <a
                    href="#"
                    @click.prevent="openProjectCalenderModal"
                    class="text-grey text-decoration-none small ms-2"
                    >+ 추가하기</a
                  >
                  <input
                    type="text"
                    class="form-control text-3 h-auto py-2"
                    name="period"
                    placeholder="예: 2025-04 ~ 2025-10"
                    v-model="projectPeriodDisplay"
                    required=""
                  />
                </div>
              </div>

              <!-- 모집 기간 -->
              <div class="row">
                <div class="form-group col">
                  <label
                    class="form-label mb-1 text-2"
                    style="font-weight: bold"
                    >모집 기간</label
                  >
                  <a
                    href="#"
                    @click.prevent="openRecruitCalenderModal"
                    class="text-grey text-decoration-none small ms-2"
                    >+ 추가하기</a
                  >
                  <input
                    type="text"
                    class="form-control text-3 h-auto py-2"
                    name="period"
                    placeholder="예: 2025-04 ~ 2025-10"
                    v-model="recruitPeriodDisplay"
                    required=""
                  />
                </div>
              </div>
              <!-- 근무형태 / 모집직군 -->
              <div class="form-group mb-3">
                <label class="form-label mb-1 text-2" style="font-weight: bold">
                  근무 형태
                  <a
                    href="#"
                    @click.prevent="addContract"
                    class="text-grey text-decoration-none small ms-2"
                    >+ 추가하기</a
                  >
                </label>

                <div
                  id="selectedSkillsPreview"
                  class="mb-2 d-flex gap-2 flex-wrap"
                >
                  <ProjectRecruitButtonGroup
                    v-if="selectedContractTypes.length > 0"
                    @remove="removeContract"
                    :selectedJobs="selectedContractTypes"
                  />
                </div>
              </div>
              <div class="form-group mb-3">
                <label class="form-label mb-1 text-2" style="font-weight: bold">
                  모집 직군
                  <a
                    href="#"
                    @click.prevent="addJob"
                    class="text-grey text-decoration-none small ms-2"
                    >+ 추가하기</a
                  >
                </label>

                <div
                  id="selectedSkillsPreview"
                  class="mb-2 d-flex gap-2 flex-wrap"
                >
                  <ProjectRecruitButtonGroup
                    v-if="selectedJobs.length > 0"
                    @remove="removeJob"
                    :selectedJobs="selectedJobs"
                  />
                </div>
              </div>

              <!-- 기술 -->
              <div class="form-group mb-3">
                <div class="d-flex align-items-center mb-1">
                  <label class="form-label text-2 fw-bold mb-0"
                    >사용 기술</label
                  >
                  <a
                    href="#"
                    @click.prevent="openSkillModal"
                    class="text-grey text-decoration-none small ms-2"
                    >+ 추가하기</a
                  >
                </div>

                <!-- 선택된 기술 미리보기 -->
                <div
                  id="selectedSkillsPreview"
                  class="mb-2 d-flex gap-2 flex-wrap"
                >
                  <ProjectSkillButtonGroup
                    v-if="selectedSkills.length > 0"
                    :selectedSkills="selectedSkills"
                    @remove="removeSkill"
                  />
                </div>
              </div>

              <div class="form-group mb-3">
                <label class="form-label mb-1 text-2" style="font-weight: bold"
                  >우대 기술
                  <a
                    href="#"
                    @click.prevent="openPreferSkillModal"
                    class="text-grey text-decoration-none small ms-2"
                    >+ 추가하기</a
                  >
                </label>
                <div
                  id="selectedSkillsPreview"
                  class="mb-2 d-flex gap-2 flex-wrap"
                >
                  <ProjectSkillButtonGroup
                    @remove="removePreferSkill"
                    v-if="selectedPreferSkills.length > 0"
                    :selectedSkills="selectedPreferSkills"
                  />
                </div>
              </div>

              <!-- 자격요건 -->
              <div class="row">
                <div class="form-group col">
                  <label
                    class="form-label mb-1 text-2"
                    style="font-weight: bold"
                    >우대 사항</label
                  >
                  <input
                    type="text"
                    class="form-control text-3 h-auto py-2"
                    name="qualification"
                    v-model="preferContent"
                    required=""
                  />
                </div>
              </div>

              <!-- 상세내용 -->
              <div class="row">
                <div class="form-group col">
                  <label
                    class="form-label mb-1 text-2"
                    style="font-weight: bold"
                    >상세 내용</label
                  >
                  <textarea
                    maxlength="5000"
                    rows="6"
                    class="form-control text-3 h-auto py-2"
                    name="description"
                    v-model="description"
                    required=""
                  ></textarea>
                </div>
              </div>

              <div class="form-group mb-3">
                <label
                  class="form-label mb-1 text-2"
                  style="font-weight: bold; position: relative"
                >
                  인터뷰 가능 시간
                  <a
                    href="#"
                    @click.prevent="openCalenderModal"
                    class="text-grey text-decoration-none small ms-2"
                    >+ 추가하기</a
                  >
                </label>

                <div
                  id="selectedSkillsPreview"
                  class="mb-2 d-flex gap-2 flex-wrap"
                >
                  <ProjectInverviewTimeButtonGroupVue
                    @remove="removeInterviewTime"
                    :interviewTimes="selectedInterviewTimes"
                  />
                </div>
              </div>

              <!-- 알림발신 -->
              <div class="form-group">
                <div class="form-check d-inline-flex align-items-center gap-2">
                  <input
                    class="form-check-input"
                    type="checkbox"
                    id="notifyCheckbox"
                    v-model="notifyEnabled"
                  />
                  <label class="form-check-label mb-0" for="notifyCheckbox">
                    알림 발신 여부
                  </label>
                </div>
              </div>

              <!-- 등록 / 취소 버튼 -->
              <div class="row">
                <div class="form-group col">
                  <button type="submit" class="btn btn-primary">등록</button>
                  <button type="reset" class="btn btn-light">취소</button>
                </div>
              </div>
            </form>
          </div>
        </div>
      </div>
    </main>
  </div>
</template>

<script setup>
import CalendarModal from '@/fo/components/project/CalendarModal.vue'
import SkillSelectModal from '@/fo/components/project/SkillSelectModal.vue'
//import InterviewTimeModal from '../../components/project/InterviewTimeModal.vue'
import MyPageSideBar from '@/fo/components/mypage/MyPageSideBar.vue'
import ProjectRecruitButtonGroup from '@/fo/components/project/ProjectRecruitButtonGroup.vue'
import ProjectSkillButtonGroup from '@/fo/components/project/ProjectSkillButtonGroup.vue'
import ProjectInverviewTimeButtonGroupVue from '@/fo/components/project/ProjectInverviewTimeButtonGroup.vue'
import { useModalStore } from '@/fo/stores/modalStore.js'

import { ref, computed } from 'vue'

const projectTitle = ref('')
const selectedCity = ref('')
const selectedDistrict = ref('')
const selectedDevGrade = ref('')
const selectedEducation = ref('')
const projectStartDt = ref('')
const projectEndDt = ref('')
const recruitStartDt = ref('')
const recruitEndDt = ref('')
const selectedInterviewTimes = ref([])
const selectedContractTypes = ref([])
const selectedJobs = ref([])
const selectedSkills = ref([])
const selectedPreferSkills = ref([])
const preferContent = ref('')
const description = ref('')
const notifyEnabled = ref(false)

const modalStore = useModalStore()
const openSkillModal = () => {
  modalStore.openModal(SkillSelectModal, {
    onConfirm: onSkillsConfirmed,
  })
}
const openPreferSkillModal = () => {
  modalStore.openModal(SkillSelectModal, {
    onConfirm: onPreferSkillsConfirmed,
  })
}

const openProjectCalenderModal = () => {
  modalStore.openModal(CalendarModal, {
    onConfirm: onProjectTimeConfirmed,
  })
}

const openRecruitCalenderModal = () => {
  modalStore.openModal(CalendarModal, {
    onConfirm: onRecruitTimeConfirmed,
  })
}

const onSkillsConfirmed = (skills) => {
  selectedSkills.value = skills
  console.log('부모에서 받은 skills:', skills)
}

const onPreferSkillsConfirmed = (skills) => {
  selectedPreferSkills.value = skills
  console.log('부모에서 받은 skills:', skills)
}

const onProjectTimeConfirmed = ({ start, end }) => {
  projectStartDt.value = start
  projectEndDt.value = end
}

const onRecruitTimeConfirmed = ({ start, end }) => {
  recruitStartDt.value = start
  recruitEndDt.value = end
}
// 테스트용 함수
const addContract = () => {
  selectedContractTypes.value.push({ id: Date.now(), name: '정규직' })
}

// 테스트용 함수
const addJob = () => {
  selectedJobs.value.push({ id: Date.now(), name: '백엔드드' })
}

// const updateSkills = (skills) => {
//   selectedSkills.value = skills
// }

const removeContract = (id) => {
  selectedContractTypes.value = selectedContractTypes.value.filter(
    (contract) => contract.id !== id,
  )
}

const removeJob = (id) => {
  selectedJobs.value = selectedJobs.value.filter((job) => job.id !== id)
}

const removeSkill = (name) => {
  selectedSkills.value = selectedSkills.value.filter(
    (skill) => skill.name !== name,
  )
}

const removePreferSkill = (id) => {
  selectedPreferSkills.value = selectedPreferSkills.value.filter(
    (preferSkill) => preferSkill.id !== id,
  )
}

const removeInterviewTime = (date) => {
  selectedInterviewTimes.value = selectedInterviewTimes.value.filter(
    (interviewTimes) => interviewTimes.date !== date,
  )
}

const projectPeriodDisplay = computed({
  get: () =>
    projectStartDt.value && projectEndDt.value
      ? `${projectStartDt.value} ~ ${projectEndDt.value}`
      : '',
  set: (val) => {
    const parts = val.split('~').map((v) => v.trim())
    if (parts.length === 2) {
      projectStartDt.value = parts[0]
      projectEndDt.value = parts[1]
    }
  },
})

const recruitPeriodDisplay = computed({
  get: () =>
    recruitStartDt.value && recruitEndDt.value
      ? `${recruitStartDt.value} ~ ${recruitEndDt.value}`
      : '',
  set: (val) => {
    const parts = val.split('~').map((v) => v.trim())
    if (parts.length === 2) {
      recruitStartDt.value = parts[0]
      recruitEndDt.value = parts[1]
    }
  },
})
</script>
<style scoped>
.layout-wrapper {
  display: flex;
  max-width: 1200px;
  margin: 0 auto; /* 👉 중앙 정렬 */
}

.sidebar {
  width: 220px;
  flex-shrink: 0;
}

.content {
  flex-grow: 1;
  max-width: calc(100% - 220px); /* 👉 사이드바 제외한 공간 */
}
</style>
