<template>
  <section>
    <CommonPageHeader />
    <div class="container pt-3 pb-2">
      <div class="row pt-2">
        <!-- Sidebar -->
        <div class="col-lg-3 order-2 order-lg-1 mt-4 mt-lg-0">
          <MyPageSideBar />
        </div>

        <!-- Main content (컴포넌트로 분리) -->
        <div class="col-lg-9 order-1 order-lg-2">
          <router-view />
        </div>
      </div>
    </div>
  </section>
</template>

<script setup>
import MyPageSideBar from '@/fo/components/mypage/MyPageSideBar.vue'
import CommonPageHeader from '@/fo/components/common/CommonPageHeader.vue'
</script>
