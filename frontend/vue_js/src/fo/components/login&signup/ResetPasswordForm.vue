<template>
  <form @submit.prevent="handleSubmit">
    <div class="mb-3">
      <label for="resetPasswordEmailId" class="form-label">아이디</label>
      <input
        v-model="form.userId"
        type="text"
        class="form-control"
        id="resetPasswordEmailId"
        required
      />
    </div>
    <div class="mb-3">
      <label for="resetPasswordName" class="form-label">이름</label>
      <input
        v-model="form.name"
        type="text"
        class="form-control"
        id="resetPasswordName"
        required
      />
    </div>
    <div class="mb-3">
      <label for="resetPasswordEmailDomain" class="form-label"
        >이메일 주소</label
      >
      <div class="input-group">
        <input
          v-model="form.emailId"
          type="text"
          class="form-control form-control-sm"
          placeholder="이메일 아이디"
          required
        />
        <span class="input-group-text small">@</span>
        <input
          v-model="form.customDomain"
          type="text"
          class="form-control form-control-sm"
          placeholder="이메일 도메인"
        />
        <select
          v-model="form.domain"
          class="form-control form-control-sm"
          required
          @change="updateEmailInput"
        >
          <option value="" disabled selected>선택하세요</option>
          <option value="naver.com">naver.com</option>
          <option value="gmail.com">gmail.com</option>
          <option value="yahoo.com">yahoo.com</option>
          <option value="custom">직접 입력</option>
        </select>
        <button type="button" class="btn btn-primary" @click="emitVerification">
          인증
        </button>
      </div>
    </div>
    <div class="mb-3">
      <label for="resetPasswordVerificationCode" class="form-label"
        >인증번호</label
      >
      <input
        v-model="form.verificationCode"
        type="text"
        class="form-control"
        id="resetPasswordVerificationCode"
        required
      />
      <div
        v-if="verificationError"
        class="invalid-feedback text-primary"
        style="display: block"
      >
        일치하지 않습니다.
      </div>
    </div>
    <div class="d-grid mb-3">
      <button type="submit" class="btn btn-primary btn-block">
        비밀번호 찾기
      </button>
    </div>
  </form>
</template>

<script setup>
import { reactive, ref, defineEmits } from 'vue'

const emit = defineEmits(['submit', 'verify'])

const form = reactive({
  userId: '',
  name: '',
  emailId: '',
  domain: '',
  customDomain: '',
  verificationCode: '',
})

const verificationError = ref(false)

const updateEmailInput = () => {
  if (form.domain !== 'custom') {
    form.customDomain = form.domain
  } else {
    form.customDomain = ''
  }
}

const emitVerification = () => {
  emit('verify', `${form.emailId}@${form.customDomain}`)
}

const handleSubmit = () => {
  const email = `${form.emailId}@${form.customDomain}`
  emit('submit', {
    userId: form.userId,
    name: form.name,
    email,
    code: form.verificationCode,
  })
}
</script>
