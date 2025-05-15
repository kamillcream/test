<template>
  <div class="modal-content">
    <div class="modal-header">
      <h3 class="modal-title" id="largeModalLabel">소속 인원 리스트</h3>
      <button
        @click="close"
        type="button"
        class="btn-close"
        data-bs-dismiss="modal"
        aria-hidden="true"
      >
        ×
      </button>
    </div>
    <div
      class="modal-body"
      style="max-height: 80vh; overflow-y: auto; padding: 0"
    >
      <!-- 🔽 여기에 기존 지원자 리스트 코드 전체 삽입 -->
      <div class="container py-1 mt-3">
        <!-- 🔽 필터 UI 추가 영역 -->
        <div class="row align-items-center mt-3 mb-2">
          <div class="col-md-12 d-flex justify-content-end gap-2">
            <select
              v-model="searchCategory"
              class="form-select form-select-sm w-auto"
              style="font-size: 14px; padding: 4px"
            >
              <option value="전체">전체</option>
              <option value="이름">이름</option>
              <option value="사용 기술">사용 기술</option>
            </select>
            <input
              v-model="searchWord"
              type="text"
              class="form-control form-control-sm w-auto"
              placeholder="검색어 입력"
              style="font-size: 14px; padding: 4px"
            />
            <button
              class="btn btn-primary btn-sm"
              style="font-size: 14px; padding: 4px"
              @click="filterResumes"
            >
              검색
            </button>
          </div>
        </div>
        <!-- 🔼 필터 UI 끝 -->
        <div class="mb-3">
          <span>현재 선택한 인원 :</span>
          <div class="d-flex flex-wrap gap-2 mt-2">
            <span
              v-for="member in selectedResumes"
              :key="member.id"
              class="btn btn-rounded btn-light d-flex align-items-center gap-2 btn-3d position-relative"
              style="padding-right: 24px"
            >
              <span>{{ member.name }}</span>
              <a
                href="#"
                @click.prevent="removeMember(member.id)"
                class="position-absolute end-0 me-2 text-grey text-decoration-none"
                style="top: 50%; transform: translateY(-50%)"
                title="삭제"
                >×</a
              >
            </span>
          </div>
        </div>

        <div class="row">
          <div class="col pt-2 mt-1">
            <hr class="my-4" />
          </div>
        </div>

        <div class="row">
          <div class="col">
            <ul
              v-if="filteredResumes.length > 0"
              class="simple-post-list m-0 position-relative"
              style="padding: 0"
            >
              <li
                v-for="resume in filteredResumes"
                :key="resume.id"
                class="d-flex justify-content-between align-items-center"
                style="
                  border-bottom: 1px rgb(230, 230, 230) solid;
                  padding: 8px 10px;
                  font-size: 14px;
                "
              >
                <div class="post-info position-relative">
                  <div class="d-flex gap-2">
                    <a
                      @click="handleResumeSpecModalClick(resume.isMain)"
                      href="#"
                      class="text-5 m-0"
                      style="font-size: 14px"
                    >
                      {{ resume.name }} /
                    </a>
                    <span class="text-4 m-0 text-grey" style="font-size: 14px">
                      {{ resume.greeting }}
                    </span>
                  </div>

                  <div
                    class="d-flex justify-content-between align-items-center mt-2"
                    style="font-size: 14px"
                  >
                    <div class="d-flex gap-2 flex-wrap">
                      <div
                        class="post-meta text-4"
                        style="font-size: 14px !important"
                      >
                        <span
                          class="text-dark text-uppercase font-weight-semibold"
                          >경력</span
                        >
                        | {{ resume.experience }}
                      </div>
                      <span
                        class="text-dark text-uppercase font-weight-semibold"
                        style="margin-left: 10px"
                        >사용 기술</span
                      >
                      |
                      <div
                        v-for="skill in resume.skills"
                        :key="skill"
                        class="btn d-flex align-items-center gap-2 border-0"
                        style="padding: 2px 6px; font-size: 14px"
                      ></div>
                    </div>
                  </div>
                </div>

                <div class="d-flex gap-2 ms-auto">
                  <button
                    v-if="isSelected(resume.id)"
                    @click="toggleSelection(resume.id, resume.name)"
                    class="btn btn-primary btn-lg"
                    style="font-size: 14px; padding: 8px 12px"
                  >
                    선택됨
                  </button>
                  <button
                    v-else
                    @click="toggleSelection(resume.id, resume.name)"
                    class="btn btn-primary btn-outline btn-lg"
                    style="font-size: 14px; padding: 8px 12px"
                  >
                    선택하기
                  </button>
                  <button
                    @click="openResumeModal(resume.id)"
                    class="btn btn-primary btn-outline btn-lg"
                    style="font-size: 14px; padding: 8px 12px"
                  >
                    이력서 변경
                  </button>
                </div>
              </li>
            </ul>
            <div
              v-else
              class="text-center text-muted py-5"
              style="font-size: 16px"
            >
              검색 결과가 없습니다.
            </div>
          </div>
          <div class="mt-5">
            <ul class="pagination float-end">
              <li class="page-item">
                <a class="page-link" href="#"
                  ><i class="fas fa-angle-left"></i
                ></a>
              </li>
              <li class="page-item active">
                <a class="page-link" href="#">1</a>
              </li>
              <li class="page-item"><a class="page-link" href="#">2</a></li>
              <li class="page-item"><a class="page-link" href="#">3</a></li>
              <li class="page-item">
                <a class="page-link" href="#"
                  ><i class="fas fa-angle-right"></i
                ></a>
              </li>
            </ul>
          </div>
        </div>
      </div>
      <div class="modal-footer">
        <button
          @click="applyCheck"
          type="button"
          class="btn btn-primary"
          data-bs-dismiss="modal"
        >
          선택완료
        </button>
        <button
          @click="close"
          type="button"
          class="btn btn-light"
          data-bs-dismiss="modal"
        >
          닫기
        </button>
      </div>
    </div>
  </div>
