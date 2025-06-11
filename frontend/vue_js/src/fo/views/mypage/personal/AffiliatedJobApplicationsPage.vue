<template>
  <div>
    <div class="row">
      <div class="col">
        <h1 class="font-weight-normal text-10 mb-20">
          <strong>소속 공고 지원 현황</strong>
        </h1>
      </div>
    </div>

    <!-- 🔽 필터 UI 추가 영역 -->
    <div class="row align-items-center mt-3 mb-2">
      <!-- 좌측 토글 버튼 -->
      <div class="col-md-6 d-flex gap-2">
        <button
          v-for="filter in filters"
          :key="filter.type"
          class="btn btn-primary fw-bold px-4 py-2 d-flex align-items-center gap-2 fs-6"
          :class="{ active: readType === filter.type }"
          @click="setFilter(filter.type)"
        >
          {{ filter.label }}
          <span class="badge bg-white text-primary fw-bold px-2 py-1">{{
            filter.count
          }}</span>
        </button>
      </div>

      <!-- 우측 셀렉트 + 검색 -->
      <div class="col-md-6 d-flex justify-content-end gap-2">
        <select v-model="searchType" class="form-select form-select-sm w-auto">
          <option value="all">전체</option>
          <option value="title">이력서 제목</option>
          <option value="name">회사명</option>
          <option value="greeting">인사말</option>
        </select>
        <input
          v-model="keyword"
          type="text"
          class="form-control form-control-sm w-auto"
          placeholder="검색어 입력"
          @keyup.enter="getApplies"
        />
        <button class="btn btn-primary btn-sm" @click="getApplies">검색</button>
      </div>
    </div>
    <!-- 🔼 필터 UI 끝 -->

    <div class="row">
      <div class="col pt-2 mt-1">
        <hr class="my-4" />
      </div>
    </div>

    <div class="row">
      <div class="col">
        <ul class="simple-post-list m-0 position-relative">
          <li
            v-for="apply in applies"
            :key="apply.applicationSq"
            style="border-bottom: 1px rgb(230, 230, 230) solid"
          >
            <div class="post-info position-relative">
              <!-- 제목 + 회사명 + 지원상태 버튼 -->
              <div
                class="d-flex justify-content-between align-items-center gap-2"
              >
                <div class="d-flex gap-2">
                  <button
                    type="button"
                    class="text-6 m-0 text-primary"
                    @click="openDetailModal(apply.applicationSq)"
                  >
                    {{ apply.companyNm }}
                  </button>
                </div>
                <div class="d-flex gap-2">
                  <template v-if="apply.isDeleted == 'Y'">
                    <span class="btn btn-light btn-sm">지원 취소 완료</span>
                  </template>
                  <template v-else-if="apply.statusCd == 501">
                    <span class="btn btn-primary btn-sm">지원중</span>
                    <button
                      type="button"
                      class="btn btn-outline btn-primary btn-sm"
                      @click.prevent="cancelApply(apply.applicationSq)"
                    >
                      지원 취소
                    </button>
                  </template>
                  <template v-else>
                    <span
                      class="btn btn-light btn-sm"
                      v-if="apply.statusCd == 502"
                      >합격</span
                    >
                    <span
                      class="btn btn-light btn-sm"
                      v-if="apply.statusCd == 503"
                      >불합격</span
                    >
                  </template>
                </div>
              </div>

              <!-- 지원일자 + 지원자 수 -->
              <div
                class="d-flex justify-content-between align-items-center mt-2"
              >
                <div class="post-meta text-4">
                  <span class="text-dark text-uppercase font-weight-semibold"
                    >지원일자</span
                  >
                  | {{ convertDate(apply.createdAt) }}
                </div>
                <div class="post-meta text-4">
                  <span class="text-dark text-uppercase font-weight-semibold"
                    >지원자 수</span
                  >
                  | {{ apply.applicantCnt }}
                </div>
              </div>
              <!-- 지원 이력서 + 열람일자 -->
              <div
                class="d-flex justify-content-between align-items-center mt-2"
              >
                <div class="post-meta text-4">
                  <span class="text-dark text-uppercase font-weight-semibold"
                    >지원 이력서</span
                  >
                  | {{ apply.resumeTtl }}
                </div>
                <div class="post-meta text-4">
                  <span class="text-dark text-uppercase font-weight-semibold"
                    >열람일자</span
                  >
                  | {{ apply.readAt ? convertDate(apply.readAt) : '미열람' }}
                </div>
              </div>
            </div>
          </li>
        </ul>

        <!-- 페이징 -->
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
import { onMounted, ref, watch } from 'vue'
import { useModalStore } from '@/fo/stores/modalStore'
import AffiliationRequestDetailModal from '@/fo/components/mypage/personal/AffiliationRequestDetailModal.vue'
import { api } from '@/axios'
import { useAlertStore } from '@/fo/stores/alertStore'
import CommonConfirmModal from '@/fo/components/common/CommonConfirmModal.vue'
import CommonPagination from '@/fo/components/common/CommonPagination.vue'

