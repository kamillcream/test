<template>
  <div class="modal-content">
    <div class="modal-header">
      <h3 class="modal-title">지원 현황</h3>
      <button
        type="button"
        class="btn-close"
        @click="closeModal"
        aria-hidden="true"
      >
        ×
      </button>
    </div>
    <div
      class="modal-body"
      style="max-height: 80vh; overflow-y: auto; padding: 0"
    >
      <div class="container py-1 mt-3">
        <div class="row">
          <div class="col">
            <h1 class="font-weight-normal text-10 mb-20">
              <strong>프로젝트 Title</strong>
            </h1>
          </div>
        </div>

        <!-- 필터 UI -->
        <div class="row align-items-center mt-3 mb-2">
          <!-- 좌측 토글 버튼 -->
          <div class="col-md-8 d-flex gap-2">
            <button
              v-for="filter in filters"
              :key="filter.type"
              class="btn btn-primary fw-bold px-4 py-2 d-flex align-items-center gap-2 fs-6 btn-sm"
              :class="{ active: currentFilter === filter.type }"
              @click="setFilter(filter.type)"
            >
              {{ filter.label }}
              <span class="badge bg-white text-primary fw-bold px-2 py-1">{{
                filter.count
              }}</span>
            </button>
          </div>

          <!-- 우측 셀렉트 + 검색 -->
          <div class="col-md-4 d-flex justify-content-end gap-2">
            <select
              v-model="searchType"
              class="form-select form-select-sm w-auto"
            >
              <option value="all">전체</option>
              <option value="name">이름</option>
              <option value="skills">사용 기술</option>
            </select>
            <input
              v-model="searchText"
              type="text"
              class="form-control form-control-sm w-auto"
              placeholder="검색어 입력"
            />
            <button class="btn btn-primary btn-sm" @click="search">검색</button>
          </div>
        </div>

        <div class="row">
          <div class="col-12 d-flex justify-content-end pt-2 mt-1">
            <div class="btn-group" role="group" aria-label="개인 기업 토글">
              <button
                type="button"
                class="btn btn-primary btn-outline"
                :class="{ active: applicantType === 'personal' }"
                @click="toggleToPersonal()"
              >
                개인
              </button>
              <button
                type="button"
                class="btn btn-primary"
                :class="{ active: applicantType === 'company' }"
              >
                기업
              </button>
            </div>
          </div>
        </div>

        <div class="row">
          <div class="col" style="padding: 0">
            <hr class="my-4" />
          </div>
        </div>

        <!-- 기업별 지원자 목록 -->
        <div class="row">
          <div class="col">
            <div
              v-for="(company, index) in companies"
              :key="index"
              class="row mb-3"
            >
              <div class="col">
                <!-- 회사 정보 및 토글 버튼 -->
                <button
                  class="btn w-100 text-start d-flex justify-content-between align-items-center px-3 py-2 bg-grey text-dark border"
                  type="button"
                  data-bs-toggle="collapse"
                  :data-bs-target="'#collapse-company-' + index"
                  aria-expanded="true"
                  :aria-controls="'collapse-company-' + index"
                  style="font-size: 1.5rem; border-color: black"
                >
                  <div>
                    <strong>{{ company.name }}</strong>
                  </div>
                  <i class="fas fa-chevron-down"></i>
                </button>

                <!-- 토글로 열고 닫을 지원자 정보 영역 -->
                <div
                  class="collapse show mt-2"
                  :id="'collapse-company-' + index"
                >
                  <!-- 지원자 목록 -->
                  <ul class="simple-post-list m-0 position-relative">
                    <li
                      v-for="applicant in applicants"
                      :key="applicant.applicationSq"
                      style="border-bottom: 1px rgb(230, 230, 230) solid"
                    >
                      <div class="post-info position-relative">
                        <!-- 제목 + 회사명 + 지원상태 버튼 -->
                        <div
                          class="d-flex justify-content-between align-items-center gap-2"
                        >
                          <div class="d-flex gap-2">
                            <a href="#" class="text-6 m-0"
                              >{{ applicant.nameTitleVo.resumeNm }} /</a
                            >
                            <a href="#" class="text-5 m-0">{{
                              applicant.nameTitleVo.resumeTtl
                            }}</a>
                          </div>
                          <div class="d-flex gap-2">
                            <template
                              v-if="applicant.appStatusVo.appStatus === '합격'"
                            >
                              <span
                                @click.prevent="
                                  fetchAvailableInterviewTimes(
                                    applicant.applicationSq,
                                  )
                                "
                                href="#"
                                class="btn btn-outline btn-primary btn-sm"
                                >인터뷰 요청</span
                              >
                            </template>
                            <template
                              v-else-if="
                                applicant.appStatusVo.appStatus === '불합격'
                              "
                            >
                              <span
                                class="btn btn-primary btn-outline btn-sm"
                                @click="handleReject(applicant)"
                                >불합격</span
                              >
                            </template>

                            <template
                              v-if="
                                applicant.appStatusVo.appStatus === '지원중'
                              "
                            >
                              <span
                                @click.prevent="
                                  updateAppStatus(
                                    '인터뷰요청중',
                                    applicant.applicationSq,
                                  )
                                "
                                class="btn btn-outline btn-primary btn-sm"
                              >
                                인터뷰 요청
                              </span>
                            </template>
                            <template
                              v-else-if="
                                applicant.appStatusVo.appStatus ===
                                '인터뷰 요청 중'
                              "
                            >
                              <span class="btn btn-primary btn-sm"
                                >인터뷰 요청중</span
                              >
                            </template>
                            <template
                              v-else-if="
                                applicant.appStatusVo.appStatus ===
                                '인터뷰 확정'
                              "
                            >
                              <div
                                class="interview-wrapper position-relative d-inline-block"
                              >
                                <!-- 툴팁 -->
                                <div
                                  class="interview-tooltip position-absolute bg-white border p-2 rounded shadow-sm text-dark font-weight-semibold"
                                  style="
                                    bottom: 80%;
                                    left: 50%;
                                    transform: translateX(-60%);
                                    white-space: nowrap;
                                  "
                                >
                                  {{
                                    formatDate(
                                      applicant.appStatusVo.interviewDt,
                                    )
                                  }}
                                </div>

                                <!-- 인터뷰 확정 버튼 -->
                                <span class="btn btn-light btn-sm interview"
                                  >인터뷰 확정</span
                                >
                              </div>
                            </template>
                            <template
                              v-else-if="applicant.status === 'rejected'"
                            >
                              <span class="btn btn-light btn-sm">불합격</span>
                              <span class="btn btn-light btn-sm"
                                >지원 취소</span
                              >
                            </template>
                          </div>
                        </div>

                        <!-- 경력 + 열람일자 -->
                        <div
                          class="d-flex justify-content-between align-items-center mt-2"
                        >
                          <div class="post-meta text-4">
                            <span
                              class="text-dark text-uppercase font-weight-semibold"
                              >경력</span
                            >
                            | {{ applicant.careerYear }}년차
                          </div>
                          <div class="post-meta text-4">
                            <span
                              class="text-dark text-uppercase font-weight-semibold"
                              >열람일자</span
                            >
                            |
                            {{ applicant.appStatusVo.readResumeDt || '미열람' }}
                          </div>
                        </div>

                        <!-- 사용 기술 + 지원일자 -->
                        <div
                          class="d-flex justify-content-between align-items-center mt-2"
                          style="font-size: 16.8px !important"
                        >
                          <div class="d-flex align-items-center gap-2">
                            <span
                              class="text-dark text-uppercase font-weight-semibold"
                              >사용 기술</span
                            >
                            |
                            <div
                              v-for="skill in applicant.skillNames"
                              :key="skill"
                              class="btn d-flex align-items-center gap-2 border-0"
                            >
                              <img
                                :src="generateIconUrl(skill)"
                                :alt="skill"
                                width="24"
                                height="24"
                              />
                              <span>{{ skill }}</span>
                            </div>
                          </div>
                          <div class="post-meta" style="font-size: 16.8px">
                            <span
                              class="text-dark text-uppercase font-weight-semibold"
                              style="font-size: 16.8px"
                              >지원일자</span
                            >
                            | {{ applicant.appStatusVo.appDt }}
                          </div>
                        </div>
                      </div>
                    </li>
                  </ul>
                </div>
              </div>
            </div>

            <!-- 페이지네이션 -->
            <div class="mt-5 py-5">
              <ul class="pagination float-end">
                <li class="page-item">
                  <a
                    class="page-link"
                    href="#"
                    @click.prevent="changePage(currentPage - 1)"
                  >
                    <i class="fas fa-angle-left"></i>
                  </a>
                </li>
                <li
                  v-for="page in totalPages"
                  :key="page"
                  class="page-item"
                  :class="{ active: currentPage === page }"
                >
                  <a
                    class="page-link"
                    href="#"
                    @click.prevent="changePage(page)"
                    >{{ page }}</a
                  >
                </li>
                <li class="page-item">
                  <a
                    class="page-link"
                    href="#"
                    @click.prevent="changePage(currentPage + 1)"
                  >
                    <i class="fas fa-angle-right"></i>
                  </a>
                </li>
              </ul>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="modal-footer">
      <button type="button" class="btn btn-light" @click="closeModal">
        닫기
      </button>
    </div>
  </div>
