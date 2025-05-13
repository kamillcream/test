<template>
  <div>
    <div class="modal-header">
      <h4 class="modal-title" id="largeModalLabel">QnA 답변</h4>
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
      <div class="post-content ms-0">
        <BoardPost :isAnswer="true" :boardInfo="boardInfo" />

        <BoardComment :comments="boardInfo.comments" />
      </div>
    </div>
    <div class="modal-footer">
      <a
        href="resume-list.html"
        class="btn btn-outline-secondary"
        @click="closeModal"
        >닫기</a
      >
    </div>
  </div>
</template>
<script setup>
import { useModalStore } from '@/fo/stores/modalStore'
import BoardComment from './BoardComment.vue'
import BoardPost from './BoardPost.vue'

const modalStore = useModalStore()

const closeModal = () => {
  modalStore.closeModal()
}

const boardInfo = {
  board_sq: 1,
  board_ttl: 'React.js와 Vue.js의 차이점은 무엇인가요?',
  user_sq: 1,
  user_nm: '홍길동',
  created_at: '2025-04-17',
  board_adopt_status_cd: 4,
  view_cnt: 123,
  recommend_cnt: 45,
  board_description_edt:
    'React.js와 Vue.js는 모두 인기 있는 프론트엔드 라이브러리인데, 각 기술의 주요 차이점은 무엇인가요? 두 기술 중 어떤 상황에서 어떤 것을 선택하는 것이 좋을지 궁금합니다.',
  attachments: ['React_vs_Vue_개발비교.pdf', 'React_기초_가이드.pdf'],
  skill_tags: ['React.js', 'Vue.js'],
  normal_tags: ['프론트엔드', '사이드 프로젝트'],
  comments: [
    {
      comment_sq: 1,
      user_sq: 4,
      user_profile_img_url: 'imgurl',
      user_nm: '홍길동',
      comment_description_edt:
        'React.js는 대규모 애플리케이션에서 많이 사용되는 것 같아요 Vue.js는 더 직관적이고 빠르게 시작할 수 있어서 소규모 프로젝트에서 유리할 수 있을 것 같네요',
      created_at: '2025년 1월 12일 1:38 PM',
      recommend_cnt: 6,
    },
    {
      comment_sq: 2,
      user_sq: 3,
      user_profile_img_url: 'imgurl',
      user_nm: '박영수',
      comment_description_edt:
        'Vue.js는 더 간단하고 빠르게 구현할 수 있는 것 같아요. React는 다양한 기능을 확장할 수 있어서 유연하게 사용할 수 있죠.',
      created_at: '2025년 1월 12일 1:45 PM',
      recommend_cnt: 6,
    },
  ],
}
</script>
<style></style>
