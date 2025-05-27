<template>
  <section>
    <!-- <PasswordCheck v-if="!isConfirmed" @confirmed="isConfirmed = true" />
    <div v-else> -->
    <div>
      <div class="overflow-hidden mb-3">
        <h2 class="font-weight-normal text-7 mb-0">회원 정보 수정</h2>
      </div>

      <!-- 프로필 이미지 (사람 아이콘으로 대체) -->
      <div class="text-center mb-4">
        <div class="position-relative d-inline-block">
          <!-- 기본 이미지 아이콘 (사람 아이콘) -->
          <div class="rounded-circle">
            <i class="fas fa-user text-muted"></i>
          </div>

          <!-- 사진 변경 버튼 (카메라 아이콘이 이미지의 우측 하단 밖으로 걸쳐짐) -->
          <label
            for="profileImage"
            class="btn btn-light btn-sm position-absolute add"
          >
            <i class="fas fa-camera text-muted"></i>
          </label>

          <!-- 파일 입력 필드 (보이지 않음) -->
          <input
            type="file"
            id="profileImage"
            class="position-absolute top-0 start-0 w-100 h-100 opacity-0"
            title="사진 변경"
          />
        </div>
      </div>

      <form
        role="form"
        class="needs-validation"
        novalidate="novalidate"
        @submit.prevent="saveAll"
      >
        <!-- 아이디 (변경 불가) -->
        <div class="form-group row align-items-center">
          <label class="col-lg-3 col-form-label text-2">아이디</label>
          <div class="col-lg-9">
            <input
              class="form-control text-3 h-auto py-2 border-0"
              type="text"
              name="username"
              value="user123"
              readonly
            />
          </div>
        </div>

        <!-- 비밀번호 + 수정 버튼 -->
        <div class="form-group row align-items-center">
          <label class="col-lg-3 col-form-label text-2">비밀번호</label>
          <div class="col-lg-6">
            <template v-if="!editing.password">
              <input
                class="form-control text-3 h-auto py-2"
                type="password"
                name="password"
                readonly
                value="********"
              />
            </template>
            <template v-else>
              <input
                class="form-control text-3 h-auto py-2"
                type="password"
                name="password"
                :value="form.userPw"
                @input="onPasswordInput"
              />
              <div v-if="passwordError" class="invalid-feedback d-block">
                {{ passwordError }}
              </div>
            </template>
          </div>
          <div class="col-lg-3 text-end">
            <template v-if="!editing.password">
              <button
                type="button"
                class="btn btn-light btn-outline"
                @click="toggleEdit('password')"
              >
                수정
              </button>
            </template>
            <template v-else>
              <button
                type="button"
                class="btn btn-primary btn-outline me-2"
                @click="saveField('password')"
                :disabled="!passwordValid"
              >
                확인
              </button>
              <button
                type="button"
                class="btn btn-light btn-outline"
                @click="cancelEdit('password')"
              >
                취소
              </button>
            </template>
          </div>
        </div>

        <!-- 이름 (변경 불가) -->
        <div class="form-group row align-items-center">
          <label class="col-lg-3 col-form-label text-2">이름</label>
          <div class="col-lg-9">
            <input
              class="form-control text-3 h-auto py-2 border-0"
              type="text"
              name="name"
              v-model="form.userNm"
              readonly
            />
          </div>
        </div>

        <!-- 생년월일 + 수정 버튼 -->
        <div class="form-group row align-items-center">
          <label class="col-lg-3 col-form-label text-2">생년월일</label>
          <div class="col-lg-6">
            <input
              class="form-control text-3 h-auto py-2 border-0"
              type="date"
              name="dob"
              :readonly="!editing.dob"
              v-model="form.userBirthDt"
            />
          </div>
          <div class="col-lg-3 text-end">
            <template v-if="!editing.dob">
              <button
                type="button"
                class="btn btn-light btn-outline"
                @click="toggleEdit('dob')"
              >
                수정
              </button>
            </template>
            <template v-else>
              <button
                type="button"
                class="btn btn-primary btn-outline me-2"
                @click="saveField('dob')"
              >
                확인
              </button>
              <button
                type="button"
                class="btn btn-light btn-outline"
                @click="cancelEdit('dob')"
              >
                취소
              </button>
            </template>
          </div>
        </div>

        <!-- 성별 + 수정 버튼 -->
        <div class="form-group row align-items-center">
          <label class="col-lg-3 col-form-label text-2">성별</label>
          <div class="col-lg-6">
            <input
              class="form-control text-3 h-auto py-2 border-0"
              name="gender"
              value="남성"
              v-model="form.userGenderNm"
              readonly
            />
          </div>
        </div>

        <!-- 이메일 + 수정 버튼 -->
        <div class="form-group row align-items-center">
          <label class="col-lg-3 col-form-label text-2">이메일</label>
          <div class="col-lg-6">
            <input
              class="form-control text-3 h-auto py-2 border-0"
              type="email"
              name="email"
              :readonly="!editing.email"
              v-model="form.userEmail"
            />
          </div>
          <div class="col-lg-3 text-end">
            <template v-if="!editing.email">
              <button
                type="button"
                class="btn btn-light btn-outline"
                @click="toggleEdit('email')"
              >
                수정
              </button>
            </template>
            <template v-else>
              <button
                type="button"
                class="btn btn-primary btn-outline me-2"
                @click="saveField('email')"
              >
                확인
              </button>
              <button
                type="button"
                class="btn btn-light btn-outline"
                @click="cancelEdit('email')"
              >
                취소
              </button>
            </template>
          </div>
        </div>

        <!-- 휴대폰번호 + 수정 버튼 -->
        <div class="form-group row align-items-center">
          <label class="col-lg-3 col-form-label text-2">휴대폰번호</label>
          <div class="col-lg-6">
            <input
              class="form-control text-3 h-auto py-2 border-0"
              type="text"
              name="phone"
              :readonly="!editing.phone"
              v-model="form.userPhoneNum"
            />
          </div>
          <div class="col-lg-3 text-end">
            <template v-if="!editing.phone">
              <button
                type="button"
                class="btn btn-light btn-outline"
                @click="toggleEdit('phone')"
              >
                수정
              </button>
            </template>
            <template v-else>
              <button
                type="button"
                class="btn btn-primary btn-outline me-2"
                @click="saveField('phone')"
              >
                확인
              </button>
              <button
                type="button"
                class="btn btn-light btn-outline"
                @click="cancelEdit('phone')"
              >
                취소
              </button>
            </template>
          </div>
        </div>

        <!-- 주소 + 수정 버튼 -->
        <div class="form-group row align-items-center">
          <label class="col-lg-3 col-form-label text-2">주소</label>

          <div class="col-lg-6">
            <template v-if="!editing.address">
              <input
                class="form-control text-3 h-auto py-2"
                type="text"
                :value="form.address + ' ' + form.detailAddress"
                readonly
              />
            </template>

            <template v-else>
              <div class="row">
                <div class="col-8 pe-1">
                  <input
                    class="form-control text-3 h-auto py-2"
                    type="text"
                    name="address"
                    :value="form.address"
                    @click="openPostcode"
                    placeholder="주소 검색 클릭"
                  />
                </div>
                <div class="col-4 ps-1">
                  <input
                    class="form-control text-3 h-auto py-2"
                    type="text"
                    name="detailAddress"
                    v-model="form.detailAddress"
                    placeholder="상세주소 입력"
                  />
                </div>
              </div>
            </template>
          </div>

          <div class="col-lg-3 text-end">
            <template v-if="!editing.address">
              <button
                type="button"
                class="btn btn-light btn-outline"
                @click="toggleEdit('address')"
              >
                수정
              </button>
            </template>
            <template v-else>
              <button
                type="button"
                class="btn btn-primary btn-outline d-inline-block me-2"
                @click="saveField('address')"
              >
                확인
              </button>
              <button
                type="button"
                class="btn btn-light btn-outline d-inline-block"
                @click="cancelEdit('address')"
              >
                취소
              </button>
            </template>
          </div>
        </div>

        <!-- 하단 버튼 -->
        <div class="form-group row mt-4">
          <div class="col-lg-12 text-center">
            <button
              type="submit"
              class="btn btn-primary btn-modern d-inline-block me-2"
              :disabled="isSaving"
            >
              저장
            </button>
            <button
              class="btn btn-light btn-modern d-inline-block"
              @click="resetForm"
              :disabled="isSaving"
            >
              초기화
            </button>
          </div>
        </div>
      </form>
    </div>
  </section>
