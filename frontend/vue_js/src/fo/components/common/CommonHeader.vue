<template>
  <header
    id="header"
    class="header-effect-shrink header-spacing"
    data-plugin-options='{"stickyEnabled": true}'
  >
    <div class="header-body border-0">
      <div class="header-container container">
        <div class="header-row">
          <div class="header-left d-flex align-items-center order-0 order-lg-0">
            <a href="/" class="text-primary fs-3 text-decoration-none home">
              Freelancer<br />
              Service
            </a>
          </div>

          <!-- 네비게이션 메뉴 -->
          <div
            class="header-nav header-nav-line header-nav-top-line header-nav-top-line-with-border order-2 order-lg-1"
          >
            <div
              class="header-nav-main header-nav-main-square header-nav-main-effect-2 header-nav-main-sub-effect-1"
            >
              <nav class="collapse">
                <ul class="nav nav-pills" id="mainNav">
                  <li class="dropdown">
                    <a
                      class="dropdown-item dropdown-toggle active current-page-active"
                      href="/affiliation"
                    >
                      소속 <i class="fas fa-chevron-down"></i>
                    </a>
                  </li>
                  <li class="dropdown">
                    <a
                      class="dropdown-item dropdown-toggle current-page-active"
                      href="#"
                    >
                      프로젝트 <i class="fas fa-chevron-down"></i>
                    </a>
                  </li>
                  <li class="dropdown">
                    <a
                      class="dropdown-item dropdown-toggle current-page-active"
                      href="#"
                    >
                      커뮤니티 <i class="fas fa-chevron-down"></i>
                    </a>
                    <ul class="dropdown-menu">
                      <li>
                        <a class="dropdown-item" href="/board">일반 게시판</a>
                      </li>
                      <li>
                        <a class="dropdown-item" href="/qna">Q&A 게시판</a>
                      </li>
                    </ul>
                  </li>
                </ul>
              </nav>
            </div>
            <button
              class="btn header-btn-collapse-nav"
              data-bs-toggle="collapse"
              data-bs-target=".header-nav-main nav"
            >
              <i class="fas fa-bars"></i>
            </button>
          </div>

          <!-- 로그인 링크 -->
          <div
            class="header-nav-features header-nav-features-no-border header-nav-features-lg-show-border order-1 order-lg-2"
          >
            <div class="header-nav-feature d-inline-flex">
              <a href="/login" class="text-muted text-decoration-none"
                >로그인</a
              >
            </div>
          </div>
        </div>
      </div>
    </div>
  </header>
</template>

<script setup>
import { onMounted, onBeforeUnmount } from 'vue'

const handleScroll = () => {
  const header = document.querySelector('.header-body')
  if (window.scrollY > 50) {
    header.classList.add('shrink')
  } else {
    header.classList.remove('shrink')
  }
}

onMounted(() => {
  window.addEventListener('scroll', handleScroll)
})

onBeforeUnmount(() => {
  window.removeEventListener('scroll', handleScroll)
})
</script>

<style>
.home {
  font-weight: bold;
  display: block;
  text-align: center;
}

.header-body {
  position: fixed !important;
  top: 0;
  z-index: 999;
  width: 100%;
  background: white;
  height: 100px; /* 초기 높이 설정 */
  transition:
    height 0.3s ease,
    box-shadow 0.3s ease; /* transition을 height와 box-shadow에만 적용 */
  margin-top: 2px; /* 상단 여백 추가 */
}

.header-body.shrink {
  height: 70px; /* 스크롤 시 헤더 크기 줄어듬 */
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1); /* 스크롤 시 그림자 효과 */
}
</style>
