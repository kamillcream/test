<template>
  <div>
    <div class="row">
      <div class="col">
        <h1 class="font-weight-normal text-10 mb-20">
          <strong>소속 공고 지원 현황</strong>
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
          <option value="all">전체</option>
          <option value="title">제목</option>
          <option value="company">회사명</option>
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
            v-for="apply in applies"
            :key="apply.id"
            style="border-bottom: 1px rgb(230, 230, 230) solid"
          >
            <div class="post-info position-relative">
              <!-- 제목 + 회사명 + 지원상태 버튼 -->
              <div
                class="d-flex justify-content-between align-items-center gap-2"
              >
                <div class="d-flex gap-2">
                  <a
                    href="#"
                    class="text-6 m-0"
                    @click.prevent="openDetailModal(apply)"
                    >{{ apply.company }}</a
                  >
                </div>
                <div class="d-flex gap-2">
                  <template v-if="apply.status === '지원중'">
                    <span class="btn btn-primary btn-sm">{{
                      apply.status
                    }}</span>
                    <a
                      href="#"
                      class="btn btn-outline btn-primary btn-sm"
                      @click.prevent="cancelApply(apply.id)"
                      >지원 취소</a
                    >
                  </template>
                  <template v-else>
                    <span class="btn btn-light btn-sm">{{ apply.status }}</span>
                    <span class="btn btn-light btn-sm">{{ apply.result }}</span>
                  </template>
                </div>
              </div>

              <!-- 지원일자 + 지원자 수 -->
              <div
                class="d-flex justify-content-between align-items-center mt-2"
              >
                <div class="post-meta text-4">
                  <span class="text-dark text-uppercase font-weight-semibold"
                    >지원일자</span
                  >
                  | {{ apply.applyDate }}
                </div>
                <div class="post-meta text-4">
                  <span class="text-dark text-uppercase font-weight-semibold"
                    >지원자 수</span
                  >
                  | {{ apply.applicantCount }}
                </div>
              </div>
              <!-- 지원 이력서 + 열람일자 -->
              <div
                class="d-flex justify-content-between align-items-center mt-2"
              >
                <div class="post-meta text-4">
                  <span class="text-dark text-uppercase font-weight-semibold"
                    >지원 이력서</span
                  >
                  | {{ apply.resumeTitle }}
                </div>
                <div class="post-meta text-4">
                  <span class="text-dark text-uppercase font-weight-semibold"
                    >열람일자</span
                  >
                  | {{ apply.readDate }}
                </div>
              </div>
            </div>
          </li>
        </ul>

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
import { useModalStore } from '@/fo/stores/modalStore'
import AffiliationRequestDetailModal from '@/fo/components/mypage/personal/AffiliationRequestDetailModal.vue'

const modalStore = useModalStore()

const currentFilter = ref('all')
const searchType = ref('all')
const searchText = ref('')
const currentPage = ref(1)
const totalPages = ref(3)

const filters = ref([
  { type: 'all', label: '전체', count: 10 },
  { type: 'read', label: '열람', count: 5 },
  { type: 'unread', label: '미열람', count: 5 },
])

const applies = ref([
  {
    id: 1,
    company: 'EST Soft',
    status: '지원중',
    result: '',
    applyDate: '2025.04.30',
    applicantCount: 50,
    resumeTitle: '안녕하세요. JAVA 개발자입니다.',
    readDate: '2025.05.15',
  },
  {
    id: 2,
    company: 'EST Soft',
    status: '합격',
    result: '불합격',
    applyDate: '2025.04.30',
    applicantCount: '미열람',
    resumeTitle: '안녕하세요. JAVA 개발자입니다.',
    readDate: '2025.05.15',
  },
  {
    id: 3,
    company: 'EST Soft',
    status: '합격',
    result: '불합격',
    applyDate: '2025.04.30',
    applicantCount: '미열람',
    resumeTitle: '안녕하세요. JAVA 개발자입니다.',
    readDate: '2025.05.15',
  },
  {
    id: 4,
    company: 'EST Soft',
    status: '합격',
    result: '불합격',
    applyDate: '2025.04.30',
    applicantCount: '미열람',
    resumeTitle: '안녕하세요. JAVA 개발자입니다.',
    readDate: '2025.05.15',
  },
])

function setFilter(type) {
  currentFilter.value = type
  // 필터링 로직 구현
}

function search() {
  // 검색 로직 구현
  console.log('검색:', searchType.value, searchText.value)
}

function cancelApply(id) {
  // 지원 취소 로직 구현
  console.log('지원 취소:', id)
}

function changePage(page) {
  if (page < 1 || page > totalPages.value) return
  currentPage.value = page
  // 페이지 변경 로직 구현
}

function openDetailModal(apply) {
  modalStore.openModal(AffiliationRequestDetailModal, {
    size: ' ',
    applyData: apply,
  })
}
</script>

<style scoped>
.simple-post-list {
  list-style: none;
  padding: 0;
}
</style>
