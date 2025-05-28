<template>
  <div class="container py-5 mt-3">
    <div class="row">
      <div class="col">
        <h1 class="font-weight-normal text-10 mb-20">
          <strong>프로젝트 지원 현황</strong>
        </h1>
      </div>
    </div>

    <!-- 🔽 필터 UI 추가 영역 -->
    <div class="row align-items-center mt-3 mb-2">
      <!-- 좌측 토글 버튼 -->
      <div class="col-md-6 d-flex gap-2">
        <button
          v-for="type in toggleTypes"
          :key="type.value"
          class="btn btn-primary fw-bold px-4 py-2 d-flex align-items-center gap-2 fs-6"
          :class="{ active: currentToggle === type.value }"
          @click="setToggle(type.value)"
        >
          {{ type.label }}
          <span class="badge bg-white text-primary fw-bold px-2 py-1">{{
            type.count
          }}</span>
        </button>
      </div>

      <!-- 우측 셀렉트 + 검색 -->
      <div class="col-md-6 d-flex justify-content-end gap-2">
        <select v-model="searchType" class="form-select form-select-sm w-auto">
          <option value="all">전체</option>
          <option value="title">제목</option>
          <option value="company">회사명</option>
        </select>
        <input
          v-model="searchKeyword"
          type="text"
          class="form-control form-control-sm w-auto"
          placeholder="검색어 입력"
        />
        <button class="btn btn-primary btn-sm" @click="handleSearch">
          검색
        </button>
      </div>
    </div>
    <!-- 🔼 필터 UI 끝 -->

    <div class="row">
      <div class="col pt-2 mt-1">
        <hr class="my-4" />
      </div>
    </div>

    <div class="row">
      <div class="col">
        <ul class="simple-post-list m-0 position-relative">
          <li
            v-for="item in pagedProjects"
            :key="item.id"
            style="border-bottom: 1px rgb(230, 230, 230) solid"
          >
            <div class="post-info position-relative">
              <!-- 제목 + 회사명 + 지원상태 버튼 -->
              <div
                class="d-flex justify-content-between align-items-center gap-2"
              >
                <!-- 왼쪽: 제목 / 회사명 -->
                <div class="d-flex gap-2">
                  <a @click="goToProjectSpec(item)" href="#" class="text-6 m-0"
                    >{{ item.projectTitle }} /</a
                  >
                  <span class="text-5 m-0">{{ item.companyTitle }}</span>
                </div>

                <!-- 오른쪽: 상태 버튼들 -->
                <div class="d-flex gap-2">
                  <template v-if="item.applicantType === '지원중'">
                    <span class="btn btn-primary btn-sm">지원중</span>
                    <span
                      class="btn btn-primary btn-outline btn-sm"
                      @click.prevent="
                        cancelApplication('지원취소', item.applicationSq)
                      "
                      >지원취소</span
                    >
                  </template>

                  <template v-else-if="item.applicantType === '합격'">
                    <span class="btn btn-light btn-sm">합격</span>
                  </template>

                  <template v-else-if="item.applicantType === '인터뷰확정'">
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
                        {{ formatDate(item.interviewDt) }}
                      </div>

                      <!-- 인터뷰 확정 버튼 -->
                      <span class="btn btn-light btn-sm interview"
                        >인터뷰 확정</span
                      >
                    </div>
                  </template>

                  <template v-else-if="item.applicantType === '불합격'">
                    <span class="btn btn-light btn-sm">불합격</span>
                  </template>
                  <template v-else-if="item.applicantType === '지원취소'">
                    <span class="btn btn-light btn-sm">지원 취소됨</span>
                  </template>

                  <template v-else-if="item.applicantType === '인터뷰요청중'">
                    <a
                      @click.prevent="
                        fetchAvailableInterviewTimes(
                          item.projectSq,
                          item.applicationSq,
                        )
                      "
                      href="#"
                      class="btn btn-outline btn-primary btn-sm"
                      >인터뷰 요청중</a
                    >
                  </template>

                  <template v-if="item.recruitEnded === true">
                    <span class="btn btn-light btn-sm">지원 마감</span>
                  </template>
                </div>
              </div>

              <!-- 지원일자 + 열람일자 -->
              <div
                class="d-flex justify-content-between align-items-center mt-2"
              >
                <div class="post-meta text-4">
                  <span class="text-dark text-uppercase font-weight-semibold"
                    >지원일자</span
                  >
                  | {{ formatDate(item.appliedDt) }}
                </div>
                <div class="post-meta text-4">
                  <span class="text-dark text-uppercase font-weight-semibold"
                    >지원자 수</span
                  >
                  | {{ item.applicantCnt }}
                </div>
              </div>
              <div
                class="d-flex justify-content-between align-items-center mt-2"
              >
                <div class="post-meta text-4">
                  <span class="text-dark text-uppercase font-weight-semibold"
                    >지원 이력서</span
                  >
                  |
                  <span
                    @click="openResumeDetailModal()"
                    class="text-muted resume-hover"
                  >
                    {{ item.resumeTitle }}
                  </span>
                </div>

                <div class="post-meta text-4">
                  <span class="text-dark text-uppercase font-weight-semibold"
                    >열람일자</span
                  >
                  | {{ formatDate(item.readApplicationDt) }}
                </div>
              </div>
            </div>
          </li>
        </ul>

        <!-- 페이징 -->
        <div class="mt-5 py-5">
          <ul class="pagination float-end">
            <li class="page-item" :class="{ disabled: currentPage === 1 }">
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
              :class="['page-item', { active: page === currentPage }]"
            >
              <a class="page-link" href="#" @click.prevent="changePage(page)">{{
                page
              }}</a>
            </li>
            <li
              class="page-item"
              :class="{ disabled: currentPage === totalPages }"
            >
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
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useModalStore } from '../../../stores/modalStore.js'
import InterviewTimeModal from '@/fo/components/mypage/common/InterviewSelectModal.vue'
import CommonConfirmModal from '@/fo/components/common/CommonConfirmModal.vue'
import ResumeDetailModal from '@/fo/components/mypage/common/ResumeDetailModal.vue'

