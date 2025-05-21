<template>
  <div class="modal-overlay">
    <div class="modal-content">
      <div class="modal-header">
        <span class="modal-title">학력 검색</span>
        <button class="modal-close" @click="close">×</button>
      </div>
      <div class="modal-tabs">
        <button
          :class="{ active: tab === 'high' }"
          @click="onTabChange('high')"
        >
          고등학교
        </button>
        <button
          :class="{ active: tab === 'univ' }"
          @click="onTabChange('univ')"
        >
          대학교
        </button>
      </div>
      <div class="modal-search">
        <input
          v-model="search"
          @keyup.enter="fetchSchools"
          placeholder="학교명을 입력하세요"
        />
        <button class="modal-search-btn" @click="fetchSchools">
          <svg width="18" height="18" fill="none" viewBox="0 0 18 18">
            <circle cx="8" cy="8" r="7" stroke="#fff" stroke-width="2" />
            <path
              d="M13 13l3 3"
              stroke="#fff"
              stroke-width="2"
              stroke-linecap="round"
            />
          </svg>
        </button>
      </div>
      <div class="modal-list">
        <div v-if="schools.length">
          <div v-for="school in schools" :key="school.id" class="modal-item">
            <div>
              <div class="school-name">{{ school.name }}</div>
              <div class="school-type">
                {{ tab === 'high' ? '고등학교' : '대학교' }}
              </div>
            </div>
            <button class="modal-add-btn" @click="addSchool(school)">
              추가
            </button>
          </div>
        </div>
        <div v-else class="modal-empty">검색 결과가 없습니다.</div>
      </div>
      <div class="modal-pagination">
        <button :disabled="page === 1" @click="prevPage">&lt;</button>
        <button
          v-for="p in totalPages"
          :key="p"
          :class="{ active: page === p }"
          @click="goPage(p)"
        >
          {{ p }}
        </button>
        <button :disabled="page === totalPages" @click="nextPage">&gt;</button>
      </div>
      <div class="modal-footer">
        <button class="modal-footer-close" @click="close">닫기</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { watch } from 'vue'
import { ref, defineProps } from 'vue'
import { useModalStore } from '@/fo/stores/modalStore'
import axios from 'axios'

const props = defineProps(['onComplete'])
const modalStore = useModalStore()

const tab = ref('high')
const search = ref('')
const schools = ref([])
const page = ref(1)
const totalPages = ref(1) // 실제 API 연동시 변경
const perPage = 10

let allContent = ref([])

const fetchSchools = async () => {
  const apiKey = '28c12cecb3e103d5b10acd6a0e76209f'
  const gubun = tab.value === 'high' ? 'high_list' : 'univ_list'
  const keyword = search.value.trim()

  try {
    const res = await axios.get(
      'https://www.career.go.kr/cnet/openapi/getOpenApi',
      {
        params: {
          apiKey,
          svcType: 'api',
          svcCode: 'SCHOOL',
          contentType: 'json',
          gubun,
          searchSchulNm: keyword,
        },
      });
      console.log('axios 요청 성공:', res);
    // content를 항상 배열로 변환
    let content = res.data?.dataSearch?.content
    if (!content) {
      content = []
    } else if (!Array.isArray(content)) {
      content = [content] // 객체 하나만 온 경우도 배열로 감싸줌
    }
    allContent.value = content; // 전체 결과 저장
    page.value = 1 //검색할 때는 항상 1페이지로

    // allContent.value에서 schoolName 기준으로 중복 제거
    const uniqueSchools = [];
    const seen = new Set();
    for (const item of allContent.value) {
      if (!seen.has(item.schoolName)) {
        seen.add(item.schoolName);
        uniqueSchools.push(item);
      }
    }
    schools.value = uniqueSchools
      .slice((page.value - 1) * perPage, page.value * perPage)
      .map((item, idx) => ({
        id: item.seq || idx,
        name: item.schoolName,
      }))
    totalPages.value = Math.max(1, Math.ceil(allContent.value.length / perPage))
  } catch (error) {
    console.error('학교 검색 오류:', error);
    if (error.response) {
      console.log('에러 응답:', error.response.data);
    }
    schools.value = []
  }
};


//페이지 변경 시 schools.value 갱신
watch(page, () => {
  schools.value = allContent.value
    .slice((page.value - 1) * perPage, page.value * perPage)
    .map((item, idx) => ({
      id: item.seq || idx,
      name: item.schoolName,
    }))
})

const addSchool = (school) => {
  props.onComplete &&
    props.onComplete({
      school: school.name,
      status: tab.value === 'high' ? '고등학교' : '대학교',
      period: '',
    })
  close()
}
const close = () => modalStore.closeModal()
const prevPage = () => {
  if (page.value > 1) page.value--
}
const nextPage = () => {
  if (page.value < totalPages.value) page.value++
}
const goPage = (p) => {
  page.value = p
}

