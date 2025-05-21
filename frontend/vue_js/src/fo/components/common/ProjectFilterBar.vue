<template>
  <div
    class="filter-bar border rounded p-3 d-flex align-items-center gap-3 flex-wrap"
    style="max-width: 880px; margin: 0 auto"
  >
    <div class="dropdown">
      <button
        class="btn btn-outline-secondary dropdown-toggle"
        type="button"
        data-bs-toggle="dropdown"
      >
        지역
      </button>
      <ul class="dropdown-menu">
        <li v-for="local in localOptions" :key="local.areaSq">
          <a
            class="dropdown-item"
            href="#"
            @click.prevent="updateRegion(local.areaSq)"
          >
            {{ local.areaName }}
          </a>
        </li>
      </ul>
    </div>

    <div class="dropdown">
      <button
        class="btn btn-outline-secondary dropdown-toggle"
        type="button"
        data-bs-toggle="dropdown"
      >
        경력
      </button>
      <ul class="dropdown-menu">
        <li v-for="career in careerOptions" :key="career.common_code_sq">
          <a
            class="dropdown-item"
            href="#"
            @click.prevent="updateCareer(career.common_code_sq)"
          >
            {{ career.common_code_nm }}
          </a>
        </li>
      </ul>
    </div>

    <div class="dropdown">
      <button
        class="btn btn-outline-primary dropdown-toggle"
        type="button"
        data-bs-toggle="dropdown"
      >
        학력
      </button>
      <ul class="dropdown-menu">
        <li
          v-for="education in educationOptions"
          :key="education.common_code_sq"
        >
          <a
            class="dropdown-item"
            href="#"
            @click.prevent="updateEducation(education.common_code_sq)"
          >
            {{ education.common_code_nm }}
          </a>
        </li>
      </ul>
    </div>

    <div class="dropdown">
      <button
        class="btn btn-outline-primary dropdown-toggle"
        type="button"
        data-bs-toggle="dropdown"
      >
        직종
      </button>
      <ul class="dropdown-menu">
        <li v-for="job in jobTypeOptions" :key="job.common_code_sq">
          <a
            class="dropdown-item"
            href="#"
            @click.prevent="updateJobType(job.common_code_sq)"
          >
            {{ job.common_code_nm }}
          </a>
        </li>
      </ul>
    </div>

    <div class="flex-grow-1">
      <input
        type="text"
        class="form-control"
        placeholder="검색어를 입력하세요..."
        style="max-width: 400px"
        @input="updateKeyword"
      />
    </div>

    <div class="dropdown">
      <button
        class="btn btn-outline-primary dropdown-toggle"
        type="button"
        data-bs-toggle="dropdown"
      >
        제목
      </button>
      <ul class="dropdown-menu">
        <li>
          <a
            class="dropdown-item"
            href="#"
            @click.prevent="updateTargetField('제목')"
            >제목</a
          >
        </li>
        <li>
          <a
            class="dropdown-item"
            href="#"
            @click.prevent="updateTargetField('작성자명')"
            >작성자명</a
          >
        </li>
        <li>
          <a
            class="dropdown-item"
            href="#"
            @click.prevent="updateTargetField('내용')"
            >내용</a
          >
        </li>
      </ul>
    </div>

    <!-- 정렬 -->
    <div class="dropdown">
      <button
        class="btn btn-outline-primary dropdown-toggle"
        type="button"
        data-bs-toggle="dropdown"
      >
        정렬
      </button>
      <ul class="dropdown-menu">
        <li>
          <a
            class="dropdown-item"
            href="#"
            @click.prevent="updateSort('최신순')"
            >최신순</a
          >
        </li>
        <li>
          <a
            class="dropdown-item"
            href="#"
            @click.prevent="updateSort('조회순')"
            >조회순</a
          >
        </li>
        <li>
          <a
            class="dropdown-item"
            href="#"
            @click.prevent="updateSort('지원자순')"
            >지원자순</a
          >
        </li>
      </ul>
    </div>
  </div>
</template>
<script setup>
import { ref, defineEmits, onMounted } from 'vue'
import { api } from '@/axios.js'

const localOptions = ref([])
const careerOptions = ref([])
const educationOptions = ref([])
const jobTypeOptions = ref([])

const emit = defineEmits(['update'])

const fetchFilterOptions = async () => {
  try {
    const 지역 = await api.$get('/projects/filters', {
      params: { type: '지역' },
    })
    const 경력 = await api.$get('/projects/filters', {
      params: { type: '경력' },
    })
    const 학력 = await api.$get('/projects/filters', {
      params: { type: '학력' },
    })
    const 직종 = await api.$get('/projects/filters', {
      params: { type: '직종' },
    })

    localOptions.value = 지역.output
    careerOptions.value = 경력.output
    educationOptions.value = 학력.output
    jobTypeOptions.value = 직종.output
    console.log(localOptions.value)
  } catch (e) {
    console.error('필터 데이터 불러오기 실패', e)
  }
}

onMounted(() => {
  fetchFilterOptions()
})

const updateRegion = (value) => {
  emit('update', { addressCodeSq: value })
}

const updateCareer = (value) => {
  emit('update', { projectDeveloperGradeCd: value })
}

const updateEducation = (value) => {
  emit('update', { educationCd: value })
}

const updateJobType = (value) => {
  emit('update', { jobRoleCd: value })
}

const updateTargetField = (value) => {
  emit('update', { searchType: value })
}

const updateSort = (value) => {
  if (value === '최신순') {
    emit('update', { sortBy: 'project_start_dt', sortOrder: 'desc' })
  } else if (value === '조회순') {
    emit('update', { sortBy: 'view_count', sortOrder: 'desc' })
  } else if (value === '지원자순') {
    emit('update', { sortBy: 'applicant_count', sortOrder: 'desc' })
  }
}

const updateKeyword = (event) => {
  emit('update', { searchKeyword: event.target.value })
}
</script>
