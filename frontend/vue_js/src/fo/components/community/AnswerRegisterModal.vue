<template>
  <div>
    <div class="modal-header">
      <h4 class="modal-title" id="largeModalLabel">QnA 답변 작성</h4>
      <button
        type="button"
        class="btn-close"
        data-bs-dismiss="modal"
        aria-hidden="true"
        @click="closeModal"
      >
        ×
      </button>
    </div>
    <div class="modal-body">
      <div
        class="tab-pane tab-pane-navigation active show"
        id="formsStyleWithoutBorders"
        role="tabpanel"
      >
        <div class="card bg-color-grey mb-4">
          <div class="card-body">
            <form class="post-form" method="POST" enctype="multipart/form-data">
              <BoardPostRegisterContent :skillActive="true" />
            </form>
          </div>
        </div>
      </div>
    </div>
    <div class="modal-footer">
      <button
        href="resume-edit.html"
        class="btn btn-primary"
        @click="registerFunc"
      >
        등록
      </button>
      <button
        href="resume-list.html"
        class="btn btn-outline-secondary"
        @click="closeModal"
      >
        취소
      </button>
    </div>
  </div>
</template>
<script setup>
import { defineProps } from 'vue'
import BoardPostRegisterContent from './BoardPostRegisterContent.vue'
import { useModalStore } from '@/fo/stores/modalStore'

const modalStore = useModalStore()

defineProps({
  title: String,
  isQna: Boolean,
  size: String,
})

const closeModal = () => {
  modalStore.closeModal()
}

// [추가] 등록 함수
const registerFunc = () => {}
</script>
<style></style>
