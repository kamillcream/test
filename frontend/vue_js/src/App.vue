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
  const autoLogin = localStorage.getItem('autoLogin') === 'true'

  if (!autoLogin) {
    userStore.clearUser()
    return
  }

  try {
    const res = await api.$get('/me') // 쿠키에서 토큰 가져가 자동 로그인
    userStore.setUser(res.data.output)
  } catch (e) {
    userStore.clearUser()
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
