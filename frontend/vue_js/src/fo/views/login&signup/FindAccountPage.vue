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
              <div
                class="btn-group w-100 mb-4"
                role="group"
                aria-label="회원 유형 선택"
              >
                <button
                  class="btn btn-primary btn-outline w-50"
                  @click="selectTab('findId')"
                  :class="{ active: currentTab === 'findId' }"
                  type="button"
                >
                  아이디 찾기
                </button>
                <button
                  class="btn btn-primary btn-outline w-50"
                  @click="selectTab('resetPassword')"
                  :class="{ active: currentTab === 'resetPassword' }"
                  type="button"
                >
                  비밀번호 찾기
                </button>
              </div>

              <div class="tab-content" id="loginTabContent">
                <!-- 아이디 찾기 -->
                <div
                  v-if="currentTab === 'findId'"
                  class="tab-pane fade show active"
                  role="tabpanel"
                >
                  <form @submit.prevent="handleFindId">
                    <div class="mb-3">
                      <label for="findIdEmailName" class="form-label"
                        >이름</label
                      >
                      <input
                        v-model="findIdEmailName"
                        type="text"
                        class="form-control"
                        id="findIdEmailName"
                        required
                      />
                    </div>
                    <div class="mb-3">
                      <label for="findIdEmailDomain" class="form-label"
                        >이메일 주소</label
                      >
                      <div class="input-group">
                        <input
                          v-model="findIdEmailId"
                          type="text"
                          class="form-control form-control-sm"
                          placeholder="이메일 아이디"
                          required
                          id="findIdEmailId"
                        />
                        <span class="input-group-text small">@</span>
                        <input
                          v-model="customFindIdEmailDomain"
                          type="text"
                          class="form-control form-control-sm"
                          placeholder="이메일 도메인"
                        />
                        <select
                          v-model="findIdEmailDomain"
                          class="form-control form-control-sm"
                          required
                          id="findIdEmailDomain"
                          @change="updateFindIdEmailInput"
                        >
                          <option value="" disabled selected>선택하세요</option>
                          <option value="naver.com">naver.com</option>
                          <option value="gmail.com">gmail.com</option>
                          <option value="yahoo.com">yahoo.com</option>
                          <option value="custom">직접 입력</option>
                        </select>
                        <button
                          type="button"
                          class="btn btn-primary"
                          @click="sendFindIdVerification"
                        >
                          인증
                        </button>
                      </div>
                    </div>
                    <div class="mb-3">
                      <label for="findIdVerificationCode" class="form-label"
                        >인증번호</label
                      >
                      <input
                        v-model="findIdVerificationCode"
                        type="text"
                        class="form-control"
                        id="findIdVerificationCode"
                        required
                      />
                      <div
                        v-if="findIdVerificationError"
                        class="invalid-feedback text-primary"
                        style="display: block"
                      >
                        일치하지 않습니다.
                      </div>
                    </div>
                    <div class="d-grid mb-3">
                      <button type="submit" class="btn btn-primary btn-block">
                        아이디 찾기
                      </button>
                    </div>
                  </form>
                </div>

                <!-- 비밀번호 찾기 -->
                <div
                  v-if="currentTab === 'resetPassword'"
                  class="tab-pane fade show active"
                  role="tabpanel"
                >
                  <form @submit.prevent="handleResetPassword">
                    <div class="mb-3">
                      <label for="resetPasswordEmailId" class="form-label"
                        >아이디</label
                      >
                      <input
                        v-model="resetPasswordEmailId"
                        type="text"
                        class="form-control"
                        id="resetPasswordEmailId"
                        required
                      />
                    </div>
                    <div class="mb-3">
                      <label for="resetPasswordName" class="form-label"
                        >이름</label
                      >
                      <input
                        v-model="resetPasswordName"
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
                          v-model="resetPasswordEmailId"
                          type="text"
                          class="form-control form-control-sm"
                          placeholder="이메일 아이디"
                          required
                          id="resetPasswordEmailId"
                        />
                        <span class="input-group-text small">@</span>
                        <!-- 항상 출력되는 인풋 박스 -->
                        <input
                          v-model="customResetPasswordEmailDomain"
                          type="text"
                          class="form-control form-control-sm"
                          placeholder="이메일 도메인"
                        />

                        <!-- 셀렉트 박스 -->
                        <select
                          v-model="resetPasswordEmailDomain"
                          class="form-control form-control-sm"
                          required
                          id="resetPasswordEmailDomain"
                          @change="updateResetPasswordEmailInput"
                        >
                          <option value="" disabled selected>선택하세요</option>
                          <option value="naver.com">naver.com</option>
                          <option value="gmail.com">gmail.com</option>
                          <option value="yahoo.com">yahoo.com</option>
                          <option value="custom">직접 입력</option>
                        </select>

                        <button
                          type="button"
                          class="btn btn-primary"
                          @click="sendResetPasswordVerification"
                        >
                          인증
                        </button>
                      </div>
                    </div>
                    <div class="mb-3">
                      <label
                        for="resetPasswordVerificationCode"
                        class="form-label"
                        >인증번호</label
                      >
                      <input
                        v-model="resetPasswordVerificationCode"
                        type="text"
                        class="form-control"
                        id="resetPasswordVerificationCode"
                        required
                      />
                      <div
                        v-if="resetPasswordVerificationError"
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
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </section>
</template>

