<template>
  <div>
    <div class="post-header">
      <h1 class="font-weight-semi-bold mb-0 position-relative">
        <div v-if="boardType == 'qna'" class="d-flex flex-wrap gap-2 mb-2">
          <span
            v-if="boardInfo.boardAdoptStatusCd == 1501"
            class="badge bg-warning badge-xs font-size-l"
            >진행중</span
          >
          <span
            v-if="boardInfo.boardAdoptStatusCd == 1502"
            class="badge bg-success badge-xs font-size-l"
            >채택완료</span
          >
          <span
            v-if="boardInfo.boardAdoptStatusCd == 1503"
            class="badge bg-secondary badge-xs font-size-l"
            >자체해결</span
          >
          <span
            v-if="boardInfo.boardAdoptStatusCd == 1504"
            class="badge bg-danger badge-xs font-size-l"
            >미해결</span
          >
        </div>
        <div v-if="isAdopted" class="d-flex flex-wrap gap-2 mb-2">
          <span class="badge bg-primary badge-xs font-size-l">채택 답변</span>
        </div>
        <!-- 제목 + 오른쪽 아이콘들 -->
        <div class="d-flex justify-content-between align-items-start ttl-area">
          <p class="text-primary board-ttl">{{ boardInfo.ttl }}</p>
          <!-- 아이콘 버튼 -->
          <span class="post-icons d-flex ttl-icon-area">
            <!-- 조회수 버튼 추가 -->
            <button
              class="btn btn-light btn-rounded text-grey d-flex align-items-center me-2 font-size-xs"
            >
              <i class="fa-solid fa-eye font-size-s"></i>
              <span class="me-2 ms-2 text-grey">조회수</span>
              <span>{{ boardInfo.viewCnt }}</span>
            </button>
            <!-- 추천 버튼 -->
            <button
              class="btn btn-light btn-rounded text-grey d-flex align-items-center me-2 font-size-xs"
            >
              <i class="fa-regular fa-thumbs-up font-size-s"></i>
              <span class="me-2 ms-2 text-grey">추천</span>
              <span>{{ boardInfo.recommendCnt }}</span>
            </button>
            <!-- 신고 버튼 -->
            <!-- [추가] 본인 인증 로직 -->
            <button
              v-if="boardInfo.userSq != viewUsersq"
              class="btn btn-light btn-rounded text-grey d-flex align-items-center me-2 font-size-xs"
              @click="clickRepostApplication"
            >
              <i class="fa-solid fa-land-mine-on font-size-s"></i>
              <span class="me-2 ms-2 text-grey">신고</span>
            </button>
          </span>
        </div>
      </h1>
      <div class="post-meta">
        <span class="me-2"
          ><i class="far fa-user"></i> By
          <a href="#">{{ boardInfo.userNm }}</a>
        </span>
        <span
          ><i class="far fa-calendar-alt me-1"></i>
          <a href="#"> {{ formatTime(boardInfo.createdAt) }}</a></span
        >
      </div>
    </div>
    <!-- 게시글 본문 -->
    <div
      class="post-description mt-5 mb-5 text-5"
      v-html="boardInfo.description"
    ></div>
    <!-- 첨부파일 섹션 -->
    <div
      v-if="boardInfo.attachments?.length > 0"
      class="post-attachments mt-3 mb-4"
    >
      <h5 class="font-weight-bold text-grey">첨부파일</h5>
      <ul class="list-unstyled">
        <li v-for="attachment in boardInfo.attachments" :key="attachment">
          <a :href="`files/${attachment}`" download>{{ attachment }}</a>
        </li>
      </ul>
    </div>
    <!-- "태그" 제목 추가 -->
    <div
      v-if="boardInfo.normalTags.length > 0 || boardInfo.skillTags?.length > 0"
      class="post-tags mt-4"
    >
      <h5 class="font-weight-bold text-grey">태그</h5>
      <!-- 태그 제목 추가 -->

      <a
        v-for="skill_tag in boardInfo.skillTags"
        :key="skill_tag"
        href="#"
        class="btn btn-rounded btn-primary me-2"
        >{{ skill_tag.skillTagNm }}</a
      >
      <a
        v-for="normal_tag in boardInfo.normalTags"
        :key="normal_tag"
        href="#"
        class="btn btn-rounded btn-light me-2"
        >{{ normal_tag }}</a
      >
    </div>
    <div class="post-admin mt-4 text-end">
      <!-- [추가] 본인 게시글이 아닐 경우에만 보이게 -->
      <button
        v-if="boardType == 'qna' && boardInfo.userSq != viewUsersq"
        type="button"
        class="btn btn-primary me-2"
        @click="clickApplication"
      >
        답변 작성
      </button>
      <!-- [추가] 아래 버튼 4개 본인 게시글인 경우에만 보이게 -->
      <button
        v-if="boardType == 'qna'"
        type="button"
        class="btn btn-primary me-2"
      >
        자체 해결
      </button>
      <button
        v-if="boardType == 'qna'"
        type="button"
        class="btn btn-primary me-2"
      >
        미해결
      </button>
      <button class="btn btn-primary me-2" @click="editBoard">수정</button>
      <button type="button" class="btn btn-primary" @click="openConfirm">
        삭제
      </button>

      <!-- [추가] 답변글인 경우에만 && 원 QnA 게시글의 작성자 본인일 경우에만 -->
      <a v-if="boardInfo.userSq == viewUsersq" href="#" class="btn btn-primary"
        >답변 채택하기</a
      >
    </div>
  </div>
