<template>
  <div class="modal-content">
    <div class="modal-header">
      <h4 class="modal-title">{{ title }}</h4>
      <button
        type="button"
        class="btn-close"
        @click="closeModal"
        aria-hidden="true"
      >
        ×
      </button>
    </div>
    <div class="modal-body">
      <div
        class="tab-pane tab-pane-navigation active show"
        id="resumeDetail"
        role="tabpanel"
      >
        <div class="card bg-color-grey mb-4 p-4">
          <div class="row align-items-start">
            <!-- 이력서 제목 -->
            <div class="mb-4">
              <h5 class="mb-1 text-primary" style="font-weight: bold">
                이력서 제목
              </h5>
              <p class="mb-0 text-dark">{{ resumeInfo.title }}</p>
            </div>

            <!-- 사진 -->
            <div class="col-lg-3 mb-4 d-flex justify-content-center">
              <div
                class="photo-box bg-white"
                style="
                  width: 100%;
                  max-width: 200px;
                  height: 200px;
                  border: 1px solid #ddd;
                  display: flex;
                  align-items: center;
                  justify-content: center;
                "
              >
                <img
                  :src="resumeInfo.photo || 'img/placeholders/user.png'"
                  alt="사진"
                  class="img-fluid rounded"
                  style="max-height: 100%; object-fit: cover"
                />
              </div>
            </div>

            <!-- 기본 정보 -->
            <div class="col-lg-9">
              <p>
                <strong class="text-primary">이름 :</strong>
                <span class="text-dark">{{ resumeInfo.name }}</span>
              </p>
              <p>
                <strong class="text-primary">생년월일 :</strong>
                <span class="text-dark">{{ resumeInfo.birthDate }}</span>
              </p>
              <p>
                <strong class="text-primary">전화번호 :</strong>
                <span class="text-dark">{{ resumeInfo.phone }}</span>
              </p>
              <p>
                <strong class="text-primary">이메일 :</strong>
                <span class="text-dark">{{ resumeInfo.email }}</span>
              </p>
              <p>
                <strong class="text-primary">주소 :</strong>
                <span class="text-dark">{{ resumeInfo.address }}</span>
              </p>
            </div>
          </div>

          <hr />

          <!-- 학력 -->
          <h5 class="text-primary">학력</h5>
          <ul class="list-unstyled">
            <li v-for="(education, index) in resumeInfo.education" :key="index">
              {{ education.school }} {{ education.major }} ({{
                education.period
              }})
            </li>
          </ul>

          <!-- 경력 -->
          <h5 class="text-primary">회사 이력</h5>
          <ul class="list-unstyled">
            <li v-for="(career, index) in resumeInfo.career" :key="index">
              {{ career.company }} {{ career.department }} -
              {{ career.position }} ({{ career.period }})
            </li>
          </ul>

          <!-- 교육 이력 -->
          <h5 class="text-primary">교육 이력</h5>
          <ul class="list-unstyled">
            <li v-for="(training, index) in resumeInfo.training" :key="index">
              {{ training.name }} - {{ training.institution }} ({{
                training.period
              }})
            </li>
          </ul>

          <!-- 프로젝트 이력 -->
          <h5
            class="text-primary d-flex justify-content-between align-items-center mb-3"
          >
            프로젝트 이력
            <div class="d-flex gap-3">
              <a
                href="#"
                @click.prevent="expandAllProjects"
                class="text-grey text-decoration-none small"
              >
                <i class="fas fa-chevron-down me-2"></i>전체 펼치기
              </a>
              <a
                href="#"
                @click.prevent="collapseAllProjects"
                class="text-grey text-decoration-none small"
              >
                <i class="fas fa-chevron-up me-2"></i>전체 닫기
              </a>
            </div>
          </h5>

          <ul class="list-unstyled">
            <li
              v-for="(project, index) in resumeInfo.projects"
              :key="index"
              class="d-flex flex-wrap align-items-center gap-2"
            >
              <div
                class="btn btn-rounded btn-3d btn-light mb-2 w-100 d-flex align-items-center justify-content-between position-relative px-3 py-2"
                style="text-align: left"
              >
                <div
                  class="d-flex align-items-center flex-grow-1"
                  @click="toggleProject(index)"
                >
                  <i
                    :class="[
                      'fas',
                      'fa-chevron-right',
                      'me-2',
                      'transition-transform',
                      { 'rotate-90': project.isExpanded },
                    ]"
                  ></i>
                  <span>{{ project.name }} ({{ project.period }})</span>
                </div>
              </div>

              <div v-show="project.isExpanded" class="collapse show mb-3">
                <div class="bg-light rounded p-3 border">
                  <div class="row mb-2">
                    <div class="col-sm-4">
                      <strong>고객사:</strong> {{ project.client }}
                    </div>
                    <div class="col-sm-4">
                      <strong>업무단:</strong> {{ project.workType }}
                    </div>
                    <div class="col-sm-4">
                      <strong>역할:</strong> {{ project.role }}
                    </div>
                  </div>

                  <div class="row mb-2">
                    <div class="col-sm-4">
                      <strong>기종:</strong> {{ project.platform }}
                    </div>
                    <div class="col-sm-4">
                      <strong>OS:</strong> {{ project.os }}
                    </div>
                    <div class="col-sm-4">
                      <strong>DBMS:</strong> {{ project.dbms }}
                    </div>
                  </div>

                  <div class="row mb-2">
                    <div class="col-sm-12">
                      <strong>언어:</strong>
                      <button
                        v-for="lang in project.languages"
                        :key="lang"
                        class="btn btn-rounded btn-3d btn-light btn-sm me-2"
                      >
                        <img
                          v-if="lang.icon"
                          :src="lang.icon"
                          :alt="lang.name"
                          width="16"
                          height="16"
                        />
                        {{ lang.name }}
                      </button>
                    </div>
                  </div>

                  <div class="row mb-2">
                    <div class="col-sm-12">
                      <strong style="margin-right: 8px">TOOL:</strong>
                      <button
                        v-for="tool in project.tools"
                        :key="tool"
                        class="btn btn-rounded btn-3d btn-light btn-sm me-2"
                      >
                        {{ tool }}
                      </button>
                    </div>
                  </div>

                  <div class="row mb-2">
                    <div class="col-sm-12">
                      <strong style="margin-right: 8px">FW:</strong>
                      <button
                        v-for="fw in project.frameworks"
                        :key="fw"
                        class="btn btn-rounded btn-3d btn-light btn-sm me-2"
                      >
                        <img
                          v-if="fw.icon"
                          :src="fw.icon"
                          :alt="fw.name"
                          width="16"
                          height="16"
                        />
                        {{ fw.name }}
                      </button>
                    </div>
                  </div>

                  <div class="row mb-3">
                    <div class="col-sm-12">
                      <strong style="margin-right: 8px">기타:</strong>
                      <button
                        v-for="etc in project.etc"
                        :key="etc"
                        class="btn btn-rounded btn-3d btn-light btn-sm me-2"
                      >
                        <img
                          v-if="etc.icon"
                          :src="etc.icon"
                          :alt="etc"
                          width="16"
                          height="16"
                        />
                        {{ etc }}
                      </button>
                    </div>
                  </div>
                </div>
              </div>
            </li>
          </ul>

          <!-- 자격증  -->
          <h5 class="text-primary">자격증</h5>
          <ul class="list-unstyled">
            <li
              v-for="(certificate, index) in resumeInfo.certificates"
              :key="index"
            >
              {{ certificate }}
            </li>
          </ul>

          <!-- 기술 -->
          <h5 class="text-primary">보유 기술</h5>
          <div class="d-flex gap-2 flex-wrap mb-3">
            <div
              v-for="(skill, index) in resumeInfo.skills"
              :key="index"
              class="btn d-flex align-items-center gap-2 border-0"
            >
              <img v-if="skill.icon" :src="skill.icon" width="20" />
              {{ skill.name }}
            </div>
          </div>

          <!-- 자기소개 -->
          <h5 class="text-primary">자기소개</h5>
          <p class="border p-3 bg-white rounded text-dark">
            {{ resumeInfo.introduction }}
          </p>

          <!-- 첨부파일 -->
          <h5 class="text-primary">첨부 파일</h5>
          <p v-for="(file, index) in resumeInfo.attachments" :key="index">
            <a :href="file.url" target="_blank">{{ file.name }}</a>
          </p>
        </div>
      </div>
    </div>
    <div class="modal-footer">
      <button class="btn btn-primary" @click="handleSelect">선택하기</button>
      <button class="btn btn-outline-secondary" @click="closeModal">
        닫기
      </button>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useModalStore } from '@/fo/stores/modalStore'

