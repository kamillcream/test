<template>
  <div class="container py-5 mt-3">
    <div class="row">
      <div class="col">
        <h1 class="font-weight-normal text-10 mb-20"><strong>소속 스크랩 내역</strong></h1>
      </div>
    </div>
    <!-- 🔽 필터 UI 추가 영역 -->
    <div class="row align-items-center mt-3 mb-2">
      <div class="col-md-12 d-flex justify-content-end gap-2">
        <select class="form-select form-select-sm w-auto">
          <option selected>전체</option>
          <option>제목 + 내용</option>
          <option>제목</option>
          <option>내용</option>
        </select>
        <input type="text" class="form-control form-control-sm w-auto" placeholder="검색어 입력">
        <button class="btn btn-primary btn-sm">검색</button>
      </div>
    </div>
    <!-- 🔼 필터 UI 끝 -->
    <div class="row">
      <div class="col pt-2 mt-1">
        <hr class="my-4">
      </div>
    </div>
    <div class="row">
      <div class="col">
        <ul class="simple-post-list m-0 position-relative">
          <li v-for="scrap in scraps" :key="scrap.id" style="border-bottom: 1px rgb(230, 230, 230) solid;">
            <div class="post-info position-relative">
              <!-- 제목 + 회사명 + 지원상태 버튼 -->
              <div class="d-flex justify-content-between align-items-center gap-2">
                <div class="d-flex gap-2">
                  <a href="#" class="text-6 m-0">{{ scrap.company }}</a>
                </div>
                <div class="d-flex gap-2">
                  <span :class="['btn btn-sm', scrap.status === '모집 마감' ? 'btn-light' : 'btn-primary']">{{ scrap.status }}</span>
                  <a href="#" class="btn btn-outline btn-primary btn-sm" @click.prevent="removeScrap(scrap.id)">삭제</a>
                </div>
              </div>
              <!-- 직원수 -->
              <div class="d-flex justify-content-between align-items-center mt-2">
                <div class="post-meta text-4 text-end ms-auto">
                  <span class="text-dark text-uppercase font-weight-semibold">소속 직원 수</span> | {{ scrap.employeeCount }}
                </div>
              </div>
              <!-- 지원자격/개업일자 -->
              <div class="d-flex justify-content-between align-items-center mt-2">
                <div class="post-meta text-4">
                  <span class="text-dark text-uppercase font-weight-semibold">지원 자격</span> | {{ scrap.qualification }}
                </div>
                <div class="post-meta text-4">
                  <span class="text-dark text-uppercase font-weight-semibold">개업일자</span> | {{ scrap.openDate }}
                </div>
              </div>
            </div>
          </li>
        </ul>
        <!-- 페이징 -->
        <div class="mt-5 py-5">
          <ul class="pagination float-end">
            <li class="page-item"><a class="page-link" href="#"><i class="fas fa-angle-left"></i></a></li>
            <li class="page-item active"><a class="page-link" href="#">1</a></li>
            <li class="page-item"><a class="page-link" href="#">2</a></li>
            <li class="page-item"><a class="page-link" href="#">3</a></li>
            <li class="page-item"><a class="page-link" href="#"><i class="fas fa-angle-right"></i></a></li>
          </ul>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'ScrapList',
  data() {
    return {
      scraps: [
        {
          id: 1,
          company: 'EST Soft',
          status: '모집중',
          employeeCount: 50,
          qualification: '서울 / 신입 / 학력무관 / JAVA',
          openDate: '2025.04.30'
        },
        {
          id: 2,
          company: 'EST Soft',
          status: '모집중',
          employeeCount: 50,
          qualification: '서울 / 신입 / 학력무관 / JAVA',
          openDate: '2025.04.30'
        },
        {
          id: 3,
          company: 'EST Soft',
          status: '모집중',
          employeeCount: 50,
          qualification: '서울 / 신입 / 학력무관 / JAVA',
          openDate: '2025.04.30'
        },
        {
          id: 4,
          company: 'EST Soft',
          status: '모집 마감',
          employeeCount: 50,
          qualification: '서울 / 신입 / 학력무관 / JAVA',
          openDate: '2025.04.30'
        }
      ]
    }
  },
  methods: {
    removeScrap(id) {
      this.scraps = this.scraps.filter(s => s.id !== id)
    }
  }
}
</script>

<style scoped>
.simple-post-list {
  list-style: none;
  padding: 0;
}
</style> 