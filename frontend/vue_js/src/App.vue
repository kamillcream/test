<template>
  <div>
    <CommonHeader />
    <CommonAlert />
    <div class="main">
      <router-view />
      <CommonModalContainer />
    </div>
    <CommonFooter />
  </div>
</template>

<script setup>
import { onMounted } from 'vue'
import CommonHeader from './fo/components/common/CommonHeader.vue'
import CommonFooter from './fo/components/common/CommonFooter.vue'
import CommonModalContainer from './fo/components/common/CommonModalContainer.vue'
import CommonAlert from './fo/components/common/CommonAlert.vue'
import { useUserStore } from './fo/stores/userStore'
import { api } from '@/axios'

const userStore = useUserStore()

onMounted(async () => {
  const autologinData = localStorage.getItem('autologinData')
  if (autologinData && !userStore.isLoggedIn) {
    try {
      const { userId, userPw, userTypeCd } = JSON.parse(autologinData)
      const payload = {
        userId,
        userPw,
        userTypeCd,
      }
      // 로그인 API 호출 (자동 로그인용)
      const res = await api.$post('/login', payload)
      const data = res.output

      // Pinia 스토어에 사용자 정보 저장
      userStore.setUser({
        userNm: data.userNm,
        userTypeCd: data.userTypeCd,
      })

      // 로컬스토리지에도 저장 (중복 저장이지만 안전)
      localStorage.setItem('userNm', data.userNm)
      localStorage.setItem('userType', userStore.userType)

      console.log('자동 로그인 성공')
    } catch (error) {
      console.error('자동 로그인 실패:', error)
      // 실패 시 자동 로그인 데이터 제거해도 됨
      localStorage.removeItem('autologinData')
    }
  }
})
</script>

<style>
#app {
  flex-direction: column;
}

.main {
  flex: 1;
  min-height: 700px;
  padding-top: 100px;
}
</style>
