<template>
  <form @submit.prevent="handleSubmit">
    <!-- 아이디 -->
    <div class="row">
      <div class="form-group col-lg-6">
        <label class="form-label">아이디</label>
        <input
          type="text"
          v-model="form.id"
          class="form-control form-control-lg"
        />
      </div>
    </div>

    <!-- 비밀번호 -->
    <div class="row">
      <div class="form-group col-lg-6">
        <label class="form-label">비밀번호</label>
        <input
          type="password"
          v-model="form.password"
          class="form-control form-control-lg"
        />
      </div>
      <div class="form-group col-lg-6">
        <label class="form-label">비밀번호 확인</label>
        <input
          type="password"
          v-model="form.confirmPassword"
          class="form-control form-control-lg"
        />
      </div>
    </div>
    <!-- 담당자 이름 -->
    <div class="row">
      <div class="form-group col-lg-6">
        <label class="form-label">담당자 이름</label>
        <input
          type="text"
          v-model="form.contactName"
          class="form-control form-control-lg"
        />
      </div>
    </div>
    <!-- 휴대폰 -->
    <div class="row">
      <div class="form-group col-lg-6">
        <label class="form-label">휴대폰 번호</label>
        <input
          type="text"
          v-model="form.phone"
          class="form-control form-control-lg"
        />
      </div>
    </div>
    <!-- 기업명 -->
    <div class="row">
      <div class="form-group col-lg-6">
        <label class="form-label">기업명</label>
        <input
          type="text"
          v-model="form.companyName"
          class="form-control form-control-lg"
          readonly
          @click="openCompanyModal"
        />
      </div>
      <div class="form-group col-lg-6">
        <label class="form-label">사업자 번호</label>
        <input
          type="text"
          v-model="form.businessNumber"
          class="form-control form-control-lg"
        />
      </div>
    </div>

    <!-- 주소 -->
    <div class="row">
      <div class="form-group col-lg-7 mb-2">
        <label class="form-label">주소</label>
        <input
          type="text"
          v-model="form.address"
          class="form-control form-control-lg"
          placeholder="주소를 검색하세요"
          readonly
          @click="openPostcode"
        />
      </div>

      <div class="form-group col-lg-5">
        <label class="form-label">상세 주소</label>
        <input
          type="text"
          v-model="form.addressDetail"
          class="form-control form-control-lg"
          placeholder=""
        />
      </div>
    </div>
    <!-- 이메일 -->
    <div class="row">
      <div class="form-group col-lg-12">
        <label class="form-label">이메일 주소</label>
        <div class="input-group">
          <input
            type="text"
            v-model="form.emailId"
            class="form-control form-control-lg"
            placeholder="이메일 아이디"
          />
          <span class="input-group-text">@</span>
          <input
            type="text"
            v-model="form.emailDomain"
            :readonly="!isCustomDomain"
            class="form-control form-control-lg"
            placeholder="도메인 입력"
          />
          <select
            v-model="selectedDomain"
            @change="handleDomainChange"
            class="form-control form-control-lg"
          >
            <option disabled value="">선택하세요</option>
            <option value="naver.com">naver.com</option>
            <option value="gmail.com">gmail.com</option>
            <option value="daum.net">daum.net</option>
            <option value="nate.com">nate.com</option>
            <option value="hotmail.com">hotmail.com</option>
            <option value="custom">직접입력</option>
          </select>
          <button
            type="button"
            class="btn btn-primary btn-lg"
            @click="sendVerification"
          >
            인증 요청
          </button>
        </div>
      </div>
    </div>

    <!-- 인증번호 -->
    <div class="row">
      <div class="form-group col-lg-6">
        <label class="form-label">인증번호</label>
        <div class="input-group">
          <input
            type="text"
            v-model="form.verificationCode"
            class="form-control form-control-lg"
          />
          <button
            type="button"
            class="btn btn-primary btn-lg"
            @click="verifyCode"
          >
            확인
          </button>
        </div>
      </div>
    </div>

    <!-- 약관 동의 -->
    <div class="row">
      <div class="form-group col">
        <div class="form-check">
          <input
            type="checkbox"
            v-model="form.terms"
            id="terms"
            class="form-check-input"
          />
          <label for="terms" class="form-check-label me-1">
            약관에 동의합니다.
          </label>
          <a class="font-primary" @click="openTermsModal">이용약관</a>
        </div>
      </div>
    </div>

    <!-- 회원가입 버튼 -->
    <div class="row">
      <div class="form-group col text-6">
        <input
          type="submit"
          value="회원가입"
          class="btn btn-primary btn-modern w-100"
        />
      </div>
    </div>
  </form>
</template>

<script setup>
import { reactive, ref } from 'vue'
import { useModalStore } from '@/fo/stores/modalStore'
import { personalAgreementText } from '@/assets/terms'
import TermsAgreementModal from '@/fo/components/login&signup/TermsAgreementModal.vue'
import { useAlertStore } from '@/fo/stores/alertStore'

const form = reactive({
  id: '',
  password: '',
  confirmPassword: '',
  name: '',
  dob: '',
  gender: '',
  phone: '',
  emailId: '',
  emailDomain: '',
  verificationCode: '',
  terms: false,
  address: '',
  addressDetail: '',
})

const modalStore = useModalStore()
const alertStore = useAlertStore()

const selectedDomain = ref('')
const isCustomDomain = ref(false)

function handleDomainChange() {
  if (selectedDomain.value === 'custom') {
    form.emailDomain = ''
    isCustomDomain.value = true
  } else {
    form.emailDomain = selectedDomain.value
    isCustomDomain.value = false
  }
}

function sendVerification() {
  alert(`인증 메일 전송: ${form.emailId}@${form.emailDomain}`)
}

function verifyCode() {
  alert('인증번호 확인 요청')
}

// 약관 모달을 열기 위한 함수
function openTermsModal() {
  modalStore.openModal(TermsAgreementModal, {
    title: '개인정보 수집 및 이용 동의서',
    body: personalAgreementText,
    onConfirm: () => {
      alertStore.show('약관 동의 처리되었습니다.', 'success')
      form.terms = true
      modalStore.closeModal()
    },
  })
}
</script>
<style scoped>
select.form-control,
select.form-control-lg {
  line-height: 1.6;
  padding-top: 0.5rem;
  padding-bottom: 0.5rem;
  height: auto;
}
</style>
