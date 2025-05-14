<template>
  <div class="modal-content">
    <div class="modal-header">
      <h4 class="modal-title">이력서 선택</h4>
      <button
        type="button"
        class="btn-close"
        @click="closeModal"
        aria-hidden="true"
      ></button>
    </div>
    <div class="modal-body">
      <div>
        <ul class="simple-post-list m-0">
          <li
            v-for="(resume, index) in resumes"
            :key="index"
            class="d-flex align-items-center"
          >
            <div class="post-info">
              <a href="#">{{ resume.title }}</a>
              <span v-if="resume.isDefault" class="badge badge-primary badge-xs"
                >대표이력서</span
              >
              <div class="post-meta">
                <span class="text-dark text-uppercase font-weight-semibold"
                  >등록일자</span
                >
                | {{ resume.registrationDate }}
              </div>
            </div>
            <div class="ms-auto">
              <button
                class="btn btn-sm"
                :class="
                  selectedResumeId === resume.id
                    ? 'btn-primary'
                    : 'btn-primary btn-outline'
                "
                @click="selectResume(resume.id)"
              >
                {{ selectedResumeId === resume.id ? '선택됨' : '선택하기' }}
              </button>
            </div>
          </li>
        </ul>
      </div>

      <!-- 페이지네이션 -->
      <ul class="pagination float-end mt-3 mb-0">
        <li class="page-item">
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
          class="page-item"
          :class="{ active: currentPage === page }"
        >
          <a class="page-link" href="#" @click.prevent="changePage(page)">{{
            page
          }}</a>
        </li>
        <li class="page-item">
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
    <div class="modal-footer">
      <button type="button" class="btn btn-primary" @click="handleSubmit">
        지원하기
      </button>
      <button type="button" class="btn btn-light" @click="closeModal">
        닫기
      </button>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useModalStore } from '@/fo/stores/modalStore'

const modalStore = useModalStore()

// 상태 관리
const currentPage = ref(1)
const totalPages = ref(3)
const selectedResumeId = ref(null)

// 이력서 데이터
const resumes = ref([
  {
    id: 1,
    title: '이력서 제목 1',
    isDefault: true,
    registrationDate: '2025.04.01',
  },
  {
    id: 2,
    title: '이력서 제목 2',
    isDefault: false,
    registrationDate: '2025.04.01',
  },
  {
    id: 3,
    title: '이력서 제목 3',
    isDefault: false,
    registrationDate: '2025.04.01',
  },
  {
    id: 4,
    title: '이력서 제목 4',
    isDefault: false,
    registrationDate: '2025.04.01',
  },
])

// 이력서 선택
const selectResume = (resumeId) => {
  selectedResumeId.value = resumeId
}

// 페이지 변경
const changePage = (page) => {
  if (page < 1 || page > totalPages.value) return
  currentPage.value = page
  // TODO: 페이지 데이터 로드
}

// 지원하기
const handleSubmit = () => {
  if (!selectedResumeId.value) {
    alert('이력서를 선택해주세요.')
    return
  }
  // TODO: 지원 로직 구현
  console.log('선택된 이력서 ID:', selectedResumeId.value)
  closeModal()
}

// 모달 닫기
const closeModal = () => {
  modalStore.closeModal()
}
</script>

<style scoped>
.modal-content {
  border-radius: 8px;
}

.simple-post-list {
  list-style: none;
  padding: 0;
}

.simple-post-list li {
  padding: 1rem 0;
  border-bottom: 1px solid #eee;
}

.simple-post-list li:last-child {
  border-bottom: none;
}

.badge-xs {
  font-size: 0.7rem;
  padding: 0.2rem 0.4rem;
}

.post-meta {
  font-size: 0.9rem;
  color: #6c757d;
  margin-top: 0.5rem;
}

.btn-outline {
  border: 1px solid #007bff;
  color: #007bff;
}

.btn-outline:hover {
  background-color: #007bff;
  color: white;
}
</style>
