<template>
  <div>
    <div class="overflow-hidden mb-3">
      <h2 class="font-weight-normal text-7 mb-0">소속 정보 수정</h2>
    </div>

    <!-- 프로필 이미지 (사람 아이콘으로 대체) -->
    <div class="text-center mb-4">
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
          class="position-absolute top-0 start-0 w-100 h-100 opacity-0"
          title="사진 변경"
        />
      </div>
    </div>

    <form role="form" class="needs-validation" novalidate="novalidate">
      <!-- 소속 모집 여부 체크박스 -->
      <div class="form-group row align-items-center">
        <label class="col-lg-3 col-form-label text-2">소속 모집 여부</label>
        <div class="col-lg-9">
          <input
            type="checkbox"
            name="recruiting"
            id="recruiting"
            class="form-check-input"
          />
          <label for="recruiting" class="form-check-label text-dark text-3"
            >모집중</label
          >
        </div>
      </div>

      <!-- 대표자 이름 -->
      <div class="form-group row align-items-center">
        <label class="col-lg-3 col-form-label text-2">대표자 이름</label>
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

      <!-- 개업일자 -->
      <div class="form-group row align-items-center">
        <label class="col-lg-3 col-form-label text-2">개업일자</label>
        <div class="col-lg-9">
          <input
            class="form-control text-3 h-auto py-2 border-0"
            type="text"
            name="name"
            value="2020.03.01"
            readonly=""
          />
        </div>
      </div>

      <!-- 생년월일 + 수정 버튼 -->
      <div class="form-group row align-items-center">
        <label class="col-lg-3 col-form-label text-2">기업 url</label>
        <div class="col-lg-7">
          <input
            class="form-control text-3 h-auto py-2 border-0"
            type="date"
            name="dob"
            value="estse.co.kr"
            readonly=""
          />
        </div>
        <div class="col-lg-2">
          <button type="button" class="btn btn-light btn-outline">수정</button>
        </div>
      </div>

      <!-- 휴대폰번호 + 수정 버튼 -->
      <div class="form-group row align-items-center">
        <label class="col-lg-3 col-form-label text-2">대표 번호</label>
        <div class="col-lg-7">
          <input
            class="form-control text-3 h-auto py-2 border-0"
            type="text"
            name="phone"
            value="1588-1588"
            readonly=""
          />
        </div>
        <div class="col-lg-2">
          <button type="button" class="btn btn-light btn-outline">수정</button>
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

      <!-- 모집 내용 (div로 변경) + 수정 버튼 -->
      <div class="form-group row align-items-center">
        <label class="col-lg-3 col-form-label text-2">모집 내용</label>
        <div class="col-lg-8 position-relative">
          <div
            class="form-control text-3 h-auto py-2 border-0 recruitment-content"
          >
            열정있는 개발자 모집합니다<br />
            많은 지원 바랍니다.
          </div>
        </div>
      </div>
      <div class="form-group row mt-4">
        <div class="col-lg-7"></div>
        <div class="col-lg-3"></div>
        <div class="col-lg-2">
          <button type="button" class="btn btn-light btn-outline">수정</button>
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
          <button type="reset" class="btn btn-light btn-modern d-inline-block">
            취소
          </button>
        </div>
      </div>
    </form>
  </div>
</template>

<script setup></script>

<style scoped>
/* UserProfile.css */
/* <!--    <label for="profileImage" class="btn btn-light btn-sm position-absolute" style="cursor:pointer; 
z-index: 10; border-radius: 50%; padding: 10px; width: 40px; height: 40px; 
                          border: 2px solid #ddd; bottom: -10px; right: -10px;
                          display: flex; justify-content: center; align-items: center;">
                <i class="fas fa-camera text-muted" style="font-size: 20px;"></i>
            </label> --> */

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
  border-radius: 20px;
}

/* 하단 버튼 */
.btn-modern {
  font-weight: 500;
}
</style>