const modalStore = useModalStore()

const readType = ref('all')
const searchType = ref('all')
const keyword = ref(null)
const size = 10
const currentPage = ref(1)
const totalPages = ref(1)
const totalElements = ref(0)
const readElements = ref(0)
const unreadElements = ref(0)

const alertStore = useAlertStore()

const filters = ref([
  { type: 'all', label: '전체', count: totalElements },
  { type: 'read', label: '열람', count: readElements },
  { type: 'unread', label: '미열람', count: unreadElements },
])

const applies = ref([])

const getApplies = async () => {
  try {
    const searchFilter =
      keyword.value == null || keyword.value.trim() == ''
        ? ''
        : `&searchType=${searchType.value}&keyword=${keyword.value}`
    const readFilter =
      readType.value == null || readType.value == 'all'
        ? ''
        : `&readType=${readType.value}`
    const res = await api.$get(
      `/mypage/applications/user?page=${currentPage.value}&size=${size}${searchFilter}${readFilter}`,
    )

    if (res.status == 'OK') {
      const totalCnt = res.output.totalElements
      const unreadCnt = res.output.totalElements - res.output.readElements
      const readCnt = res.output.readElements

      applies.value = res.output.applies
      totalElements.value = totalCnt
      readElements.value = readCnt
      unreadElements.value = unreadCnt

      if (!totalCnt || !size || size <= 0) {
        totalPages.value = 1
      } else {
        totalPages.value = Math.floor((totalCnt + size - 1) / size)
      }

      if (readType.value == 'read') {
        if (!readCnt || !size || size <= 0) {
          totalPages.value = 1
        } else {
          totalPages.value = Math.floor((readCnt + size - 1) / size)
        }
      } else if (readType.value == 'unread') {
        if (!unreadCnt || !size || size <= 0) {
          totalPages.value = 1
        } else {
          totalPages.value = Math.floor((unreadCnt + size - 1) / size)
        }
      }
    }
  } catch (error) {
    alertStore.show('지원 현황을 불러올 수 없습니다.', 'danger')
  }
}

const convertDate = (createdAt) => {
  const date = new Date(createdAt)
  const year = date.getFullYear()
  let month = date.getMonth() + 1
  let day = date.getDate()

  if (month < 10) month = '0' + month
  if (day < 10) day = '0' + day

  return `${year}.${month}.${day}`
}

function setFilter(type) {
  readType.value = type
  getApplies()
}

function cancelApply(id) {
  modalStore.openModal(CommonConfirmModal, {
    title: '지원 상태 변경',
    message: `해당 소속에 지원 취소하시겠습니까?`,
    onConfirm: async () => {
      try {
        const res = await api.$patch(`/mypage/applications/${id}`)
        if (res.status == 'OK') {
          alertStore.show('지원 취소되었습니다.', 'success')
          getApplies()
        }
      } catch (error) {
        alertStore.show('지원 취소에 실패했습니다.', 'danger')
      }
      modalStore.closeModal()
    },
  })
}

function openDetailModal(applicationSq) {
  modalStore.openModal(AffiliationRequestDetailModal, {
    applicationSq,
  })
}

watch(totalElements, () => {
  unreadElements.value = totalElements.value - readElements.value
})

watch(readElements, () => {
  unreadElements.value = totalElements.value - readElements.value
})

onMounted(() => {
  getApplies()
})
</script>

<style scoped>
.simple-post-list {
  list-style: none;
  padding: 0;
}
</style>
