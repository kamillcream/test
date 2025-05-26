<template>
  <section>
    <CommonPageHeader
      title=""
      strongText="아이디 / 비밀번호 찾기"
      :breadcrumbs="[{ text: 'Home', link: '/' }, { text: 'ID/PW' }]"
    />
    <div class="container py-5">
      <div class="row justify-content-center">
        <div class="col-md-6 col-lg-5">
          <div class="card border-0 shadow-lg">
            <div class="card-body p-4">
              <div class="btn-group w-100 mb-4" role="group">
                <button
                  class="btn btn-primary btn-outline w-50"
                  @click="changeTab('findId')"
                  :class="{ active: currentTab === 'findId' }"
                >
                  아이디 찾기
                </button>
                <button
                  class="btn btn-primary btn-outline w-50"
                  @click="changeTab('resetPassword')"
                  :class="{ active: currentTab === 'resetPassword' }"
                >
                  비밀번호 찾기
                </button>
              </div>

              <FindIdForm v-if="currentTab === 'findId'" />
              <ResetPasswordForm
                @submit="handleResetPassword"
                @verify="sendResetPasswordVerification"
                v-else
              />
            </div>
          </div>
        </div>
      </div>
    </div>
  </section>
</template>

<script setup>
import { ref, watch, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import FindIdForm from '@/fo/components/login&signup/FindIdForm.vue'
import ResetPasswordForm from '@/fo/components/login&signup/ResetPasswordForm.vue'

const route = useRoute()
const router = useRouter()

const currentTab = ref('findId')

// 초기값 세팅
if (route.query.tab === 'resetPassword') {
  currentTab.value = 'resetPassword'
} else {
  currentTab.value = 'findId'
}

// URL 쿼리 파라미터가 바뀌면 탭도 변경
watch(
  () => route.query.tab,
  (newTab) => {
    if (newTab === 'resetPassword') {
      currentTab.value = 'resetPassword'
    } else {
      currentTab.value = 'findId'
    }
  },
)

// 탭 변경 시 URL 쿼리도 동기화
const changeTab = (tabName) => {
  currentTab.value = tabName
  router.replace({ query: {} })
}

onMounted(() => {
  if (route.query.tab) {
    router.replace({ query: {} })
  }
})
</script>
