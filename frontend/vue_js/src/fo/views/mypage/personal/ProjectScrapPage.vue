<template>
  <div>
    <div class="row">
      <div class="col">
        <h1 class="font-weight-normal text-10 mb-20">
          <strong>프로젝트 스크랩 내역</strong>
        </h1>
      </div>
    </div>

    <!-- 필터 UI -->
    <div class="row align-items-center mt-3 mb-2">
      <div class="col-md-12 d-flex justify-content-end gap-2">
        <select v-model="searchType" class="form-select form-select-sm w-auto">
          <option value="전체">전체</option>
          <option value="제목 + 회사명">제목 + 회사명</option>
          <option value="제목">제목</option>
          <option value="회사명">회사명</option>
        </select>
        <input
          v-model="searchKeyword"
          @keyup.enter="handleSearch"
          type="text"
          class="form-control form-control-sm w-auto"
          placeholder="검색어 입력"
        />

        <button class="btn btn-primary btn-sm" @click="handleSearch">
          검색
        </button>
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
            v-for="item in pagedScraps"
            :key="item.projectSq"
            style="border-bottom: 1px rgb(230, 230, 230) solid"
          >
            <div class="post-info position-relative">
              <!-- 제목 + 회사명 + 상태 -->
              <div
                class="d-flex justify-content-between align-items-center gap-2"
              >
                <div class="d-flex gap-2">
                  <a href="#" class="text-6 m-0">{{ item.projectTtl }} /</a>
                  <a href="#" class="text-5 m-0">{{
                    item.company.companyNm
                  }}</a>
                </div>
                <div class="d-flex gap-2 align-items-center">
                  <span
                    :class="[
                      'btn',
                      item.dday <= 0 ? 'btn-primary' : 'btn-light',
                      'btn-sm',
                    ]"
                  >
                    <template v-if="item.dday <= 0">채용중</template>
                    <template v-else>채용 마감</template>

                    <span
                      v-if="item.dday !== null && item.dday <= 0"
                      class="badge bg-white text-primary fw-bold px-2 py-1 ms-1"
                    >
                      D{{ item.dday }}
                    </span>
                  </span>

                  <a
                    href="#"
                    class="btn btn-outline btn-primary btn-sm"
                    @click.prevent="removeScrap(item.projectSq)"
                    >삭제</a
                  >
                </div>
              </div>

              <!-- 지원자 수 + 등록일 -->
              <div
                class="d-flex justify-content-between align-items-center mt-2"
              >
                <div class="post-meta text-4">
                  <span class="text-dark text-uppercase font-weight-semibold"
                    >등록일자</span
                  >
                  | {{ formatDate(item.createdAt) }}
                </div>
                <div class="post-meta text-4">
                  <span class="text-dark text-uppercase font-weight-semibold"
                    >지원자 수</span
                  >
                  | {{ item.candidateCnt }}
                </div>
              </div>

              <!-- 지원 자격 + 기간 -->
              <div
                class="d-flex justify-content-between align-items-center mt-2"
              >
                <div
                  class="post-meta text-4 me-3 flex-grow-1"
                  style="min-width: 0"
                >
                  <div class="mb-1">
                    <span class="text-dark text-uppercase font-weight-semibold"
                      >지원 자격</span
                    >
                    | {{ item.address.parentSigungu }}
                    {{ item.address.sigungu }} / {{ item.developerGrade }} /
                    {{ item.requiredEducation }}
                  </div>

                  <!-- 기술 스택 -->
                  <div class="d-flex flex-wrap gap-2">
                    <span
                      v-for="(skill, index) in item.skillTags"
                      :key="index"
                      class="badge bg-light text-dark px-2 py-1"
                    >
                      <img
                        v-if="generateIconUrl(skill)"
                        :src="generateIconUrl(skill)"
                        width="24"
                        height="24"
                      />
                      {{ skill }}
                    </span>
                  </div>
                </div>

                <div class="post-meta text-4">
                  <span class="text-dark text-uppercase font-weight-semibold"
                    >채용기간</span
                  >
                  | {{ item.recruitStartDt }} ~ {{ item.recruitEndDt }}
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
import { api } from '@/axios'
import { useAlertStore } from '@/fo/stores/alertStore'

const alertStore = useAlertStore()

const scraps = ref([])
const searchType = ref('전체')
const searchKeyword = ref('')
const searchTrigger = ref('')
const currentPage = ref(1)
const itemsPerPage = 4

// 날짜 포맷 함수 (yyyy.MM.dd)
function formatDate(dateStr) {
  if (!dateStr) return ''
  const d = new Date(dateStr)
  const yyyy = d.getFullYear()
  const mm = String(d.getMonth() + 1).padStart(2, '0')
  const dd = String(d.getDate()).padStart(2, '0')
  return `${yyyy}.${mm}.${dd}`
}

// API 호출
async function fetchScraps() {
  try {
    const res = await api.$get('/mypage/projectScrap')
    scraps.value = res.output || []
  } catch (error) {
    console.error('프로젝트 스크랩 조회 실패:', error)
  }
}
onMounted(fetchScraps)

const filteredScraps = computed(() => {
  if (!searchTrigger.value.trim()) return scraps.value

  const keyword = searchTrigger.value.toLowerCase()

  if (searchType.value === '전체' || searchType.value === '제목 + 회사명') {
    return scraps.value.filter(
      (item) =>
        item.projectTtl.toLowerCase().includes(keyword) ||
        item.company.companyNm.toLowerCase().includes(keyword),
    )
  }
  if (searchType.value === '제목') {
    return scraps.value.filter((item) =>
      item.projectTtl.toLowerCase().includes(keyword),
    )
  }
  if (searchType.value === '회사명') {
    return scraps.value.filter((item) =>
      item.company.companyNm.toLowerCase().includes(keyword),
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
  searchTrigger.value = searchKeyword.value
  currentPage.value = 1
}

async function removeScrap(projectSq) {
  try {
    const response = await api.$delete(`/mypage/projectScrap/${projectSq}`)

    if (response.status === 'OK') {
      scraps.value = scraps.value.filter((item) => item.projectSq !== projectSq)
      alertStore.show('스크랩이 삭제되었습니다.', 'success')
    } else {
      alertStore.show('스크랩 삭제에 실패했습니다.', 'danger')
    }
  } catch (error) {
    alertStore.show('스크랩 삭제 중 오류가 발생했습니다.', 'danger')
    console.error(error)
  }
}

function changePage(page) {
  if (page < 1 || page > totalPages.value) return
  currentPage.value = page
}

const generateIconUrl = (name) => {
  const supportedIcons = {
    Java: 'java',
    Python: 'python',
    'Spring Boot': 'spring',
    Django: 'django',
    React: 'react',
    'Vue.js': 'vuejs',
    Docker: 'docker',
    Git: 'git',
    Windows: 'windows8',
    MacOS: 'apple',
    Linux: 'linux',
    MySQL: 'mysql',
    OracleDB: 'oracle',
    MongoDB: 'mongodb',
    MariaDB: 'mariadb',
    Redis: 'redis',
  }

  const mapped = supportedIcons[name]
  if (!mapped) return null

  const fileName =
    name === 'Django' ? `${mapped}-plain.svg` : `${mapped}-original.svg`

  return `https://cdn.jsdelivr.net/gh/devicons/devicon/icons/${mapped}/${fileName}`
}
</script>

<style scoped>
.simple-post-list {
  list-style: none;
  padding-left: 0;
}
</style>