</template>
<script setup>
import { useModalStore } from '@/fo/stores/modalStore'
import { computed, defineProps, ref } from 'vue'
import AnswerRegisterModal from './AnswerRegisterModal.vue'
import CommonConfirmModal from '../common/CommonConfirmModal.vue'
import { useAlertStore } from '@/fo/stores/alertStore'
import RepostModal from './RepostModal.vue'
import { useBoardStore } from '@/fo/stores/boardStore'
import { useRouter } from 'vue-router'

const alertStore = useAlertStore()
const modalStore = useModalStore()
const boardStore = useBoardStore()

const router = useRouter()

const props = defineProps({ boardInfo: Array, boardType: String })

const boardInfo = computed(() => props.boardInfo)
const boardType = computed(() => props.boardType)

const formatTime = (createdAt) => {
  const date = new Date(createdAt)
  let year = date.getFullYear()
  let month = date.getMonth() + 1
  let day = date.getDate()
  if (month < 10) month = '0' + month
  if (day < 10) day = '0' + day

  return `${year}-${month}-${day}`
}

// 현재 사용자 시퀀스
const viewUsersq = ref(1)

// 게시글 수정 모달
const editBoard = () => {
  boardStore.setBoard(boardInfo.value)
  boardStore.editSq = boardInfo.value.sq
  console.log(boardType.value)
  if (boardType.value == 'board' || boardType.value == 'qna') {
    router.push(`/${boardType.value}/register`)
  } else if (boardType.value == 'answer') {
    modalStore.openModal(AnswerRegisterModal, { size: 'modal-lg' })
  }
}

// 삭제 컨펌 모달
const openConfirm = () => {
  modalStore.openModal(CommonConfirmModal, {
    title: '게시글 삭제',
    message: '정말 삭제하시겠습니까?',
    onConfirm: () => {
      // 성공
      alertStore.show('삭제하였습니다.', 'success')
      // 실패
      // alertStore.show('삭제에 실패하였습니다.', 'danger')
      modalStore.closeModal()
    },
  })
}

// 답변 작성 모달
const clickApplication = () => {
  modalStore.openModal(AnswerRegisterModal, { size: 'modal-lg' })
}

// 신고 모달
const clickRepostApplication = () => {
  modalStore.openModal(RepostModal, {})
}
</script>
<style>
button {
  background: inherit;
  border: none;
  box-shadow: none;
  border-radius: 0;
  padding: 0;
  overflow: visible;
  cursor: pointer;
}
.font-size-xs {
  font-size: 1rem;
}
.font-size-s {
  font-size: 1.2rem;
}
.font-size-l {
  font-size: large;
}
.ttl-area {
  flex-wrap: wrap;
}
.board-ttl {
  line-height: 1.2;
}
.ttl-icon-area {
  margin-left: auto;
}
</style>