const modalStore = useModalStore()

// 이력서 정보 데이터
const resumeInfo = ref({
  title: '백엔드 개발자 이력서',
  name: '홍길동',
  birthDate: '1990-01-01',
  phone: '010-1234-5678',
  email: 'example@naver.com',
  address: '서울특별시 강남구 역삼동',
  photo: 'img/placeholders/user.png',
  education: [
    { school: '00고등학교', major: '', period: '2011.03 ~ 2014.02' },
    { school: '00대학교', major: '000과', period: '2014.03 ~ 2018.02' },
  ],
  career: [
    {
      company: '00회사',
      department: '000부서',
      position: '프로그램 개발 및 운영',
      period: '2025.04 ~ 2025.10',
    },
  ],
  training: [
    {
      name: 'Public Cloud 활용 자바 웹 애플리케이션 개발자 양성 과정',
      institution: '중앙에이치티에이㈜',
      period: '2011.03 ~ 2014.02',
    },
  ],
  projects: [
    {
      name: '자사 쇼핑몰 구축',
      period: '2023.07 ~ 2024.02',
      client: '코코마랑',
      workType: '사용자 단 화면 개발',
      role: '개발',
      platform: 'Win',
      os: 'Java',
      dbms: 'MySQL',
      languages: [
        {
          name: 'Java',
          icon: 'https://cdn.jsdelivr.net/gh/devicons/devicon/icons/java/java-original.svg',
        },
        {
          name: 'JavaScript',
          icon: 'https://cdn.jsdelivr.net/gh/devicons/devicon/icons/javascript/javascript-original.svg',
        },
      ],
      tools: ['Eclipse', 'VScode', 'WorkBench'],
      frameworks: [
        {
          name: 'SpringBoot',
          icon: 'https://cdn.jsdelivr.net/gh/devicons/devicon/icons/spring/spring-original.svg',
        },
        {
          name: 'MyBatis',
          icon: 'https://cdn.jsdelivr.net/gh/devicons/devicon/icons/mybatis/mybatis-original.svg',
        },
        {
          name: 'Vue.js',
          icon: 'https://cdn.jsdelivr.net/gh/devicons/devicon/icons/vuejs/vuejs-original.svg',
        },
      ],
      etc: ['Git'],
      isExpanded: false,
    },
  ],
  certificates: ['정보처리기사', 'SQL 전문가'],
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
  introduction:
    '책임감 있게 업무를 수행하며 팀워크를 중요시합니다. 성장하는 개발자가 되기 위해 끊임없이 배우고 있습니다.',
  attachments: [
    { name: '홍길동_포트폴리오.pdf', url: 'uploads/홍길동_포트폴리오.pdf' },
    {
      name: '홍길동_개인 이력 카드.docx',
      url: 'uploads/홍길동_개인 이력 카드.docx',
    },
  ],
})

// 프로젝트 토글 함수
const toggleProject = (index) => {
  resumeInfo.value.projects[index].isExpanded =
    !resumeInfo.value.projects[index].isExpanded
}

// 전체 프로젝트 펼치기
const expandAllProjects = () => {
  resumeInfo.value.projects.forEach((project) => {
    project.isExpanded = true
  })
}

// 전체 프로젝트 접기
const collapseAllProjects = () => {
  resumeInfo.value.projects.forEach((project) => {
    project.isExpanded = false
  })
}

// 모달 닫기
const closeModal = () => {
  modalStore.closeModal()
}

// 이력서 선택
const handleSelect = () => {
  // TODO: 이력서 선택 로직 구현
  console.log('이력서 선택')
  closeModal()
}
</script>

<style scoped>
.modal-content {
  border-radius: 8px;
}

.btn-rounded {
  border-radius: 20px;
}

.btn-3d {
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.gap-2 {
  gap: 0.5rem;
}

.rotate-90 {
  transform: rotate(90deg);
}

.transition-transform {
  transition: transform 0.3s ease;
}

.bg-color-grey {
  background-color: #f5f5f5;
}
</style>
