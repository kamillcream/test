<template>
  <div>
    <CommonHeader />
    <div class="main"><router-view /> <CommonModalContainer /></div>
    <CommonFooter />
    <CommonAlert />
  </div>
</template>

<script>
import CommonHeader from './fo/components/common/CommonHeader.vue'
import CommonFooter from './fo/components/common/CommonFooter.vue'
import CommonModalContainer from './fo/components/common/CommonModalContainer.vue'
import CommonAlert from './fo/components/common/CommonAlert.vue'

export default {
  name: 'App',
  components: {
    CommonHeader,
    CommonFooter,
    CommonModalContainer,
    CommonAlert,
  },
}
</script>

<style>
html,
body {
  height: 100%;
  margin: 0;
}

#app {
  flex-direction: column;
  min-height: 100vh;
}

.main {
  flex: 1;
  min-height: 700px;
}
</style>
