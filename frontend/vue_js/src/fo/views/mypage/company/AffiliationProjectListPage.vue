<template>
  <div class="container py-5 mt-3">
    <div class="row">
      <div class="col">
        <h1 class="font-weight-normal text-10 mb-20">
          <strong>프로젝트 목록</strong>
        </h1>
      </div>
    </div>

    <!-- 🔽 필터 UI 추가 영역 -->
    <div class="row align-items-center mt-3 mb-2">
      <!-- 좌측 토글 버튼 -->
      <div class="col-md-6 d-flex gap-2">
        <button
          v-for="filter in filters"
          :key="filter.type"
          class="btn btn-primary fw-bold px-4 py-2 d-flex align-items-center gap-2 fs-6"
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
      <div class="col-md-6 d-flex justify-content-end gap-2">
        <select v-model="searchType" class="form-select form-select-sm w-auto">
          <option
            v-for="option in searchOptions"
            :key="option.value"
            :value="option.value"
          >
            {{ option.label }}
          </option>
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
      <div class="col pt-2 mt-1">
        <hr class="my-4" />
      </div>
    </div>

    <div class="row">
      <div class="col">
        <ul
          v-for="(post, index) in posts"
          :key="index"
          class="simple-post-list m-0 position-relative"
        >
          <li style="border-bottom: 1px rgb(230, 230, 230) solid">
            <div class="post-info position-relative">
              <!-- 제목 + 회사명 + 지원상태 버튼 -->
              <div
                class="d-flex justify-content-between align-items-center gap-2"
              >
                <div class="d-flex gap-2">
                  <a @click="goToProjectSpec(post)" href="#" class="text-6 m-0"
                    >{{ post.title }} /</a
                  >
                  <span href="#" class="text-5 m-0">{{ post.company }}</span>
                </div>
                <div class="d-flex gap-2">
                  <span
                    :class="[
                      'btn',
                      post.status === '채용중' ? 'btn-primary' : 'btn-light',
                      'btn-sm',
                    ]"
                  >
                    {{ post.status }}
                    <span
                      v-if="post.status === '채용중'"
                      class="badge bg-white text-primary fw-bold px-2 py-1"
                      >{{ post.dDay }}</span
                    >
                  </span>
                  <a href="#" class="btn btn-outline btn-primary btn-sm"
                    >수정</a
                  >
                  <a href="#" class="btn btn-outline btn-primary btn-sm"
                    >삭제</a
                  >
                </div>
              </div>

              <!-- 등록일자 + 지원자 수 -->
              <div
                class="d-flex justify-content-between align-items-center mt-2"
              >
                <div class="post-meta text-4">
                  <span class="text-dark text-uppercase font-weight-semibold"
                    >등록일자</span
                  >
                  | {{ post.registrationDate }}
                </div>
                <div class="d-flex align-items-center gap-2 text-4">
                  <span class="text-dark text-uppercase font-weight-semibold"
                    >지원자 수</span
                  >
                  | {{ post.applicantCount }}
                  <a
                    @click="openUserApplyModal(post.projectSq)"
                    href="#"
                    class="btn btn-outline btn-primary btn-sm"
                    >지원현황 바로가기</a
                  >
                </div>
              </div>

              <!-- 지원 자격 + 채용기간 -->
              <div
                class="d-flex justify-content-between align-items-center mt-2"
              >
                <div class="post-meta text-4">
                  <span class="text-dark text-uppercase font-weight-semibold"
                    >지원 자격</span
                  >
                  | {{ post.qualification }}
                </div>
                <div class="post-meta text-4">
                  <span class="text-dark text-uppercase font-weight-semibold"
                    >채용기간</span
                  >
                  | {{ post.recruitmentPeriod }}
                </div>
              </div>
            </div>
          </li>
        </ul>

        <!-- 프로젝트 등록하기 버튼 -->
        <div class="d-flex justify-content-end mt-4 mb-5">
          <a href="#" class="btn btn-primary px-4 py-2">프로젝트 등록하기</a>
        </div>

        <!-- 페이징 -->
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
              <a class="page-link" href="#" @click.prevent="changePage(page)">{{
                page
              }}</a>
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
</template>