import { api } from '@/axios.js'

import { useRouter } from 'vue-router'

const router = useRouter()
const searchType = ref('all')
const searchAppliedType = ref('')
const searchKeyword = ref('')
const searchAppliedKeyword = ref('')
const currentPage = ref(1)
const itemsPerPage = 4
const currentToggle = ref('all')

const selectedInterviewTimes = ref([])

const modalStore = useModalStore()

const projects = ref([])

onMounted(async () => {
  fetchApplicationList()
})

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

const fetchApplicationList = async () => {
  try {
    const response = await api.$get(`/projects/applications`, {
      withCredentials: true, // <-- 필수!
    })

    projects.value = response.output
    console.log(projects.value)
  } catch (e) {
    console.error('❌ [catch 블록 진입]', e)

    console.error('프로젝트 상세 정보 불러오기 실패', e)
  }
}

const updateAppStatus = async (status, applicationSq) => {
  try {
    await api.$patch(
      `/projects/applications/${applicationSq}`,
      { status },
      { withCredentials: true },
    )
  } catch (e) {
    console.error('❌ 지원 상태 변경 실패:', e)
  }
}

const goToProjectSpec = (project) => {
  router.push({
    name: 'CompanyProjectSpec',
    params: {
      project_sq: project.projectSq,
    },
  })
}

const cancelApplication = (status, applicationSq) => {
  modalStore.openModal(CommonConfirmModal, {
    title: '프로젝트 지원 취소',
    message: '취소한 프로젝트 내역은 복구할 수 없습니다. 취소하시겠습니까?',
    onConfirm: async () => {
      await updateAppStatus(status, applicationSq) // 1. 상태 변경 완료 대기
      await fetchApplicationList() // 2. 그 후 리스트 갱신
      modalStore.closeModal() // 3. 그리고 모달 닫기
    },
  })
}

const filteredCounts = computed(() => {
  const list = filteredProjects.value
  return {
    all: list.length,
    read: list.filter((p) => p.readApplicationDt !== null).length,
    unread: list.filter((p) => p.readApplicationDt === null).length,
  }
})

const toggleTypes = computed(() => [
  {
    value: 'all',
    label: '전체',
    count: filteredCounts.value.all,
  },
  {
    value: 'read',
    label: '열람',
    count: filteredCounts.value.read,
  },
  {
    value: 'unread',
    label: '미열람',
    count: filteredCounts.value.unread,
  },
])

const filteredProjects = computed(() => {
  let list = projects.value
  if (currentToggle.value === 'read') {
    list = list.filter((p) => p.readApplicationDt !== null)
  } else if (currentToggle.value === 'unread') {
    list = list.filter((p) => p.readApplicationDt === null)
  }

  if (searchAppliedKeyword.value) {
    const keyword = searchAppliedKeyword.value.toLowerCase()
    if (searchAppliedType.value === 'all') {
      list = list.filter(
        (p) =>
          (p.projectTitle || '').toLowerCase().includes(keyword) ||
          (p.companyTitle || '').toLowerCase().includes(keyword),
      )
    } else if (searchAppliedType.value === 'title') {
      list = list.filter((p) =>
        (p.projectTitle || '').toLowerCase().includes(keyword),
      )
    } else if (searchAppliedType.value === 'company') {
      list = list.filter((p) =>
        (p.companyTitle || '').toLowerCase().includes(keyword),
      )
    }
  }

  return list
})

const totalPages = computed(() =>
  Math.ceil(filteredProjects.value.length / itemsPerPage),
)
const pagedProjects = computed(() => {
  const start = (currentPage.value - 1) * itemsPerPage
  return filteredProjects.value.slice(start, start + itemsPerPage)
})

function setToggle(type) {
  currentToggle.value = type
  currentPage.value = 1
}
function handleSearch() {
  currentPage.value = 1
  searchAppliedKeyword.value = searchKeyword.value
  searchAppliedType.value = searchType.value
}

function changePage(page) {
  if (page < 1 || page > totalPages.value) return
  currentPage.value = page
}

const fetchAvailableInterviewTimes = async (projectSq, applicationSq) => {
  try {
    console.log(projectSq, applicationSq)
    const response = await api.$get(
      `/projects/applications/interviews/${projectSq}`,
      { withCredentials: true },
    )

    selectedInterviewTimes.value = response.output

    const confirmed = await openInterviewTimeModal(applicationSq)
    if (confirmed) {
      await fetchApplicationList()
    }

    console.log(response.output)
  } catch (e) {
    console.error('❌ 인터뷰 시간 조회 실패:', e)
  }
}

const openInterviewTimeModal = (applicationSq) => {
  return new Promise((resolve) => {
    modalStore.openModal(InterviewTimeModal, {
      applicationSq,
      interviewTimes: selectedInterviewTimes.value,
      onConfirm: (result) => {
        resolve(result) // 모달 내부에서 확인 눌렀을 때 호출
        modalStore.closeModal()
      },
      onCancel: () => {
        resolve(null)
        modalStore.closeModal()
      },
    })
  })
}

const openResumeDetailModal = () => {
  modalStore.openModal(ResumeDetailModal, {
    size: 'modal-xl',
  })
}
</script>

<style scoped>
.simple-post-list {
  list-style: none;
  padding: 0;
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

.resume-hover {
  cursor: pointer;
}

.resume-hover:hover {
  text-decoration: underline;
}
</style>
