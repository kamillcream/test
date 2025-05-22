<template>
  <section>
    <CommonPageHeader
      title=""
      strongText="일반 게시판"
      :breadcrumbs="[{ text: 'Home', link: '/' }, { text: '커뮤니티' }]"
    />
    <div class="container py-5 mt-3">
      <div class="post-content ms-0">
        <BoardPost :boardInfo="boardInfo" boardType="board" />
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
import BoardPost from '@/fo/components/community/BoardPost.vue'
import BoardComment from '@/fo/components/community/BoardComment.vue'
import CommonPageHeader from '@/fo/components/common/CommonPageHeader.vue'
import { onMounted, ref } from 'vue'
import { defineProps } from 'vue'
import { useAlertStore } from '@/fo/stores/alertStore'
import { api } from '@/axios'

const alertStore = useAlertStore()

const props = defineProps({ board_sq: String })

const boardInfo = ref({
  sq: 0,
  ttl: '',
  userSq: 0,
  userNm: '',
  createdAt: new Date(),
  viewCnt: 0,
  recommendCnt: 0,
  description: '',
  attachments: [],
  normalTags: [],
  comments: [],
})

// 게시글 불러오기
const getBoard = async () => {
  try {
    const res = await api.$get(`/board/${props.board_sq}`)
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
