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
                  @click="currentTab = 'findId'"
                  :class="{ active: currentTab === 'findId' }"
                >
                  아이디 찾기
                </button>
                <button
                  class="btn btn-primary btn-outline w-50"
                  @click="currentTab = 'resetPassword'"
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
import { ref } from 'vue'
import FindIdForm from '@/fo/components/login&signup/FindIdForm.vue'
import ResetPasswordForm from '@/fo/components/login&signup/ResetPasswordForm.vue'

const currentTab = ref('findId')
</script>
