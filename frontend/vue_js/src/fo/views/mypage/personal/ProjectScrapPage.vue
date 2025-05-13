<template>
  <div class="container py-5 mt-3">
    <div class="row">
      <div class="col">
        <h1 class="font-weight-normal text-10 mb-20">
          <strong>스크랩 내역</strong>
        </h1>
      </div>
    </div>

    <!-- 🔽 필터 UI 추가 영역 -->
    <div class="row align-items-center mt-3 mb-2">
      <div class="col-md-12 d-flex justify-content-end gap-2">
        <select v-model="searchType" class="form-select form-select-sm w-auto">
          <option selected>전체</option>
          <option>제목 + 내용</option>
          <option>제목</option>
          <option>내용</option>
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
            v-for="item in pagedScraps"
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
                <div class="d-flex gap-2">
                  <span
                    :class="[
                      'btn',
                      item.status === '채용중' ? 'btn-primary' : 'btn-light',
                      'btn-sm',
                    ]"
                  >
                    {{ item.status }}
                    <span
                      v-if="item.dDay"
                      class="badge bg-white text-primary fw-bold px-2 py-1"
                      >{{ item.dDay }}</span
                    >
                  </span>
                  <a
                    href="#"
                    class="btn btn-outline btn-primary btn-sm"
                    @click.prevent="removeScrap(item.id)"
                    >삭제</a
                  >
                </div>
              </div>
              <!-- 지원일자 + 열람일자 -->
              <div
                class="d-flex justify-content-between align-items-center mt-2"
              >
                <div class="post-meta text-4">
                  <span class="text-dark text-uppercase font-weight-semibold"
                    >등록일자</span
                  >
                  | {{ item.registerDate }}
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
                    >지원 자격</span
                  >
                  | {{ item.requirements }}
                </div>
                <div class="post-meta text-4">
                  <span class="text-dark text-uppercase font-weight-semibold"
                    >채용기간</span
                  >
                  | {{ item.period }}
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

const searchType = ref('전체')
const searchKeyword = ref('')
const currentPage = ref(1)
const itemsPerPage = 4

const scraps = ref([
  {
    id: 1,
    title: '프로젝트 제목',
    company: 'EST Soft',
    status: '채용중',
    dDay: 'D-5',
    registerDate: '2025.04.30',
    applicantCount: 50,
    requirements: '서울 / 신입 / 학력무관 / JAVA',
    period: '2025.04.15 ~ 2025.05.31',
  },
  {
    id: 2,
    title: '프로젝트 제목',
    company: 'EST Soft',
    status: '채용 예정',
    registerDate: '2025.04.30',
    applicantCount: 0,
    requirements: '서울 / 신입 / 학력무관 / JAVA',
    period: '2025.04.15 ~ 2025.05.31',
  },
  {
    id: 3,
    title: '프로젝트 제목',
    company: 'EST Soft',
    status: '채용 마감',
    registerDate: '2025.04.30',
    applicantCount: 50,
    requirements: '서울 / 신입 / 학력무관 / JAVA',
    period: '2025.04.15 ~ 2025.05.31',
  },
  {
    id: 4,
    title: '프로젝트 제목',
    company: 'EST Soft',
    status: '채용 마감',
    registerDate: '2025.04.30',
    applicantCount: 50,
    requirements: '서울 / 신입 / 학력무관 / JAVA',
    period: '2025.04.15 ~ 2025.05.31',
  },
])

const filteredScraps = computed(() => {
  if (!searchKeyword.value) return scraps.value
  if (searchType.value === '전체' || searchType.value === '제목 + 내용') {
    return scraps.value.filter(
      (s) =>
        s.title.includes(searchKeyword.value) ||
        s.company.includes(searchKeyword.value) ||
        s.requirements.includes(searchKeyword.value),
    )
  } else if (searchType.value === '제목') {
    return scraps.value.filter((s) => s.title.includes(searchKeyword.value))
  } else if (searchType.value === '내용') {
    return scraps.value.filter((s) =>
      s.requirements.includes(searchKeyword.value),
    )
  }
  return scraps.value
})

const totalPages = computed(() =>
  Math.ceil(filteredScraps.value.length / itemsPerPage),
)
const pagedScraps = computed(() => {
  const start = (currentPage.value - 1) * itemsPerPage
  return filteredScraps.value.slice(start, start + itemsPerPage)
})

function handleSearch() {
  currentPage.value = 1
}
function removeScrap(id) {
  scraps.value = scraps.value.filter((s) => s.id !== id)
}
function changePage(page) {
  if (page < 1 || page > totalPages.value) return
  currentPage.value = page
}
</script>

<style scoped>
.simple-post-list {
  list-style: none;
  padding: 0;
}
</style>
