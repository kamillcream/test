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
                class="btn btn-primary"
                :class="{ active: applicantType === 'personal' }"
                @click="setApplicantType('personal')"
              >
                개인
              </button>
              <button
                type="button"
                class="btn btn-primary btn-outline"
                :class="{ active: applicantType === 'company' }"
                @click="setApplicantType('company')"
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

        <!-- 지원자 목록 -->
        <div class="row">
          <div class="col">
            <ul class="simple-post-list m-0 position-relative">
              <li
                v-for="(applicant, index) in applicants"
                :key="index"
                style="border-bottom: 1px rgb(230, 230, 230) solid"
              >
                <div class="post-info position-relative">
                  <!-- 제목 + 회사명 + 지원상태 버튼 -->
                  <div
                    class="d-flex justify-content-between align-items-center gap-2"
                  >
                    <div class="d-flex gap-2">
                      <a href="#" class="text-6 m-0">{{ applicant.name }} /</a>
                      <a href="#" class="text-5 m-0">{{ applicant.title }}</a>
                    </div>
                    <div class="d-flex gap-2">
                      <template v-if="applicant.status === 'pending'">
                        <span
                          class="btn btn-primary btn-outline btn-sm"
                          @click="handleInterviewRequest(applicant)"
                          >합격 / 인터뷰 요청</span
                        >
                        <span
                          class="btn btn-primary btn-outline btn-sm"
                          @click="handleReject(applicant)"
                          >불합격</span
                        >
                      </template>
                      <template
                        v-else-if="applicant.status === 'interview_requested'"
                      >
                        <span class="btn btn-primary btn-sm"
                          >인터뷰 요청중</span
                        >
                      </template>
                      <template
                        v-else-if="applicant.status === 'interview_confirmed'"
                      >
                        <div class="position-relative d-inline-block">
                          <span class="btn btn-light btn-sm interview"
                            >인터뷰 확정</span
                          >
                          <div
                            class="position-absolute bg-white border p-2 rounded shadow-sm text-dark font-weight-semibold"
                            style="bottom: 80%; left: 30%; white-space: nowrap"
                          >
                            {{ applicant.interviewDate }}
                          </div>
                        </div>
                      </template>
                      <template v-else-if="applicant.status === 'rejected'">
                        <span class="btn btn-light btn-sm">불합격</span>
                        <span class="btn btn-light btn-sm">지원 취소</span>
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
                      | {{ applicant.career }}년차
                    </div>
                    <div class="post-meta text-4">
                      <span
                        class="text-dark text-uppercase font-weight-semibold"
                        >열람일자</span
                      >
                      | {{ applicant.readDate || '미열람' }}
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
                        v-for="skill in applicant.skills"
                        :key="skill.name"
                        class="btn d-flex align-items-center gap-2 border-0"
                      >
                        <img :src="skill.icon" :alt="skill.name" width="20" />
                        {{ skill.name }}
                      </div>
                    </div>
                    <div class="post-meta" style="font-size: 16.8px">
                      <span
                        class="text-dark text-uppercase font-weight-semibold"
                        style="font-size: 16.8px"
                        >지원일자</span
                      >
                      | {{ applicant.applyDate }}
                    </div>
                  </div>
                </div>
              </li>
            </ul>

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
import { ref } from 'vue'
import { useModalStore } from '@/fo/stores/modalStore'

const modalStore = useModalStore()

// 필터 상태
const currentFilter = ref('all')
const searchType = ref('all')
const searchText = ref('')
const applicantType = ref('personal')
const currentPage = ref(1)
const totalPages = ref(3)

// 필터 데이터
const filters = ref([
  { type: 'all', label: '전체', count: 15 },
  { type: 'interview_confirmed', label: '인터뷰 확정', count: 5 },
  { type: 'interview_requested', label: '인터뷰 요청중', count: 5 },
  { type: 'rejected', label: '불합격 / 반려 / 취소', count: 5 },
])

// 지원자 데이터
const applicants = ref([
  {
    name: '홍길동',
    title: '안녕하세요. Java 개발자입니다.',
    status: 'pending',
    career: 0,
    readDate: '미열람',
    skills: [
      {
        name: 'Java',
        icon: 'https://cdn.jsdelivr.net/gh/devicons/devicon/icons/java/java-original.svg',
      },
      {
        name: 'Python',
        icon: 'https://cdn.jsdelivr.net/gh/devicons/devicon/icons/python/python-original.svg',
      },
      {
        name: 'Spring Boot',
        icon: 'https://cdn.jsdelivr.net/gh/devicons/devicon/icons/spring/spring-original.svg',
      },
    ],
    applyDate: '2025.04.15',
  },
  {
    name: '홍길동',
    title: '안녕하세요. Java 개발자입니다.',
    status: 'interview_requested',
    career: 0,
    readDate: '2025.04.30',
    skills: [
      {
        name: 'Java',
        icon: 'https://cdn.jsdelivr.net/gh/devicons/devicon/icons/java/java-original.svg',
      },
      {
        name: 'Python',
        icon: 'https://cdn.jsdelivr.net/gh/devicons/devicon/icons/python/python-original.svg',
      },
      {
        name: 'Spring Boot',
        icon: 'https://cdn.jsdelivr.net/gh/devicons/devicon/icons/spring/spring-original.svg',
      },
    ],
    applyDate: '2025.04.15',
  },
  {
    name: '홍길동',
    title: '안녕하세요. Java 개발자입니다.',
    status: 'interview_confirmed',
    career: 0,
    readDate: '미열람',
    interviewDate: '2025.05.01 16:00 ~ 17:00',
    skills: [
      {
        name: 'Java',
        icon: 'https://cdn.jsdelivr.net/gh/devicons/devicon/icons/java/java-original.svg',
      },
      {
        name: 'Python',
        icon: 'https://cdn.jsdelivr.net/gh/devicons/devicon/icons/python/python-original.svg',
      },
      {
        name: 'Spring Boot',
        icon: 'https://cdn.jsdelivr.net/gh/devicons/devicon/icons/spring/spring-original.svg',
      },
    ],
    applyDate: '2025.04.15',
  },
  {
    name: '홍길동',
    title: '안녕하세요. Java 개발자입니다.',
    status: 'rejected',
    career: 0,
    readDate: '2025.04.01',
    skills: [
      {
        name: 'Java',
        icon: 'https://cdn.jsdelivr.net/gh/devicons/devicon/icons/java/java-original.svg',
      },
      {
        name: 'Python',
        icon: 'https://cdn.jsdelivr.net/gh/devicons/devicon/icons/python/python-original.svg',
      },
      {
        name: 'Spring Boot',
        icon: 'https://cdn.jsdelivr.net/gh/devicons/devicon/icons/spring/spring-original.svg',
      },
    ],
    applyDate: '2025.03.01',
  },
])

// 필터 변경
const setFilter = (type) => {
  currentFilter.value = type
  // TODO: 필터링 로직 구현
}

// 지원자 타입 변경
const setApplicantType = (type) => {
  applicantType.value = type
  // TODO: 지원자 타입에 따른 데이터 필터링
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

// 인터뷰 요청
const handleInterviewRequest = (applicant) => {
  // TODO: 인터뷰 요청 로직 구현
  console.log('인터뷰 요청:', applicant)
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
</style>
