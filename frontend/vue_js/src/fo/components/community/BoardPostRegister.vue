<template>
  <div
    class="tab-pane tab-pane-navigation active show"
    id="formsStyleWithoutBorders"
    role="tabpanel"
  >
    <h4 class="mb-3">{{ title }}</h4>
    <div class="card bg-color-grey mb-4">
      <div class="card-body">
        <form class="post-form" method="POST" enctype="multipart/form-data">
          <BoardPostRegisterContent :skillActive="isQna" />

          <!-- 버튼 영역 -->
          <div class="d-flex justify-content-end gap-2">
            <button type="submit" class="btn btn-primary" @click="registerFunc">
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

defineProps({
  title: String,
  isQna: Boolean,
})

// [추가] 등록 함수
const registerFunc = () => {}
</script>
<style></style>
