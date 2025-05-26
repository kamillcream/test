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
                <div class="d-flex gap-2">
                  <a href="#" class="text-6 m-0">{{ item.title }} /</a>
                  <a href="#" class="text-5 m-0">{{ item.company }}</a>
                </div>
                <div class="d-flex gap-2" v-if="item.status === '지원중'">
                  <span class="btn btn-primary btn-sm">지원중</span>
                  <a
                    href="#"
                    class="btn btn-outline btn-primary btn-sm"
                    @click.prevent="removeProject(item.id)"
                    >지원 취소</a
                  >
                </div>
                <div class="d-flex gap-2" v-else-if="item.status === '합격'">
                  <span class="btn btn-light btn-sm">합격</span>
                  <a
                    @click.prevent="openInterviewTimeModal"
                    href="#"
                    class="btn btn-outline btn-primary btn-sm"
                    >인터뷰 일정 선택</a
                  >
                </div>
                <div
                  class="position-relative d-inline-block"
                  v-else-if="item.status === '인터뷰 확정'"
                >
                  <span class="btn btn-light btn-sm interview"
                    >인터뷰 확정</span
                  >
                  <div
                    class="position-absolute bg-white border p-2 rounded shadow-sm text-dark font-weight-semibold"
                    style="
                      bottom: 80%;
                      left: 30%;
                      white-space: nowrap;
                      display: block;
                    "
                  >
                    {{ item.interviewDate }}
                  </div>
                </div>
                <div class="d-flex gap-2" v-else>
                  <span class="btn btn-light btn-sm">불합격</span>
                  <span class="btn btn-light btn-sm">지원 취소</span>
                  <span class="btn btn-light btn-sm">지원 마감</span>
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
                  | {{ item.applyDate }}
                </div>
                <div class="post-meta text-4">
                  <span class="text-dark text-uppercase font-weight-semibold"
                    >지원자 수</span
                  >
                  | {{ item.applicantCount }}
                </div>
              </div>
              <div
                class="d-flex justify-content-between align-items-center mt-2"
              >
                <div class="post-meta text-4">
                  <span class="text-dark text-uppercase font-weight-semibold"
                    >지원 이력서</span
                  >
                  | {{ item.resume }}
                </div>
                <div class="post-meta text-4">
                  <span class="text-dark text-uppercase font-weight-semibold"
                    >열람일자</span
                  >
                  | {{ item.readDate }}
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
import { ref, computed } from 'vue'
import { useModalStore } from '../../../stores/modalStore.js'
import InterviewTimeModal from '@/fo/components/project/InterviewTimeModal.vue'

const searchType = ref('all')
const searchKeyword = ref('')
const currentPage = ref(1)
const itemsPerPage = 4
const currentToggle = ref('all')

const selectedInterviewTimes = ref([])

const modalStore = useModalStore()

const projects = ref([
  {
    id: 1,
    title: '프로젝트 제목',
    company: 'EST Soft',
    status: '지원중',
    applyDate: '2025.04.30',
    applicantCount: 50,
    resume: '안녕하세요. JAVA 개발자입니다.',
    readDate: '2025.05.15',
  },
  {
    id: 2,
    title: '프로젝트 제목',
    company: 'EST Soft',
    status: '합격',
    applyDate: '2025.04.30',
    applicantCount: 50,
    resume: '안녕하세요. JAVA 개발자입니다.',
    readDate: '2025.05.15',
  },
  {
    id: 3,
    title: '프로젝트 제목',
    company: 'EST Soft',
    status: '인터뷰 확정',
    interviewDate: '2025.05.30 16:00 ~ 17:00',
    applyDate: '2025.04.30',
    applicantCount: 50,
    resume: '안녕하세요. JAVA 개발자입니다.',
    readDate: '2025.05.15',
  },
  {
    id: 4,
    title: '프로젝트 제목',
    company: 'EST Soft',
    status: '불합격',
    applyDate: '2025.04.30',
    applicantCount: '미열람',
    resume: '안녕하세요. JAVA 개발자입니다.',
    readDate: '2025.05.15',
  },
])

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
      (p) => p.status === '합격' || p.status === '인터뷰 확정',
    ).length,
  },
  {
    value: 'unread',
    label: '미열람',
    count: projects.value.filter((p) => p.status === '불합격').length,
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
const openInterviewTimeModal = () => {
  modalStore.openModal(InterviewTimeModal, {
    interviewTimes: selectedInterviewTimes.value,
  })
}
</script>

<style scoped>
.simple-post-list {
  list-style: none;
  padding: 0;
}
</style>
