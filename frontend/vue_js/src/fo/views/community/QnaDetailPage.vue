<template>
  <section>
    <CommonPageHeader
      title=""
      strongText="QnA 게시판"
      :breadcrumbs="[{ text: 'Home', link: '/' }, { text: '커뮤니티' }]"
    />
    <div class="container py-5 mt-3">
      <div class="post-content ms-0">
        <BoardPost :boardInfo="boardInfo" :isQna="true" />
        <!-- 답변 영역 -->
        <div class="answers-section mt-5">
          <h4 class="mb-4 font-size-m">
            답변 ({{ boardInfo.answers.length }})
          </h4>
          <div
            v-for="answer in boardInfo.answers"
            :key="answer"
            class="card p-4 mb-3 border-0 answer-box"
            @click="clickApplication"
          >
            <div class="d-flex justify-content-between align-items-center mb-2">
              <h5 class="mb-0 text-dark font-size-s">
                {{ answer.answer_ttl }}
              </h5>
              <span
                v-if="answer.answer_is_adopted_yn == 'Y'"
                class="badge bg-primary font-size-xs"
                >채택 답변</span
              >
            </div>
            <div class="d-flex justify-content-between text-muted font-size-xs">
              <div>
                <i class="far fa-user"></i> By
                <span>{{ answer.user_nm }}</span> &nbsp;&nbsp;
                <i class="far fa-calendar-alt"></i>{{ answer.created_at }}
              </div>
              <div>
                조회 {{ answer.view_cnt }} · 댓글 {{ answer.comment_cnt }} ·
                추천
                {{ answer.recommend_cnt }}
              </div>
            </div>
          </div>
        </div>
        <BoardComment :comments="boardInfo.comments" />
      </div>
    </div>
  </section>
</template>
<script setup>
import CommonPageHeader from '@/fo/components/common/CommonPageHeader.vue'
import BoardComment from '@/fo/components/community/BoardComment.vue'
import BoardPost from '@/fo/components/community/BoardPost.vue'
import AnswerDetailModal from '@/fo/components/community/AnswerDetailModal.vue'
import { useModalStore } from '@/fo/stores/modalStore'
// import { defineProps } from 'vue'

const modalStore = useModalStore()

// const props = defineProps({ board_sq: String })

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
  answers: [
    {
      answer_sq: 1,
      user_nm: '김철수',
      answer_ttl: 'React.js와 Vue.js의 차이점',
      answer_description_edt: '',
      created_at: '2025-04-16',
      view_cnt: 123,
      recommend_cnt: 45,
      comment_cnt: 3,
      answer_is_adopted_yn: 'Y',
    },
    {
      answer_sq: 2,
      user_nm: '이영희',
      answer_ttl: '어떤 프로젝트에서 React.js가 더 적합할까요?',
      answer_description_edt: '',
      created_at: '2025-04-15',
      view_cnt: 98,
      recommend_cnt: 12,
      comment_cnt: 0,
      answer_is_adopted_yn: 'N',
    },
    {
      answer_sq: 3,
      user_nm: '박민수',
      answer_ttl: '당신에게 맞는 프론트엔드 스킬',
      answer_description_edt: '',
      created_at: '2025-04-14',
      view_cnt: 32,
      recommend_cnt: 5,
      comment_cnt: 1,
      answer_is_adopted_yn: 'N',
    },
  ],
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

// 답변 작성 모달
const clickApplication = () => {
  modalStore.openModal(AnswerDetailModal, { size: 'modal-lg' })
}
</script>
<style>
.font-size-xs {
  font-size: 1.1rem;
}
.font-size-x {
  font-size: 1.3rem;
}
.font-size-m {
  font-size: 1.5rem;
}
.answer-box {
  background-color: #f4f4f4;
  border-radius: 10px;
  display: flex;
  flex-direction: column;
}
</style>
