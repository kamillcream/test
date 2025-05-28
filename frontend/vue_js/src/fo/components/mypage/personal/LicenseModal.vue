<template>
  <div class="modal-overlay">
    <div class="modal-content">
      <div class="modal-header">
        <span class="modal-title">자격증 검색</span>
        <button class="modal-close" @click="close">×</button>
      </div>
      <div class="modal-search">
        <input
          v-model="search"
          @keyup.enter="fetchLicenses"
          placeholder="자격증을 입력하세요."
        />
        <button class="modal-search-btn" @click="fetchLicenses">
          <svg width="18" height="18" fill="none" viewBox="0 0 18 18">
            <circle cx="8" cy="8" r="7" stroke="#fff" stroke-width="2"/>
            <path d="M13 13l3 3" stroke="#fff" stroke-width="2" stroke-linecap="round"/>
          </svg>
        </button>
      </div>
      <div class="modal-list">
        <div v-if="licenses.length">
          <div v-for="license in licenses" :key="license.id" class="modal-item">
            <div class="license-name">{{ license.name }}</div>
            <div class="license-meta">{{ license.level }} | {{ license.org }}</div>
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
        >{{ p }}</button>
        <button :disabled="page === totalPages" @click="nextPage">&gt;</button>
      </div>
      <div class="modal-footer">
        <button class="modal-footer-close" @click="close">닫기</button>
      </div>
    </div>
  </div>
</template>


<script setup>
import { ref } from 'vue'
import { useModalStore } from '@/fo/stores/modalStore'

const modalStore = useModalStore()
const search = ref('')
const licenses = ref([
  { id: 1, name: '자격증 1', level: '1급', org: '한국산업인력공단' },
  { id: 2, name: '자격증 2', level: '2급', org: '한국산업인력공단' }
])
const page = ref(1)
const totalPages = 3

const fetchLicenses = () => {
  // 실제 API 연동 필요
  // 예시: 검색어에 따라 licenses.value를 변경
}
const close = () => modalStore.closeModal()
const prevPage = () => { if (page.value > 1) page.value-- }
const nextPage = () => { if (page.value < totalPages) page.value++ }
const goPage = (p) => { page.value = p }
</script>

<style scoped>
.modal-overlay {
  position: fixed;
  left: 0;
  top: 0;
  width: 100vw;
  height: 100vh;
  background: rgba(0, 0, 0, 0.15);
  z-index: 1050;
  display: flex;
  align-items: center;
  justify-content: center;
}
.modal-content {
  background: #fff;
  border-radius: 12px;
  width: 450px;
  max-width: 98vw;
  box-shadow: 0 2px 16px rgba(0, 0, 0, 0.13);
  padding: 0;
  position: relative;
  display: flex;
  flex-direction: column;
}
.modal-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 18px 20px 8px 20px;
  border-bottom: 1px solid #eee;
}
.modal-title {
  font-size: 17px;
  font-weight: bold;
  color: #222;
}
.modal-close {
  background: none;
  border: none;
  font-size: 22px;
  cursor: pointer;
  color: #888;
  line-height: 1;
  transition: color 0.2s;
}
.modal-close:hover {
  color: #007bff;
}
.modal-search {
  display: flex;
  align-items: center;
  gap: 0;
  padding: 12px 20px 8px 20px;
}
.modal-search input {
  flex: 1;
  padding: 7px 12px;
  border: 1px solid #ddd;
  border-right: none;
  border-radius: 4px 0 0 4px;
  font-size: 15px;
  height: 34px;
  box-sizing: border-box;
}
.modal-search-btn {
  background: #007bff;
  color: #fff;
  border-radius: 0 4px 4px 0;
  width: 42px;
  height: 34px;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  border: 1px solid #007bff;
  border-left: none;
  transition: background 0.2s;
  font-size: 18px;
  box-sizing: border-box;
}
.modal-search-btn:hover {
  background: #0056b3;
}
.modal-search-btn svg {
  display: block;
}
.modal-list {
  max-height: 180px;
  overflow-y: auto;
  padding: 0 20px;
}
.modal-item {
  display: flex;
  flex-direction: column;
  border-bottom: 1px solid #f0f0f0;
  padding: 13px 10px 10px 10px;
}
.license-name {
  font-weight: 500;
  color: #222;
  font-size: 15px;
  margin-bottom: 2px;
}
.license-meta {
  color: #888;
  font-size: 13px;
}
.modal-empty {
  color: #aaa;
  text-align: center;
  padding: 30px 0;
}
.modal-pagination {
  display: flex;
  justify-content: flex-end;
  align-items: center;
  gap: 4px;
  padding: 10px 20px 10px 0;
}
.modal-pagination button {
  background: #fff;
  border: 1px solid #007bff;
  border-radius: 4px;
  padding: 2px 9px;
  font-size: 15px;
  cursor: pointer;
  color: #007bff;
  transition: background 0.2s, color 0.2s;
}
.modal-pagination button.active,
.modal-pagination button:focus {
  background: #007bff;
  color: #fff;
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
  padding: 10px 20px 16px 10px;
  border-top: 1px solid #eee;
}
.modal-footer-close {
  background: #fff;
  color: #333;
  border: 1px solid #ddd;
  border-radius: 4px;
  padding: 7px 18px;
  font-size: 15px;
  cursor: pointer;
  transition: all 0.2s;
}
.modal-footer-close:hover {
  background: #007bff;
  color: #fff;
  border-color: #007bff;
}
</style>
