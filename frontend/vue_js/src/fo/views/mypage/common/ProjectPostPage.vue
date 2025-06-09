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
              <label class="form-label fw-bold">단가</label>
              <input
                type="text"
                class="form-control"
                v-model="projectSalary"
                placeholder="예: 1500000"
              />
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

                <!-- 태그 리스트 -->
                <div class="mb-2">
                  <span
                    class="badge me-1"
                    :style="{
                      backgroundColor: '#0088CC',
                      color: 'white',
                      cursor: 'pointer',
                    }"
                    v-for="(item, index) in preferList"
                    :key="index"
                    @click="preferList.splice(index, 1)"
                    title="클릭하여 삭제"
                  >
                    {{ item }} &times;
                  </span>
                </div>

                <input
                  type="text"
                  class="form-control text-3 h-auto py-2"
                  v-model="preferContent"
                  placeholder="쉼표(,)로 구분하여 입력"
                  name="qualification"
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
import { useRoute, useRouter } from 'vue-router'

import { ref, computed, watch, onMounted, reactive } from 'vue'

import { api } from '@/axios.js'

const route = useRoute()
const router = useRouter()
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
const projectSalary = ref('')
const selectedCity = ref(null)
const selectedDistrict = ref(null)
const selectedCityName = computed(() => {
  const raw =
    cities.value.find((city) => city.code === selectedCity.value)?.name || ''
  return raw.replace('전체', '') // '서울전체' → '서울'
})

const selectedDistrictName = computed(() => {
  const raw =
    districts.value.find((district) => district.code === selectedDistrict.value)
      ?.name || ''
  return raw.replace('전체', '') // 필요 시 동일하게 처리
})
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
const preferList = ref([])
const description = ref('')
const notifyEnabled = ref(false)

const modalStore = useModalStore()

// 모달 열려있는 동안 부모 페이지 스크롤 비활성화, 닫히면 다시 활성화
const isOpen = computed(() => modalStore.isOpen)

let prevScrollY = 0

const form = reactive({
  postcode: '',
  address: '',
  detailAddress: '',
  sigungu: '',
  latitude: null,
  longitude: null,
})

const loadKakao = () => {
  return new Promise((resolve, reject) => {
    if (window.kakao?.maps?.services?.Geocoder) return resolve()

    // 이미 로딩 중이면 기다림
    if (!document.querySelector('script[src*="dapi.kakao.com"]')) {
      const script = document.createElement('script')
      script.src =
        'https://dapi.kakao.com/v2/maps/sdk.js?appkey=90610faa13d02b09f83a700d0885a872&libraries=services'
      script.async = false
      document.head.appendChild(script)

      script.onload = () => {
        const start = Date.now()
        const timer = setInterval(() => {
          if (window.kakao?.maps?.services?.Geocoder) {
            clearInterval(timer)
            resolve()
          } else if (Date.now() - start > 7000) {
            clearInterval(timer)
            reject('⏱ Geocoder 로딩 7초 초과 실패')
          }
        }, 100)
      }

      script.onerror = () => reject('❌ Kakao 지도 API 스크립트 로드 실패')
    }
  })
}

const loadDefaultFormData = async () => {
  try {
    const response = await api.$get('/projects/forms')
    cities.value = response.output.cities.map((city) => ({
      code: city.areaSq,
      name: city.areaName,
    }))
    devGrades.value = response.output.devGrades
    educationLevels.value = response.output.educationLevels
    recruitJobs.value = response.output.recruitJobs
    workTypes.value = response.output.workTypes
    skills.value = response.output.skills
  } catch (e) {
    console.error('프로젝트 정보 불러오기 실패 (신규)', e)
  }
}

const loadEditFormData = async (projectSq) => {
  try {
    const { output } = await api.$get(`/projects/forms`, {
      params: { projectSq },
    })
    console.log(output)
    cities.value = output.cities.map((city) => ({
      code: city.areaSq,
      name: city.areaName,
    }))
    devGrades.value = output.devGrades
    educationLevels.value = output.educationLevels
    recruitJobs.value = output.recruitJobs
    workTypes.value = output.workTypes
    skills.value = output.skills
    const exist = output.existProjectVo
    if (!exist) return

    // 이후 프로젝트 상세값 덮어쓰기
    projectTitle.value = exist.projectTtl
    projectSalary.value = exist.projectSalary
    selectedCity.value = exist.parentDistrict.areaSq
    await fetchDistricts(exist.parentDistrict.areaSq)
    selectedDistrict.value = exist.subDistrict.areaSq
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
    preferList.value = exist.preferredEtc
      ? exist.preferredEtc
          .split(',')
          .map((s) => s.trim())
          .filter(Boolean)
      : []
    preferContent.value = ''
    description.value = exist.description
    selectedInterviewTimes.value = Object.entries(exist.interviewTimes).map(
      ([date, times]) => ({
        date,
        times,
      }),
    )
    isInitialLoad.value = false
  } catch (e) {
    console.error('프로젝트 상세 조회 실패 (수정)', e)

    let message = '프로젝트 정보를 불러오는 중 오류가 발생했습니다.'

    alert(message)
    router.push({ name: 'ProjectListPage' })
  }
}