</template>

<script setup>
import { ref, defineProps, computed } from 'vue'
import { useModalStore } from '@/fo/stores/modalStore'
import InterviewTimeModal from '@/fo/components/mypage/common/InterviewSelectModal.vue'

import { api } from '@/axios.js'

const modalStore = useModalStore()

// 필터 상태
const currentFilter = ref('all')
const searchType = ref('all')
const searchText = ref('')
const applicantType = ref('company')
const currentPage = ref(1)
const totalPages = ref(3)

const selectedInterviewTimes = ref([])

const companies = computed(() => {
  const grouped = {}

  props.applicants.forEach((applicant) => {
    const name = applicant.companyNm || '미정'
    if (!grouped[name]) {
      grouped[name] = []
    }
    grouped[name].push(applicant)
  })

  return Object.entries(grouped).map(([name, applicants]) => ({
    name,
    applicants,
  }))
})

// 필터 데이터
const filters = ref([
  { type: 'all', label: '전체', count: 15 },
  { type: 'interview_confirmed', label: '인터뷰 확정', count: 5 },
  { type: 'interview_requested', label: '인터뷰 요청중', count: 5 },
  { type: 'rejected', label: '불합격 / 반려 / 취소', count: 5 },
])

const props = defineProps({
  applicants: Array,
  projectSq: Number,
  onToggle: Function,
})