const onTabChange = (newTab) => {
  tab.value = newTab
  search.value = ''
  schools.value = []
  allContent.value = []
  page.value = 1
  totalPages.value = 1
}
</script>

<style scoped>
.modal-overlay {
  position: fixed;
  left: 0;
  top: 0;
  width: 100vw;
  height: 100vh;
  background: rgba(0, 0, 0, 0.3);
  z-index: 1050;
  display: flex;
  align-items: center;
  justify-content: center;
}
.modal-content {
  background: #fff;
  border-radius: 12px;
  width: 520px;
  max-width: 98vw;
  box-shadow: 0 2px 16px rgba(0, 0, 0, 0.15);
  padding: 0;
  position: relative;
  display: flex;
  flex-direction: column;
}
.modal-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 28px 36px 12px 36px;
  border-bottom: 1px solid #eee;
}
.modal-title {
  font-size: 21px;
  font-weight: bold;
  color: #222;
}
.modal-close {
  background: none;
  border: none;
  font-size: 28px;
  cursor: pointer;
  color: #888;
  line-height: 1;
  transition: color 0.2s;
}
.modal-close:hover {
  color: var(--primary);
}
.modal-tabs {
  display: flex;
  gap: 5px;
  padding: 18px 36px 0 36px;
  justify-content: flex-start;
}
.modal-tabs button {
  flex: 0 1 auto;
  padding: 8px 20px;
  border: none;
  background: #f5f5f5;
  color: #333;
  font-weight: 500;
  border-radius: 4px 4px 0 0;
  cursor: pointer;
  font-size: 14px;
  transition:
    background 0.2s,
    color 0.2s;
}
.modal-tabs button.active {
  background: var(--primary);
  color: #fff;
  border-bottom: 2.5px solid var(--primary);
}
.modal-search {
  display: flex;
  align-items: center;
  gap: 0;
  padding: 14px 36px;
}
.modal-search input {
  flex: 1;
  padding: 11px 15px;
  border: 1px solid #ddd;
  border-right: none;
  border-radius: 4px 0 0 4px;
  font-size: 16px;
  height: 42px;
  box-sizing: border-box;
}
.modal-search-btn {
  background: var(--primary);
  color: #fff;
  border-radius: 0 4px 4px 0;
  width: 42px;
  height: 42px;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  border: 1px solid var(--primary);
  border-left: none;
  transition: background 0.2s;
  font-size: 18px;
  box-sizing: border-box;
}
.modal-search-btn:hover {
  background: var(--primary);
}
.modal-search-btn svg {
  display: block;
}
.modal-search-btn svg circle,
.modal-search-btn svg path {
  stroke: #fff;
  transition: stroke 0.2s;
}
.modal-search-btn:hover svg circle,
.modal-search-btn:hover svg path {
  stroke: #fff;
}
.modal-list {
  max-height: 260px;
  overflow-y: auto;
  padding: 0 36px;
}
.modal-item {
  display: flex;
  align-items: center;
  justify-content: space-between;
  border-bottom: 1px solid #f0f0f0;
  padding: 18px 0;
}
.school-name {
  font-weight: 500;
  color: #222;
  font-size: 16px;
}
.school-type {
  color: #888;
  font-size: 14px;
  margin-top: 2px;
}
.modal-add-btn {
  background: var(--primary);
  color: #fff;
  border: none;
  border-radius: 4px;
  padding: 8px 18px;
  font-size: 15px;
  cursor: pointer;
  transition: background 0.2s;
}
.modal-add-btn:hover {
  background: #0056b3;
}
.modal-empty {
  color: #aaa;
  text-align: center;
  padding: 36px 0;
}
.modal-pagination {
  display: flex;
  justify-content: flex-end;
  align-items: center;
  gap: 6px;
  padding: 14px 36px;
}
.modal-pagination button {
  background: #fff;
  border: 1px solid #f5f5f5;
  border-radius: 4px;
  padding: 6px 14px;
  font-size: 16px;
  cursor: pointer;
  color: var(--primary);
  transition:
    background 0.2s,
    color 0.2s;
}
.modal-pagination button.active {
  background: var(--primary);
  color: #fff;
  border-color: var(--primary);
}
.modal-pagination button:disabled {
  background: #fff;
  color: #bbb;
  border-color: #eee;
  cursor: not-allowed;
}
.modal-footer {
  display: flex;
  justify-content: flex-end;
  padding: 16px 36px 22px 36px;
  border-top: 1px solid #eee;
}
.modal-footer-close {
  background: #fff;
  color: #333;
  border: 1px solid #f5f5f5;
  border-radius: 4px;
  padding: 9px 26px;
  font-size: 16px;
  cursor: pointer;
  transition: all 0.2s;
}
.modal-footer-close:hover {
  background: var(--primary);
  color: #fff;
  border-color: var(--primary);
}
</style>
