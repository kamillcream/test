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
            class="btn btn-light btn-sm position-absolute"
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
          <div class="col-lg-7">
            <input
              class="form-control text-3 h-auto py-2 border-0"
              type="password"
              name="password"
              value="********"
              readonly
            />
          </div>
          <div class="col-lg-2">
            <button
              type="button"
              class="btn btn-light btn-outline"
              @click="toggleEdit('password')"
            >
              {{ editing.password ? '확인' : '수정' }}
            </button>
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
              value="홍길동"
              readonly
            />
          </div>
        </div>

        <!-- 생년월일 + 수정 버튼 -->
        <div class="form-group row align-items-center">
          <label class="col-lg-3 col-form-label text-2">생년월일</label>
          <div class="col-lg-7">
            <input
              class="form-control text-3 h-auto py-2 border-0"
              type="date"
              name="dob"
              :readonly="!editing.dob"
              v-model="form.dob"
            />
          </div>
          <div class="col-lg-2">
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
          <div class="col-lg-7">
            <input
              class="form-control text-3 h-auto py-2 border-0"
              name="gender"
              value="남성"
              v-model="form.gender"
              readonly
            />
          </div>
        </div>

        <!-- 이메일 + 수정 버튼 -->
        <div class="form-group row align-items-center">
          <label class="col-lg-3 col-form-label text-2">이메일</label>
          <div class="col-lg-7">
            <input
              class="form-control text-3 h-auto py-2 border-0"
              type="email"
              name="email"
              :readonly="!editing.email"
              v-model="form.email"
            />
          </div>
          <div class="col-lg-2">
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
          <div class="col-lg-7">
            <input
              class="form-control text-3 h-auto py-2 border-0"
              type="text"
              name="phone"
              :readonly="!editing.phone"
              v-model="form.phone"
            />
          </div>
          <div class="col-lg-2">
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
          <div class="col-lg-5">
            <input
              class="form-control text-3 h-auto py-2"
              type="text"
              name="address"
              :readonly="!editing.address"
              v-model="form.address"
            />
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
              type="reset"
              class="btn btn-light btn-modern d-inline-block"
              @click="resetForm"
              :disabled="isSaving"
            >
              취소
            </button>
          </div>
        </div>
      </form>
    </div>
  </section>
</template>

<script setup>
// import PasswordCheck from '../common/PasswordCheck.vue'
import { reactive, ref } from 'vue'

// const isConfirmed = ref(false)
const isSaving = ref(false)

const originalData = {
  dob: '1990-01-01',
  gender: '남성',
  email: 'hong@example.com',
  phone: '010-1234-5678',
  address: '서울시 강남구',
}

const form = reactive({ ...originalData })

const editing = reactive({
  password: false,
  dob: false,
  email: false,
  phone: false,
  address: false,
})

function toggleEdit(field) {
  if (editing[field]) {
    // 확인 클릭 (실제 저장 처리 필요하면 여기서)
    saveField(field)
  } else {
    editing[field] = true
  }
}

function saveField(field) {
  // 여기서 서버 저장 로직 추가 가능
  // 현재는 단순히 편집모드 해제
  editing[field] = false
  // originalData 업데이트 (필요 시)
  originalData[field] = form[field]
}

function cancelEdit(field) {
  editing[field] = false
  form[field] = originalData[field]
}

function saveAll() {
  isSaving.value = true
  // 전체 저장 처리 예시 (API 호출 등)
  setTimeout(() => {
    // 저장 완료 후
    isSaving.value = false
    // 모든 편집 모드 해제
    Object.keys(editing).forEach((key) => {
      editing[key] = false
      originalData[key] = form[key]
    })
    alert('저장이 완료되었습니다.')
  }, 1000)
}

function resetForm() {
  Object.keys(form).forEach((key) => {
    form[key] = originalData[key]
  })
  Object.keys(editing).forEach((key) => {
    editing[key] = false
  })
}
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
