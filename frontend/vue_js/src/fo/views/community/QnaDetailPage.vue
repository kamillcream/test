<template>
  <section>
    <CommonPageHeader
      title=""
      strongText="QnA 게시판"
      :breadcrumbs="[{ text: 'Home', link: '/' }, { text: '커뮤니티' }]"
    />

    <div class="container py-5 mt-3">
      <div class="post-content ms-0">
        <BoardPost :boardInfo="boardInfo" boardType="qna" />
        <!-- 답변 영역 -->
        <div class="answers-section mt-5">
          <h4 class="mb-4" style="font-size: 1.5rem">
            답변 ({{ boardInfo.answers.length }})
          </h4>
          <div
            v-for="answer in boardInfo.answers"
            :key="answer"
            class="card p-4 mb-3 border-0"
            style="
              background-color: #f4f4f4;
              border-radius: 10px;
              display: flex;
              flex-direction: column;
            "
            @click="clickApplication(answer.sq)"
          >
            <div
              class="d-flex justif y-content-between align-items-center mb-2"
            >
              <h5 class="mb-0 text-dark" style="font-size: 1.3rem">
                {{ answer.ttl || '삭제된 답변입니다.' }}
              </h5>
              <span
                v-if="answer.isAdoptedYn == 'Y'"
                class="badge bg-primary"
                style="font-size: 1.1rem"
                >채택 답변</span
              >
            </div>
            <div
              class="d-flex justify-content-between text-muted"
              style="font-size: 1.1rem"
              v-if="answer.isDeletedYn == 'N'"
            >
              <div>
                <i class="far fa-user"></i> By
                <span>{{ answer.userNm }}</span> &nbsp;&nbsp;
                <i class="far fa-calendar-alt"></i>{{ answer.createdAt }}
              </div>
              <div>
                조회 {{ answer.viewCnt }} · 댓글 {{ answer.commentCnt }} · 추천
                {{ answer.recommendCnt }}
              </div>
            </div>
          </div>
        </div>
        <BoardComment
          :comments="boardInfo.comments"
          :isAnswer="false"
          :getBoard="getBoard"
        />
      </div>
    </div>
  </section>
</template>
<script setup>
import BoardComment from '@/fo/components/community/BoardComment.vue'
import BoardPost from '@/fo/components/community/BoardPost.vue'
import AnswerDetailModal from '@/fo/components/community/AnswerDetailModal.vue'
import { useModalStore } from '@/fo/stores/modalStore'
import CommonPageHeader from '@/fo/components/common/CommonPageHeader.vue'
import { defineProps, ref } from 'vue'
import { api } from '@/axios'
import { useAlertStore } from '@/fo/stores/alertStore'
import { onMounted } from 'vue'

const alertStore = useAlertStore()

const modalStore = useModalStore()

const props = defineProps({ board_sq: String })

const boardInfo = ref({
  attachments: [],
  skillTags: [],
  normalTags: [],
  answers: [],
  comments: [],
})

// 답변 모달
const clickApplication = (sq) => {
  modalStore.openModal(AnswerDetailModal, { size: 'modal-lg', answerSq: sq })
}

// 게시글 불러오기
const getBoard = async () => {
  try {
    const res = await api.$get(`/qna/${props.board_sq}`)
    console.log(res)
    if (res) {
      boardInfo.value = res.output
      console.log(boardInfo.value)
    }
  } catch (error) {
    alertStore.show('게시글을 불러올 수 없습니다.', 'danger')
  }
}

const addViewCnt = async () => {
  try {
    const res = await api.$patch(`/board/${props.board_sq}/increment-view`)
    console.log('조회수 증가 실행')
    if (res) {
      console.log(res)
    }
  } catch (error) {
    console.error
  }
}

onMounted(() => {
  addViewCnt()
  getBoard()
})
</script>
<style></style>
