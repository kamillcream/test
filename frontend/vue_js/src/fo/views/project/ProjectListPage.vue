<template>
  <div>
    <HeaderBeforeLogin />
    <ProjectFilterBar
      :localFilters="['서울', '부산', '대구']"
      :careerFilters="['신입', '경력']"
      :jobTypeFilters="['백엔드', '프론트엔드', 'PM', '디자이너']"
      @update="updateFilters"
    />
    <div class="container py-4">
      <div class="d-flex justify-content-end mb-3">
        <a href="#" class="btn btn-rounded btn-light">등록하기</a>
      </div>
      <ProjectCardGroup :projects="projects" />
      <!-- 페이지네이션 -->
      <div class="row">
        <div class="col">
          <ul class="pagination float-end">
            <li class="page-item"><a class="page-link" href="#"><i class="fas fa-angle-left"></i></a></li>
            <li class="page-item active"><a class="page-link" href="#">1</a></li>
            <li class="page-item"><a class="page-link" href="#">2</a></li>
            <li class="page-item"><a class="page-link" href="#">3</a></li>
            <li class="page-item"><a class="page-link" href="#"><i class="fas fa-angle-right"></i></a></li>
          </ul>
        </div>
      </div>
    </div>
  </div>
</template>
<script setup>
import HeaderBeforeLogin from "../../components/common/HeaderBeforeLogin.vue";
import ProjectFilterBar from "../../components/common/ProjectFilterBar.vue";
import ProjectCardGroup from "../../components/project/ProjectCardGroup.vue";
import { ref, watch } from 'vue';
const filters = ref({
  region: '',
  experience: '',
  education: '',
  keyword: '',
  sort: '',
})

const projects = ref([
  {
    id: 1,
    name: "웹 서비스 개발",
    remainingDay: 5,
    company: "EST Soft",
    local: "서울",
    career: "신입",
    education: "학력무관",
    salary: "협의 후 결정",
    hits: 123,
    skills: [
      { id: 1, name: "Java", imageUrl: "https://cdn.jsdelivr.net/gh/devicons/devicon/icons/java/java-original.svg" },
      { id: 2, name: "SpringBoot", imageUrl: "https://cdn.jsdelivr.net/gh/devicons/devicon/icons/spring/spring-original.svg" },
      { id: 3, name: "Vue.js", imageUrl: "https://cdn.jsdelivr.net/gh/devicons/devicon/icons/vuejs/vuejs-original.svg" }
    ]
  },
  {
    id: 2,
    name: "AI 추천 시스템",
    remainingDay: 3,
    company: "Naver",
    local: "부산",
    career: "경력",
    education: "대학(4년제)",
    salary: "5000만원 이상",
    hits: 87,
    skills: [
      { id: 1, name: "Python", imageUrl: "https://cdn.jsdelivr.net/gh/devicons/devicon/icons/python/python-original.svg" },
      { id: 2, name: "TensorFlow", imageUrl: "https://cdn.jsdelivr.net/gh/devicons/devicon/icons/tensorflow/tensorflow-original.svg" }
    ]
  }
])


watch(filters, (newVal) => {
    console.log(newVal);
})


const updateFilters = (updated) => {
  filters.value = { ...filters.value, ...updated }
}

</script>
<style lang="">
    
</style>