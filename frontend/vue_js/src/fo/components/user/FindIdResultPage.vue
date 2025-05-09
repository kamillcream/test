<template>
  <section>
    <CommonPageHeader
      title=""
      strongText="아이디 찾기"
      :breadcrumbs="[{ text: 'Home', link: '/' }, { text: 'ID/PW' }]"
    />
    <div class="container py-5">
      <div class="row justify-content-center">
        <div class="col-lg-10">
          <div class="card border-0 shadow-lg">
            <div class="card-header bg-grey text-dark">
              <h5 class="mb-0">개인 아이디 찾기 결과</h5>
            </div>
            <div class="card-body">
              <div class="table-responsive">
                <table
                  class="table table-bordered table-hover text-center align-middle mb-4"
                >
                  <thead class="table-light">
                    <tr>
                      <th scope="col">구분</th>
                      <th scope="col">아이디</th>
                      <th scope="col">이름</th>
                      <th scope="col">가입일</th>
                    </tr>
                  </thead>
                  <tbody>
                    <tr>
                      <td>인증여부</td>
                      <td>
                        <a
                          href="#"
                          class="d-flex align-items-center justify-content-center gap-2 text-decoration-none"
                        >
                          <span class="fw-bold">est1234</span>
                        </a>
                      </td>
                      <td>홍길동</td>
                      <td>2025.04.17</td>
                    </tr>
                  </tbody>
                </table>
              </div>

              <div class="d-flex justify-content-center gap-3">
                <a href="/login" class="btn btn-primary px-4">로그인</a>
                <a
                  href="/findAccount?tab=resetPassword"
                  class="btn btn-primary btn-outline px-4"
                >
                  비밀번호 찾기
                </a>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </section>
</template>

<script setup>
import CommonPageHeader from '../common/CommonPageHeader.vue'
</script>
