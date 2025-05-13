<template>
  <div>
    <CommonHeader />
    <CommonAlert />
    <div class="main"><router-view /> <CommonModalContainer /></div>
    <CommonFooter />
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
#app {
  flex-direction: column;
}

.main {
  flex: 1;
  min-height: 700px;
  padding-top: 100px;
}
</style>
