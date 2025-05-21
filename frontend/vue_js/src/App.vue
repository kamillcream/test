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

const fetchUserInfo = async () => {
  try {
    const res = await api.$post('/me')
    const data = res.output

    localStorage.setItem('userNm', data.userNm)
    localStorage.setItem('userTypeCd', data.userTypeCd)

    userStore.setUser({
      userNm: data.userNm,
      userTypeCd: data.userTypeCd,
    })
  } catch (error) {
    console.error('자동 로그인 실패:', error)
    clearLoginState()
  }
}

const clearLoginState = () => {
  localStorage.removeItem('userNm')
  localStorage.removeItem('userTypeCd')
  localStorage.removeItem('autoLogin')

  userStore.clearUser() // 이건 Pinia의 clearUser() 메서드를 너가 만들어야 해
}

onMounted(() => {
  const autoLogin = localStorage.getItem('autoLogin') === 'true'
  if (autoLogin) {
    fetchUserInfo()
  } else {
    clearLoginState()
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
