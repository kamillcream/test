<template>
  <div>
    <!-- 제목 -->
    <div class="form-group mb-3">
      <label class="form-label mb-1 text-2">제목</label>
      <input
        v-model="ttl"
        type="text"
        name="title"
        class="form-control text-3 h-auto py-2"
        placeholder="제목을 입력하세요."
      />
    </div>

    <!-- 내용 -->
    <div class="form-group mb-4">
      <label class="form-label mb-1 text-2">내용</label>
      <QuillEditor
        v-model:content="description"
        content-type="html"
        class="editor text-muted border rounded p-3 edt-area"
        id="editor"
        ref="editorRef"
        :options="options"
      />
    </div>

    <!-- 태그 영역 -->
    <div class="form-group mb-4">
      <!-- 태그 + 아이콘 -->
      <div class="d-flex align-items-center mb-2">
        <span class="form-label mb-0 text-2 me-2">태그</span>
        <!-- 기술 태그 모달창 오픈 -->
        <i
          v-if="skillActive"
          class="fas fa-search text-muted"
          @click.prevent="openSkillModal"
        ></i>
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
          {{ tag.skillTagNm }}
          <i class="fas fa-times ms-2" @click.prevent="removeSTag(tag)"></i>
        </a>
        <a
          v-for="tag in normalTags"
          :key="tag"
          href="#"
          class="btn btn-rounded btn-3d btn-light btn-sm d-flex align-items-center px-3 py-2"
        >
          {{ tag }}
          <i class="fas fa-times ms-2" @click.prevent="removeNTag(tag)"></i>
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
import { useModalStore } from '@/fo/stores/modalStore'
import { defineProps, defineExpose, ref, watchEffect } from 'vue'
import { QuillEditor } from '@vueup/vue-quill'
import '@vueup/vue-quill/dist/vue-quill.snow.css'
import '@vueup/vue-quill/dist/vue-quill.bubble.css'
import { useBoardStore } from '@/fo/stores/boardStore'
import SkillTagModal from './SkillTagModal.vue'

defineProps({ skillActive: { type: Boolean, default: false } })

const modalStore = useModalStore()
const boardStore = useBoardStore()

const ttl = ref(boardStore.boardData.ttl)
const normalTags = ref(boardStore.boardData.normalTags)
const skillTags = ref(boardStore.boardData.skillTags)
const editorRef = ref(null)
const description = ref(boardStore.boardData.description)
const attachments = ref(boardStore.boardData.attachments)

const options = {
  placeholder: '내용을 입력해주세요.',
  theme: 'snow',
  contentType: 'html',
}

const openSkillModal = async () => {
  modalStore.openModal(SkillTagModal, {
    skillTags: [...skillTags.value],
    onConfirm: onSkillsConfirmed,
  })
}

const onSkillsConfirmed = (skills) => {
  boardStore.boardData.skillTags = skills
  skillTags.value = [...skills]
}

const addNTag = (tag) => {
  normalTags.value.push(tag)
}

const removeNTag = (tag) => {
  let filtered = normalTags.value.filter((el) => el != tag)
  normalTags.value = filtered
}

const removeSTag = (tag) => {
  let filtered = skillTags.value.filter((el) => el != tag)
  skillTags.value = filtered
}

// 전달 데이터
const sendData = () => {
  return {
    userSq: 10,
    ttl: ttl.value,
    description: description.value,
    normalTags: [...normalTags.value],
    skillTags: [...skillTags.value],
    attachments: [...attachments.value],
  }
}

defineExpose({ sendData })

watchEffect(() => {
  boardStore.boardData.ttl = ttl.value
  boardStore.boardData.description = description.value
  boardStore.boardData.normalTags = normalTags.value
  boardStore.boardData.skillTags = skillTags.value
  boardStore.boardData.attachments = attachments.value
})
</script>
<style>
.edt-area {
  min-height: 200px;
  background-color: #fff;
  min-height: 200px;
}
</style>
