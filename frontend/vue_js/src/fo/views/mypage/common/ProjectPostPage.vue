<template lang="">
  <div class="d-flex layout-wrapper mx-auto">
    <div
      class="tab-pane tab-pane-navigation active show content flex-grow-1 px-4"
      id="projectRegisterForm"
      role="tabpanel"
    >
      <h4 class="mb-3">프로젝트 등록</h4>
      <div class="card bg-color-grey mb-4">
        <div class="card-body">
          <form
            class="contact-form form-style-2"
            @submit.prevent="submitProject"
          >
            <!-- 프로젝트 제목 -->
            <div class="row">
              <div class="form-group col">
                <label class="form-label mb-1 text-2" style="font-weight: bold"
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
                <label class="form-label mb-1 text-2" style="font-weight: bold"
                  >시</label
                >
                <select
                  class="form-select form-control h-auto"
                  name="city"
                  v-model="selectedCity"
                  required=""
                >
                  <option value="">선택</option>
                  <option
                    v-for="city in cities"
                    :key="city.code"
                    :value="city.code"
                  >
                    {{ city.name }}
                  </option>
                </select>
              </div>
              <div class="form-group col-lg-6">
                <label class="form-label mb-1 text-2" style="font-weight: bold"
                  >구</label
                >
                <select
                  class="form-select form-control h-auto"
                  name="district"
                  v-model="selectedDistrict"
                  required=""
                >
                  <option value="">선택</option>
                  <option
                    v-for="district in districts"
                    :key="district.code"
                    :value="district.code"
                  >
                    {{ district.name }}
                  </option>
                </select>
              </div>
            </div>
            <!-- 개발자 등급 / 학력 -->
            <div class="row">
              <div class="form-group col-lg-6">
                <label class="form-label mb-1 text-2" style="font-weight: bold"
                  >개발자 등급(경력)</label
                >
                <select
                  class="form-select form-control h-auto"
                  name="career"
                  v-model="selectedDevGrade"
                  required=""
                >
                  <option value="">선택</option>
                  <option
                    v-for="grade in devGrades"
                    :key="grade.id"
                    :value="grade"
                  >
                    {{ grade }}
                  </option>
                </select>
              </div>
              <div class="form-group col-lg-6">
                <label class="form-label mb-1 text-2" style="font-weight: bold"
                  >학력</label
                >
                <select
                  class="form-select form-control h-auto"
                  name="education"
                  v-model="selectedEducation"
                  required=""
                >
                  <option value="">선택</option>
                  <option
                    v-for="education in educationLevels"
                    :key="education.id"
                    :value="education"
                  >
                    {{ education }}
                  </option>
                </select>
              </div>
            </div>
            <!-- 프로젝트 기간 -->
            <div class="row">
              <div class="form-group col">
                <label class="form-label mb-1 text-2" style="font-weight: bold"
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
                <label class="form-label mb-1 text-2" style="font-weight: bold"
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
                  @click.prevent="openWorkTypeModal"
                  class="text-grey text-decoration-none small ms-2"
                  >+ 추가하기</a
                >
              </label>

              <div
                id="selectedSkillsPreview"
                class="mb-2 d-flex gap-2 flex-wrap"
              >
                <ProjectJobButtonGroup
                  v-if="selectedWorkTypes.length > 0"
                  @remove="removeWorkType"
                  :selectedJobs="selectedWorkTypes"
                />
              </div>
            </div>
            <div class="form-group mb-3">
              <label class="form-label mb-1 text-2" style="font-weight: bold">
                모집 직군
                <a
                  href="#"
                  @click.prevent="openJobModal"
                  class="text-grey text-decoration-none small ms-2"
                  >+ 추가하기</a
                >
              </label>

              <div
                id="selectedSkillsPreview"
                class="mb-2 d-flex gap-2 flex-wrap"
              >
                <ProjectJobButtonGroup
                  v-if="selectedJobs.length > 0"
                  @remove="removeJob"
                  :selectedJobs="selectedJobs"
                />
              </div>
            </div>

            <!-- 기술 -->
            <div class="form-group mb-3">
              <div class="d-flex align-items-center mb-1">
                <label class="form-label text-2 fw-bold mb-0">사용 기술</label>
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
                <label class="form-label mb-1 text-2" style="font-weight: bold"
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
                <label class="form-label mb-1 text-2" style="font-weight: bold"
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
                  @click.prevent="openInterviewTimeModal"
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
  </div>
</template>

