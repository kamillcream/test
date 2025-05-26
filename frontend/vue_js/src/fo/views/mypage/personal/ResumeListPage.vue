<template>
  <div>
    <div class="row">
      <div class="col">
        <h1 class="font-weight-normal text-10 mb-0">
          <strong>이력서 목록</strong>
        </h1>
      </div>
    </div>
    <div class="row">
      <div class="col pt-2 mt-1">
        <hr class="my-4" />
      </div>
    </div>
    <div class="row">
      <div class="col">
        <ul class="simple-post-list m-0 position-relative">
          <li v-for="resume in resumeList" :key="resume.resumeSq">
            <!-- 닫기(X) 버튼 -->
            <button
              class="btn btn-light btn-lg position-absolute"
              style="top: 0; right: 0; color: #aaa; border: none"
              @click="removeResume(resume.resumeSq)"
            >
              &times;
            </button>
            <div class="post-info position-relative">
              <!-- 제목 + 뱃지 -->
              <div class="d-flex align-items-center gap-2">
                <a href="#" class="text-6 m-0">{{ resume.resumeTtl }}</a>
                <span v-if="resume.resumeIsRepresentativeYn === 'Y'" class="btn btn-primary btn-sm"
                  >대표 이력서</span
                >
              </div>
              <!-- 등록일자 + 버튼 -->
              <div
                class="d-flex justify-content-between align-items-center mt-2"
              >
                <div class="post-meta text-4">
                  <span class="text-dark text-uppercase font-weight-semibold"
                    >등록일자</span
                  >
                  |{{ resume.resumeCreatedAtDtm?.substring(0, 10).replaceAll('-', '.') ?? resume.resumeCreatedAtDtm }}

                </div>
                <div class="d-flex gap-2">
                  <a
                    v-if="resume.resumeIsRepresentativeYn !== 'Y'"
                    href="#"
                    class="btn btn-outline btn-primary btn-sm"
                    @click.prevent="setMainResume(resume.resumeSq)"
                    >대표이력서 설정</a
                  >
                  <a
                    href="#"
                    class="btn btn-outline btn-primary btn-sm"
                    @click.prevent="editResume(resume.resumeSq)"
                    >수정하기</a
                  >
                  <a
                    href="#"
                    class="btn btn-outline btn-primary btn-sm"
                    @click.prevent="copyResume(resume.resumeSq)"
                    >복사하기</a
                  >
                </div>
              </div>
            </div>
          </li>
        </ul>
        <!-- 이력서 등록하기 버튼 -->
        <div class="d-flex justify-content-end mt-4 mb-5">
          <a
            href="#"
            class="btn btn-primary px-4 py-2"
            @click.prevent="registerResume"
            >이력서 등록하기</a
          >
        </div>
        <!-- 페이징 -->
        <div class="mt-5 py-5">
          <ul class="pagination float-end">
            <li class="page-item">
              <a class="page-link" href="#"
                ><i class="fas fa-angle-left"></i
              ></a>
            </li>
            <li class="page-item active">
              <a class="page-link" href="#">1</a>
            </li>
            <li class="page-item"><a class="page-link" href="#">2</a></li>
            <li class="page-item"><a class="page-link" href="#">3</a></li>
            <li class="page-item">
              <a class="page-link" href="#"
                ><i class="fas fa-angle-right"></i
              ></a>
            </li>
          </ul>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { api } from '@/axios.js'

const resumeList = ref([])

onMounted(async () => {
  const res = await api.$get('/mypage/resume/list')
  // res가 배열인지 확인
  console.log('이력서 목록:', res)
  resumeList.value = res // 반드시 배열이어야 함!
})

function removeResume(id) {
  resumeList.value = resumeList.value.filter((r) => r.resumeSq !== id)
}

function setMainResume(id) {
  resumeList.value.forEach((r) => (r.resumeIsRepresentativeYn = 'N'))
  const main = resumeList.value.find((r) => r.resumeSq === id)
  if (main) main.resumeIsRepresentativeYn = 'Y'
}

function editResume(/*id*/) {
  // 수정 페이지 이동
}

function copyResume(/*id*/) {
  // 복사 기능
}

function registerResume() {
  // 등록 페이지 이동
}
</script>
