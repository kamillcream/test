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
                  <a href="#" class="text-6 m-0">{{ item.projectTitle }} /</a>
                  <a href="#" class="text-5 m-0">{{ item.companyTitle }}</a>
                </div>

                <!-- 오른쪽: 상태 버튼들 -->
                <div class="d-flex gap-2">
                  <template v-if="item.applicantType === '지원중'">
                    <span class="btn btn-primary btn-sm">지원중</span>
                    <a
                      href="#"
                      class="btn btn-outline btn-primary btn-sm"
                      @click.prevent="removeProject(item.id)"
                      >지원 취소</a
                    >
                  </template>

                  <template v-else-if="item.applicantType === '합격'">
                    <span class="btn btn-light btn-sm">합격</span>
                    <a
                      @click.prevent="
                        fetchAvailableInterviewTimes(
                          item.projectSq,
                          item.applicationSq,
                        )
                      "
                      href="#"
                      class="btn btn-outline btn-primary btn-sm"
                      >인터뷰 일정 선택</a
                    >
                  </template>

                  <template v-else-if="item.applicantType === '인터뷰 확정'">
                    <div class="position-relative d-inline-block">
                      <span class="btn btn-light btn-sm interview"
                        >인터뷰 확정</span
                      >
                      <div
                        class="position-absolute bg-white border p-2 rounded shadow-sm text-dark font-weight-semibold"
                        style="bottom: 80%; left: 30%; white-space: nowrap"
                      >
                        {{ item.interviewDate }}
                      </div>
                    </div>
                  </template>

                  <template v-else-if="item.applicantType === '불합격'">
                    <span class="btn btn-light btn-sm">불합격</span>
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
                  | {{ item.resumeTitle }}
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

import { api } from '@/axios.js'

const searchType = ref('all')
const searchKeyword = ref('')
const currentPage = ref(1)
const itemsPerPage = 4
const currentToggle = ref('all')

const selectedInterviewTimes = ref([])

const modalStore = useModalStore()

const projects = ref([])

onMounted(async () => {
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

const toggleTypes = computed(() => [
  {
    value: 'all',
    label: '전체',
    count: projects.value.length,
  },
  {
    value: 'read',
    label: '열람',
    count: projects.value.filter(
      (p) =>
        p.applicantType === '합격' ||
        p.applicantType === '인터뷰 확정' ||
        p.applicantType === '불합격',
    ).length,
  },
  {
    value: 'unread',
    label: '미열람',
    count: projects.value.filter((p) => p.applicantType === '지원중').length,
  },
])

const filteredProjects = computed(() => {
  let list = projects.value
  if (currentToggle.value === 'read') {
    list = list.filter((p) => p.status === '합격' || p.status === '인터뷰 확정')
  } else if (currentToggle.value === 'unread') {
    list = list.filter((p) => p.status === '불합격')
  }
  if (searchKeyword.value) {
    if (searchType.value === 'all') {
      list = list.filter(
        (p) =>
          p.title.includes(searchKeyword.value) ||
          p.company.includes(searchKeyword.value),
      )
    } else if (searchType.value === 'title') {
      list = list.filter((p) => p.title.includes(searchKeyword.value))
    } else if (searchType.value === 'company') {
      list = list.filter((p) => p.company.includes(searchKeyword.value))
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
}
function removeProject(id) {
  projects.value = projects.value.filter((p) => p.id !== id)
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
</script>

<style scoped>
.simple-post-list {
  list-style: none;
  padding: 0;
}
</style>
