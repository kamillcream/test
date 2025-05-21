<template>
  <div
    class="tab-pane tab-pane-navigation active show"
    id="formsStyleWithoutBorders"
    role="tabpanel"
  >
    <h4 class="mb-3">{{ props.title }}</h4>
    <div class="card bg-color-grey mb-4">
      <div class="card-body">
        <form class="post-form" method="POST" enctype="multipart/form-data">
          <BoardPostRegisterContent :skillActive="props.isQna" />

          <!-- 버튼 영역 -->
          <div class="d-flex justify-content-end gap-2">
            <button type="button" class="btn btn-primary" @click="insertBoard">
              등록
            </button>
            <button
              type="button"
              class="btn btn-light"
              @click="this.$router.go(-1)"
            >
              취소
            </button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>
<script setup>
import { defineProps } from 'vue'
import BoardPostRegisterContent from './BoardPostRegisterContent.vue'
import { api } from '@/axios'
import { useAlertStore } from '@/fo/stores/alertStore'

const alertStore = useAlertStore()

const props = defineProps({
  title: String,
  isQna: Boolean,
})

// [추가] 등록 함수
const insertBoard = async () => {
  try {
    const res = await api.$post(`/${props.isQna ? 'qna' : 'board'}`, {
      // title: title.value,
      // description: description.value,
    })
    console.log(res)
  } catch (error) {
    alertStore.show('게시글 등록에 실패하였습니다.', 'danger')
  }
}
</script>
<style></style>