<script setup>
import CalendarModal from '@/fo/components/project/CalendarModal.vue'
import SkillSelectModal from '@/fo/components/project/SkillSelectModal.vue'
import WorkTypeModal from '@/fo/components/project/WorkTypeModal.vue'
import JobModal from '@/fo/components/project/JobModal.vue'
import InterviewTimeModal from '@/fo/components/project/InterviewTimeModal.vue'
import ProjectJobButtonGroup from '@/fo/components/project/ProjectJobButtonGroup.vue'
import ProjectSkillButtonGroup from '@/fo/components/project/ProjectSkillButtonGroup.vue'
import ProjectInverviewTimeButtonGroupVue from '@/fo/components/project/ProjectInverviewTimeButtonGroup.vue'
import { useModalStore } from '../../../stores/modalStore.js'
import { useRoute } from 'vue-router'

import { ref, computed, watch, onMounted } from 'vue'

import { api } from '@/axios.js'

const route = useRoute()
const projectSq = route.params.project_sq || null // 등록이면 NULL, 수정이면 숫자
console.log(projectSq)

const cities = ref([])
const districts = ref([])
const devGrades = ref([])
const educationLevels = ref([])
const recruitJobs = ref([])
const workTypes = ref([])
const skills = ref([])

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
const selectedWorkTypes = ref([])
const selectedJobs = ref([])
const selectedSkills = ref([])
const selectedPreferSkills = ref([])
const preferContent = ref('')
const description = ref('')
const notifyEnabled = ref(false)

const modalStore = useModalStore()

// 모달 열려있는 동안 부모 페이지 스크롤 비활성화, 닫히면 다시 활성화
const isOpen = computed(() => modalStore.isOpen)

let prevScrollY = 0

onMounted(async () => {
  try {
    const response = await api.$get('/projects/forms')
    cities.value = response.output.cities
      .sort((a, b) => {
        // "전국"이면 항상 맨 앞으로
        if (a.areaName === '전국') return -1
        if (b.areaName === '전국') return 1
        return 0
      })
      .map((city) => ({
        code: city.areaSq,
        name: city.areaName,
      }))
    devGrades.value = response.output.devGrades
    educationLevels.value = response.output.educationLevels
    recruitJobs.value = response.output.recruitJobs
    workTypes.value = response.output.workTypes
    skills.value = response.output.skills
    console.log(response)
  } catch (e) {
    console.error('프로젝트 정보 불러오기 실패', e)
  }
  if (projectSq) {
    try {
      const { output } = await api.$get(`/projects/forms`, {
        params: { projectSq },
      })

      const exist = output.existProjectVo

      if (exist) {
        projectTitle.value = exist.projectTtl
        selectedCity.value = exist.parentDistrict
        selectedDistrict.value = exist.subDistrict
        selectedDevGrade.value = exist.devGrade
        selectedEducation.value = exist.educationLvl
        projectStartDt.value = exist.projectStartDt
        projectEndDt.value = exist.projectEndDt
        recruitStartDt.value = exist.recruitStartDt
        recruitEndDt.value = exist.recruitEndDt
        selectedWorkTypes.value = [...exist.contract]
        selectedJobs.value = [...exist.jobs]
        selectedSkills.value = [...exist.reqSkills]
        selectedPreferSkills.value = [...exist.preferSkills]
        preferContent.value = exist.preferredEtc
        description.value = exist.description

        selectedInterviewTimes.value = Object.entries(exist.interviewTimes).map(
          ([date, times]) => ({
            date,
            times,
          }),
        )
      }
    } catch (e) {
      console.error('기존 프로젝트 상세 조회 실패', e)
    }
  }
})

watch(selectedCity, async (areaCodeSq) => {
  if (!areaCodeSq) {
    districts.value = []
    selectedDistrict.value = ''
    return
  }

  try {
    const response = await api.$get(`/projects/${areaCodeSq}/districts`)
    districts.value = response.output.map((area) => ({
      code: area.areaSq,
      name: area.areaName,
    }))
    selectedDistrict.value = '' // 선택 초기화
  } catch (err) {
    console.error('구 정보 불러오기 실패', err)
  }
})

