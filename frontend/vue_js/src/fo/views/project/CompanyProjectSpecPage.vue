<template lang="">
  <CommonPageHeader
    title=""
    strongText="프로젝트 상세 정보"
    :breadcrumbs="[{ text: 'Home', link: '/' }, { text: '프로젝트' }]"
  />
  <div class="container py-5">
    <div class="row pt-4 mt-2 mb-5">
      <!-- 좌측: 지원 자격 (스크롤 없애기) -->
      <div
        class="col-md-8 mb-4 mb-md-0"
        style="
          border: 1px solid #dee2e6;
          border-radius: 10px;
          padding: 24px 32px;
          background-color: #ffffff;
          box-shadow: 0 2px 8px rgba(0, 0, 0, 0.03);
        "
      >
        <h2 class="text-color-dark font-weight-normal text-5 mb-2">
          지원 자격 / 근무 조건
        </h2>

        <ul>
          <li>
            <i class="fas fa-caret-right left-10"></i
            ><strong class="text-color-primary">필수 기술 </strong>
            <ul class="ps-4 mb-2">
              <li
                v-for="skillGroup in project.projectRequiredSkills"
                :key="skillGroup.parentSkillTagNm"
              >
                <strong class="text-dark">{{
                  skillGroup.parentSkillTagNm
                }}</strong>
                <div
                  v-for="skill in skillGroup.childSkillTagNms"
                  :key="skill"
                  class="d-flex align-items-center gap-2 mt-1"
                >
                  <div class="d-flex align-items-center me-3">
                    <img
                      :src="getSkillIconUrl(skill)"
                      :alt="skill"
                      width="24"
                      height="24"
                      class="me-1"
                    />
                    <span>{{ skill }}</span>
                  </div>
                </div>
              </li>
            </ul>
          </li>

          <li>
            <i class="fas fa-caret-right left-10"></i
            ><strong class="text-color-primary">우대 기술 </strong>
            <ul class="ps-4 mb-2">
              <li
                v-for="skillGroup in project.projectPreferredSkills"
                :key="skillGroup.parentSkillTagNm"
              >
                <strong class="text-dark">{{
                  skillGroup.parentSkillTagNm
                }}</strong>
                <div
                  v-for="skill in skillGroup.childSkillTagNms"
                  :key="skill"
                  class="d-flex align-items-center gap-2 mt-1"
                >
                  <div class="d-flex align-items-center me-3">
                    <img
                      :src="getSkillIconUrl(skill)"
                      :alt="skill"
                      width="24"
                      height="24"
                      class="me-1"
                    />
                    <span>{{ skill }}</span>
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
            <i class="fas fa-caret-right left-10"></i>
            <strong class="text-color-primary">근무 형태 :</strong>
            <span>{{ project.projectWorkType?.join(' / ') }}</span>
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
              >조회수: {{ project.projectViewCnt }}</span
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
import CommonPageHeader from '@/fo/components/common/CommonPageHeader.vue'
import { useModalStore } from '../../stores/modalStore.js'

const modalStore = useModalStore()

const projectSq = route.params.project_sq

const project = ref([])

onMounted(async () => {
  try {
    // 스크롤 막기
    document.body.style.overflow = 'hidden'

    const projectSq = route.params.project_sq

    const response = await api.$get(`/projects/${projectSq}/details`)
    project.value = response.output
    console.log(project.value)
  } catch (e) {
    console.error('프로젝트 상세 정보 불러오기 실패', e)
    const rawMessage =
      e?.response?.data?.message ||
      '프로젝트 정보를 불러오는 중 오류가 발생했습니다.'
    const message = rawMessage.replace(/^Unexpected Error:\s*/, '')
    alert(message)
    router.push({ name: 'ProjectListPage' })
  }
})

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

const goToProjectPost = (project) => {
  router.push({
    name: 'ProjectPostPage',
    params: {
      project_sq: project.id,
    },
  })
}

const deleteProject = () => {
  modalStore.openModal(CommonConfirmModal, {
    title: '프로젝트 삭제',
    message: '한 번 삭제한 프로젝트는 복구할 수 없습니다. 삭제하시겠습니까?',
    onConfirm: async () => {
      try {
        console.log('삭제 확정됨')
        const res = await api.$delete(`/projects/${projectSq}`)
        alert(res.message || '삭제되었습니다.')
        modalStore.closeModal()
        router.push({ name: 'ProjectListPage' }) // 삭제 후 이동 (예시)
      } catch (error) {
        console.error('삭제 실패:', error)
        alert('삭제 중 오류가 발생했습니다.')
      }
    },
  })
}

const getSkillIconUrl = (skill) => {
  const skillMap = {
    Java: 'https://cdn.jsdelivr.net/gh/devicons/devicon/icons/java/java-original.svg',
    Python:
      'https://cdn.jsdelivr.net/gh/devicons/devicon/icons/python/python-original.svg',
    Django:
      'https://cdn.jsdelivr.net/gh/devicons/devicon/icons/django/django-plain.svg',
    'Spring Boot':
      'https://cdn.jsdelivr.net/gh/devicons/devicon/icons/spring/spring-original.svg',
    React:
      'https://cdn.jsdelivr.net/gh/devicons/devicon/icons/react/react-original.svg',
    'Vue.js':
      'https://cdn.jsdelivr.net/gh/devicons/devicon/icons/vuejs/vuejs-original.svg',
    Git: 'https://cdn.jsdelivr.net/gh/devicons/devicon/icons/git/git-original.svg',
    Docker:
      'https://cdn.jsdelivr.net/gh/devicons/devicon/icons/docker/docker-original.svg',
    IntelliJ:
      'https://cdn.jsdelivr.net/gh/devicons/devicon/icons/intellij/intellij-original.svg',
  }

  return (
    skillMap[skill] ||
    'https://cdn.jsdelivr.net/gh/devicons/devicon/icons/code/code-original.svg'
  )
}

// TODO: 스크랩 토글
</script>
<style lang=""></style>
