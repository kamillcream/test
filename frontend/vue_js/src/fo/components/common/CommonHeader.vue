<template>
  <header
    id="header"
    class="header-effect-shrink header-spacing"
    data-plugin-options='{"stickyEnabled": true}'
  >
    <div class="header-body border-0 box-shadow-none">
      <div class="header-container container">
        <div class="header-row">
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
                      href="#"
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
                        <a class="dropdown-item" href="#">일반 게시판</a>
                      </li>
                      <li>
                        <a class="dropdown-item" href="#">Q&A 게시판</a>
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

<script>
export default {
  name: 'HeaderBeforeLogin',
}
</script>

<style scoped>
.header-spacing {
  margin-top: 2px; /* 상단 여백 추가 */
}
</style>