const submitProject = async () => {
  const requestBody = {
    projectId: projectSq ?? null,
    projectTitle: projectTitle.value,
    projectImageUrl: '',

    district: selectedCity.value,
    subDistrict: selectedDistrict.value,
    devGrade: selectedDevGrade.value,
    educationLvl: selectedEducation.value,

    projectStartDt: projectStartDt.value,
    projectEndDt: projectEndDt.value,
    recruitStartDt: recruitStartDt.value,
    recruitEndDt: recruitEndDt.value,

    workType: [...selectedWorkTypes.value],

    recruitJob: [...selectedJobs.value],

    preferSkills: selectedPreferSkills.value
      .map((s) => (typeof s === 'string' ? s : s?.name))
      .filter((name) => !!name),
    usingSkills: selectedSkills.value
      .map((s) => (typeof s === 'string' ? s : s?.name))
      .filter((name) => !!name),

    preference: preferContent.value,
    description: description.value,

    interviewTime: selectedInterviewTimes.value.flatMap((item) =>
      item.times.map((time) => `${item.date}T${time}`),
    ),

    isNotification: notifyEnabled.value ? 'Y' : 'N',
  }
  console.log(requestBody)
  try {
    if (projectSq) {
      await api.$patch('/projects', requestBody)
      alert('수정 성공')
    } else {
      await api.$post('/projects', requestBody)
      alert('등록 성공')
    }
  } catch (error) {
    console.error('프로젝트 등록 실패: ', error)
  }
}

watch(isOpen, (newVal) => {
  if (newVal) {
    prevScrollY = window.scrollY
    document.body.style.setProperty('overflow', 'hidden', 'important')
    document.documentElement.style.setProperty(
      'overflow',
      'hidden',
      'important',
    )
  } else {
    document.body.style.removeProperty('overflow')
    document.documentElement.style.removeProperty('overflow')
    window.scrollTo(0, prevScrollY)
  }
})

const openSkillModal = () => {
  modalStore.openModal(SkillSelectModal, {
    onConfirm: onSkillsConfirmed,
    skills: skills.value,
  })
}
const openPreferSkillModal = () => {
  modalStore.openModal(SkillSelectModal, {
    onConfirm: onPreferSkillsConfirmed,
    skills: skills.value,
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

const openWorkTypeModal = () => {
  modalStore.openModal(WorkTypeModal, {
    onConfirm: onWorkTypeConfirmed,
    works: workTypes.value,
  })
}

const openJobModal = () => {
  console.log(selectedJobs.value)
  modalStore.openModal(JobModal, {
    onConfirm: onJobConfirmed,
    jobs: recruitJobs.value,
  })
}

const openInterviewTimeModal = () => {
  modalStore.openModal(InterviewTimeModal, {
    onConfirm: onInterviewTimeConfirmed,
    interviewTimes: selectedInterviewTimes.value,
  })
}

const onSkillsConfirmed = (skills) => {
  selectedSkills.value = skills
  console.log(selectedSkills.value)
}

const onPreferSkillsConfirmed = (skills) => {
  selectedPreferSkills.value = skills
}

const onProjectTimeConfirmed = ({ start, end }) => {
  projectStartDt.value = start
  projectEndDt.value = end
}

const onRecruitTimeConfirmed = ({ start, end }) => {
  recruitStartDt.value = start
  recruitEndDt.value = end
}

const onInterviewTimeConfirmed = (times) => {
  selectedInterviewTimes.value = times
  console.log(selectedInterviewTimes.value)
}

const onWorkTypeConfirmed = (workTypes) => {
  console.log(workTypes)
  selectedWorkTypes.value = workTypes
}

const onJobConfirmed = (jobs) => {
  console.log(jobs)
  selectedJobs.value = jobs
}

const removeWorkType = (name) => {
  console.log('삭제 대상:', name)

  selectedWorkTypes.value = selectedWorkTypes.value.filter(
    (work) => work !== name,
  )

  console.log('삭제 후:', selectedWorkTypes.value)
}

const removeJob = (name) => {
  console.log('삭제 대상:', name)

  selectedJobs.value = selectedJobs.value.filter((job) => job !== name)

  console.log('삭제 후:', selectedJobs.value)
}

const removeSkill = (name) => {
  selectedSkills.value = selectedSkills.value.filter((skill) => {
    const skillName = typeof skill === 'string' ? skill : skill.name
    return skillName !== name
  })
}

const removePreferSkill = (name) => {
  selectedPreferSkills.value = selectedPreferSkills.value.filter(
    (preferSkill) => preferSkill.name !== name,
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
  margin: 0 auto;
}

.sidebar {
  width: 220px;
  flex-shrink: 0;
}

.content {
  flex-grow: 1;
  max-width: calc(100% - 220px);
}
</style>