console.log('기업')
console.log(props.applicants)

const toggleToPersonal = () => {
  props.onToggle?.()
}

const fetchAvailableInterviewTimes = async (applicationSq) => {
  try {
    const response = await api.$get(
      `/projects/applications/interviews/${props.projectSq}`,
      { withCredentials: true },
    )

    selectedInterviewTimes.value = response.output
    openInterviewTimeModal(applicationSq)
    console.log(response.output)
  } catch (e) {
    console.error('❌ 인터뷰 시간 조회 실패:', e)
  }
}

const openInterviewTimeModal = (applicationSq) => {
  modalStore.openModal(InterviewTimeModal, {
    interviewTimes: selectedInterviewTimes.value,
    applicationSq, // 전달
  })
}

const updateAppStatus = async (status, applicationSq) => {
  try {
    await api.$patch(
      `/projects/applications/${applicationSq}`,
      { status },
      { withCredentials: true },
    )
    const target = props.applicants.find(
      (app) => app.applicationSq === applicationSq,
    )
    if (target) {
      target.appStatusVo.appStatus = '인터뷰 요청 중'
    }
  } catch (e) {
    console.error('❌ 지원 상태 변경 실패:', e)
  }
}

// 필터 변경
const setFilter = (type) => {
  currentFilter.value = type
  // TODO: 필터링 로직 구현
}

// 검색
const search = () => {
  // TODO: 검색 로직 구현
  console.log('검색:', searchType.value, searchText.value)
}

// 페이지 변경
const changePage = (page) => {
  if (page < 1 || page > totalPages.value) return
  currentPage.value = page
  // TODO: 페이지 데이터 로드
}

// 불합격 처리
const handleReject = (applicant) => {
  // TODO: 불합격 처리 로직 구현
  console.log('불합격 처리:', applicant)
}

// 모달 닫기
const closeModal = () => {
  modalStore.closeModal()
}

function formatDate(dateString) {
  if (!dateString) return ''
  const date = new Date(dateString)
  const year = date.getFullYear()
  const month = String(date.getMonth() + 1).padStart(2, '0')
  const day = String(date.getDate()).padStart(2, '0')
  const hours = String(date.getHours()).padStart(2, '0')
  const minutes = String(date.getMinutes()).padStart(2, '0')
  return `${year}-${month}-${day} ${hours}:${minutes}`
}

const generateIconUrl = (name) => {
  const exceptionList = [
    '전자정부 프레임워크',
    'myBatis',
    'Notepad++',
    'PyCharm',
    'Sublime Text',
  ]
  if (exceptionList.includes(name)) return null

  const processed = name
    .toLowerCase()
    .replace('#', 'sharp')
    .replace('++', 'plusplus')

  return `https://cdn.jsdelivr.net/gh/devicons/devicon/icons/${processed}/${processed}-original.svg`
}
</script>

<style scoped>
.modal-content {
  border-radius: 8px;
}

.btn-rounded {
  border-radius: 20px;
}

.btn-3d {
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.gap-2 {
  gap: 0.5rem;
}

.interview:hover div {
  display: block;
}

.interview div {
  display: none;
}

.interview-tooltip {
  bottom: 80%;
  left: 30%;
  white-space: nowrap;
  display: none;
}

.interview-wrapper:hover .interview-tooltip {
  display: block;
}

.bg-color-grey {
  background-color: #f5f5f5;
}

.simple-post-list {
  list-style: none;
  padding: 0;
}

.simple-post-list li {
  padding: 1rem 0;
}

.text-6 {
  font-size: 1.2rem;
}

.text-5 {
  font-size: 1.1rem;
}

.text-4 {
  font-size: 0.9rem;
}

.bg-grey {
  background-color: #f8f9fa;
}
</style>