</template>
<script setup>
import { ref, watch, onMounted, onBeforeUnmount } from 'vue'
import { useModalStore } from '../../stores/modalStore'
import { useAlertStore } from '../../stores/alertStore'
import UserResumeModal from '@/fo/components/mypage/personal/ResumeModal.vue'
import CommonConfirmModal from '@/fo/components/common/CommonConfirmModal.vue'

const modalStore = useModalStore()
const alert = useAlertStore()

const searchWord = ref('')
const searchCategory = ref('전체') // 기본값이 전체.
const resumes = ref([
  {
    id: 1,
    name: '홍길동',
    greeting: '안녕하세요. Java 개발자입니다.',
    experience: '0년차',
    skills: ['Java', 'Python', 'Spring Boot'],
    isMain: true,
  },
  {
    id: 2,
    name: '홍길동',
    greeting: '이력서를 선택하세요.',
    experience: '0년차',
    skills: ['Java', 'Python', 'Spring Boot'],
    isMain: true,
  },
  {
    id: 3,
    name: '홍길동',
    greeting: '이력서를 선택하세요.',
    experience: '0년차',
    skills: ['Java', 'Python', 'Spring Boot'],
    isMain: false,
  },
  {
    id: 4,
    name: '홍길동',
    greeting: '이력서를 선택하세요.',
    experience: '0년차',
    skills: ['Java', 'Python', 'Spring Boot'],
    isMain: true,
  },
  {
    id: 5,
    name: '홍길동',
    greeting: '이력서를 선택하세요.',
    experience: '0년차',
    skills: ['Java', 'Python', 'Spring Boot'],
    isMain: false,
  },
])

const filteredResumes = ref([...resumes.value])

const selectedResumes = ref([
  { id: 1, name: '홍길동' },
  { id: 2, name: '김철수' },
  { id: 3, name: '이영희' },
])

const removeMember = (id) => {
  selectedResumes.value = selectedResumes.value.filter(
    (member) => member.id !== id,
  )
}

const openResumeModal = () => {
  modalStore.openModal(UserResumeModal)
}

const toggleSelection = (resumeId, name) => {
  const fullResume = resumes.value.find((r) => r.id === resumeId)

  if (!fullResume?.isMain) {
    alert.show(`해당 인원의 대표 이력서를 먼저 선택해주세요`, 'danger')
    return
  }

  const index = selectedResumes.value.findIndex((r) => r.id === resumeId)

  if (index === -1) {
    selectedResumes.value.push({
      id: resumeId,
      name,
      isMain: true, // 이미 검증된 상태
    })
  } else {
    selectedResumes.value.splice(index, 1)
  }
}

const isSelected = (resumeId) => {
  return selectedResumes.value.some((r) => r.id === resumeId)
}

const filterResumes = () => {
  const keyword = searchWord.value.toLowerCase().trim()

  if (!keyword) {
    filteredResumes.value = [...resumes.value]
    return
  }

  if (searchCategory.value === '이름') {
    filteredResumes.value = resumes.value.filter((resume) =>
      resume.name.toLowerCase().includes(keyword),
    )
  } else if (searchCategory.value === '사용 기술') {
    filteredResumes.value = resumes.value.filter((resume) =>
      resume.skills.some((skill) => skill.toLowerCase().includes(keyword)),
    )
  } else if (searchCategory.value === '전체') {
    filteredResumes.value = resumes.value.filter(
      (resume) =>
        resume.name.toLowerCase().includes(keyword) ||
        resume.skills.some((skill) => skill.toLowerCase().includes(keyword)),
    )
  }
}

const applyCheck = () => {
  modalStore.openModal(CommonConfirmModal, {
    title: '프로젝트 지원',
    message: '선택한 인원들로 프로젝트에 지원하시겠습니까?',
    onConfirm: () => {
      console.log('삭제 확정됨')
      // api 요청 추가
      // 예: await projectService.delete(projectId)
    },
  })
}

const handleResumeSpecModalClick = (isMain) => {
  console.log(isMain)
  if (isMain) {
    // 이력서 상세 모달창 호출
  } else {
    alert.show('해당 인원의 대표 이력서를 먼저 선택해주세요.', 'danger')
  }
}
watch(searchWord, (newVal, oldVal) => {
  console.log('변경됨:', oldVal, '→', newVal)
})

watch(searchCategory, (newVal, oldVal) => {
  console.log('변경됨:', oldVal, '→', newVal)
})

onMounted(() => {
  document.body.style.setProperty('overflow', 'hidden', 'important')
  document.documentElement.style.setProperty('overflow', 'hidden', 'important')
})

onBeforeUnmount(() => {
  document.body.style.removeProperty('overflow')
  document.documentElement.style.removeProperty('overflow')
})
const close = () => {
  modalStore.closeModal()
}
</script>
<style></style>
