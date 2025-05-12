<template>
  <div class="container py-5 mt-3">
    <!-- 페이지 제목 -->
    <div class="row">
      <div class="col">
        <h1 class="font-weight-normal text-10 mb-20">
          <strong>소속 공고 지원자 현황</strong>
        </h1>
      </div>
    </div>

    <!-- 🔽 필터/검색 UI -->
    <div class="row align-items-center mt-3 mb-2">
      <!-- 좌측: 필터 버튼 -->
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
      <!-- 우측: 검색 -->
      <div class="col-md-6 d-flex justify-content-end gap-2">
        <select v-model="searchType" class="form-select form-select-sm w-auto">
          <option value="all">전체</option>
          <option value="title">제목</option>
          <option value="name">지원자명</option>
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
        <ul class="simple-post-list m-0 position-relative">
          <li
            v-for="applicant in applicants"
            :key="applicant.id"
            style="border-bottom: 1px rgb(230, 230, 230) solid"
          >
            <div class="post-info position-relative">
              <!-- 이름 + 합격/불합격 버튼 -->
              <div
                class="d-flex justify-content-between align-items-center gap-2"
              >
                <div class="d-flex gap-2">
                  <a href="#" class="text-6 m-0">{{ applicant.name }}</a>
                </div>
                <div class="d-flex gap-2">
                  <span
                    :class="[
                      'btn',
                      'btn-outline',
                      'btn-primary',
                      'btn-sm',
                      { 'btn-light': applicant.status !== '합격' },
                    ]"
                    >합격</span
                  >
                  <a
                    href="#"
                    :class="[
                      'btn',
                      'btn-outline',
                      'btn-primary',
                      'btn-sm',
                      { 'btn-light': applicant.status !== '불합격' },
                    ]"
                    >불합격</a
                  >
                </div>
              </div>
              <!-- 경력/열람일자 -->
              <div
                class="d-flex justify-content-between align-items-center mt-2"
              >
                <div class="post-meta text-4">
                  <span class="text-dark text-uppercase font-weight-semibold"
                    >경력</span
                  >
                  | {{ applicant.career }}
                </div>
                <div class="post-meta text-4">
                  <span class="text-dark text-uppercase font-weight-semibold"
                    >열람일자</span
                  >
                  | {{ applicant.readDate }}
                </div>
              </div>
              <!-- 사용 기술/지원일자 -->
              <div
                class="d-flex justify-content-between align-items-center mt-2"
                style="font-size: 16.8px !important"
              >
                <div class="d-flex align-items-center gap-2">
                  <span class="text-dark text-uppercase font-weight-semibold"
                    >사용 기술</span
                  >
                  |
                  <div
                    v-for="skill in applicant.skills"
                    :key="skill.name"
                    class="btn d-flex align-items-center gap-2 border-0"
                  >
                    <img :src="skill.icon" width="20" />
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

        <!-- 페이징 -->
        <div class="mt-5 py-5">
          <ul class="pagination float-end">
            <li class="page-item">
              <a
                class="page-link"
                href="#"
                @click.prevent="changePage(currentPage - 1)"
                ><i class="fas fa-angle-left"></i
              ></a>
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
                ><i class="fas fa-angle-right"></i
              ></a>
            </li>
          </ul>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'

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

const applicants = ref([
  {
    id: 1,
    name: '홍길동',
    status: '합격',
    career: '0년차',
    readDate: '2025.04.18',
    applyDate: '2025.04.15',
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
  },
  {
    id: 2,
    name: '홍길동',
    status: '불합격',
    career: '0년차',
    readDate: '미열람',
    applyDate: '2025.04.15',
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
  },
])

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
</script>

<style scoped>
.simple-post-list {
  list-style: none;
  padding: 0;
}
</style>
