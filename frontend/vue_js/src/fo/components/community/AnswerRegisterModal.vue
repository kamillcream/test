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
              <BoardPostRegisterContent :skillActive="true" ref="registerRef" />
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
import { useAlertStore } from '@/fo/stores/alertStore'
import BoardPostRegisterContent from './BoardPostRegisterContent.vue'
import { useModalStore } from '@/fo/stores/modalStore'
import { useRoute } from 'vue-router'
import { ref } from 'vue'
import { api } from '@/axios'
import { useBoardStore } from '@/fo/stores/boardStore'

const alertStore = useAlertStore()
const modalStore = useModalStore()
const boardStore = useBoardStore()

const route = useRoute()
const boardSq = route.params.board_sq
const answerSq = ref(Number(boardStore.editSq))
const closeModal = () => {
  modalStore.closeModal()
  boardStore.resetBoard()
}

// 전달 데이터
const registerRef = ref(null)

// html 공백 파악
const isHtmlEmpty = (htmlString) => {
  // 1. 태그 제거 (텍스트만 추출)
  const textOnly = htmlString
    .replace(/<[^>]*>/g, '') // 모든 HTML 태그 제거
    .replace(/&nbsp;/gi, '') // 공백 문자 제거 (html 공백)
    .trim() // 앞뒤 공백 제거

  return textOnly === ''
}

// 등록 함수
const registerFunc = async () => {
  try {
    const reqData = registerRef.value.sendData()
    reqData.append('boardSq', Number(boardSq))

    if (reqData.get('ttl') == null || reqData.get('ttl').trim() == '') {
      alertStore.show('제목을 입력해주세요.', 'danger')
      return
    } else if (isHtmlEmpty(reqData.get('description'))) {
      alertStore.show('내용을 입력해주세요.', 'danger')
      return
    }

    if (answerSq.value != 0) {
      const res = await api.$put(`/answer/${answerSq.value}`, reqData)
      if (res.status == 'OK') {
        alertStore.show(res.message, 'success')
        modalStore.closeModal()
      } else {
        alertStore.show('답변 수정에 실패하였습니다.', 'danger')
      }
    } else {
      const res = await api.$post(`/answer`, reqData)
      if (res.status == 'CREATED') {
        alertStore.show(res.message, 'success')
        modalStore.closeModal()
      } else {
        alertStore.show('답변 등록에 실패하였습니다.', 'danger')
      }
    }
  } catch (error) {
    alertStore.show('답변 등록에 실패하였습니다.', 'danger')
  }
}
</script>
<style></style>
