<template>
  <div>
    <div class="row">
      <div class="col">
        <h1 class="font-weight-normal text-10 mb-0">
          <strong>이력서 목록</strong>
        </h1>
      </div>
    </div>
    <div class="row">
      <div class="col pt-2 mt-1">
        <hr class="my-4" />
      </div>
    </div>
    <div class="row">
      <div class="col">
        <ul class="simple-post-list m-0 position-relative">
          <li
            v-for="resume in resumeList"
            :key="resume.resumeSq"
            class="position-relative"
          >
            <!-- X(닫기) 버튼 -->
            <button
              class="btn btn-light btn-lg position-absolute"
              style="
                top: -6px;
                right: 0;
                color: #aaa;
                border: none;
                font-size: 24px;
                margin: 0.5rem 1rem;
                z-index: 1000;
                width: 32px;
                height: 32px;
                display: flex;
                align-items: center;
                justify-content: center;
              "
              @click="removeResume(resume.resumeSq)"
            >
              &times;
            </button>
            <div class="post-info position-relative">
              <!-- 제목 + 뱃지 -->
              <div class="d-flex align-items-center gap-2">
                <a
                  href="#"
                  class="text-6 m-0"
                  @click.prevent="openResumeDetail(resume.resumeSq)"
                >
                  {{ resume.resumeTtl }}
                </a>
                <span
                  v-if="resume.resumeIsRepresentativeYn === 'Y'"
                  class="btn btn-primary btn-sm"
                  >대표 이력서</span
                >
              </div>
              <!-- 등록일자 + 버튼 -->
              <div
                class="d-flex justify-content-between align-items-center mt-2"
              >
                <div class="post-meta text-4">
                  <span class="text-dark text-uppercase font-weight-semibold"
                    >등록일자</span
                  >
                  |{{
                    resume.resumeCreatedAtDtm
                      ?.substring(0, 10)
                      .replaceAll('-', '.') ?? resume.resumeCreatedAtDtm
                  }}
                </div>
                <div class="d-flex gap-2">
                  <a
                    v-if="resume.resumeIsRepresentativeYn !== 'Y'"
                    href="#"
                    class="btn btn-outline btn-primary btn-sm"
                    @click.prevent="setMainResume(resume.resumeSq)"
                    >대표이력서 설정</a
                  >
                  <a
                    href="#"
                    class="btn btn-outline btn-primary btn-sm"
                    @click.prevent="editResume(resume.resumeSq)"
                    >수정하기</a
                  >
                  <a
                    href="#"
                    class="btn btn-outline btn-primary btn-sm"
                    @click.prevent="copyResume(resume.resumeSq)"
                    >복사하기</a
                  >
                </div>
              </div>
            </div>
          </li>
        </ul>
        <!-- 이력서 등록하기 버튼 -->
        <div class="d-flex justify-content-end mt-4 mb-5">
          <a
            href="#"
            class="btn btn-primary px-4 py-2"
            @click.prevent="registerResume"
            >이력서 등록하기</a
          >
        </div>
        <!-- 페이지네이션: 우측 하단 정렬 -->
        <CommonPagination
          :currentPage="currentPage"
          :totalPages="totalPages"
          @update:currentPage="currentPage = $event"
        />
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { api } from '@/axios.js'
// import { useMypageStore } from '@/fo/stores/mypageStore'
import ResumeDetailModal from '@/fo/components/mypage/common/ResumeDetailModal.vue'
import CommonPagination from '@/fo/components/common/CommonPagination.vue'
import { watch } from 'vue'
import { useRouter } from 'vue-router'
import { useModalStore } from '@/fo/stores/modalStore'

const resumeList = ref([])
// const mypageStore = useMypageStore()
const modalStore = useModalStore()
const isDeleting = ref(false) // 삭제 중 상태 추가
const router = useRouter()

// 페이지네이션 설정
const size = 5
const currentPage = ref(1)
const totalPages = ref(1)

//목록 불러오기
const getResume = async () => {
  const res = await api.$get(
    `/mypage/resume/list?currentPage=${currentPage.value}&size=${size}`,
  )
  console.log('이력서 목록 응답:', res)
  if (Array.isArray(res.output)) {
    console.log(res.output)
    resumeList.value = res.output
    if (res.totalCount) {
      totalPages.value = Math.ceil(res.totalCount / size)
    }
  } else {
    console.error('이력서 목록을 불러올 수 없습니다.', res)
  }
}

onMounted(() => {
  getResume()
})

watch(currentPage, () => {
  getResume()
})

function removeResume(resumeSq) {
  if (!confirm('정말 삭제하시겠습니까?')) return
  api
    .$patch(`/mypage/resume/${resumeSq}/delete`)
    .then(() => {
      getResume()
    })
    .catch((err) => {
      alert('삭제 실패: ' + (err?.response?.data?.message || err.message))
    })
    .finally(() => {
      isDeleting.value = false
    })
}

function setMainResume(resumeSq) {
  // 버튼 여러 번 클릭 방지용: 중복 클릭 막기(선택)
  if (setMainResume.loading) return
  setMainResume.loading = true

  api
    .$patch(`/mypage/resume/representative/${resumeSq}`)

    .then(() => getResume())
    .catch((err) => {
      alert(
        '대표 이력서 설정 실패: ' +
          (err?.response?.data?.message || err.message),
      )
    })
    .finally(() => {
      setMainResume.loading = false
    })
}

function openResumeDetail(resumeSq) {
  console.log('resumeSq', resumeSq)
  modalStore.openModal(ResumeDetailModal, {
    title: '이력서 상세보기',
    size: 'modal-lg',
    resumeSq: resumeSq,
    onConfirm: () => {
      modalStore.closeModal()
    },
  })
}

//수정하기
function editResume(resumeSq) {
  router.push({ name: 'ResumeFormEdit', params: { resumeSq } })
}

// 복사 기능
function copyResume(/*id*/) {}

function registerResume() {
  router.push('/mypage/resumeform').then(() => {
    window.scrollTo({ top: 0, behavior: 'auto' })
  })
}
</script>
