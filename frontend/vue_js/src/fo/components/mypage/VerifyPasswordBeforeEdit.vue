<template>
  <div>
    <div class="overflow-hidden mb-1">
      <h2 class="font-weight-normal text-7 mb-0">회원 정보 수정</h2>
    </div>
    <div class="overflow-hidden mb-4 pb-3">
      <p class="mb-0">비밀번호 확인</p>
    </div>

    <form role="form" class="needs-validation" novalidate="novalidate">
      <div class="form-group row">
        <label
          class="col-lg-3 col-form-label form-control-label line-height-9 pt-2 text-2 required"
          >비밀번호</label
        >
        <div class="col-lg-9">
          <input
            class="form-control text-3 h-auto py-2"
            type="password"
            name="firstName"
            value="John"
            required=""
          />
          <div class="invalid-feedback text-primary" style="display: block">
            비밀번호가 일치하지 않습니다.
          </div>
        </div>
      </div>
      <div class="form-group row">
        <div class="form-group col-lg-9"></div>
        <div class="form-group col-lg-3">
          <input
            type="submit"
            value="확인"
            class="btn btn-primary btn-modern float-end"
            data-loading-text="Loading..."
          />
        </div>
      </div>
    </form>
  </div>
</template>

<script setup></script>
