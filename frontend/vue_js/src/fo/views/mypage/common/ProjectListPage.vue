<template>
  <div>
    <ProjectFilterBar
      :localFilters="['서울', '부산', '대구']"
      :careerFilters="['신입', '경력']"
      :jobTypeFilters="['백엔드', '프론트엔드', 'PM', '디자이너']"
      @update="updateFilters"
    />
    <div class="container py-4">
      <div class="d-flex justify-content-end mb-3">
        <button class="btn btn-rounded btn-primary me-2" @click="fetchProjects">
          검색
        </button>
        <a href="#" class="btn btn-rounded btn-light">등록하기</a>
      </div>
      <ProjectCardGroup :projects="projects" />
      <!-- 페이지네이션 -->
      <div>
        <CommonPagination
          :currentPage="currentPage"
          :totalPages="totalPages"
          @update:currentPage="currentPage = $event"
        />
      </div>
    </div>
  </div>
</template>
<script setup>
import ProjectFilterBar from '@/fo/components/common/ProjectFilterBar.vue'
import ProjectCardGroup from '@/fo/components/project/ProjectCardGroup.vue'
import CommonPagination from '@/fo/components/common/CommonPagination.vue'
import { ref, watch, onMounted } from 'vue'

import { api } from '@/axios.js'
const filters = ref({
  addressCodeSq: null,
  projectDeveloperGradeCd: null,
  sortBy: 'project_start_dt',
  sortOrder: 'desc',
  searchKeyword: '',
  searchType: '프로젝트명',
  offset: 1,
  size: 5,
})

const currentPage = ref(1)
const totalPages = ref(1)

const projects = ref([])

onMounted(async () => {
  fetchProjects()
})

watch(currentPage, (newPage) => {
  filters.value.offset = newPage
  fetchProjects()
})

const fetchProjects = async () => {
  try {
    const params = { ...filters.value }
    const response = await api.$get('/projects', { params })
    projects.value = response.output.projects

    // ✅ totalCount가 응답에 있어야 정확한 페이지네이션 계산 가능
    const totalCount = response.totalCount ?? 0 // totalCount가 반드시 있어야 함
    totalPages.value = Math.max(1, Math.ceil(totalCount / filters.value.size))
  } catch (e) {
    console.error('프로젝트 정보 불러오기 실패', e)
  }
}
const updateFilters = (updated) => {
  filters.value = { ...filters.value, ...updated }
  currentPage.value = 1 // 필터 바꾸면 1페이지부터
  fetchProjects()
}
</script>
<style lang=""></style>
