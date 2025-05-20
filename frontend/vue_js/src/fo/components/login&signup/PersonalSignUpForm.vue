<template>
  <form @submit.prevent="handleSubmit">
    <!-- 아이디 -->
    <div class="row">
      <div class="form-group col-lg-6">
        <label class="form-label"
          >아이디<i
            v-if="idValid"
            class="bi bi-check-circle-fill ms-1"
            style="color: #007bff"
          ></i
        ></label>
        <input
          type="text"
          v-model="form.id"
          class="form-control form-control-lg"
          @input="validateId"
        />
        <div v-if="idError" class="invalid-feedback">{{ idError }}</div>
      </div>
    </div>

    <!-- 비밀번호 -->
    <div class="row">
      <div class="form-group col-lg-6">
        <label class="form-label"
          >비밀번호
          <i
            v-if="passwordValid"
            class="bi bi-check-circle-fill ms-1"
            style="color: #007bff"
          ></i
        ></label>
        <input
          type="password"
          v-model="form.password"
          class="form-control form-control-lg"
          @input="validatePassword"
        />
        <div v-if="passwordError" class="invalid-feedback">
          {{ passwordError }}
        </div>
      </div>
      <div class="form-group col-lg-6">
        <label class="form-label">
          비밀번호 확인
          <i
            v-if="confirmPasswordValid"
            class="bi bi-check-circle-fill ms-1"
            style="color: #007bff"
          ></i>
        </label>
        <input
          type="password"
          v-model="form.confirmPassword"
          class="form-control form-control-lg"
          @input="passwordValid ? validateConfirmPassword() : ''"
        />
        <div v-if="confirmPasswordError" class="invalid-feedback">
          {{ confirmPasswordError }}
        </div>
      </div>
    </div>

    <!-- 이름 -->
    <div class="row">
      <div class="form-group col-lg">
        <label class="form-label"
          >이름
          <i
            v-if="nameValid"
            class="bi bi-check-circle-fill ms-1"
            style="color: #007bff"
          ></i
        ></label>
        <input
          type="text"
          v-model="form.name"
          class="form-control form-control-lg"
          @input="validateName"
        />
        <div v-if="nameError" class="invalid-feedback">{{ nameError }}</div>
      </div>
    </div>

    <!-- 생년월일 / 성별 -->
    <div class="row">
      <div class="form-group col-lg-8">
        <label class="form-label"
          >생년월일
          <i
            v-if="dobValid"
            class="bi bi-check-circle-fill ms-1"
            style="color: #007bff"
          ></i
        ></label>
        <input
          type="date"
          v-model="form.dob"
          class="form-control form-control-lg"
          @input="validateDob"
        />
        <div v-if="dobError" class="invalid-feedback">{{ dobError }}</div>
      </div>
      <div class="form-group col-lg-4">
        <label class="form-label">성별</label>
        <select
          v-model="form.gender"
          class="form-control form-control-lg"
          @change="validateGender"
        >
          <option disabled value="">성별</option>
          <option value="male">남성</option>
          <option value="female">여성</option>
        </select>
        <div v-if="genderError" class="invalid-feedback">{{ genderError }}</div>
      </div>
    </div>

    <!-- 휴대폰 -->
    <div class="row">
      <div class="form-group col-lg">
        <label class="form-label"
          >휴대폰 번호
          <i
            v-if="phoneValid"
            class="bi bi-check-circle-fill ms-1"
            style="color: #007bff"
          ></i
        ></label>
        <input
          type="text"
          v-model="form.phone"
          class="form-control form-control-lg"
          @input="validatePhone"
        />
        <div v-if="phoneError" class="invalid-feedback">{{ phoneError }}</div>
      </div>
    </div>

    <!-- 주소 -->
    <div class="row">
      <div class="form-group col-lg-7 mb-2">
        <label class="form-label"
          >주소
          <i
            v-if="adressValid"
            class="bi bi-check-circle-fill ms-1"
            style="color: #007bff"
          ></i
        ></label>
        <input
          type="text"
          v-model="form.address"
          class="form-control form-control-lg"
          placeholder="주소를 검색하세요"
          readonly
          @click="openPostcode"
          @input="validateAdress"
        />
        <div v-if="addressError" class="invalid-feedback">
          {{ addressError }}
        </div>
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
        <label class="form-label"
          >이메일 주소
          <i
            v-if="emailValid"
            class="bi bi-check-circle-fill ms-1"
            style="color: #007bff"
          ></i
        ></label>
        <div class="input-group">
          <input
            type="text"
            v-model="form.emailId"
            class="form-control form-control-lg"
            @input="validateEmail"
            placeholder="이메일 아이디"
          />
          <span class="input-group-text">@</span>

          <!-- 도메인 입력 인풋 -->
          <input
            type="text"
            v-model="form.emailDomain"
            :readonly="!isCustomDomain"
            class="form-control form-control-lg"
            @input="validateEmail"
            placeholder="도메인 입력"
          />

          <!-- 셀렉트 박스 -->
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
        <div v-if="emailError" class="invalid-feedback">{{ emailError }}</div>
      </div>
    </div>

    <!-- 인증번호 -->
    <div class="row">
      <div class="form-group col-lg-8">
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
        <div v-if="!isVerificationCodeEmpty" class="invalid-feedback">
          인증번호를 입력해주세요.
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
            @change="validateTerms"
          />
          <label for="terms" class="form-check-label me-1">
            약관에 동의합니다.
          </label>
          <a class="font-primary" @click="openTermsModal">이용약관</a>
        </div>
        <div v-if="termsError" class="invalid-feedback">{{ termsError }}</div>
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
import { api } from '@/axios'

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

  validateEmail()
}

