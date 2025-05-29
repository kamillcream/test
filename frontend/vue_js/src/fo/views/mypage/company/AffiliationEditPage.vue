<template>
  <div>
    <div class="overflow-hidden mb-3">
      <h2 class="font-weight-normal text-7 mb-0">소속 정보 수정</h2>
    </div>

    <!-- 프로필 이미지 (사람 아이콘으로 대체) -->
    <div
      class="text-center mb-4"
      :class="{ 'disabled-form': form.companyIsRecruitingYn !== 'Y' }"
    >
      <div class="position-relative d-inline-block">
        <!-- 기본 이미지 아이콘 (사람 아이콘) -->
        <div class="rounded-circle">
          <i class="fas fa-building text-muted"></i>
        </div>

        <!-- 사진 변경 버튼 (카메라 아이콘이 이미지의 우측 하단 밖으로 걸쳐짐) -->
        <label
          for="profileImage"
          class="btn btn-light btn-sm position-absolute"
        >
          <i class="fas fa-camera text-muted"></i>
        </label>

        <!-- 파일 입력 필드 (보이지 않음) -->
        <input
          type="file"
          id="profileImage"
          class="position-absolute top-0 start-0 w-100 h-100 opacity-0 add"
          title="사진 변경"
        />
      </div>
    </div>
    <!-- 소속 모집 여부 체크박스 -->
    <div class="form-group row align-items-center">
      <label class="col-lg-2 col-form-label text-2">소속 모집 여부</label>
      <div class="col-lg-10">
        <input
          type="checkbox"
          name="recruiting"
          id="recruiting"
          class="form-check-input"
          :checked="form.companyIsRecruitingYn === 'Y'"
          @change="onCheckboxChange"
        />
        <label for="recruiting" class="form-check-label text-dark text-3"
          >모집중</label
        >
      </div>
    </div>
    <form
      role="form"
      class="needs-validation"
      novalidate="novalidate"
      @submit.prevent="saveAll"
      :class="{ 'disabled-form': form.companyIsRecruitingYn !== 'Y' }"
    >
      <!-- 대표자 이름 (변경 불가) -->
      <div class="form-group row align-items-center">
        <label class="col-lg-2 col-form-label text-2">대표자 이름</label>
        <div class="col-lg-10">
          <input
            class="form-control text-3 h-auto py-2 border-0"
            type="text"
            name="CompanyCeoName"
            :value="form.companyCeoNm"
            readonly
          />
        </div>
      </div>

      <!-- 기업 이름 (변경 불가) -->
      <div class="form-group row align-items-center">
        <label class="col-lg-2 col-form-label text-2">기업명</label>
        <div class="col-lg-10">
          <input
            class="form-control text-3 h-auto py-2 border-0"
            type="text"
            name="companyName"
            :value="form.companyNm"
            readonly
          />
        </div>
      </div>

      <!-- 개업일자 (변경 불가) -->
      <div class="form-group row align-items-center">
        <label class="col-lg-2 col-form-label text-2">개업일자</label>
        <div class="col-lg-10">
          <input
            class="form-control text-3 h-auto py-2 border-0"
            type="text"
            name="openDate"
            :value="form.companyOpenDt"
            readonly
          />
        </div>
      </div>

      <!-- 기업 URL -->
      <div class="form-group row align-items-center">
        <label class="col-lg-2 col-form-label text-2">기업 URL</label>
        <div class="col-lg-7">
          <template v-if="!editing.companyUrl"
            ><input
              class="form-control text-3 h-auto py-2 border-0"
              type="text"
              name="name"
              v-model="form.companyUrl"
              placeholder="기업 URL을 입력하세요."
              readonly
            />
          </template>
          <template v-else
            ><input
              class="form-control text-3 h-auto py-2"
              type="text"
              name="name"
              v-model="form.companyUrl"
              placeholder="기업 URL을 입력하세요."
              @input="validateUrl"
            />
            <div v-if="urlError" class="invalid-feedback d-block">
              {{ urlError }}
            </div>
          </template>
        </div>
        <div class="col-lg-3 text-end">
          <template v-if="!editing.companyUrl">
            <button
              type="button"
              class="btn btn-light btn-outline"
              @click="toggleEdit('companyUrl')"
            >
              수정
            </button>
          </template>
          <template v-else>
            <button
              type="button"
              class="btn btn-primary btn-outline d-inline-block me-2"
              @click="saveField('companyUrl')"
              :disabled="!urlValid"
            >
              확인
            </button>
            <button
              type="button"
              class="btn btn-light btn-outline d-inline-block"
              @click="cancelEdit('companyUrl')"
            >
              취소
            </button>
          </template>
        </div>
      </div>

      <!-- 대표 번호 + 수정 버튼 -->
      <div class="form-group row align-items-center">
        <label class="col-lg-2 col-form-label text-2">대표번호</label>
        <div class="col-lg-7">
          <template v-if="!editing.userPhoneNum">
            <input
              class="form-control text-3 h-auto py-2 border-0"
              type="text"
              name="phone"
              readonly
              :value="formatPhoneNumber(form.userPhoneNum)"
            />
          </template>
          <template v-else>
            <input
              class="form-control text-3 h-auto py-2"
              type="text"
              name="phone"
              v-model="form.userPhoneNum"
              @input="validatePhone"
            />
            <div v-if="phoneError" class="invalid-feedback">
              {{ phoneError }}
            </div>
          </template>
        </div>
        <div class="col-lg-3 text-end">
          <template v-if="!editing.userPhoneNum">
            <button
              type="button"
              class="btn btn-light btn-outline"
              @click="toggleEdit('userPhoneNum')"
            >
              수정
            </button>
          </template>
          <template v-else>
            <button
              type="button"
              class="btn btn-primary btn-outline me-2"
              @click="saveField('userPhoneNum')"
            >
              확인
            </button>
            <button
              type="button"
              class="btn btn-light btn-outline"
              @click="cancelEdit('userPhoneNum')"
            >
              취소
            </button>
          </template>
        </div>
      </div>
      <!-- 주소 + 수정 버튼 -->
      <div class="form-group row align-items-center">
        <label class="col-lg-2 col-form-label text-2">주소</label>

        <div class="col-lg-7">
          <template v-if="!editing.address">
            <input
              class="form-control text-3 h-auto py-2 border-0"
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
      <!-- 모집 내용 -->
      <div class="form-group row align-items-center">
        <label class="col-lg-2 col-form-label text-2">모집 내용</label>
        <div class="col-lg-7 position-relative">
          <!-- 보기 모드 -->
          <div
            v-if="!editing.companyGreetingTxt"
            class="form-control text-3 h-auto py-2 border-0 recruitment-content"
          >
            {{ form.companyGreetingTxt }}
          </div>

          <!-- 수정 모드 -->
          <textarea
            v-else
            v-model="form.companyGreetingTxt"
            class="form-control text-3 h-auto py-2"
            rows="4"
          ></textarea>
        </div>
        <div class="col-lg-3 text-end">
          <template v-if="!editing.companyGreetingTxt">
            <button
              type="button"
              class="btn btn-light btn-outline"
              @click="toggleEdit('companyGreetingTxt')"
            >
              수정
            </button>
          </template>
          <template v-else>
            <button
              type="button"
              class="btn btn-primary btn-outline d-inline-block me-2"
              @click="saveField('companyGreetingTxt')"
            >
              확인
            </button>
            <button
              type="button"
              class="btn btn-light btn-outline d-inline-block"
              @click="cancelEdit('companyGreetingTxt')"
            >
              취소
            </button>
          </template>
        </div>
      </div>

      <!-- 태그 + 수정 버튼 -->
      <div class="form-group row align-items-center">
        <label class="col-lg-2 col-form-label text-2">태그</label>
        <!-- 태그 리스트 -->
        <div class="col-lg-7">
          <span
            class="badge me-1"
            :style="{
              backgroundColor: '#0088CC',
              color: 'white',
              cursor: 'pointer',
            }"
            v-for="(item, index) in editTagNm"
            :key="index"
            @click="editTagNm.value.splice(index, 1)"
            title="클릭하여 삭제"
          >
            {{ item }} &times;
          </span>
        </div>

        <!-- 오른쪽 버튼 영역 -->
        <div class="col-lg-3 text-end">
          <template v-if="!editing.tagNm">
            <button
              type="button"
              class="btn btn-light btn-outline"
              @click="toggleEdit('tagNm')"
            >
              수정
            </button>
          </template>
          <template v-else>
            <button
              type="button"
              class="btn btn-primary btn-outline me-2"
              @click="saveField('tagNm')"
            >
              확인
            </button>
            <button
              type="button"
              class="btn btn-light btn-outline"
              @click="cancelEdit('tagNm')"
            >
              취소
            </button>
          </template>
        </div>
      </div>

      <!-- 태그 입력 영역 (수정 모드일 때만 표시) -->
      <div v-if="editing.tagNm" class="row mt-2">
        <div class="col-lg-2"></div>
        <div class="form-group col-lg-7">
          <div class="input-group text-end">
            <input
              type="text"
              class="form-control text-3 h-auto py-2"
              v-model="preferContent"
              placeholder="쉼표(,)로 구분하여 입력"
              name="qualification"
            />
          </div>
        </div>
      </div>

      <!-- 하단 버튼 -->
      <div class="form-group row mt-4">
        <div class="col-lg-12 text-center">
          <button
            type="submit"
            class="btn btn-primary btn-modern d-inline-block me-2"
          >
            저장
          </button>
          <button
            class="btn btn-light btn-modern d-inline-block"
            @click="resetForm"
          >
            초기화
          </button>
        </div>
      </div>
    </form>
  </div>
