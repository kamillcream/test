<template>
  <section>
    <div v-if="userStore.userType === 'COMPANY'">
      <div class="overflow-hidden mb-3">
        <h2 class="font-weight-normal text-7 mb-0">회원 정보 수정</h2>
      </div>

      <!-- 프로필 이미지 (기업 아이콘으로 대체) -->
      <div class="text-center mb-4">
        <div class="position-relative d-inline-block">
          <!-- 기본 이미지 아이콘 (기업 아이콘) -->
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
            class="position-absolute top-0 start-0 w-100 h-100 opacity-0"
            title="사진 변경"
          />
        </div>
      </div>

      <form role="form" class="needs-validation" novalidate="novalidate">
        <!-- 아이디 (변경 불가) -->
        <div class="form-group row align-items-center">
          <label class="col-lg-3 col-form-label text-2">아이디</label>
          <div class="col-lg-9">
            <input
              class="form-control text-3 h-auto py-2 border-0"
              type="text"
              name="username"
              value="user123"
              readonly=""
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
              readonly=""
            />
          </div>
          <div class="col-lg-2">
            <button type="button" class="btn btn-light btn-outline">
              수정
            </button>
          </div>
        </div>

        <!-- 담당자 이름 -->
        <div class="form-group row align-items-center">
          <label class="col-lg-3 col-form-label text-2">담당자 이름</label>
          <div class="col-lg-7">
            <input
              class="form-control text-3 h-auto py-2 border-0"
              type="text"
              name="name"
              value="홍길동"
              readonly=""
            />
          </div>
          <div class="col-lg-2">
            <button type="button" class="btn btn-light btn-outline">
              수정
            </button>
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
              value="1990-01-01"
              readonly=""
            />
          </div>
          <div class="col-lg-2">
            <button type="button" class="btn btn-light btn-outline">
              수정
            </button>
          </div>
        </div>

        <!-- 성별 + 수정 버튼 -->
        <div class="form-group row align-items-center">
          <label class="col-lg-3 col-form-label text-2">성별</label>
          <div class="col-lg-7">
            <div class="d-flex">
              <div class="form-check">
                <input
                  class="form-check-input"
                  type="radio"
                  name="gender"
                  value="male"
                  checked
                />
                남성
              </div>
              <div class="form-check ms-3">
                <input
                  class="form-check-input"
                  type="radio"
                  name="gender"
                  value="female"
                />
                여성
              </div>
            </div>
          </div>
          <div class="col-lg-2">
            <button type="button" class="btn btn-light btn-outline">
              수정
            </button>
          </div>
        </div>

        <!-- 기업 이름 -->
        <div class="form-group row align-items-center">
          <label class="col-lg-3 col-form-label text-2">기업명</label>
          <div class="col-lg-9">
            <input
              class="form-control text-3 h-auto py-2 border-0"
              type="text"
              name="name"
              value="(주) 이에스티소프트"
              readonly=""
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
              value="hong@example.com"
              readonly=""
            />
          </div>
          <div class="col-lg-2">
            <button type="button" class="btn btn-light btn-outline">
              수정
            </button>
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
              value="010-1234-5678"
              readonly=""
            />
          </div>
          <div class="col-lg-2">
            <button type="button" class="btn btn-light btn-outline">
              수정
            </button>
          </div>
        </div>

        <!-- 주소 + 수정 버튼 -->
        <div class="form-group row align-items-center">
          <label class="col-lg-3 col-form-label text-2">주소</label>
          <div class="col-lg-6">
            <input
              class="form-control text-3 h-auto py-2"
              type="text"
              name="address"
              value="서울시 강남구"
              readonly=""
            />
          </div>
          <div class="col-lg-3">
            <button
              type="button"
              class="btn btn-primary btn-outline d-inline-block me-2"
            >
              확인
            </button>
            <button
              type="button"
              class="btn btn-light btn-outline d-inline-block"
            >
              취소
            </button>
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
              type="reset"
              class="btn btn-light btn-modern d-inline-block"
            >
              취소
            </button>
          </div>
        </div>
      </form>
    </div>
    <div v-else>
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

      <form role="form" class="needs-validation" novalidate="novalidate">
        <!-- 아이디 (변경 불가) -->
        <div class="form-group row align-items-center">
          <label class="col-lg-3 col-form-label text-2">아이디</label>
          <div class="col-lg-9">
            <input
              class="form-control text-3 h-auto py-2 border-0"
              type="text"
              name="username"
              value="user123"
              readonly=""
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
              readonly=""
            />
          </div>
          <div class="col-lg-2">
            <button type="button" class="btn btn-light btn-outline">
              수정
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
              readonly=""
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
              value="1990-01-01"
              readonly=""
            />
          </div>
          <div class="col-lg-2">
            <button type="button" class="btn btn-light btn-outline">
              수정
            </button>
          </div>
        </div>

        <!-- 성별 + 수정 버튼 -->
        <div class="form-group row align-items-center">
          <label class="col-lg-3 col-form-label text-2">성별</label>
          <div class="col-lg-7">
            <div class="d-flex">
              <div class="form-check">
                <input
                  class="form-check-input"
                  type="radio"
                  name="gender"
                  value="male"
                  checked
                />
                남성
              </div>
              <div class="form-check ms-3">
                <input
                  class="form-check-input"
                  type="radio"
                  name="gender"
                  value="female"
                />
                여성
              </div>
            </div>
          </div>
          <div class="col-lg-2">
            <button type="button" class="btn btn-light btn-outline">
              수정
            </button>
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
              value="hong@example.com"
              readonly=""
            />
          </div>
          <div class="col-lg-2">
            <button type="button" class="btn btn-light btn-outline">
              수정
            </button>
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
              value="010-1234-5678"
              readonly=""
            />
          </div>
          <div class="col-lg-2">
            <button type="button" class="btn btn-light btn-outline">
              수정
            </button>
          </div>
        </div>

        <!-- 주소 + 수정 버튼 -->
        <div class="form-group row align-items-center">
          <label class="col-lg-3 col-form-label text-2">주소</label>
          <div class="col-lg-6">
            <input
              class="form-control text-3 h-auto py-2"
              type="text"
              name="address"
              value="서울시 강남구"
              readonly=""
            />
          </div>
          <div class="col-lg-3">
            <button
              type="button"
              class="btn btn-primary btn-outline d-inline-block me-2"
            >
              확인
            </button>
            <button
              type="button"
              class="btn btn-light btn-outline d-inline-block"
            >
              취소
            </button>
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
              type="reset"
              class="btn btn-light btn-modern d-inline-block"
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
import { useUserStore } from '@/fo/stores/userStore'

const userStore = useUserStore()
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

.fas.fa-user.text-muted {
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
  border-radius: 20px;
}

/* 하단 버튼 */
.btn-modern {
  font-weight: 500;
}
</style>