const isVerificationCodeEmpty = ref(true)

// 이메일 인증 요청 함수
const sendVerification = async () => {
  const email = `${form.emailId}@${form.emailDomain}`

  try {
    const response = await api.$post('/email/send-code', { email })
    console.log('인증 이메일 전송 완료', response)
    alertStore.show(
      '인증 코드를 전송했습니다. 인증 코드 : ' + response.code,
      'info',
    )
  } catch (error) {
    console.error('이메일 인증 요청 실패:', error)
    alertStore.show('이메일 인증 요청에 실패했습니다.', 'danger')
  }
}

// 인증 코드 확인 함수
const verifyCode = async () => {
  if (!form.verificationCode || form.verificationCode.trim() === '') {
    isVerificationCodeEmpty.value = false
    return
  } else {
    const email = `${form.emailId}@${form.emailDomain}`
    const code = form.verificationCode

    try {
      const response = await api.$post('/email/verify-code', { email, code })
      console.log('인증 성공', response)
      alertStore.show('이메일 인증에 성공하였습니다.', 'info')
      isVerificationCodeEmpty.value = true
    } catch (error) {
      console.error('인증 코드 검증 실패:', error)
      alertStore.show('이메일 인증에 실패하였습니다.', 'danger')
    }
  }
}

const idError = ref('')
const passwordError = ref('')
const confirmPasswordError = ref('')
const nameError = ref('')
const dobError = ref('')
const genderError = ref('')
const phoneError = ref('')
const addressError = ref('')
const emailError = ref('')
const termsError = ref('')

const idValid = ref(false)
const passwordValid = ref(false)
const confirmPasswordValid = ref(false)
const nameValid = ref(false)
const dobValid = ref(false)
const genderValid = ref(false)
const phoneValid = ref(false)
const addressValid = ref(false)
const emailValid = ref(false)
const termsValid = ref(false)

// 아이디 유효성 검사
const validateId = () => {
  idError.value = ''
  idValid.value = false
  if (!form.id) {
    idError.value = '아이디를 입력해주세요.'
  } else if (!/^[a-zA-Z0-9]{5,20}$/.test(form.id)) {
    idError.value = '영문 또는 숫자 5~20자로 입력해주세요.'
  } else {
    idValid.value = true
  }
}