<script setup>
import { ref } from 'vue'
import { useModalStore } from '../../../stores/modalStore.js'
import PersonalApplyStatusModal from '@/fo/components/mypage/personal/PersonalApplyStatusModal.vue'
import CompanyApplyStatusModal from '@/fo/components/mypage/company/ApplyStatusModal.vue'

import { api } from '@/axios.js'
import { useRouter } from 'vue-router'

const modalStore = useModalStore()
const router = useRouter()

// 필터 상태
const currentFilter = ref('all')
const filters = ref([
  { type: 'all', label: '전체', count: 11 },
  { type: 'recruiting', label: '채용중', count: 5 },
  { type: 'closed', label: '지원 마감', count: 5 },
  { type: 'scheduled', label: '예정', count: 1 },
])

// 검색 상태
const searchType = ref('all')
const searchText = ref('')
const searchOptions = ref([
  { value: 'all', label: '전체' },
  { value: 'title_content', label: '제목 + 내용' },
  { value: 'title', label: '제목' },
  { value: 'content', label: '내용' },
])

// 페이지네이션 상태
const currentPage = ref(1)
const totalPages = ref(3)

// 게시글 데이터
const posts = ref([
  {
    title: '프로젝트 제목',
    projectSq: 17,
    company: 'EST Soft',
    status: '채용중',
    dDay: 'D-5',
    registrationDate: '2025.04.30',
    applicantCount: 50,
    qualification: '서울 / 신입 / 학력무관 / JAVA',
    recruitmentPeriod: '2025.04.15 ~ 2025.05.31',
  },
  {
    title: '프로젝트 제목',
    projectSq: 17,
    company: 'EST Soft',
    status: '채용 예정',
    registrationDate: '2025.04.30',
    applicantCount: 0,
    qualification: '서울 / 신입 / 학력무관 / JAVA',
    recruitmentPeriod: '2025.04.15 ~ 2025.05.31',
  },
  {
    title: '프로젝트 제목',
    projectSq: 17,
    company: 'EST Soft',
    status: '채용 마감',
    registrationDate: '2025.04.30',
    applicantCount: 50,
    qualification: '서울 / 신입 / 학력무관 / JAVA',
    recruitmentPeriod: '2025.04.15 ~ 2025.05.31',
  },
  {
    title: '프로젝트 제목',
    projectSq: 17,
    company: 'EST Soft',
    status: '채용 마감',
    registrationDate: '2025.04.30',
    applicantCount: 50,
    qualification: '서울 / 신입 / 학력무관 / JAVA',
    recruitmentPeriod: '2025.04.15 ~ 2025.05.31',
  },
])

// 메서드
const setFilter = (type) => {
  currentFilter.value = type
  // 필터링 로직 구현
}

const search = () => {
  // 검색 로직 구현
  console.log('검색:', searchType.value, searchText.value)
}

const changePage = (page) => {
  if (page < 1 || page > totalPages.value) return
  currentPage.value = page
  // 페이지 변경 로직 구현
}

const goToProjectSpec = (project) => {
  router.push({
    name: 'CompanyProjectSpec',
    params: {
      project_sq: project.projectSq,
    },
  })
}

function resetModal(component, props = {}) {
  modalStore.modalStack = [] // 기존 스택 초기화
  modalStore.openModal(component, props)
}

const openUserApplyModal = async (projectSq) => {
  const data = await api.$get(`/projects/applications/${projectSq}`)

  const allApplicants = data.output

  const personalApplicants =
    allApplicants.find((g) => g.applicantType === '개인')?.response || []
  const corporateApplicants =
    allApplicants.find((g) => g.applicantType === '기업')?.response || []

  const openPersonalModal = () => {
    resetModal(PersonalApplyStatusModal, {
      size: 'modal-xl',
      applicants: personalApplicants,
      projectSq, // 넘김
      onToggle: openCorporateModal,
    })
  }

  const openCorporateModal = () => {
    resetModal(CompanyApplyStatusModal, {
      size: 'modal-xl',
      applicants: corporateApplicants,
      projectSq, // 넘김
      onToggle: openPersonalModal,
    })
  }

  openPersonalModal()
}
</script>

<style scoped>
.simple-post-list {
  list-style: none;
  padding: 0;
}
</style>
