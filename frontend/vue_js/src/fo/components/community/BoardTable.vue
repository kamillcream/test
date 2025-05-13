<template>
  <div class="table-responsive">
    <table class="table table-hover table-bordered align-middle text-center">
      <thead class="table-light">
        <tr>
          <th class="th-ttl">제목</th>
          <th class="th-wm">작성자</th>
          <th class="th-wl">등록일</th>
          <th class="th-ws">조회</th>
          <th class="th-ws">댓글</th>
          <th class="th-ws">추천</th>
          <th v-if="isQna" class="th-wl">상태</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="board in boardList" :key="board.board_sq">
          <td class="text-start px-3">
            <a :href="`/${isQna ? 'qna' : 'board'}/${board.board_sq}`"
              >{{ board.board_ttl
              }}<span
                class="text-grey ml-1 px-2"
                v-show="isQna && board.answer_cnt > 0"
                ><i class="fas fa-comment-dots me-1"></i>답변
                {{ board.answer_cnt }}</span
              ></a
            >
            <BoardTags
              :skillTags="board.skill_tags"
              :normalTags="board.normal_tags"
            />
          </td>
          <td>{{ board.user_nm }}</td>
          <td>{{ board.created_at }}</td>
          <td>{{ board.view_cnt }}</td>
          <td>{{ board.comment_cnt }}</td>
          <td>{{ board.recommend_cnt }}</td>
          <td v-if="isQna">
            <span
              v-if="board.board_adopt_status_cd == 1"
              class="badge bg-secondary"
              >자체해결</span
            >
            <span
              v-if="board.board_adopt_status_cd == 2"
              class="badge bg-warning"
              >진행중</span
            >
            <span
              v-if="board.board_adopt_status_cd == 3"
              class="badge bg-success"
              >채택완료</span
            >
            <span
              v-if="board.board_adopt_status_cd == 4"
              class="badge bg-danger"
              >미해결</span
            >
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>
<script setup>
import { computed, defineProps } from 'vue'
import BoardTags from './BoardTags.vue'

const props = defineProps({ boardList: Array, isQna: Boolean })

const boardList = computed(() => props.boardList)
const isQna = computed(() => props.isQna)
</script>
<style>
.tag {
  font-size: 12px;
}
.th-ttl {
  min-width: 300px;
}
.th-wl {
  width: 100px;
}
.th-wm {
  width: 90px;
}
.th-ws {
  width: 70px;
}
</style>