</template>

<script setup>
import { reactive, ref, onMounted, watch } from 'vue'
import { api } from '@/axios'
import { useAlertStore } from '@/fo/stores/alertStore'
import _ from 'lodash'

const alertStore = useAlertStore()

const error = ref(null)

const originalData = reactive({
  companyIsRecruitingYn: '',
  companyCeoNm: '',
  companyNm: '',
  companyOpenDt: '',
  companyUrl: '',
  userPhoneNum: '',
  address: '',
  detailAddress: '',
  zonecode: '',
  sigungu: '',
  latitude: null,
  longitude: null,
  companyGreetingTxt: '',
})

const originalTagNm = ref([])
const editTagNm = ref([])

// 양방향 바인딩용 폼 객체
const form = reactive({ ...originalData })

// 편집 상태를 관리할 객체
const editing = reactive({
  userPhoneNum: false,
  address: false,
  companyUrl: false,
  companyGreetingTxt: false,
  tagNm: false,
})

function onCheckboxChange(event) {
  const isChecked = event.target.checked
  form.companyIsRecruitingYn = isChecked ? 'Y' : 'N'

  if (!isChecked) {
    api
      .$post('/mypage/edit/affiliation/recruiting/cancel')
      .then((res) => {
        alertStore.show(res.message, 'success')
      })
      .catch((err) => {
        alertStore.show(
          err.response?.data?.message ||
            '모집 상태 해제 중 오류가 발생했습니다.',
          'danger',
        )
      })
  }
}

