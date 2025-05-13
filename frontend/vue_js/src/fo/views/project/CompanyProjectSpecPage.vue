<template lang="">
  <HeaderBeforeLogin />
  <div class="container py-5">
    <div class="row pt-4 mt-2 mb-5">
      <!-- 좌측: 지원 자격 (스크롤 없애기) -->
      <div class="col-md-8 mb-4 mb-md-0" style="max-height: auto">
        <h2 class="text-color-dark font-weight-normal text-5 mb-2">
          지원 자격 / 근무 조건
        </h2>
        <ul class="list list-icons list-primary list-borders text-2">
          <li>
            <i class="fas fa-caret-right left-10"></i
            ><strong class="text-color-primary">경력 / 학력 :</strong>
            {{ project.career }} / {{ project.education }}
          </li>
          <li v-for="job in project.jobs" :key="job.id">
            <i class="fas fa-caret-right left-10"></i>
            <strong class="text-color-primary">모집 직군 :</strong> {{ job }}
          </li>

          <!-- 필수 기술 -->
          <li>
            <i class="fas fa-caret-right left-10"></i
            ><strong class="text-color-primary">필수 기술 </strong>
            <ul class="ps-4 mb-2">
              <li>
                <strong class="text-dark">언어</strong>
                <div
                  v-for="language in project.skills.languages"
                  :key="language.id"
                  class="d-flex align-items-center gap-2 mt-1"
                >
                  <div class="d-flex align-items-center me-3">
                    <img
                      src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/java/java-original.svg"
                      alt="Java"
                      width="24"
                      height="24"
                      class="me-1"
                    />
                    <span>{{ language }}</span>
                  </div>
                </div>
              </li>
              <li>
                <strong class="text-dark">Framework</strong>
                <div
                  v-for="framework in project.skills.frameworks"
                  :key="framework.id"
                  class="d-flex align-items-center gap-2 mt-1"
                >
                  <div class="d-flex align-items-center me-3">
                    <img
                      src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/spring/spring-original.svg"
                      alt="Spring Boot"
                      width="24"
                      height="24"
                      class="me-1"
                    />
                    <span>{{ framework }}</span>
                  </div>
                </div>
              </li>
              <li>
                <strong class="text-dark">Tool</strong>
                <div
                  v-for="tool in project.skills.tools"
                  :key="tool.id"
                  class="d-flex align-items-center gap-2 mt-1"
                >
                  <div class="d-flex align-items-center me-3">
                    <img
                      src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/git/git-original.svg"
                      alt="Git"
                      width="24"
                      height="24"
                      class="me-1"
                    />
                    <span>{{ tool }}</span>
                  </div>
                </div>
              </li>
            </ul>
          </li>

          <!-- 우대 기술 -->
          <li>
            <i class="fas fa-caret-right left-10"></i
            ><strong class="text-color-primary">우대 기술 </strong>
            <ul class="ps-4 mb-2">
              <li>
                <strong class="text-dark">언어</strong>
                <div
                  v-for="language in project.preferSkills.languages"
                  :key="language.id"
                  class="d-flex align-items-center gap-2 mt-1"
                >
                  <div class="d-flex align-items-center me-3">
                    <img
                      src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/java/java-original.svg"
                      alt="Java"
                      width="24"
                      height="24"
                      class="me-1"
                    />
                    <span>{{ language }}</span>
                  </div>
                </div>
              </li>
              <li>
                <strong class="text-dark">Framework</strong>
                <div
                  v-for="framework in project.preferSkills.frameworks"
                  :key="framework.id"
                  class="d-flex align-items-center gap-2 mt-1"
                >
                  <div class="d-flex align-items-center me-3">
                    <img
                      src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/spring/spring-original.svg"
                      alt="Spring Boot"
                      width="24"
                      height="24"
                      class="me-1"
                    />
                    <span>{{ framework }}</span>
                  </div>
                </div>
              </li>
              <li>
                <strong class="text-dark">Tool</strong>
                <div
                  v-for="tool in project.preferSkills.tools"
                  :key="tool.id"
                  class="d-flex align-items-center gap-2 mt-1"
                >
                  <div class="d-flex align-items-center me-3">
                    <img
                      src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/intellij/intellij-original.svg"
                      alt="IntelliJ"
                      width="24"
                      height="24"
                      class="me-1"
                    />
                    <span>{{ tool }}</span>
                  </div>
                </div>
              </li>
            </ul>
          </li>

          <!-- 우대 사항 -->
          <li>
            <i class="fas fa-caret-right left-10"></i
            ><strong class="text-color-primary">우대 사항 :</strong>
            {{ project.preferContent }}
          </li>

          <!-- 근무 조건 -->
          <li>
            <i class="fas fa-caret-right left-10"></i
            ><strong class="text-color-primary">근무 형태 :</strong>
            {{ project.contract }}
          </li>
          <li>
            <i class="fas fa-caret-right left-10"></i
            ><strong class="text-color-primary">근무 지역 :</strong>
            {{ project.local }}
          </li>
          <li>
            <i class="fas fa-caret-right left-10"></i
            ><strong class="text-color-primary">단가 :</strong>
            {{ project.salary }}
          </li>
        </ul>
      </div>

      <!-- 우측: 회사 정보 (고정된 카드) -->
      <div class="col-md-4" style="position: sticky; top: 100px">
        <div class="card">
          <div class="card-body">
            <div class="d-flex align-items-center mb-3">
              <div class="me-4 flex-shrink-0">
                <img
                  src="img/blog/medium/blog-2.jpg"
                  alt="프로젝트 이미지"
                  class="rounded-circle"
                  style="width: 70px; height: 70px; object-fit: cover"
                />
              </div>
              <div>
                <h2 class="text-color-dark font-weight-normal text-5 mb-0">
                  {{ project.title }}
                </h2>
                <p class="text-muted mb-0">{{ project.company }}</p>
              </div>
            </div>

            <p>{{ project.description }}</p>

            <div class="card-footer bg-white border-top-0 pt-4">
              <div class="text-start text-2">
                <p class="mb-1 text-color-primary">
                  <i class="fas fa-caret-right me-2"></i
                  ><strong class="text-color-primary">모집 기간 :</strong>
                  {{ project.recruitStartDt }} ~ {{ project.recruitEndDt }}
                </p>
                <p class="mb-1 text-color-primary">
                  <i class="fas fa-caret-right me-2"></i
                  ><strong class="text-color-primary">인터뷰 기간 :</strong>
                  {{ project.interviewStartDt }} ~ {{ project.interviewEndDt }}
                </p>
                <p class="mb-0 text-color-primary">
                  <i class="fas fa-caret-right me-2"></i
                  ><strong class="text-color-primary">수행 기간 :</strong>
                  {{ project.StartDt }} ~ {{ project.EndDt }}
                </p>
              </div>
            </div>

            <hr class="solid my-4" />

            <div class="d-flex justify-content-center align-items-center gap-3">
              <a
                @click="openMemberModal"
                v-if="companyId !== 'test1'"
                href="#"
                class="btn btn-lg btn-rounded btn-primary btn-lg"
              >
                지원하기
              </a>
              <a
                v-if="companyId !== 'test1'"
                href="#"
                class="btn btn-lg btn-rounded btn-light btn-lg"
              >
                스크랩
              </a>
              <a
                v-if="companyId === 'test1'"
                href="#"
                class="btn btn-lg btn-rounded btn-primary btn-lg"
              >
                수정하기
              </a>
              <a
                v-if="companyId === 'test1'"
                href="#"
                class="btn btn-lg btn-rounded btn-light btn-lg"
              >
                삭제하기
              </a>
            </div>
          </div>

          <!-- 조회수 텍스트 (우측 하단으로 배치) -->
          <div class="position-absolute top-0 end-0 p-2">
            <span class="text-grey" style="font-size: 0.8rem"
              >조회수: {{ project.hits }}</span
            >
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script setup>
import { onMounted, onBeforeUnmount } from 'vue'
import AffiliationMemberModal from '@/fo/components/company/AffiliationMemberModal.vue'
import { useModalStore } from '../../stores/modalStore.js'

