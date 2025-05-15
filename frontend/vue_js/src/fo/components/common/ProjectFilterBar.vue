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
        <li v-for="local in localFilters" :key="local.id">
          <a
            class="dropdown-item"
            href="#"
            @click.prevent="updateRegion(region)"
          >
            {{ local }}
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
        <li v-for="career in careerFilters" :key="career.id">
          <a
            class="dropdown-item"
            href="#"
            @click.prevent="updateCareer(career)"
          >
            {{ career }}
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
        <li>
          <a
            class="dropdown-item"
            href="#"
            @click.prevent="updateEducation('학력무관')"
            >학력무관</a
          >
        </li>
        <li>
          <a
            class="dropdown-item"
            href="#"
            @click.prevent="updateEducation('고등학교')"
            >고등학교</a
          >
        </li>
        <li>
          <a
            class="dropdown-item"
            href="#"
            @click.prevent="updateEducation('대학(2,3년제)')"
            >대학(2,3년제)</a
          >
        </li>
        <li>
          <a
            class="dropdown-item"
            href="#"
            @click.prevent="updateEducation('대학(4년제)')"
            >대학(4년제)</a
          >
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
        <li v-for="job in jobTypeFilters" :key="job.id">
          <a class="dropdown-item" href="#" @click.prevent="updateJobType(job)">
            {{ job }}
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
<script>
export default {
  props: {
    localFilters: {
      type: Array,
      required: true,
    },
    careerFilters: {
      type: Array,
      required: true,
    },
    jobTypeFilters: {
      type: Array,
      required: true,
    },
  },
  emits: ['update'],
  methods: {
    updateRegion(value) {
      this.$emit('update', { addressCodeSq: value })
    },
    updateExperience(value) {
      this.$emit('update', { experience: value })
    },
    updateEducation(value) {
      this.$emit('update', { education: value })
    },
    updateJobType(value) {
      this.$emit('update', { jobRole: value })
    },
    updateTargetField(value) {
      this.$emit('update', { searchType: value })
    },
    updateSort(value) {
      if (value === '최신순') {
        this.$emit('update', { sortBy: 'project_start_dt', sortOrder: 'desc' })
      } else if (value === '조회순') {
        this.$emit('update', { sortBy: 'view_count', sortOrder: 'desc' })
      } else if (value === '지원자순') {
        this.$emit('update', { sortBy: 'applicant_count', sortOrder: 'desc' })
      }
    },
    updateKeyword(event) {
      this.$emit('update', { searchKeyword: event.target.value })
    },
  },
}
</script>
