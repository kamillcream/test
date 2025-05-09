<template>
  <section>
    <CommonPageHeader
      title=""
      strongText="비밀번호 재설정"
      :breadcrumbs="[{ text: 'Home', link: '/' }, { text: 'ResetPassword' }]"
    />
    <div class="container py-5">
      <div class="row justify-content-center">
        <div class="col-md-6 col-lg-5">
          <div class="card border-0 shadow-lg">
            <div class="card-header bg-grey text-dark">
              <h5 class="mb-0">비밀번호 재설정</h5>
            </div>
            <div class="card-body">
              <div class="mb-3">
                <label for="password" class="form-label">새로운 비밀번호</label>
                <input
                  type="password"
                  class="form-control"
                  id="password"
                  v-model="password"
                  maxlength="32"
                />
                <div
                  v-if="showPasswordError"
                  class="invalid-feedback text-primary"
                  style="display: block"
                >
                  비밀번호를 입력해주세요.
                </div>
              </div>

              <div class="mb-3">
                <label for="confirmPassword" class="form-label"
                  >비밀번호 확인</label
                >
                <input
                  type="password"
                  class="form-control"
                  id="confirmPassword"
                  v-model="confirmPassword"
                  maxlength="32"
                />
                <div
                  v-if="showConfirmPasswordError"
                  class="invalid-feedback text-primary"
                  style="display: block"
                >
                  비밀번호가 일치하지 않습니다.
                </div>
              </div>

              <div class="d-flex justify-content-center gap-3">
                <button class="btn btn-primary px-4" @click="resetPassword">
                  비밀번호 재설정
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </section>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import CommonPageHeader from '@/fo/components/common/CommonPageHeader.vue'

const router = useRouter()

const password = ref('')
const confirmPassword = ref('')
const showPasswordError = ref(false)
const showConfirmPasswordError = ref(false)

const resetPassword = () => {
  showPasswordError.value = !password.value
  showConfirmPasswordError.value = password.value !== confirmPassword.value

  if (!showPasswordError.value && !showConfirmPasswordError.value) {
    console.log('비밀번호 재설정 완료:', password.value)

    // 실제 API 호출 처리 후 성공 시:
    router.push('/login') // 또는 성공 페이지로 이동
  }
}
</script>
