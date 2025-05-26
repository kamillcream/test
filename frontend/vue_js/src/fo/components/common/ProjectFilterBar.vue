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
        {{ selectedRegionName }}
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
        {{ selectedCareerName }}
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
        class="btn btn-outline btn-primary dropdown-toggle"
        type="button"
        data-bs-toggle="dropdown"
      >
        {{ selectedEducationName }}
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
        class="btn btn-outline btn-primary dropdown-toggle"
        type="button"
        data-bs-toggle="dropdown"
      >
        {{ selectedJobTypeName }}
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
        class="btn btn-outline btn-primary dropdown-toggle"
        type="button"
        data-bs-toggle="dropdown"
      >
        {{ selectedTargetField }}
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
        class="btn btn-outline btn-primary dropdown-toggle"
        type="button"
        data-bs-toggle="dropdown"
      >
        {{ selectedSort }}
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
import { useRoute } from 'vue-router'

const localOptions = ref([])
const careerOptions = ref([])
const educationOptions = ref([])
const jobTypeOptions = ref([])

const selectedRegionName = ref('지역')
const selectedCareerName = ref('경력')
const selectedEducationName = ref('학력')
const selectedJobTypeName = ref('직종')
const selectedTargetField = ref('제목')
const selectedSort = ref('정렬')

const route = useRoute()

const basePath = route.path.includes('/affiliation')
  ? '/affiliations'
  : '/projects'

const updateRegion = (value) => {
  const selected = localOptions.value.find((item) => item.areaSq === value)
  selectedRegionName.value = selected?.areaName || '지역'
  emit('update', { addressCodeSq: value })
}

const updateCareer = (value) => {
  const selected = careerOptions.value.find(
    (item) => item.common_code_sq === value,
  )
  selectedCareerName.value = selected?.common_code_nm || '경력'
  emit('update', { projectDeveloperGradeCd: value })
}

const updateEducation = (value) => {
  const selected = educationOptions.value.find(
    (item) => item.common_code_sq === value,
  )
  selectedEducationName.value = selected?.common_code_nm || '학력'
  emit('update', { educationCd: value })
}

const updateJobType = (value) => {
  const selected = jobTypeOptions.value.find(
    (item) => item.common_code_sq === value,
  )
  selectedJobTypeName.value = selected?.common_code_nm || '직종'
  emit('update', { jobRoleCd: value })
}

const updateTargetField = (value) => {
  selectedTargetField.value = value
  emit('update', { searchType: value })
}

const updateSort = (value) => {
  selectedSort.value = value
  if (value === '최신순') {
    emit('update', { sortBy: 'project_start_dt', sortOrder: 'desc' })
  } else if (value === '조회순') {
    emit('update', { sortBy: 'view_count', sortOrder: 'desc' })
  } else if (value === '지원자순') {
    emit('update', { sortBy: 'applicant_count', sortOrder: 'desc' })
  }
}

const emit = defineEmits(['update'])

const fetchFilterOptions = async () => {
  try {
    const 지역 = await api.$get(`${basePath}/filters`, {
      params: { type: '지역' },
    })
    const 경력 = await api.$get(`${basePath}/filters`, {
      params: { type: '경력' },
    })
    const 학력 = await api.$get(`${basePath}/filters`, {
      params: { type: '학력' },
    })
    const 직종 = await api.$get(`${basePath}/filters`, {
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

const updateKeyword = (event) => {
  emit('update', { searchKeyword: event.target.value })
}
</script>