const urlError = ref('')
const urlValid = ref(false)

// URL 유효성 검사

function validateUrl() {
  const url = (form.companyUrl ?? '').trim() // null 또는 undefined일 때 빈 문자열 대체
  if (!url) {
    urlError.value = 'URL을 입력해주세요.'
    urlValid.value = false
    return
  }

  const pattern = /^(https?:\/\/)?([\w-]+\.)+[\w-]+(\/[\w\-./?%&=]*)?$/
  if (!pattern.test(url)) {
    urlError.value = '유효한 URL 형식이 아닙니다.'
    urlValid.value = false
  } else {
    urlError.value = ''
    urlValid.value = true
  }
}

// 주소 검색 함수 (다음 주소 API 사용)
function openPostcode() {
  new window.daum.Postcode({
    oncomplete: function (data) {
      let addr =
        data.userSelectedType === 'R' ? data.roadAddress : data.jibunAddress

      form.zonecode = data.zonecode
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

function formatPhoneNumber(number) {
  const clean = number.replace(/\D/g, '')
  if (clean.length === 11) {
    return clean.replace(/(\d{3})(\d{4})(\d{4})/, '$1-$2-$3')
  } else if (clean.length === 10) {
    return clean.replace(/(\d{3})(\d{3,4})(\d{4})/, '$1-$2-$3')
  } else {
    return number // 길이가 다르면 그냥 원본 반환
  }
}

const phoneError = ref('')
const phoneValid = ref(false)

// 휴대폰 번호 유효성 검사
const validatePhone = () => {
  phoneError.value = ''
  phoneValid.value = false
  if (!form.userPhoneNum) {
    phoneError.value = '휴대폰 번호를 입력해주세요.'
  } else if (!/^\d{10,11}$/.test(form.userPhoneNum)) {
    phoneError.value = '올바른 휴대폰 번호 형식이 아닙니다. (하이픈 제외)'
  } else {
    phoneError.value = ''
    phoneValid.value = true
  }
}

const preferContent = ref('')

watch(preferContent, (newVal) => {
  if (newVal.endsWith(',')) {
    const tags = newVal
      .split(',')
      .map((tag) => tag.trim())
      .filter((tag) => tag.length > 0 && !editTagNm.value.includes(tag))

    editTagNm.value.push(...tags)
    preferContent.value = ''
  }
})

// 편집 모드 토글
function toggleEdit(field) {
  editing[field] = true
}

function saveField(field) {
  if (field === 'phone' && !phoneValid.value) return

  editing[field] = false
  console.log('form', form)
}

function cancelEdit(field) {
  editing[field] = false

  if (field === 'address') {
    form.address = originalData.address
    form.detailAddress = originalData.detailAddress
    form.zonecode = originalData.zonecode
    form.sigungu = originalData.sigungu
    form.latitude = originalData.latitude
    form.longitude = originalData.longitude
  } else if (field === 'tagNm') {
    editTagNm.value = [...originalTagNm.value]
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

function isFormChanged() {
  return (
    form.userPhoneNum !== originalData.userPhoneNum ||
      form.companyUrl !== originalData.companyUrl ||
      form.zonecode !== originalData.zonecode ||
      form.address !== originalData.address ||
      form.detailAddress !== originalData.detailAddress ||
      form.sigungu !== originalData.sigungu ||
      form.latitude !== originalData.latitude ||
      form.longitude !== originalData.longitude,
    !_.isEqual(editTagNm.value, originalTagNm.value)
  )
}

const saveAll = async () => {
  const isAnyEditing = Object.values(editing).some((v) => v === true)
  if (isAnyEditing) {
    alertStore.show('수정 중인 항목을 먼저 저장하거나 취소해주세요.', 'danger')
    return
  }

  if (!isFormChanged()) {
    alertStore.show('변경된 정보가 없습니다.', 'danger')
    return
  }

  const requestBody = {
    userPhoneNum: form.userPhoneNum,
    companyUrl: form.companyUrl,
    zonecode: form.zonecode,
    address: form.address,
    detailAddress: form.detailAddress,
    sigungu: form.sigungu,
    latitude: form.latitude,
    longitude: form.longitude,
    companyGreetingTxt: form.companyGreetingTxt,
    tagNm: [...editTagNm.value],
    companyIsRecruitingYn: form.companyIsRecruitingYn,
  }

  console.log('requestBody', requestBody)
  // try {
  //   await api.$post('/mypage/edit/update', requestBody)
  //   alertStore.show('회원 정보가 성공적으로 수정되었습니다.', 'success')
  //   await fetchUserInfo() // 저장 후 다시 원본 데이터 받아오기
  //   resetForm() // 폼도 초기화
  // } catch (err) {
  //   // 서버에서 온 에러 메시지
  //   const errorMessage =
  //     err.response?.data?.message || '회원 정보 수정에 실패하였습니다.'
  //   alertStore.show(errorMessage, 'danger')
  // }
}

async function fetchAffiliationInfo() {
  try {
    const response = await api.$get('/mypage/edit/affiliation/info', null)
    const data = response.output
    console.log('data', data)

    Object.assign(originalData, {
      companyIsRecruitingYn: data.companyIsRecruitingYn,
      companyCeoNm: data.companyCeoNm,
      companyNm: data.companyNm,
      companyOpenDt: data.companyOpenDt,
      companyUrl: data.companyUrl,
      userPhoneNum: data.userPhoneNum,
      address: data.address,
      detailAddress: data.detailAddress || '',
      zonecode: data.zonecode,
      sigungu: data.sigungu,
      latitude: data.latitude,
      longitude: data.longitude,
      companyGreetingTxt: data.companyGreetingTxt,
    })

    originalTagNm.value = [...data.tagNm]

    Object.assign(form, originalData)
  } catch (err) {
    console.error('정보 조회 실패', err)
    error.value = err.message
  }
}

onMounted(() => {
  fetchAffiliationInfo()
})
</script>

<style scoped>
/* 프로필 이미지 스타일 */
.rounded-circle {
  width: 120px;
  height: 120px;
  background-color: #f2f2f2;
  display: flex;
  justify-content: center;
  align-items: center;
}
.fas.fa-building.text-muted {
  font-size: 50px;
}

input:focus {
  outline: none;
  box-shadow: none;
  border-color: inherit; /* 또는 원래의 border 색 */
}

.btn.btn-light.btn-sm.position-absolute {
  cursor: pointer;
  z-index: 10;
  border-radius: 50%;
  padding: 10px;
  width: 40px;
  height: 40px;
  border: 2px solid #ddd;
  bottom: -10px;
  right: -10px;
  display: flex;
  justify-content: center;
  align-items: center;
}

.fas.fa-camera.text-muted {
  font-size: 20px;
}

.recruitment-content {
  min-height: 120px;
  background-color: #f9f9f9;
}

/* 폼 그룹 스타일 */
.form-group.row.align-items-center {
  margin-bottom: 20px;
}

.form-group label {
  font-size: 16px;
}

.form-control {
  font-size: 16px;
}

.form-check {
  display: inline-block;
  margin-right: 15px;
}

.form-check-input {
  margin-right: 5px;
}

/* 수정 버튼 */
.btn-light.btn-outline {
  font-size: 14px;
}

/* 하단 버튼 */
.btn-modern {
  font-weight: 500;
}

.disabled-form {
  opacity: 0.5;
  filter: blur(0.8px);
  pointer-events: none; /* 클릭 불가 */
}

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
