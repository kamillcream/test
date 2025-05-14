<template>
  <div
    id="defaultModal"
    tabindex="-1"
    aria-labelledby="defaultModalLabel"
    aria-hidden="true"
  >
    <div
      class="modal-dialog modal modal-dialog-centered"
      style="margin: 0 auto; max-width: 600px"
    >
      <div class="modal-content">
        <div class="modal-header">
          <h4 class="modal-title" id="schoolSearchModalLabel">이력서 선택</h4>
          <button
            type="button"
            class="btn-close"
            data-bs-dismiss="modal"
            aria-hidden="true"
            @click="close"
          ></button>
        </div>
        <div class="modal-body">
          <div>
            <ul class="simple-post-list m-0">
              <li
                class="d-flex align-items-center"
                v-for="resume in resumes"
                :key="resume.id"
              >
                <div class="post-info">
                  <a href="#">{{ resume.title }}</a>
                  <div class="post-meta">
                    <span class="text-dark text-uppercase font-weight-semibold"
                      >등록일자</span
                    >
                    | {{ resume.date }}
                  </div>
                </div>
                <div class="ms-auto">
                  <button
                    v-if="selectedResume && selectedResume.id === resume.id"
                    class="btn btn-primary btn-sm"
                    disabled
                  >
                    선택됨
                  </button>
                  <button
                    v-else
                    class="btn btn-primary btn-outline btn-sm"
                    @click="selectResume(resume)"
                  >
                    선택하기
                  </button>
                </div>
              </li>
            </ul>
          </div>

          <!-- 페이지네이션 -->
          <ul class="pagination float-end mt-3 mb-0">
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
        <div class="modal-footer">
          <button
            @click="confirm"
            type="button"
            class="btn btn-primary"
            data-bs-dismiss="modal"
          >
            지원하기
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
  </div>
</template>
<script setup>
import { ref, defineEmits } from 'vue'
import { useModalStore } from '../../../stores/modalStore'
import { useAlertStore } from '../../../stores/alertStore'

const resumes = ref([
  { id: 1, title: '이력서 제목 1', date: '2025.04.01' },
  { id: 2, title: '이력서 제목 2', date: '2025.04.01' },
  { id: 3, title: '이력서 제목 3', date: '2025.04.01' },
])

const emit = defineEmits(['confirm'])
const modalStore = useModalStore()
const selectedResume = ref(null)
const alert = useAlertStore()

const confirm = () => {
  if (selectedResume.value.length === 0) {
    alert.show('이력서를 선택해주세요.', 'danger')
    return
  }

  alert.show('프로젝트 지원이 완료되었습니다.')
  emit('confirm', selectedResume.value)
  modalStore.closeModal()
}

const selectResume = (resume) => {
  selectedResume.value = resume
}

const close = () => {
  modalStore.closeModal()
}
</script>
<style></style>
