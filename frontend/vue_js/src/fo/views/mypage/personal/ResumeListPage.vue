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
                  @click.prevent="openResumeDetail(resume)"
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
                    v-if="!resume.isMain"
                    href="#"
                    class="btn btn-outline btn-primary btn-sm"
                    @click.prevent="setMainResume(resume.id)"
                    >대표이력서 설정</a
                  >
                  <a
                    href="#"
                    class="btn btn-outline btn-primary btn-sm"
                    @click.prevent="editResume(resume.id)"
                    >수정하기</a
                  >
                  <a
                    href="#"
                    class="btn btn-outline btn-primary btn-sm"
                    @click.prevent="copyResume(resume.id)"
                    >복사하기</a
                  >
                </div>
              </div>
            </div>
          </li>
        </ul>
        <!-- 이력서 상세 모달 -->
        <ResumeDetailModal
          v-if="showDetailModal"
          :resume="selectedResume"
          @close="closeResumeDetail"
        />
        <!-- 이력서 등록하기 버튼 -->
        <div class="d-flex justify-content-end mt-4 mb-5">
          <a
            href="#"
            class="btn btn-primary px-4 py-2"
            @click.prevent="registerResume"
            >이력서 등록하기</a
          >
        </div>
        <!-- 페이징 -->
        <div class="mt-5 py-5">
          <ul class="pagination float-end">
            <li class="page-item">
              <a class="page-link" href="#"
                ><i class="fas fa-angle-left"></i
              ></a>
            </li>
            <li class="page-item active">
              <a class="page-link" href="#">1</a>
            </li>
            <li class="page-item"><a class="page-link" href="#">2</a></li>
            <li class="page-item"><a class="page-link" href="#">3</a></li>
            <li class="page-item">
              <a class="page-link" href="#"
                ><i class="fas fa-angle-right"></i
              ></a>
            </li>
          </ul>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { api } from '@/axios.js'
import { useMypageStore } from '@/fo/stores/mypageStore'
import ResumeDetailModal from '@/fo/components/mypage/common/ResumeDetailModal.vue'
//import { useRouter } from 'vue-router'

const resumeList = ref([])
const showDetailModal = ref(false)
const selectedResume = ref(null)
const mypageStore = useMypageStore()
const isDeleting = ref(false) // 삭제 중 상태 추가
//const router = useRouter()

onMounted(async () => {
  try {
    const res = await api.$get('/mypage/resume/list')
    console.log('이력서 목록 응답:', res)
    if (Array.isArray(res)) {
      resumeList.value = res
    } else {
      console.error('이력서 목록이 배열이 아닙니다:', res)
    }
  } catch (error) {
    console.error('이력서 목록 조회 실패:', error)
  }
})

function removeResume(resumeSq) {
  if (!confirm('정말 삭제하시겠습니까?')) return
  api
    .$patch(`/mypage/resume/${resumeSq}/delete`)
    .then(() => {
      // 삭제 후 목록에서 제거
      resumeList.value = resumeList.value.filter((r) => r.resumeSq !== resumeSq)
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
    .then(() => {
      // 반드시 목록 새로고침으로만 대표 상태를 반영!
      fetchResumeList()
    })
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

// 목록 새로고침 함수
async function fetchResumeList() {
  try {
    const res = await api.$get('/mypage/resume/list')
    if (Array.isArray(res)) {
      resumeList.value = res
    }
  } catch (error) {
    console.error('이력서 목록 조회 실패:', error)
  }
}

function openResumeDetail(resume) {
  mypageStore.modalStack.push({
    component: ResumeDetailModal,
    props: { resume },
    type: 'resumeDetail',
  })
  mypageStore.isOpen = true
}

function closeResumeDetail() {
  selectedResume.value = null
  showDetailModal.value = false
}

function editResume(/*id*/) {
  // 수정 페이지 이동
}

function copyResume(/*id*/) {
  // 복사 기능
}

function registerResume() {
  // router.push('/mypage/resumeform').then(() => {
  //   window.scrollTo({ top: 0, behavior: 'auto' })
  // })
}
</script>