const modalStore = useModalStore()

const openMemberModal = () => {
  modalStore.openModal(AffiliationMemberModal, {
    size: 'modal-xl',
  })
}

onMounted(() => {
  // 바깥 페이지 스크롤 막기
  document.body.style.overflow = 'hidden'
})

onBeforeUnmount(() => {
  // 모달 닫히면 스크롤 복구
  document.body.style.overflow = ''
})
const project = {
  title: 'AI 학습 데이터 플랫폼 구축',
  company: 'DataForge Inc.',
  description:
    'AI 모델 성능 향상을 위한 데이터 수집·가공·시각화 플랫폼을 구축하는 프로젝트입니다.',
  career: '3년 이상',
  education: '대학교 졸업 이상',
  jobs: ['데이터 엔지니어', 'ML 엔지니어'],
  skills: {
    languages: ['TypeScript', 'Python'],
    frameworks: ['Django', 'FastAPI'],
    tools: ['Docker', 'Kubernetes'],
  },
  preferSkills: {
    languages: ['Go', 'Rust'],
    frameworks: ['TensorFlow', 'PyTorch'],
    tools: ['Airflow', 'MLflow'],
  },
  preferContent: '대규모 분산 처리 경험자 우대',
  contract: '정규직 / 계약직',
  local: '경기 성남시 분당구',
  salary: '월 600만원 ~ 800만원',
  recruitStartDt: '2025/05/10',
  recruitEndDt: '2025/05/20',
  interviewStartDt: '2025/05/22',
  interviewEndDt: '2025/05/28',
  StartDt: '2025/06/03',
  EndDt: '2025/12/31',
  scrap: 12,
  hits: 367,
}

// export default {
//   name: 'UserProjectSpecPage',
//   components: {
//     HeaderBeforeLogin
//   },
//   props: {
//     project: {
//       type: Object,
//       required: true
//     }
//   }
// }
</script>
<style lang=""></style>