// 비밀번호 유효성 검사
const validatePassword = () => {
  passwordError.value = ''
  passwordValid.value = false
  if (!form.password) {
    passwordError.value = '비밀번호를 입력해주세요.'
  } else if (
    !/^(?=.*[A-Za-z])(?=.*\d)(?=.*[!@#$%^&*]).{8,}$/.test(form.password)
  ) {
    passwordError.value = '8자 이상, 영문·숫자·특수문자를 조합해 입력해주세요.'
  } else {
    passwordValid.value = true
  }
}

// 비밀번호 확인 유효성 검사
const validateConfirmPassword = () => {
  confirmPasswordError.value = ''
  confirmPasswordValid.value = false
  if (!form.confirmPassword) {
    confirmPasswordError.value = '비밀번호 확인을 입력해주세요.'
  } else if (form.confirmPassword !== form.password) {
    confirmPasswordError.value = '비밀번호가 일치하지 않습니다.'
  } else {
    confirmPasswordValid.value = true
  }
}

// 이름 유효성 검사
const validateName = () => {
  nameError.value = ''
  nameValid.value = false
  if (!form.name) {
    nameError.value = '이름을 입력해주세요.'
  } else if (form.name.length < 2) {
    nameError.value = '이름은 두 글자 이상 입력해주세요.'
  } else {
    nameValid.value = true
  }
}

// 생년월일 유효성 검사
const validateDob = () => {
  dobError.value = ''
  dobValid.value = false
  if (!form.dob) {
    dobError.value = '생년월일을 입력해주세요.'
  } else {
    dobValid.value = true
  }
}

// 성별 유효성 검사
const validateGender = () => {
  genderError.value = ''
  genderValid.value = false
  if (!form.gender) {
    genderError.value = '성별을 선택해주세요.'
  } else {
    genderValid.value = true
  }
}

// 휴대폰 번호 유효성 검사
const validatePhone = () => {
  phoneError.value = ''
  phoneValid.value = false
  if (!form.phone) {
    phoneError.value = '휴대폰 번호를 입력해주세요.'
  } else if (!/^\d{10,11}$/.test(form.phone)) {
    phoneError.value = '올바른 휴대폰 번호 형식이 아닙니다. (하이픈 제외)'
  } else {
    phoneValid.value = true
  }
}

// 주소 유효성 검사
const validateAdress = () => {
  addressError.value = ''
  addressValid.value = false
  if (!form.address) {
    addressError.value = '주소를 입력해주세요.'
  } else {
    addressValid.value = true
  }
}

// 이메일 주소 유효성 검사
const validateEmail = () => {
  emailError.value = ''
  emailValid.value = false

  // 이메일 아이디와 도메인 결합
  const fullEmail =
    form.emailId +
    '@' +
    (isCustomDomain.value ? form.emailDomain : selectedDomain.value)

  // 이메일 아이디가 없거나, 이메일 도메인이 없을 경우
  if (!form.emailId) {
    emailError.value = '이메일 아이디를 입력해주세요.'
  } else if (isCustomDomain.value && !form.emailDomain) {
    emailError.value = '도메인을 입력해주세요.'
  }
  // 이메일 형식 검사
  else if (!/\S+@\S+\.\S+/.test(fullEmail)) {
    emailError.value = '올바른 이메일 주소 형식이 아닙니다.'
    console.log('fullEmail', fullEmail)
  } else {
    emailValid.value = true
  }
}

// 약관 동의 유효성 검사
const validateTerms = () => {
  termsError.value = ''
  termsValid.value = false
  if (!form.terms) {
    termsError.value = '필수 약관에 동의해주세요.'
  } else {
    termsValid.value = true
  }
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
.invalid-feedback {
  color: #007bff;
  display: block;
}
</style>
