<template>
  <div>
    <div class="row">
      <div class="col">
        <h1 class="font-weight-normal text-10 mb-20">
          <strong>소속 스크랩 내역</strong>
        </h1>
      </div>
    </div>
    <!-- 🔽 필터 UI 추가 영역 -->
    <div class="row align-items-center mt-3 mb-2">
      <div class="col-md-12 d-flex justify-content-end gap-2">
        <select v-model="searchType" class="form-select form-select-sm w-auto">
          <option value="all">전체</option>
          <option value="company">회사명</option>
          <option value="tag">태그</option>
          <option value="content">소개</option>
        </select>
        <input
          v-model="keyword"
          type="text"
          class="form-control form-control-sm w-auto"
          placeholder="검색어 입력"
          @keyup.enter="getScrapList"
        />
        <button
          type="button"
          class="btn btn-primary btn-sm"
          @click="getScrapList"
        >
          검색
        </button>
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
            v-for="scrap in scraps"
            :key="scrap.id"
            style="border-bottom: 1px rgb(230, 230, 230) solid"
          >
            <div class="post-info position-relative">
              <!-- 제목 + 회사명 + 지원상태 버튼 -->
              <div
                class="d-flex justify-content-between align-items-center gap-2"
              >
                <div class="d-flex gap-2">
                  <a href="#" class="text-6 m-0" @click="clickDetail(scrap)">{{
                    scrap.companyNm
                  }}</a>
                </div>
                <div class="d-flex gap-2">
                  <span
                    :class="[
                      'btn btn-sm',
                      scrap.isRecruitingYn === 'N'
                        ? 'btn-light'
                        : 'btn-primary',
                    ]"
                    >{{
                      scrap.isRecruitingYn == 'Y' ? '모집 중' : '모집 마감'
                    }}</span
                  >
                  <a
                    href="#"
                    class="btn btn-outline btn-primary btn-sm"
                    @click.prevent="removeScrap(scrap.sq)"
                    >삭제</a
                  >
                </div>
              </div>
              <!-- 직원수 -->
              <div
                class="d-flex justify-content-between align-items-center mt-2"
              >
                <div class="post-meta text-4 text-end ms-auto">
                  <span class="text-dark text-uppercase font-weight-semibold"
                    >소속 직원 수</span
                  >
                  | {{ scrap.memberCnt }}
                </div>
              </div>
              <!-- 지원자격/개업일자 -->
              <div
                class="d-flex justify-content-between align-items-center mt-2"
              >
                <div class="post-meta text-4">
                  <span class="text-dark text-uppercase font-weight-semibold"
                    >태그</span
                  >
                  | {{ scrap.tags?.join(' / ') }}
                </div>
                <div class="post-meta text-4">
                  <span class="text-dark text-uppercase font-weight-semibold"
                    >개업일자</span
                  >
                  | {{ scrap.openDt }}
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
import { api } from '@/axios'
import CommonConfirmModal from '@/fo/components/common/CommonConfirmModal.vue'
import CommonPagination from '@/fo/components/common/CommonPagination.vue'
import AffiliationRecruit from '@/fo/components/company/AffiliationRecruit.vue'
import { useAlertStore } from '@/fo/stores/alertStore'
import { useModalStore } from '@/fo/stores/modalStore'
import { onMounted, ref } from 'vue'

const searchType = ref('all')
const keyword = ref(null)
const size = 10
const currentPage = ref(1)
const totalPages = ref(1)
const totalElements = ref(1)

const alertStore = useAlertStore()
const modalStore = useModalStore()
const closeModal = () => {
  modalStore.closeModal()
}

const scraps = ref([
  {
    id: 1,
    company: 'EST Soft',
    status: '모집중',
    employeeCount: 50,
    qualification: '서울 / 신입 / 학력무관 / JAVA',
    openDate: '2025.04.30',
  },
  {
    id: 2,
    company: 'EST Soft',
    status: '모집중',
    employeeCount: 50,
    qualification: '서울 / 신입 / 학력무관 / JAVA',
    openDate: '2025.04.30',
  },
  {
    id: 3,
    company: 'EST Soft',
    status: '모집중',
    employeeCount: 50,
    qualification: '서울 / 신입 / 학력무관 / JAVA',
    openDate: '2025.04.30',
  },
  {
    id: 4,
    company: 'EST Soft',
    status: '모집 마감',
    employeeCount: 50,
    qualification: '서울 / 신입 / 학력무관 / JAVA',
    openDate: '2025.04.30',
  },
])

const getScrapList = async () => {
  try {
    const searchFilter =
      keyword.value == null || keyword.value.trim() == ''
        ? ''
        : `&searchType=${searchType.value}&keyword=${keyword.value}`
    const res = await api.$get(
      `/mypage/applications/scraps?page=${currentPage.value}&size=${size}${searchFilter}`,
    )
    if (res.status == 'OK') {
      const totalCnt = res.output.totalElements

      console.log(res)
      scraps.value = res.output.companies
      totalElements.value = totalCnt

      if (totalCnt == 0) {
        totalPages.value = 1
      } else {
        totalPages.value = Math.floor((totalCnt + size - 1) / size)
      }
    }
  } catch (error) {
    alertStore.show('지원자를 불러올 수 없습니다.', 'danger')
  }
}

const clickDetail = (afltnInfo) => {
  modalStore.openModal(AffiliationRecruit, { afltnInfo })
}

const removeScrap = (id) => {
  scraps.value = scraps.value.filter((s) => s.id !== id)
  modalStore.openModal(CommonConfirmModal, {
    title: '소속 스크랩 삭제',
    message: `해당 스크랩 내역을 삭제하시겠습니까?`,
    onConfirm: async () => {
      try {
        const res = await api.$post(`/affiliation/${id}/scrap`)
        if (res.status == 'OK') getScrapList()
      } catch (error) {
        alertStore.show('스크랩 내역 삭제에 실패했습니다.', 'danger')
      }
      closeModal()
    },
  })
}
onMounted(() => {
  getScrapList()
})
</script>

<style scoped>
.simple-post-list {
  list-style: none;
  padding: 0;
}
</style>