<script setup>
import { ref, watch, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import CommonPageHeader from '@/fo/components/common/CommonPageHeader.vue'

const route = useRoute()

// 데이터 상태를 관리
const currentTab = ref('findId') // 초기 활성 탭
const findIdEmailId = ref('')
const findIdEmailName = ref('')
const findIdVerificationCode = ref('')
const findIdVerificationError = ref(false)

const resetPasswordEmailId = ref('')
const resetPasswordVerificationCode = ref('')
const resetPasswordVerificationError = ref(false)
const resetPasswordName = ref('')

const findIdEmailDomain = ref('')
const customFindIdEmailDomain = ref('') // 직접 입력 이메일 도메인
const resetPasswordEmailDomain = ref('')
const customResetPasswordEmailDomain = ref('')

// 탭 변경 함수
const selectTab = (tab) => {
  currentTab.value = tab
}

onMounted(() => {
  const tabFromQuery = route.query.tab
  if (tabFromQuery === 'resetPassword') {
    currentTab.value = 'resetPassword'
  }
})

// 아이디 찾기 인증 요청
const sendFindIdVerification = () => {
  console.log(
    '아이디 찾기 인증 요청:',
    findIdEmailId.value,
    findIdEmailDomain.value === 'custom'
      ? customFindIdEmailDomain.value
      : findIdEmailDomain.value,
  )
}

// 비밀번호 찾기 인증 요청
const sendResetPasswordVerification = () => {
  console.log(
    '비밀번호 찾기 인증 요청:',
    resetPasswordEmailId.value,
    resetPasswordEmailDomain.value === 'custom'
      ? customResetPasswordEmailDomain.value
      : resetPasswordEmailDomain.value,
  )
}

// 아이디 찾기 처리
const handleFindId = () => {
  console.log(
    '아이디 찾기 요청:',
    findIdEmailId.value,
    findIdEmailDomain.value === 'custom'
      ? customFindIdEmailDomain.value
      : findIdEmailDomain.value,
    findIdVerificationCode.value,
  )
}

// 비밀번호 찾기 처리
const handleResetPassword = () => {
  console.log(
    '비밀번호 찾기 요청:',
    resetPasswordEmailId.value,
    resetPasswordEmailDomain.value === 'custom'
      ? customResetPasswordEmailDomain.value
      : resetPasswordEmailDomain.value,
    resetPasswordVerificationCode.value,
  )
}

// 이메일 도메인 선택 시 동작하는 함수 (아이디 찾기)
const updateFindIdEmailInput = () => {
  if (findIdEmailDomain.value !== 'custom') {
    customFindIdEmailDomain.value = findIdEmailDomain.value
  } else {
    customFindIdEmailDomain.value = null // '직접 입력'이 선택되면 input 값 초기화
  }
}

// 이메일 도메인 선택 시 동작하는 함수 (비밀번호 찾기)
const updateResetPasswordEmailInput = () => {
  if (resetPasswordEmailDomain.value !== 'custom') {
    customResetPasswordEmailDomain.value = resetPasswordEmailDomain.value
  } else {
    customResetPasswordEmailDomain.value = null // '직접 입력'이 선택되면 input 값 초기화
  }
}

// watch로 직접입력 시 input 필드 초기화 (옵션 변경 시마다)
watch(findIdEmailDomain, updateFindIdEmailInput)
watch(resetPasswordEmailDomain, updateResetPasswordEmailInput)
</script>
