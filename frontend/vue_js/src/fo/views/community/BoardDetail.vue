<template>
  <div class="container py-5 mt-3">
    <div class="post-content ms-0">
      <BoardPost :boardInfo="boardInfo" :isQna="false" />
      <BoardComment :comments="boardInfo.comments" />
    </div>
  </div>
</template>
<script>
import BoardPost from '@/fo/components/community/BoardPost.vue'
import BoardComment from '@/fo/components/community/BoardComment.vue'

export default {
  name: 'BoardDetail',
  data() {
    return {
      // 게시글 정보
      boardInfo: {
        board_sq: 1,
        board_ttl: '사이드 프로젝트 인원 구합니다.',
        user_sq: 1,
        user_nm: '홍길동',
        created_at: '2025-04-17',
        view_cnt: 123,
        recommend_cnt: 45,
        board_description_edt:
          '저희는 새로운 사이드 프로젝트를 함께할 인원을 모집합니다. 프로젝트의 내용은 웹 애플리케이션 개발로, 주로 React와 Node.js를 사용하여 진행할 예정입니다. 관심 있는 분들의 많은 지원 부탁드립니다!',
        attachments: ['프로젝트소개.pdf', '기획안.pptx'],
        normal_tags: ['프론트엔드', '사이드 프로젝트'],
        comments: [
          {
            comment_sq: 1,
            user_sq: 3,
            user_profile_img_url: 'imgurl',
            user_nm: '박영수',
            comment_description_edt:
              '이 프로젝트 정말 흥미롭네요. 참여하고 싶습니다!',
            created_at: '2025년 1월 12일 1:38 PM',
          },
          {
            comment_sq: 2,
            user_sq: 4,
            user_profile_img_url: 'imgurl',
            user_nm: '홍길동',
            comment_description_edt:
              '참여하고 싶습니다! 방법을 알려주시면 감사하겠습니다.',
            created_at: '2025년 1월 12일 1:45 PM',
            recommend_cnt: 3,
          },
          {
            comment_sq: 3,
            user_sq: 4,
            user_profile_img_url: 'imgurl',
            user_nm: '홍길동',
            comment_description_edt:
              '참여하고 싶습니다! 방법을 알려주시면 감사하겠습니다.',
            created_at: '2025년 1월 12일 1:45 PM',
            recommend_cnt: 6,
          },
        ],
      },
    }
  },
  components: { BoardPost, BoardComment },
  props: { board_sq: String },
}
</script>
<style></style>