</template>

<script setup>
// import PasswordCheck from '../common/PasswordCheck.vue'
import { onMounted, reactive, ref } from 'vue'
import { api } from '@/axios'
import { debounce } from 'lodash'

// const isConfirmed = ref(false)
// 상태 변수들
const error = ref(null)

// 불러온 원본 데이터를 저장할 객체
const originalData = reactive({
  userId: '',
  userPw: '',
  userEmail: '',
  userNm: '',
  userBirthDt: '',
  userGenderNm: '',
  userPhoneNum: '',
  address: '',
  detailAddress: '',
  postcode: '',
  sigungu: '',
  latitude: null,
  longitude: null,
})

// 양방향 바인딩용 폼 객체
const form = reactive({ ...originalData })

// 편집 상태를 관리할 객체
const editing = reactive({
  dob: false,
  email: false,
  phone: false,
  address: false,
  password: false,
})

const passwordError = ref('')
const passwordValid = ref(false)

// 유효성 검사 + 중복 확인 핵심 함수
const validatePasswordCore = async (pw) => {
  passwordError.value = ''
  passwordValid.value = false

  if (!pw) {
    passwordError.value = '비밀번호를 입력해주세요.'
    return
  }
  if (!/^(?=.*[A-Za-z])(?=.*\d)(?=.*[!@#$%^&*]).{8,}$/.test(pw)) {
    passwordError.value = '8자 이상, 영문·숫자·특수문자를 조합해 입력해주세요.'
    return
  }

  try {
    // API 요청 바디
    const requestBody = {
      currentPassword: form.userPw, // 입력한 비밀번호
    }
    const response = await api.$post(`/mypage/edit/check-password`, requestBody)
    console.log('res', response)
    if (response.status === 'OK' && response.output === true) {
      passwordError.value = '기존 비밀번호와 동일합니다.'
    } else {
      passwordValid.value = true
      passwordError.value = '사용 가능한 비밀번호입니다.'
    }
  } catch (e) {
    passwordError.value = '서버 오류가 발생했습니다.'
  }
}

// 디바운스 처리 함수
const validatePassword = debounce((pw) => {
  validatePasswordCore(pw)
}, 400)

// input 이벤트 핸들러
const onPasswordInput = (e) => {
  form.userPw = e.target.value
  validatePassword(form.userPw)
}

// 주소 검색 함수 (다음 주소 API 사용)
function openPostcode() {
  new window.daum.Postcode({
    oncomplete: function (data) {
      let addr =
        data.userSelectedType === 'R' ? data.roadAddress : data.jibunAddress

      form.postcode = data.zonecode
      form.address = addr
      form.detailAddress = ''
      form.sigungu = data.sigungu

      const geocoder = new window.kakao.maps.services.Geocoder()
      geocoder.addressSearch(addr, function (result, status) {
        if (status === window.kakao.maps.services.Status.OK) {
          form.latitude = result[0].y
          form.longitude = result[0].x
        } else {
          form.latitude = null
          form.longitude = null
        }
      })
    },
  }).open()
}

// 편집 모드 토글
function toggleEdit(field) {
  editing[field] = true
}

function saveField(field) {
  if (field === 'password' && !passwordValid.value) return
  editing[field] = false
}

function cancelEdit(field) {
  editing[field] = false

  if (field === 'address') {
    // 주소 관련 모든 값 복원
    form.address = originalData.address
    form.detailAddress = originalData.detailAddress
    form.postcode = originalData.postcode
    form.sigungu = originalData.sigungu
    form.latitude = originalData.latitude
    form.longitude = originalData.longitude
  } else {
    form[field] = originalData[field]
  }
}

function resetForm() {
  for (const key in originalData) {
    form[key] = originalData[key]
  }
  Object.keys(editing).forEach((field) => {
    editing[field] = false
  })
}

// API 호출로 초기 데이터 불러오기
onMounted(async () => {
  try {
    const response = await api.$get('/mypage/edit/info', null)
    const data = response.output
    console.log('불러온 값', data)

    Object.assign(originalData, {
      userId: data.userId,
      userEmail: data.userEmail,
      userPw: '',
      userNm: data.userNm,
      userBirthDt: data.userBirthDt,
      userGenderNm: data.userGenderNm,
      userPhoneNum: data.userPhoneNum,
      address: data.address,
      detailAddress: data.detailAddress || '',
      postcode: '',
      sigungu: '',
      latitude: null,
      longitude: null,
    })

    Object.assign(form, originalData)
  } catch (err) {
    console.error('정보 조회 실패', err)
    error.value = err.message
  }
})
</script>

<style scoped>
input[readonly] {
  border: none !important;
  background-color: transparent !important;
  box-shadow: none !important;
  pointer-events: none;
}

.form-check-input[disabled] {
  pointer-events: none;
}
</style>
