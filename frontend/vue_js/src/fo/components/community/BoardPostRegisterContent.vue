<template>
  <div>
    <!-- 제목 -->
    <div class="form-group mb-3">
      <label class="form-label mb-1 text-2">제목</label>
      <input type="text" name="title" class="form-control text-3 h-auto py-2" />
    </div>

    <!-- 내용 -->
    <div class="form-group mb-4">
      <label class="form-label mb-1 text-2">내용</label>
      <div
        class="border rounded p-3"
        id="editor"
        contenteditable="true"
        style="min-height: 200px; background-color: #fff"
      >
        <p class="text-muted m-0">
          에디터 영역입니다. 여기에 내용을 입력하세요.
        </p>
      </div>
    </div>

    <!-- 태그 영역 -->
    <div class="form-group mb-4">
      <!-- 태그 + 아이콘 -->
      <div class="d-flex align-items-center mb-2">
        <span class="form-label mb-0 text-2 me-2">태그</span>
        <!-- [추가] 기술 태그 모달창 오픈 -->
        <i v-if="skillActive" class="fas fa-search text-muted"></i>
      </div>

      <!-- 입력창 (검색창 스타일) -->
      <input
        type="text"
        id="tagInput"
        class="form-control mb-2"
        placeholder="태그를 입력하세요."
        @keyup.enter="
          (addNTag($event.target.value), ($event.target.value = ''))
        "
      />

      <!-- 등록된 태그 뱃지 -->
      <div class="mt-3 d-flex flex-wrap gap-2" id="tagContainer">
        <a
          v-for="tag in skillTags"
          :key="tag"
          href="#"
          class="btn btn-rounded btn-3d btn-primary btn-sm d-flex align-items-center px-3 py-2"
        >
          {{ tag }}
          <i class="fas fa-times ms-2"></i>
        </a>
        <a
          v-for="tag in normalTags"
          :key="tag"
          href="#"
          class="btn btn-rounded btn-3d btn-light btn-sm d-flex align-items-center px-3 py-2"
        >
          {{ tag }}
          <i class="fas fa-times ms-2" @click="removeNTag(tag)"></i>
        </a>
      </div>
    </div>

    <!-- 첨부파일 등록 영역 -->
    <div class="form-group mb-4">
      <label class="form-label mb-1 text-2">첨부파일</label>
      <input
        type="file"
        name="attachment"
        class="form-control mb-2"
        accept="image/*, .pdf, .doc, .docx, .zip"
      />
    </div>
  </div>
</template>
<script setup>
import { defineProps, ref } from 'vue'

defineProps({ skillActive: Boolean })

const normalTags = ref(['프론트엔드', '포트폴리오', '감사합니다'])
const skillTags = ref(['React.js', 'Vue.js'])

// 일반태그 값 추가
const addNTag = (tag) => {
  normalTags.value.push(tag)
}

const removeNTag = (tag) => {
  let filtered = normalTags.value.filter((el) => el != tag)
  normalTags.value = filtered
  console.log(normalTags.value)
}
</script>
<style></style>
