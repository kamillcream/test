<template>
  <div>
    <div class="post-comments mt-5 post-comments">
      <h4 class="mb-3 font-size-15">댓글 ({{ props.comments.length }})</h4>
      <ul class="comments">
        <li v-for="comment in props.comments" :key="comment">
          <div class="comment">
            <div
              class="img-thumbnail img-thumbnail-no-borders d-none d-sm-block"
            >
              <img
                class="avatar"
                alt=""
                :src="`${comment.userProfileImgUrl}`"
              />
            </div>
            <div class="comment-block font-size-12">
              <div class="comment-arrow"></div>
              <!-- 이름 + 신고/하트 -->
              <div
                class="d-flex justify-content-between align-items-center mb-2"
              >
                <span class="comment-by text-primary font-size-13">
                  <strong>{{ comment.userNm }}</strong>
                </span>
                <!-- 작성자 본인일 경우 -->
                <!-- [추가] 본인 인증 로직 -->
                <span v-if="comment.user_sq == 3" class="comment-icons d-flex">
                  <button href="#" class="text-danger me-2 font-size-10">
                    <span class="ms-2 text-primary">수정</span>
                  </button>
                  <button
                    href="#"
                    class="text-danger font-size-10"
                    @click="openDeleteConfirm"
                  >
                    <span class="ms-2 text-primary">삭제</span>
                  </button>
                </span>
                <!-- 작성자 아닌 경우 -->
                <span v-else class="comment-icons d-flex">
                  <button class="text-danger me-2 font-size-10">
                    <span class="ms-2 text-primary"
                      >추천 {{ comment.recommendCnt }}</span
                    >
                  </button>
                  <button
                    class="text-danger font-size-10"
                    @click="clickRepostApplication"
                  >
                    <span class="ms-2 text-primary">신고</span>
                  </button>
                </span>
              </div>
              <!-- 내용 -->
              <p class="font-size-12">
                {{ comment.description }}
              </p>
              <!-- 날짜 + 수정/삭제 (홍길동과 동일하게 float-end 사용) -->
              <span class="date float-end font-size-11">
                {{ formatTime(comment.createdAt) }}
              </span>
            </div>
          </div>
        </li>
      </ul>
    </div>
    <div class="post-block mt-5 post-leave-comment">
      <form
        class="contact-form p-4 rounded bg-color-grey"
        action="php/contact-form.php"
        method="POST"
        @submit.prevent="openRegisterConfirm"
      >
        <div class="p-2">
          <div class="row">
            <label class="form-label font-weight-bold text-dark text-5 mb-3"
              >댓글 남기기</label
            >
            <div class="form-group col-11">
              <input
                v-model="description"
                type="text"
                maxlength="5000"
                data-msg-required="댓글을 입력해주세요."
                class="form-control"
                name="message"
                required=""
              />
            </div>
            <div class="form-group col-1">
              <input
                type="submit"
                value="댓글 작성"
                class="btn btn-primary text-3"
                data-loading-text="로딩 중..."
              />
            </div>
          </div>
          <div class="row"></div>
        </div>
      </form>
    </div>
  </div>
</template>
<script setup>
import { useAlertStore } from '@/fo/stores/alertStore'
import { useModalStore } from '@/fo/stores/modalStore'
import { defineProps, ref } from 'vue'
import CommonConfirmModal from '../common/CommonConfirmModal.vue'
import RepostModal from './RepostModal.vue'
import { api } from '@/axios'
import { useRoute } from 'vue-router'

const alertStore = useAlertStore()

const modalStore = useModalStore()
const route = useRoute()
const boardSq = route.params.board_sq

const formatTime = (createdAt) => {
  const date = new Date(createdAt)
  let year = date.getFullYear()
  let month = date.getMonth() + 1
  let day = date.getDate()
  let hour = date.getHours()
  let minute = date.getMinutes()
  let timePeriod = 'am'
  if (hour > 12) {
    hour += -12
    timePeriod = 'pm'
  }
  if (minute < 10) minute = '0' + minute

  return `${year}년 ${month}월 ${day}일 ${hour}:${minute} ${timePeriod}`
}

const props = defineProps({
  comments: Array,
  isAnswer: Boolean,
  answerSq: Number,
  getBoard: Function,
})

const description = ref('')

// 삭제 컨펌 모달
const openDeleteConfirm = () => {
  modalStore.openModal(CommonConfirmModal, {
    title: '게시글 삭제',
    message: '정말 삭제하시겠습니까?',
    onConfirm: () => {
      // 성공
      alertStore.show('삭제하였습니다.', 'success')
      // 실패
      // alertStore.show('삭제에 실패하였습니다.', 'danger')
      modalStore.closeModal()
    },
  })
}

// 댓글 등록 컨펌 모달
const openRegisterConfirm = () => {
  console.log(props.boardType)
  modalStore.openModal(CommonConfirmModal, {
    title: '댓글 등록',
    message: '댓글을 등록하시겠습니까?',
    onConfirm: async () => {
      try {
        if (description.value == null || description.value.trim() == '') {
          alertStore.show('내용을 입력해주세요.', 'danger')
          return
        }

        const res = await api.$post(`/comment`, {
          userSq: 4,
          boardSq: props.isAnswer ? null : boardSq,
          answerSq: props.isAnswer ? props.answerSq : null,
          description: description.value,
        })

        if (res.status == 'CREATED') {
          alertStore.show(res.message, 'success')
          props.getBoard()
        } else {
          alertStore.show('댓글 등록에 실패하였습니다.', 'danger')
        }
      } catch (error) {
        alertStore.show('댓글 등록에 실패하였습니다.', 'danger')
      }
      modalStore.closeModal()
    },
  })
}

// 신고 모달
const clickRepostApplication = () => {
  modalStore.openModal(RepostModal, {})
}
</script>
<style>
button {
  background: inherit;
  border: none;
  box-shadow: none;
  border-radius: 0;
  padding: 0;
  overflow: visible;
  cursor: pointer;
}
.font-size-10 {
  font-size: 1rem;
}
.font-size-11 {
  font-size: 1.1rem;
}
.font-size-12 {
  font-size: 1.2rem;
}
.font-size-13 {
  font-size: 1.3rem;
}
.font-size-15 {
  font-size: 1.5rem;
}
</style>