onMounted(async () => {
  if (!projectSq) {
    await loadDefaultFormData() // 신규 등록용
  } else {
    await loadEditFormData(projectSq) // 수정용
  }
})

const fetchDistricts = async (areaCodeSq) => {
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
  } catch (err) {
    console.error('구 정보 불러오기 실패', err)
  }
}

watch([selectedCity, selectedDistrict], async () => {
  const cityName = selectedCityName.value
  const districtName = selectedDistrictName.value
  if (!cityName || !districtName) return

  try {
    await loadKakao()
    const geocoder = new window.kakao.maps.services.Geocoder()
    const fullAddr = `${cityName} ${districtName}`
    geocoder.addressSearch(fullAddr, (result, status) => {
      if (status === window.kakao.maps.services.Status.OK) {
        form.latitude = result[0].y
        form.longitude = result[0].x
        console.log('📍 좌표 변환 완료:', result[0])
      } else {
        console.warn('❌ 좌표 변환 실패:', fullAddr)
      }
    })
  } catch (err) {
    console.error('❌ Geocoder 초기화 실패:', err)
  }
})
const isInitialLoad = ref(true)

watch(selectedCity, async (newCityCode) => {
  await fetchDistricts(newCityCode)

  if (isInitialLoad.value) return // 최초 초기화일 때는 초기화하지 않음

  // 기존 선택된 하위 지역이 목록에 없다면 초기화
  const exists = districts.value.some(
    (district) => district.code === selectedDistrict.value,
  )

  if (!exists) {
    selectedDistrict.value = ''
  }
})
const submitProject = async () => {
  if (preferList.value.length === 0 && preferContent.value.trim() === '') {
    alert('우대 사항을 한 개 이상 입력해주세요.')
    return
  }
  const requestBody = {
    projectId: projectSq ?? null,
    projectTitle: projectTitle.value,
    projectSalary: projectSalary.value,
    projectImageUrl: '',

    subDistrictCode: selectedDistrict.value,
    subDistrictName: selectedDistrictName.value,

    districtLat: form.latitude,
    districtLon: form.longitude,

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

    preference: [...preferList.value, ...preferContent.value.split(',')]
      .map((s) => s.trim())
      .filter(Boolean)
      .join(','),

    description: description.value,

    interviewTime: selectedInterviewTimes.value.flatMap((item) =>
      item.times.map((time) => `${item.date}T${time}`),
    ),

    isNotification: notifyEnabled.value ? 'Y' : 'N',
  }
  console.log(requestBody)
  try {
    const token = getAccessTokenFromCookie()

    const config = {
      headers: {
        Authorization: token ? `Bearer ${token}` : '',
      },
    }
    if (projectSq) {
      await api.$patch('/projects', requestBody, config)
      alert('수정 성공')
    } else {
      await api.$post('/projects', requestBody, config)
      alert('등록 성공')
    }
    router.push({ name: 'ProjectListPage' })
  } catch (error) {
    console.error('프로젝트 등록 실패: ', error)
  }
}

function getAccessTokenFromCookie() {
  const match = document.cookie.match(/(?:^|;\s*)accessToken=([^;]*)/)
  return match ? decodeURIComponent(match[1]) : null
}

watch(preferContent, (newVal) => {
  if (newVal.endsWith(',')) {
    const tags = newVal
      .split(',')
      .map((tag) => tag.trim())
      .filter((tag) => tag.length > 0 && !preferList.value.includes(tag))

    preferList.value.push(...tags)
    preferContent.value = ''
  }
})

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
    selectedSkills: selectedSkills.value,
  })
}
const openPreferSkillModal = () => {
  modalStore.openModal(SkillSelectModal, {
    onConfirm: onPreferSkillsConfirmed,
    skills: skills.value,
    selectedSkills: selectedPreferSkills.value,
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
